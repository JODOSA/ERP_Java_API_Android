package com.empresa.erp.dialogs;

import com.empresa.erp.utils.AlertaUtils;
import com.empresa.erp.utils.ComboBoxUtils;
import empresa.dao.AlmacenDAO;
import empresa.dao.ProductosDAO;
import empresa.dao.ProveedoresDAO;
import empresa.models.Almacen;
import empresa.models.LineasPed;
import empresa.models.Productos;
import empresa.models.Proveedores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class PedidoDialog {

    private Stage dialog;
    private Proveedores proveedorSeleccionado;

    public void show(Runnable onContinue) {
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Nuevo Pedido");
        dialog.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/corporate_logo_white_50_53.png"))));


        VBox mainLayout = new VBox(15);
        mainLayout.setStyle("""
            -fx-background-color: #2c3e50;
            -fx-border-color: #98cddd;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-padding: 20;
        """);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(20));

        ProveedoresDAO proveedoresDAO = new ProveedoresDAO();
        List<Proveedores> listaProveedores = proveedoresDAO.readAll();

        ComboBox<Proveedores> comboProveedores = new ComboBox<>();
        comboProveedores.setStyle("-fx-background-color: #98cddd; -fx-border-radius: 4px; -fx-background-radius: 4px;");
        comboProveedores.getItems().addAll(listaProveedores);
        comboProveedores.setPromptText("Seleccione un Proveedor");
        comboProveedores.setPrefWidth(400);
        comboProveedores.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Proveedores proveedor, boolean empty) {
                super.updateItem(proveedor, empty);
                setText(empty || proveedor == null ? null : proveedor.getIdProveedor() + " - " + proveedor.getNombreProv());
            }
        });
        comboProveedores.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Proveedores proveedor, boolean empty) {
                super.updateItem(proveedor, empty);
                setText(empty || proveedor == null ? null : proveedor.getIdProveedor() + " - " + proveedor.getNombreProv());
            }
        });

        String lblStyle = ("-fx-text-fill: white;-fx-font-weight: bold;");

        // Contenedor para mostrar los datos del proveedor seleccionado
        Label lblNombre = new Label();
        lblNombre.setStyle(lblStyle);
        Label lblNif = new Label();
        lblNif.setStyle(lblStyle);
        Label lblTelefono = new Label();
        lblTelefono.setStyle(lblStyle);

        // Campos alineados horizontalmente
        HBox filaProveedor = new HBox(20);
        filaProveedor.setAlignment(Pos.CENTER_LEFT);
        filaProveedor.getChildren().addAll(lblNif, lblNombre, lblTelefono);

        // Campos de la cabecera del pedido
        Label lblFecha = new Label("Fecha: " + java.time.LocalDate.now());
        lblFecha.setStyle(lblStyle);
        Label lblEstado = new Label("Estado: " + "Pendiente");
        lblEstado.setStyle(lblStyle);
        Label lblUsuario = new Label("Usuario: " + empresa.utils.UserSession.getUsuario().getNombreUs());
        lblUsuario.setStyle(lblStyle);

        TextField txtIVA  = new TextField("21.0");
        txtIVA.setPromptText("IVA");
        txtIVA.setMaxWidth(100);

        // Campos alineados horizontalmente
        HBox filaCabecera = new HBox(20);
        filaCabecera.setAlignment(Pos.CENTER_LEFT);
        filaCabecera.getChildren().addAll(lblFecha, lblEstado, lblUsuario);

        // Línea del IVA
        HBox fila2Cabecera = new HBox(10);
        fila2Cabecera.setAlignment(Pos.CENTER_LEFT);
        Label lblIVA = new Label("IVA (%): ");
        lblIVA.setStyle(lblStyle);
        fila2Cabecera.getChildren().addAll(lblIVA, txtIVA);

        // Contenedor final de cabecera
        VBox datosCabeceraBox = new VBox(10, filaCabecera, fila2Cabecera);
        datosCabeceraBox.setPadding(new Insets(10));
        datosCabeceraBox.setVisible(false);

        // Selector de almacén
        Label lblAlmacen = new Label("Seleccione el almacen: ");
        lblAlmacen.setStyle(lblStyle);
        ComboBox<Almacen> comboAlmacenes = new ComboBox<>();
        AlmacenDAO almacenDAO = new AlmacenDAO();
        comboAlmacenes.setStyle("-fx-background-color: #98cddd; -fx-border-radius: 4px; -fx-background-radius: 4px;");
        comboAlmacenes.getItems().addAll(almacenDAO.readAll());
        comboAlmacenes.setPromptText("Seleccione un almacen");
        comboAlmacenes.setPrefWidth(400);

        // Estilo visual del almacén
        comboAlmacenes.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Almacen almacen, boolean empty) {
                super.updateItem(almacen, empty);
                setText(empty || almacen == null ? null : almacen.getId_Almacen() + " - " + almacen.getNombreAlm());

            }
        });

        comboAlmacenes.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Almacen almacen, boolean empty) {
                super.updateItem(almacen, empty);
                setText(empty || almacen == null ? null : almacen.getNombreAlm() + " - " + almacen.getNombreAlm());
            }
        });

        // Sección de productos
        Separator sepProductos = new Separator();
        Label lblTituloProductos = new Label("Añadir productos al pedido");
        lblTituloProductos.setStyle(lblStyle);
        //lblTituloProductos.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Campos de entrada

        ComboBox<Productos> comboProductos = new ComboBox<>();
        ProductosDAO productosDAO = new ProductosDAO();
        List<Productos> listaProductos = productosDAO.readAll();
        comboProductos.setStyle("-fx-background-color: #98cddd; -fx-border-radius: 4px; -fx-background-radius: 4px;");
        comboProductos.getItems().addAll(listaProductos);

        // Habilita la busqueda inteligente e insensible a acentos y símbolos
        comboProductos.setEditable(true);
        //comboProductos.setItems(FXCollections.observableArrayList(listaProductos));
        ComboBoxUtils.enableAutoFilter(comboProductos, Productos::getNombreProd);

        comboProductos.setPromptText("Seleccione un producto");
        comboProductos.setPrefWidth(300);

        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");
        txtCantidad.setPrefWidth(100);

        // Lista y tabla de líneas temporales
        ObservableList<LineasPed> lineasTemp = FXCollections.observableArrayList();

        TableView<LineasPed> tablaLineas = new TableView<>();
        tablaLineas.setItems(lineasTemp);
        tablaLineas.setPrefHeight(200);

        // Columnas
        TableColumn<LineasPed, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getProducto().getNombreProd()
        ));

        TableColumn<LineasPed, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadProdPed"));

        TableColumn<LineasPed, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioProdPed"));

        TableColumn<LineasPed, Double> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotalPed"));

        tablaLineas.getColumns().addAll(colProducto, colCantidad, colPrecio, colSubtotal);

        // Layout horizontal para los campos de entrada
        HBox productoBox = new HBox(10, comboProductos, txtCantidad);
        productoBox.setAlignment(Pos.CENTER_LEFT);
        productoBox.setPadding(new Insets(10, 0, 10, 0));

        // Se ocultan los campos, hasta que se acceda a las líneas del pedido
        sepProductos.setVisible(false);
        lblTituloProductos.setVisible(false);
        productoBox.setVisible(false);
        tablaLineas.setVisible(false);

        String botonStyle = ("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);

        Button btnGuardar = new Button("Guardar Pedido");
        btnGuardar.setStyle(botonStyle);
        btnGuardar.setDisable(true);

        // Lógica para pulsar ENTER en campo cantidad y validar línea
        txtCantidad.setOnAction(event -> {
            Almacen almacenSeleccionado = comboAlmacenes.getValue();
            if (almacenSeleccionado == null) {
                AlertaUtils.mostrar("Debes seleccionar un almacén antes de añadir productos");
                return;
            }


            String cantidadStr = txtCantidad.getText().trim();
            int cantidad;
            try{
                cantidad = Integer.parseInt(cantidadStr);
            }catch (NumberFormatException e){
                AlertaUtils.mostrar("El ID de producto y la cantidad deben ser números válidos");
                return;
            }

            if(cantidad <= 0){
                AlertaUtils.mostrar("El cantidad debe ser mayor que 0");
                return;
            }

            Productos producto = comboProductos.getValue();

            if(producto == null){
                AlertaUtils.mostrar("Debe seleccionar un producto");
                return;
            }

            double precio = producto.getPrecioCompra();
            double subTotal = cantidad * precio;

            LineasPed nuevaLinea = new LineasPed(null, cantidad, precio, subTotal, null, producto, almacenSeleccionado);
            lineasTemp.add(nuevaLinea);
            btnGuardar.setDisable(false);

            comboProductos.getSelectionModel().clearSelection();
            txtCantidad.clear();
            comboProductos.requestFocus();


        });


        // Botones
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button btnContinuar = new Button("Continuar");
        btnContinuar.setStyle(botonStyle);
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(botonStyle);
        btnContinuar.setDisable(true);
        btnCancelar.setOnAction(e -> dialog.close());

        btnContinuar.setOnAction(e -> {

            Almacen almacenSeleccionado = comboAlmacenes.getValue();
            if(almacenSeleccionado == null){
                AlertaUtils.mostrar("Debes seleccionar un almacén para continuar");
                return;
            }

            // Mostrar la parte de productos
            sepProductos.setVisible(true);
            lblTituloProductos.setVisible(true);
            productoBox.setVisible(true);
            tablaLineas.setVisible(true);

            // Desactivar los controles superiores para evitar cambios
            comboProveedores.setDisable(true);
            comboAlmacenes.setDisable(true);
            btnContinuar.setDisable(true);

        });

        btnGuardar.setOnAction(e -> {
            if(lineasTemp.isEmpty()){
                AlertaUtils.mostrar("Debes añadir al menos un producto antes de guardar el pedido");
                return;
            }
            try {
                double iva = Double.parseDouble(txtIVA.getText().trim());
                double totalSinIVA = lineasTemp.stream()
                        .mapToDouble(LineasPed::getSubtotalPed)
                        .sum();
                double totalConIVA = totalSinIVA + (totalSinIVA * iva / 100);

                // Crear cabecera
                empresa.models.CabPedidos cabecera = new empresa.models.CabPedidos(
                        java.time.LocalDate.now().toString(),
                        "Pendiente",
                        iva,
                        0.0,
                        proveedorSeleccionado,
                        empresa.utils.UserSession.getUsuario()
                );

                empresa.dao.CabPedidosDAO cabPedidosDAO = new empresa.dao.CabPedidosDAO();
                cabPedidosDAO.create(cabecera); // Se guarda y se le asigna el numero de pedido (numPed)

                // Guardar líneas
                empresa.dao.LineasPedDAO lineasPedDAO = new empresa.dao.LineasPedDAO();
                for(LineasPed linea : lineasTemp){
                    linea.setCabPedidos(cabecera); // Asocia la línea con la cabecera recien creada
                    lineasPedDAO.create(linea);
                }

                // Actualizar total del pedido
                cabecera.setTotalPed(totalConIVA);
                cabPedidosDAO.update(cabecera);

                AlertaUtils.mostrar("Pedido guardado exitosamente, con el número: " + cabecera.getNumPed());
                dialog.close();
                if(onContinue != null){
                    onContinue.run();
                }
            }catch (NumberFormatException ex){
                AlertaUtils.mostrar("El valor del IVA no es válido");
            }catch (Exception ex){
                ex.printStackTrace();
                AlertaUtils.mostrar("Error al guardar el pedido");
            }

        });

        buttonBox.getChildren().addAll(btnContinuar, btnCancelar, btnGuardar);

        comboProveedores.setOnAction(e -> {
            proveedorSeleccionado = comboProveedores.getValue();
            if (proveedorSeleccionado != null) {
                lblNombre.setText("Nombre: " + proveedorSeleccionado.getNombreProv());
                lblNif.setText("NIF: " + proveedorSeleccionado.getNifProv());
                lblTelefono.setText("Telefono: " + proveedorSeleccionado.getTelefonoProv());
                filaProveedor.setVisible(true);
                datosCabeceraBox.setVisible(true);
                btnContinuar.setDisable(false);
            }
        });

        Label lblTituloCabecera = new Label("Datos del pedido");
        lblTituloCabecera.setStyle(lblStyle);
        //lblTituloCabecera.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label lblProveedor = new Label("Seleccione un Proveedor:");
        lblProveedor.setStyle(lblStyle);
        mainLayout.getChildren().addAll(lblProveedor,
                comboProveedores,
                filaProveedor,
                new Separator(),
                lblTituloCabecera,
                datosCabeceraBox,
                new Separator(),
                lblAlmacen,
                comboAlmacenes,
                buttonBox);

        mainLayout.getChildren().addAll(
                sepProductos,
                lblTituloProductos,
                productoBox,
                tablaLineas);

        Scene scene = new Scene(mainLayout);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
