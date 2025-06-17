package com.empresa.erp.dialogs;

import com.empresa.erp.service.StockUpdaterHandler;
import empresa.dao.*;
import empresa.models.*;
import com.empresa.erp.utils.*;
import empresa.utils.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class FacturaDialog {

    private Cliente selectedClient;
    private Almacen selectedWarehouse;
    private ObservableList<LineasFact> facturaLines = FXCollections.observableArrayList();

    public void show(Runnable onFacturaGuardada){
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Crear Factura");
        dialog.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/corporate_logo_white_50_53.png"))));


        VBox root = new VBox(10);
        root.setStyle("""
            -fx-background-color: #2c3e50;
            -fx-border-color: #98cddd;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-padding: 20;
        """);
        root.setPadding(new Insets(10));

        // Sección de cabecera
        ComboBox<Cliente> clientComboBox = new ComboBox<>();
        ClienteDAO clienteDAO = new ClienteDAO();
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.readAll());
        clientComboBox.setItems(clientes);
        clientComboBox.setStyle("-fx-background-color: #98cddd; -fx-border-radius: 4px; -fx-background-radius: 4px;");
        clientComboBox.setPromptText("Selecciona un cliente");

        // Aplicamos búsqueda automática por nombre
        ComboBoxUtils.enableAutoFilter(clientComboBox, c -> c.getIdCliente() + " - " + c.getNombreCli());

        Button validateClientBtn = new Button("Validar Cliente");
        validateClientBtn.setStyle("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);

        Label lblCliente = new Label("Cliente");
        lblCliente.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");

        HBox clientBox = new HBox(10, lblCliente, clientComboBox, validateClientBtn);
        
        //HBox clientBox = new HBox(10, new Label("Cliente:"), clientComboBox, validateClientBtn);

        // Sección de almacén
        ComboBox<Almacen> warehouseComboBox = new ComboBox<>();
        warehouseComboBox.setStyle("-fx-background-color: #98cddd; -fx-border-radius: 4px; -fx-background-radius: 4px;");
        warehouseComboBox.setPromptText("Selecciona Almacén");
        warehouseComboBox.setDisable(true);

        // Sección de productos
        ComboBox<Productos> productComboBox = new ComboBox<>();
        productComboBox.setStyle("-fx-background-color: #98cddd; -fx-border-radius: 4px; -fx-background-radius: 4px;");
        productComboBox.setPromptText("Producto");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Cantidad");
        quantityField.setDisable(true);

        //TableView<LineasFact> productTable = TableLoader.crearTablaGenerica(LineasFact.class);
        TableView<LineasFact> productTable = new TableView<>();
        productTable.setItems(facturaLines);

        TableColumn<LineasFact, String> productoCol = new TableColumn<>("Producto");
        productoCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getProducto().getNombreProd()));

        TableColumn<LineasFact, Integer> cantidadCol = new TableColumn<>("Cantidad");
        cantidadCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(
                cellData.getValue().getCantidadProdFact()));

        TableColumn<LineasFact, Double> subtotalCol = new TableColumn<>("Subtotal");
        subtotalCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(
                cellData.getValue().getSubtotalFact()));

        subtotalCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double subtotal, boolean empty) {
                super.updateItem(subtotal, empty);
                if (empty || subtotal == null) {
                    setText(null);
                }else{
                    setText(String.format("%.2f", subtotal));
                }
            }
        });

        productTable.getColumns().addAll(productoCol, cantidadCol, subtotalCol);

        Button confirmBtn = new Button("Aceptar Factura");
        confirmBtn.setStyle("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);
        confirmBtn.setDisable(true);

        Button cancelBtn = new Button("Cancelar Factura");
        cancelBtn.setStyle("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);
        cancelBtn.setDisable(false);

        validateClientBtn.setOnAction(event -> {
            selectedClient = clientComboBox.getValue();
            if (selectedClient != null) {
                //AlertaUtils.mostrar("Cliente válido: " + selectedClient.getNombreCli());
                warehouseComboBox.setItems(FXCollections.observableArrayList(new AlmacenDAO().readAll()));
                warehouseComboBox.setDisable(false);
            }else{
                AlertaUtils.mostrar("Debes seleccionar un cliente");
            }
        });

        warehouseComboBox.setOnAction(e -> {
            selectedWarehouse = warehouseComboBox.getValue();
            if (selectedWarehouse != null) {
                ProductosAlmacenDAO dao = new ProductosAlmacenDAO();
                ProductosDAO productosDAO = new ProductosDAO();
                ObservableList<Productos> productosConStock = FXCollections.observableArrayList();
                for (Productos prod : productosDAO.readAll()) {
                    if (dao.obtenerStock(prod, selectedWarehouse) > 0) {
                        productosConStock.add(prod);
                    }
                }
                //productComboBox.setItems(productosConStock);
                productComboBox.setItems(productosConStock);
                ComboBoxUtils.enableAutoFilter(productComboBox, p -> {
                    int stock = new ProductosAlmacenDAO().obtenerStock(p, selectedWarehouse);
                    return p.getNombreProd() + " (Stock: " + stock + ")";
                });

                quantityField.setDisable(false);
                confirmBtn.setDisable(false);
            }
        });

        quantityField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                Productos selectedProduct = productComboBox.getValue();
                int cantidad;
                try{
                    cantidad = Integer.parseInt(quantityField.getText());
                }catch(NumberFormatException ex){
                    AlertaUtils.mostrar("Cantidad invalida");
                    return;
                }

                if (selectedProduct != null && cantidad > 0) {
                    ProductosAlmacenDAO dao = new ProductosAlmacenDAO();
                    int stock = dao.obtenerStock(selectedProduct, selectedWarehouse);
                    if (stock >= cantidad) {
                        // <-- CAMBIO: usamos constructor vacío y seteamos los campos manualmente
                        LineasFact linea = new LineasFact();
                        linea.setProducto(selectedProduct);
                        linea.setAlmacen(selectedWarehouse);
                        linea.setPrecioProdFact(selectedProduct.getPrecioVenta().doubleValue());
                        linea.setCantidadProdFact(cantidad);
                        facturaLines.add(linea);
                        quantityField.clear();
                        productComboBox.setValue(null);
                    } else {
                        AlertaUtils.mostrar("Stock insuficiente. Disponible: " + stock);
                    }
                }
            }
        });
        cancelBtn.setOnAction(e -> dialog.close());
        confirmBtn.setOnAction(e -> {
            CabFacturasDAO cabDAO = new CabFacturasDAO();
            LineasFactDAO lineDAO = new LineasFactDAO();

            CabFacturas factura = new CabFacturas(
                    LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                    "Emitida",
                    21.0,
                    0.0,
                    selectedClient,
                    UserSession.getUsuario()
            );
            cabDAO.create(factura);

            double total = 0.0;
            for (LineasFact linea : facturaLines) {
                linea.setFactura(factura);
                lineDAO.create(linea);
                total += linea.getSubtotalFact();
            }

            double totalConIva = total + total * factura.getIvaFact() / 100;
            factura.setTotalFact(totalConIva);
            cabDAO.update(factura);

            dialog.close();
            if(onFacturaGuardada != null){
                onFacturaGuardada.run();
            }
        });

        root.getChildren().addAll(
                clientBox,
                warehouseComboBox,
                new HBox(10, productComboBox, quantityField),
                productTable,
                new HBox(10, confirmBtn, cancelBtn)
        );

        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    }
