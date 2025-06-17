package com.empresa.erp.dialogs;

import empresa.dao.LineasPedDAO;
import empresa.models.CabPedidos;
import empresa.models.LineasPed;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.List;
import java.util.Objects;

public class ConsultaPedidoDialog {

    public void show(CabPedidos pedido) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Consulta de Pedidos");
        dialog.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/corporate_logo_white_50_53.png"))));


        VBox layout = new VBox(15);
        layout.setStyle("""
            -fx-background-color: #2c3e50;
            -fx-border-color: #98cddd;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-padding: 20;
        """);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);

        String baseStyle = ("-fx-text-fill: white;-fx-font-weight: bold;");

        Label lblTitulo = new Label("Detalle del pedido Nº " + pedido.getNumPed());
        //lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        lblTitulo.setStyle(baseStyle);

        // Datos de la cabecera
        Label lblProveedor = new Label("Proveedor: " + pedido.getProveedor().getNombreProv());
        Label lblFecha = new Label("Fecha: " + pedido.getFechaPed());
        Label lblEstado = new Label("Estado: " + pedido.getEstadoPed());
        Label lblIVA = new Label("IVA: " + pedido.getIvaPed() + "%");
        Label lblTotal = new Label("Total: " + pedido.getTotalPed() + "€");

        lblProveedor.setStyle(baseStyle);
        lblFecha.setStyle(baseStyle);
        lblEstado.setStyle(baseStyle);
        lblIVA.setStyle(baseStyle);
        lblTotal.setStyle(baseStyle);

        VBox cabeceraBox = new VBox(5, lblProveedor, lblFecha, lblEstado, lblIVA, lblTotal);
        cabeceraBox.setAlignment(Pos.CENTER_LEFT);

        // Tabla de líneas
        TableView<LineasPed> tablaLineas = new TableView<>();


        TableColumn<LineasPed, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProducto().getNombreProd()));

        TableColumn<LineasPed, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("cantidadProdPed"));

        TableColumn<LineasPed, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("precioProdPed"));

        TableColumn<LineasPed, Double> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("subtotalPed"));

        tablaLineas.getColumns().addAll(colProducto, colCantidad, colPrecio, colSubtotal);

        // Cargar líneas desde la BD
        List<LineasPed> lineas = new LineasPedDAO().readAll().stream()
                .filter(l -> l.getCabPedidos().getNumPed().equals(pedido.getNumPed()))
                .toList();

        tablaLineas.setItems(FXCollections.observableList(lineas));

        // Botón cerrar
        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);
        btnCerrar.setOnAction(e -> dialog.close());

        HBox cerrarBox = new HBox(btnCerrar);
        cerrarBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(lblTitulo, cabeceraBox, tablaLineas, cerrarBox);

        Scene scene = new Scene(layout, 700, 500);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
