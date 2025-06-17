package com.empresa.erp.dialogs;

import empresa.dao.*;
import empresa.models.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FacturaEditDialog {

    private Stage dialog;
    private ObservableList<LineasFact> lineasFactura;
    private List<LineasFact> lineasBorradas = new ArrayList<>();
    private TableView<LineasFact> tablaLineas;

    public void show(CabFacturas factura, TableView<?> tableView) {

        String lblStyle = ("-fx-text-fill: white;-fx-font-weight: bold;");

        Label lblNumFactura = new Label("Número de Factura: " + factura.getNumFact()); // <-- ID no editable
        lblNumFactura.setStyle(lblStyle);

        ComboBox<Cliente> comboClientes = new ComboBox<>();
        comboClientes.getItems().addAll(new ClienteDAO().readAll());
        comboClientes.setValue(factura.getCliente());
        comboClientes.setPrefWidth(300);
        comboClientes.setPromptText("Seleccione un cliente");
        comboClientes.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Cliente cli, boolean empty) {
                super.updateItem(cli, empty);
                setText(empty || cli == null ? null : cli.getNombreCli());
            }
        });
        comboClientes.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Cliente cli, boolean empty) {
                super.updateItem(cli, empty);
                setText(empty || cli == null ? null : cli.getNombreCli());
            }
        });

        TextField txtFecha = new TextField(factura.getFechaFact());
        TextField txtIVA = new TextField(String.valueOf(factura.getIvaFact()));
        TextField txtEstado = new TextField(factura.getEstadoFact());
        txtEstado.setDisable(true);

        Label lblCliente = new Label("Cliente");
        lblCliente.setStyle(lblStyle);
        Label lblFecha = new Label("Fecha");
        lblFecha.setStyle(lblStyle);
        Label lblIVA = new Label("IVA");
        lblIVA.setStyle(lblStyle);
        Label lblEstado = new Label("Estado");
        lblEstado.setStyle(lblStyle);

        HBox cabeceraBox1 = new HBox(15, lblNumFactura, lblCliente, comboClientes);
        HBox cabeceraBox2 = new HBox(15, lblFecha, txtFecha, lblIVA, txtIVA, lblEstado, txtEstado);
        cabeceraBox1.setAlignment(Pos.CENTER_LEFT);
        cabeceraBox2.setAlignment(Pos.CENTER_LEFT);

        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Editar Factura");
        dialog.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/corporate_logo_white_50_53.png"))));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("""
            -fx-background-color: #2c3e50;
            -fx-border-color: #98cddd;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-padding: 20;
        """);
        layout.setAlignment(Pos.TOP_CENTER);

        tablaLineas = new TableView<>();
        tablaLineas.setEditable(true);
        LineasFactDAO dao = new LineasFactDAO();
        List<LineasFact> lineas = dao.readAll().stream()
                .filter(l -> l.getFactura().getNumFact().equals(factura.getNumFact()))
                .toList();
        lineasFactura = FXCollections.observableArrayList(lineas);
        tablaLineas.setItems(lineasFactura);

        // ComboBox productos
        ComboBox<Productos> comboProductos = new ComboBox<>();
        comboProductos.getItems().addAll(new ProductosDAO().readAll());
        comboProductos.setPrefWidth(250);
        comboProductos.setStyle("-fx-background-color: #98cddd; -fx-border-radius: 4px; -fx-background-radius: 4px;");
        comboProductos.setPromptText("Producto");
        comboProductos.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Productos prod, boolean empty) {
                super.updateItem(prod, empty);
                setText(empty || prod == null ? null : prod.getNombreProd());
            }
        });
        comboProductos.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Productos prod, boolean empty) {
                super.updateItem(prod, empty);
                setText(empty || prod == null ? null : prod.getNombreProd());
            }
        });

        // ComboBox almacén
        ComboBox<Almacen> comboAlmacen = new ComboBox<>();
        comboAlmacen.getItems().addAll(new AlmacenDAO().readAll());
        comboAlmacen.setPrefWidth(200);
        comboAlmacen.setStyle("-fx-background-color: #98cddd; -fx-border-radius: 4px; -fx-background-radius: 4px;");
        comboAlmacen.setPromptText("Almacén");
        comboAlmacen.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Almacen alm, boolean empty) {
                super.updateItem(alm, empty);
                setText(empty || alm == null ? null : alm.getNombreAlm());
            }
        });
        comboAlmacen.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Almacen alm, boolean empty) {
                super.updateItem(alm, empty);
                setText(empty || alm == null ? null : alm.getNombreAlm());
            }
        });

        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");
        txtCantidad.setPrefWidth(80);

        String buttonStyle = ("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);

        Button btnAgregarLinea = new Button("Añadir Producto");
        btnAgregarLinea.setStyle(buttonStyle);
        btnAgregarLinea.setOnAction(e -> {
            Productos prod = comboProductos.getValue();
            Almacen alm = comboAlmacen.getValue();
            try {
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                if (prod == null || alm == null || cantidad <= 0) {
                    com.empresa.erp.utils.AlertaUtils.mostrar("Completa todos los campos correctamente");
                    return;
                }
                LineasFact nueva = new LineasFact();
                nueva.setFactura(factura);
                nueva.setProducto(prod);
                nueva.setAlmacen(alm);
                nueva.setPrecioProdFact(prod.getPrecioVenta().doubleValue());
                nueva.setCantidadProdFact(cantidad);
                nueva.setSubtotalFact(cantidad * prod.getPrecioVenta().doubleValue());

                lineasFactura.add(nueva);
                tablaLineas.refresh();
                comboProductos.getSelectionModel().clearSelection();
                comboAlmacen.getSelectionModel().clearSelection();
                txtCantidad.clear();

            } catch (Exception ex) {
                com.empresa.erp.utils.AlertaUtils.mostrar("Error al añadir línea: " + ex.getMessage());
            }
        });

        HBox formLineas = new HBox(10, comboProductos, comboAlmacen, txtCantidad, btnAgregarLinea);

        configurarColumnas();

        Button btnGuardar = new Button("Guardar Cambios");
        btnGuardar.setStyle(buttonStyle);
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(buttonStyle);

        btnGuardar.setOnAction(e -> {
            guardarCambios(factura, comboClientes, txtFecha, txtIVA);
            tableView.refresh();
            dialog.close();
        });

        btnCancelar.setOnAction(e -> dialog.close());

        layout.getChildren().addAll(cabeceraBox1, cabeceraBox2, formLineas, tablaLineas, btnGuardar, btnCancelar);

        Scene scene = new Scene(layout, 800, 600);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void configurarColumnas() {
        TableColumn<LineasFact, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProducto().getNombreProd()));
        colProducto.setEditable(false);

        TableColumn<LineasFact, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("cantidadProdFact"));
        colCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.IntegerStringConverter()));
        colCantidad.setOnEditCommit(event -> {
            LineasFact linea = event.getRowValue();
            linea.setCantidadProdFact(event.getNewValue());
            recalcularSubtotal(linea);
            tablaLineas.refresh();
        });

        TableColumn<LineasFact, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("precioProdFact"));
        colPrecio.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPrecio.setOnEditCommit(event -> {
            LineasFact linea = event.getRowValue();
            linea.setPrecioProdFact(event.getNewValue());
            recalcularSubtotal(linea);
            tablaLineas.refresh();
        });

        TableColumn<LineasFact, Double> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("subtotalFact"));

        TableColumn<LineasFact, Void> colBorrar = new TableColumn<>("Acciones");
        colBorrar.setCellFactory(param -> new TableCell<>() {

            // Base para los botones de las líneas
                String botonStyle = ("""
                    -fx-font-size: 13px;
                    -fx-background-radius: 10;
                    -fx-padding: 4 12;
                    -fx-cursor: hand;
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 4, 0, 0, 9);
                    -fx-background-color: transparent;
                    -fx-text-fill: #2c3e50;
                """);

            // Botón Borrar con Icono
            private final Button btnBorrar;
            {
                ImageView borrarIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/delete_24dp.png"))));
                borrarIcon.setFitHeight(20);
                borrarIcon.setFitWidth(20);
                btnBorrar = new Button("", borrarIcon);

                btnBorrar.setStyle(botonStyle);
                btnBorrar.setOnAction(e -> {
                    LineasFact linea = getTableView().getItems().get(getIndex());
                    if(linea.getIdLineaFact()!=null) {
                        lineasBorradas.add(linea);
                    }
                    lineasFactura.remove(linea);
                    tablaLineas.refresh();
                });
            }

//

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnBorrar);
            }
        });

        tablaLineas.getColumns().clear();
        tablaLineas.getColumns().addAll(colProducto, colCantidad, colPrecio, colSubtotal, colBorrar);
    }

    private void recalcularSubtotal(LineasFact linea) {
        double nuevoSubtotal = linea.getCantidadProdFact() * linea.getPrecioProdFact();
        linea.setSubtotalFact(nuevoSubtotal);
    }

    private void guardarCambios(CabFacturas factura, ComboBox<Cliente> comboClientes, TextField txtFecha, TextField txtIVA) {
        try {
            LineasFactDAO dao = new LineasFactDAO();
            for(LineasFact eliminada : lineasBorradas){
                dao.deleteById(eliminada.getIdLineaFact());
            }
            for (LineasFact linea : tablaLineas.getItems()) {
                recalcularSubtotal(linea);
                if (linea.getIdLineaFact() == null) {
                    dao.create(linea);
                } else {
                    dao.update(linea);
                }
            }

            double nuevoTotal = tablaLineas.getItems().stream()
                    .mapToDouble(LineasFact::getSubtotalFact)
                    .sum();

            factura.setCliente(comboClientes.getValue());
            factura.setFechaFact(txtFecha.getText().trim());
            factura.setIvaFact(Double.parseDouble(txtIVA.getText().trim()));

            double totalConIVA = (nuevoTotal * factura.getIvaFact()) / 100 + nuevoTotal;
            factura.setTotalFact(Math.round(totalConIVA * 100) / 100.0);

            CabFacturasDAO daoCab = new CabFacturasDAO();
            daoCab.update(factura);

            com.empresa.erp.utils.AlertaUtils.mostrar("Factura actualizada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            com.empresa.erp.utils.AlertaUtils.mostrar("Error al guardar los cambios: " + e.getMessage());
        }
    }
}
