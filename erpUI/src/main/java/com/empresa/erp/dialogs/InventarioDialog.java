package com.empresa.erp.dialogs;

import com.empresa.erp.utils.AlertaUtils;
import empresa.dao.GenericDAO;
import empresa.models.Almacen;
import empresa.models.ProductosAlmacen;
import empresa.services.ExportInventarioPDF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventarioDialog {

    private Stage dialog;
    private Almacen almacenSeleccionado;

    public void show(Runnable onClose){
        // Crear ventana
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Inventario");
        dialog.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/corporate_logo_white_50_53.png"))));
        dialog.setWidth(600);
        dialog.setHeight(500);
        
        // Layout principal
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("""
            -fx-background-color: #2c3e50;
            -fx-border-color: #98cddd;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-padding: 20;
        """);

        // ComboBox para seleccionar almacén
        Label lblAlmacen = new Label("Seleccione un almacén:");
        lblAlmacen.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");
        ComboBox<Almacen> comboAlmacenes = new ComboBox<>();
        comboAlmacenes.setPrefWidth(300);

        // Cargar almacenes usando GenericDAO
        GenericDAO<Almacen> almacenDAO = new GenericDAO<>(Almacen.class) {};
        List<Almacen> almacenes = almacenDAO.readAll();
        comboAlmacenes.getItems().addAll(almacenes);

        // Tabla para mostrar productos y stock
        TableView<ProductosAlmacen> tableStock = new TableView<>();
        TableColumn<ProductosAlmacen, Long> colId = new TableColumn<>("ID Producto");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleLongProperty(
                data.getValue().getProducto().getId_Prod()).asObject());
        TableColumn<ProductosAlmacen, String> colNombre = new TableColumn<>("Nombre Producto");
        colNombre.setPrefWidth(300);
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getProducto().getNombreProd()));

        TableColumn<ProductosAlmacen, Integer> colStock = new TableColumn<>("Stock Actual");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableStock.setEditable(true);
        tableStock.getColumns().addAll(colId, colNombre, colStock);
        tableStock.setPrefHeight(300);

        // Columna editable para introducir el recuento
        TableColumn<ProductosAlmacen, Integer> colCantidadContada = new TableColumn<>("Recuento");
//        colCantidadContada.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCantidadContada.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));
        colCantidadContada.setOnEditCommit(event -> {
            ProductosAlmacen pa = event.getRowValue();
            pa.setStock(event.getNewValue());
        });

        tableStock.getColumns().add(colCantidadContada);

        // Acción cuando se seleccione un almacén
        comboAlmacenes.setOnAction(e -> {
            almacenSeleccionado = comboAlmacenes.getValue();
            if(almacenSeleccionado != null){
                cargarStock(almacenSeleccionado, tableStock);
            }

        });

        mainLayout.getChildren().addAll(lblAlmacen, comboAlmacenes, tableStock);

        Button btnImprimir = new Button("Imprimir");
        btnImprimir.setStyle("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);
        btnImprimir.setOnAction(ev -> {     
            try{
                if(almacenSeleccionado == null){
                    AlertaUtils.mostrar("Debe seleccionar un almacén antes de imprimir");
                    return;
                }

                String nombreAlmacen = almacenSeleccionado.getNombreAlm();

                List<ProductosAlmacen> productosAlmacenList = new ArrayList<>(tableStock.getItems());
                ExportInventarioPDF.exportInventario(productosAlmacenList, nombreAlmacen);

            }catch (Exception ex){
                ex.printStackTrace();
                AlertaUtils.mostrar("Error al generar el PDF del Inventario");
            }
        });

        // Botón para actualizar el stock en la BD
        Button btnActualizar = new Button("Actualizar Stock");
        btnActualizar.setStyle("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);
        btnActualizar.setOnAction(e -> {
            // Recorremos cada fila de la tabla
            for(ProductosAlmacen pa : tableStock.getItems()){
                try{
                    // Actualizamos el stock en al BD
                    GenericDAO<ProductosAlmacen> dao = new GenericDAO<>(ProductosAlmacen.class) {};
                    dao.update(pa);
                }catch (Exception ex){
                    ex.printStackTrace();
                    AlertaUtils.mostrar("Error al actualizar Stock del producto: " + pa.getProducto().getNombreProd());
                    return;
                }
            }
            AlertaUtils.mostrar("Stock actualizado correctamente");
        });


        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);
        btnCerrar.setOnAction(e -> {
            dialog.close();
            if(onClose != null){
                onClose.run();
            }
        });

        HBox buttonsBox = new HBox(15);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(btnImprimir, btnActualizar, btnCerrar);

        mainLayout.getChildren().add(buttonsBox);

        Scene scene = new Scene(mainLayout);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    // Método para cargar productos y stock de un Almacén
    private void cargarStock(Almacen almacen, TableView<ProductosAlmacen> table) {
        GenericDAO<ProductosAlmacen> dao = new GenericDAO<>(ProductosAlmacen.class) {};
        List<ProductosAlmacen> stockList = dao.readAll().stream()
                .filter(pa -> pa.getId().getIdAlmacen() == almacen.getId_Almacen())
                .toList();
        ObservableList<ProductosAlmacen> observableStock = FXCollections.observableArrayList(stockList);
        table.setItems(observableStock);
    }
}
