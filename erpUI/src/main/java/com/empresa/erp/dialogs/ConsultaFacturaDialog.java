package com.empresa.erp.dialogs;

import empresa.dao.LineasFactDAO;
import empresa.models.CabFacturas;
import empresa.models.LineasFact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class ConsultaFacturaDialog {

    public void show(CabFacturas factura) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Consulta de Factura");
        dialog.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/corporate_logo_white_50_53.png"))));

        VBox layout = new VBox(10);
        layout.setStyle("""
            -fx-background-color: #2c3e50;
            -fx-border-color: #98cddd;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-padding: 20;
        """);
        layout.setPadding(new Insets(20));

        String baseStyle = ("-fx-text-fill: white;-fx-font-weight: bold;");

        Label lblNum = new Label("Factura Nº: " + factura.getNumFact());
        Label lblCliente = new Label("Cliente: " + factura.getCliente().getNombreCli());
        Label lblFecha = new Label("Fecha: " + factura.getFechaFact());
        Label lblIVA = new Label("IVA: " + factura.getIvaFact() + "%");
        Label lblTotal = new Label("Total: " + factura.getTotalFact() + "€");
        Label lblEstado = new Label("Estado: " + factura.getEstadoFact());

        lblNum.setStyle(baseStyle);
        lblCliente.setStyle(baseStyle);
        lblFecha.setStyle(baseStyle);
        lblIVA.setStyle(baseStyle);
        lblTotal.setStyle(baseStyle);
        lblEstado.setStyle(baseStyle);

        VBox cabeceraBox = new VBox(5, lblNum, lblCliente, lblFecha, lblEstado, lblIVA, lblTotal);
        cabeceraBox.setPadding(new Insets(10, 0, 10, 0));


        TableView<LineasFact> tablaLineas = new TableView<>();
        tablaLineas.setEditable(false);

        TableColumn<LineasFact, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getProducto().getNombreProd()));

        TableColumn<LineasFact, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getCantidadProdFact()));

        TableColumn<LineasFact, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPrecioProdFact()));

        TableColumn<LineasFact, Double> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getSubtotalFact()));

        tablaLineas.getColumns().addAll(colProducto, colCantidad, colPrecio, colSubtotal);

        LineasFactDAO dao = new LineasFactDAO();
        List<LineasFact> lineas = dao.readAll().stream()
                .filter(l -> l.getFactura().getNumFact().equals(factura.getNumFact()))
                .toList();
        ObservableList<LineasFact> datos = FXCollections.observableArrayList(lineas);
        tablaLineas.setItems(datos);

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("""
            -fx-background-color: #98cddd;
            -fx-text-fill: black;
            -fx-font-size: 14px;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
        """);
        btnCerrar.setOnAction(e -> dialog.close());

        layout.getChildren().addAll(cabeceraBox, tablaLineas, btnCerrar);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 800, 500);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
