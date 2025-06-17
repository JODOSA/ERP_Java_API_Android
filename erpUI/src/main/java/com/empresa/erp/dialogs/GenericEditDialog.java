package com.empresa.erp.dialogs;

import com.empresa.erp.utils.FriendlyNamesHelper;
import com.empresa.erp.utils.ReflectionUtils;
import com.empresa.erp.utils.TableLoader;
import empresa.dao.GenericDAO;
import empresa.dao.ProveedoresDAO;
import empresa.models.Proveedores;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;

import static com.empresa.erp.utils.AlertaUtils.agregarIcono;

public class GenericEditDialog<T> {

    public void show(T item, Class<T> clazz, boolean isNew, Runnable onSuccess){
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(isNew ? "Crear registro" : "Editar registro");
        dialog.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/corporate_logo_white_50_53.png"))));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("""
            -fx-background-color: #2c3e50;
            -fx-border-color: #98cddd;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-padding: 20;
        """);


        Map<String, TextField> fieldMap = new HashMap<>();
        Map<String, ComboBox<?>> comboMap = new HashMap<>();

        int row = 0;
        List<String> propertyOrder = TableLoader.getPropertyOrder(clazz);
        for(String propName : propertyOrder){
            try{
                Method getter = clazz.getMethod("get" + propName);
                Object value = getter.invoke(item);
                String frienlyName = FriendlyNamesHelper
                        .getFriendlyFieldNames(clazz)
                        .getOrDefault(propName, propName);

                // Etiquetas generadas dinámicamente
                Label label = new Label(frienlyName + ":");
                label.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");

                // Mostrar ComboBox solo si es la propiedad ProveedorProd de Productos
                if (clazz.getSimpleName().equals("Productos") && propName.equals("ProveedorProd")) {
                    ComboBox<String> comboBox = new ComboBox<>();
                    comboBox.setPrefWidth(250);

                    List<Proveedores> proveedores = new ProveedoresDAO().readAll();
                    for (Proveedores p : proveedores) {
                        comboBox.getItems().add(p.getNombreProv());
                    }

                    comboBox.setValue(value != null ? value.toString() : "");
                    grid.add(label, 0, row);
                    grid.add(comboBox, 1, row);
                    comboMap.put(propName, comboBox);
                } else {
                    TextField textField = new TextField(value != null ? value.toString() : "");
                    if (propName.toLowerCase().startsWith("id")) {
                        textField.setDisable(true);
                    }
                    grid.add(label, 0, row);
                    grid.add(textField, 1, row);
                    fieldMap.put(propName, textField);
                }
                row++;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // Botón Guardar
        Button btnGuardar = new Button("Guardar cambios");
        btnGuardar.setStyle("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);

        btnGuardar.setOnAction(e -> {
            if(confirmar()){
                try{
                    for(String key : propertyOrder){
                        Method setter = ReflectionUtils.getSetter(clazz, key);
                        if(setter == null) continue;

                        Object value;
                        if(comboMap.containsKey(key)){
                            value = comboMap.get(key).getValue();
                        } else if(fieldMap.containsKey(key) && !fieldMap.get(key).isDisabled()) {
                            String text = fieldMap.get(key).getText();
                            Class<?> paramType = setter.getParameterTypes()[0];
                            value = convert(text, paramType);
                            if(key.equalsIgnoreCase("Password") && value instanceof String){
                                value = empresa.utils.PasswordHelper.hashPassword((String)value);
                            }
                        } else {
                            continue;
                        }
                        setter.invoke(item, value);
                    }
                    GenericDAO<T> dao = new GenericDAO<>(clazz) {};
                    if(isNew){
                        dao.create(item);
                    }else{
                        dao.update(item);
                    }
                    dialog.close();
                    onSuccess.run();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        // Botón Cancelar
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);
        btnCancelar.setOnAction(e -> dialog.close());
        HBox buttonBox = new HBox(10, btnGuardar, btnCancelar);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        grid.add(buttonBox, 1, row);

        Scene scene = new Scene(grid);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private boolean confirmar(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro/a que desea guardar los cambios?");
        alert.setContentText("Esta acción actualizará el registro en la base de datos");

        agregarIcono(alert);

        return alert.showAndWait().filter(button -> button == ButtonType.OK).isPresent();
    }

    private Object convert(String text, Class<?> targetType){
        if(targetType == Long.class || targetType == long.class){
            return Long.parseLong(text);
        } else if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(text);
        } else if (targetType == Double.class || targetType == double.class) {
            return Double.parseDouble(text);
        }else{
            return text;
        }
    }

    public void create(Class<T> clazz, Runnable onSuccess){
        try{
            T newInstance = clazz.getDeclaredConstructor().newInstance();
            show(newInstance, clazz, true, onSuccess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
