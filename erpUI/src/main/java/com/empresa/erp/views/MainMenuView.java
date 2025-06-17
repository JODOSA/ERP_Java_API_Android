package com.empresa.erp.views;

import com.empresa.erp.dialogs.*;
import com.empresa.erp.utils.FriendlyNamesHelper;
import com.empresa.erp.utils.ReflectionUtils;
import com.empresa.erp.utils.*;
import com.empresa.erp.utils.AlertaUtils;
import empresa.utils.UserSession;
import empresa.dao.*;
import empresa.models.Almacen;
import empresa.models.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.empresa.erp.utils.AlertaUtils.agregarIcono;

public class MainMenuView {

    private final Stage stage;
    private final TableView<Object> tableView;
    private final Label tableTitle;
    private BorderPane root;
    private HBox topBar;
    private ObservableList<Object> originalItems = FXCollections.observableArrayList();
    private ComboBox<String> campoComboBox = new ComboBox<>();
    private String currentTableKey;


    public MainMenuView(Stage stage){
        this.stage = stage;
        stage.setTitle("ERP - Menú Principal");

        campoComboBox.setStyle("-fx-background-color: #98cddd; -fx-border-radius: 4px; -fx-background-radius: 4px;");

        // Logo en la esquina de la ventana
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/corporate_logo_white.png"))));

        // Centrar la ventana en la pantalla
        Platform.runLater(stage::centerOnScreen);

        // Barra lateral con botones
        VBox sideMenu = new VBox(10);
        sideMenu.setPadding(new Insets(20));
        sideMenu.setStyle("-fx-background-color: #2c3e50;");
        sideMenu.setPrefWidth(200);

        Button btnClientes = createSidebarButton("Clientes");
        Button btnProveedores = createSidebarButton("Proveedores");
        Button btnUsuarios = createSidebarButton("Usuarios");
        Button btnProductos = createSidebarButton("Productos");
        Button btnAlmacen = createSidebarButton("Almacén");
        Button btnStock = createSidebarButton("Stock");
        Button btnPedidos = createSidebarButton("Pedidos");
        Button btnFacturas = createSidebarButton("Facturas");



        // Acción de los botones
        btnClientes.setOnAction(e -> loadTable("Clientes"));
        btnProveedores.setOnAction(e -> loadTable("Proveedores"));
        btnUsuarios.setOnAction(e -> loadTable("Usuarios"));
        btnProductos.setOnAction(e -> loadTable("Productos"));
        btnAlmacen.setOnAction(e -> loadTable("Almacen"));
        btnStock.setOnAction(e -> loadTable("Stock"));
        btnPedidos.setOnAction(e -> loadTable("Pedidos"));
        btnFacturas.setOnAction(e -> loadTable("Facturas"));

        sideMenu.getChildren().addAll(btnClientes, btnProveedores, btnUsuarios, btnProductos, btnAlmacen, btnStock, btnPedidos, btnFacturas);

        // Botón salir
        Button btnSalir = new Button("Salir");
        btnSalir.setMinWidth(180);
        btnSalir.setStyle("-fx-background-color: #aa3333; -fx-text-fill: white; -fx-font-size: 14px;");
        btnSalir.setOnAction(e -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de salida");
            alert.setHeaderText("¿Desea salir de la aplicación?");
            alert.setContentText("Se cerrará el sistema ERP");

            agregarIcono(alert);

            ButtonType si = new ButtonType("Si", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(si, no);
            Button acceptButton = (Button) alert.getDialogPane().lookupButton(si);
            acceptButton.setDefaultButton(false);
            Button cancelButton = (Button) alert.getDialogPane().lookupButton(no);
            cancelButton.setDefaultButton(true);


            alert.showAndWait().ifPresent(type -> {
                if(type == si){
                    Platform.exit();
                }
            });
        });
        sideMenu.getChildren().add(btnSalir);

        // Añadir espacio flexible para empujar el logo hacia abajo
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Nombre del usuario actualmente logeado
        Label lblUsuario = new Label();
        if(UserSession.getUsuario() != null){
            lblUsuario.setText("Usuario: " + UserSession.getUsuario().getNombreUs());
        }
        lblUsuario.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        // Logo en la parte de abajo del panel lateral
        ImageView logoView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/corporate_logo_white.png"))));
        logoView.setFitHeight(80);
        logoView.setPreserveRatio(true);

        // Alineación centrada
        VBox logoBox = new VBox(logoView);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(10, 0, 0, 0));

        sideMenu.getChildren().add(spacer);         // Espacio que empuja todo hacia abajo
        HBox usuarioBox = new HBox(lblUsuario);
        usuarioBox.setAlignment(Pos.CENTER);
        usuarioBox.setPadding(new Insets(0, 0, 5, 0));
        sideMenu.getChildren().add(usuarioBox);
        sideMenu.getChildren().add(logoBox);        // Logo al final

        // Título de la tabla activa
        tableTitle = new Label("Clientes"); // Por defecto, al inicio
        tableTitle.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;-fx-text-fill: white;");

        // Barra superior
        topBar = new HBox(10);
        topBar.setStyle("-fx-background-color: #2c3e50;");
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER_LEFT);

        // Botón Crear registro
        Button btnCreate = new Button();
        btnCreate.setStyle("-fx-font-size: 14px; -fx-padding: 5px 15px;-fx-background-color: #98cddd;-fx-text-fill: black;");

        btnCreate.textProperty().bind(
                Bindings.when(tableTitle.textProperty().isEqualTo("Stock"))
                        .then("Inventarios")
                        .otherwise("Crear")
        );

        btnCreate.setOnAction(e -> {
            switch (currentTableKey){
                case "Clientes" -> {
                    GenericEditDialog<Cliente> dialog = new GenericEditDialog<>();
                    dialog.create(Cliente.class, () -> loadTable("Clientes"));
                }

                case "Proveedores" -> {
                    GenericEditDialog<Proveedores> dialog = new GenericEditDialog<>();
                    dialog.create(Proveedores.class, () -> loadTable("Proveedores"));
                }

                case "Usuarios" -> {
                    GenericEditDialog<Usuarios> dialog = new GenericEditDialog<>();
                    dialog.create(Usuarios.class, () -> loadTable("Usuarios"));
                }

                case "Productos" -> {
                    GenericEditDialog<Productos> dialog = new GenericEditDialog<>();
                    dialog.create(Productos.class, () -> loadTable("Productos"));
                }

                case "Almacen" -> {
                    GenericEditDialog<Almacen> dialog = new GenericEditDialog<>();
                    dialog.create(Almacen.class, () -> loadTable("Almacen"));
                }

                case "Pedidos" -> {
                    PedidoDialog pedidoDialog = new PedidoDialog();
                    pedidoDialog.show(() -> loadTable("Pedidos"));
                }

                case "Facturas" -> {
                    FacturaDialog facturaDialog = new FacturaDialog();
                    facturaDialog.show(() -> loadTable("Facturas"));
                }

                case "Stock" -> {
                    InventarioDialog inventarioDialog = new InventarioDialog();
                    inventarioDialog.show(() -> loadTable("Stock"));
                }

                default -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Funcionalidad no disponible");
                    alert.setContentText("La creación para esta entidad aún está en desarrollo");
                    alert.showAndWait();
                }
            }
        });

        tableView = new TableView<>();
        
        // Campo de busqueda
        TextField searchField = new TextField();
        searchField.setPromptText("Buscar...");
        searchField.setMinWidth(200);

        // Botón buscar
        ImageView searchIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/search_icon_black.png"))));
        searchIcon.setFitWidth(20);
        searchIcon.setFitHeight(20);
        Button btnSearch = new Button("", searchIcon);
        btnSearch.setStyle("-fx-background-color: #98cddd;-fx-text-fill: black;");

        btnSearch.setOnAction(e -> {

            String campoVisible = campoComboBox.getValue();
            Class<?> clazz = originalItems.isEmpty() ? null : originalItems.get(0).getClass();
            if (clazz == null) {return;}

            var camposAmigables = FriendlyNamesHelper.getFriendlyFieldNames(clazz);

            String campoReal = camposAmigables.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(campoVisible))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);
            if(campoReal == null){
                AlertaUtils.mostrar("Campo no válido");
                return;
            }

            String filtro = NormalizadorTexto.limpiarTexto(searchField.getText());

            String campoSeleccionado = campoComboBox.getValue();
            if(campoSeleccionado == null || campoSeleccionado.isEmpty()){
                AlertaUtils.mostrar("Debes seleccionar un campo antes de buscar");
                return;
            }
            if(filtro.isEmpty()){
                loadTable(tableTitle.getText()); // Restablece la tabla completa
                return;
            }

            List<Object> resultadosFiltrados = new ArrayList<>();

            for(Object obj : originalItems){
                try{
                    var method = ReflectionUtils.getGetter(obj.getClass(), campoReal);
                    Object valor = method != null ? method.invoke(obj) : "";
                    String textoNormalizado = NormalizadorTexto.limpiarTexto(valor != null ? valor.toString() : "");
                    if(textoNormalizado.contains(filtro)){
                        resultadosFiltrados.add(obj);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            tableView.setItems(FXCollections.observableArrayList(resultadosFiltrados));
        });

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        topBar.getChildren().addAll(btnCreate, leftSpacer, tableTitle, rightSpacer, campoComboBox, searchField, btnSearch);

        // Tabla central
        VBox.setVgrow(tableView, Priority.ALWAYS);


        // Contenedor central con tabla y barra superior
        VBox centerContent = new VBox(10, topBar, tableView);
        centerContent.setPadding(new Insets(20, 20, 40, 20));

        // Estructura principal
        root = new BorderPane();
        root.setLeft(sideMenu);
        root.setCenter(centerContent);
        root.setStyle("-fx-background-color: #2c3e50;");

        // Crear escena
        Scene scene = new Scene(root, 1366, 768);
        stage.setScene(scene);
        stage.centerOnScreen();

        loadTable("Clientes"); // Cargar la tabla de Clientes por defecto
    }

    // Método para mostrar la ventana
    public void show(){
        stage.show();
    }

    // Crea botones para la barra lateral
    private Button createSidebarButton(String text){
        Button button = new Button(text);
        button.setMinWidth(180);
        String estiloNormal = "-fx-background-color: #66a7bb; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-pref-height: 40px;";
        String estiloHover = "-fx-background-color: #98cddd; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-pref-height: 40px;";

        button.setStyle(estiloNormal);
        button.setOnMouseEntered(e -> button.setStyle(estiloHover));
        button.setOnMouseExited(e -> button.setStyle(estiloNormal));

        return button;
    }

    //Método para cargar la tabla seleccionada por el usuario
    private void loadTable(String tableName) {


        tableView.getColumns().clear();
        tableView.getItems().clear();
        tableTitle.setText(tableName);
        if(tableName.equals("Almacen")){
            tableTitle.setText("Almacén");
        }

        currentTableKey = tableName;
        tableTitle.setText(
                switch (tableName){
                    case "Almacen" -> "Almacén"; // Se pueden añadir más títulos a convertir si es necesario
                    default -> tableName;
                });

        switch (currentTableKey){

            case "Clientes": {
                ClienteDAO clienteDAO = new ClienteDAO();
                List<Cliente> clientes = clienteDAO.readAll();

                // Guardamos los datos originales como Object para la búsqueda
                originalItems.setAll(new ArrayList<>(clientes));

                // Rellenamos el ComboBox con los campos de la tabla
                var camposAmigables = FriendlyNamesHelper.getFriendlyFieldNames(Cliente.class);
                campoComboBox.getItems().setAll(camposAmigables.values());
                campoComboBox.setValue("Nombre");

                // Creamos una tabla temporal de tipo Cliente para evitar errores de tipo
                TableView<Cliente> clienteTable = new TableView<>();

                // Cargamos los datos en la tabla temporal con TableLoader
                TableLoader.load(
                        clienteTable,
                        clientes,
                        Cliente.class,
                        cliente -> {
                            ClienteDAO dao = new ClienteDAO();
                            dao.deleteById(cliente.getIdCliente());
                            loadTable("Clientes");
                        },
                        null
                );

                // Copiamos columnas y datos a tableView principal (Object)
                tableView.getColumns().setAll(clienteTable.getColumns().toArray(new TableColumn[0]));
                // tableView.getColumns().setAll(clienteTable.getColumns());
                tableView.setItems(FXCollections.observableArrayList(clientes));

                // Añadimos la tabla principal al centro de la vista
                VBox contentBox = new VBox(10, topBar, tableView);
                contentBox.setPadding(new Insets(20));
                VBox.setVgrow(tableView, Priority.ALWAYS);
                root.setCenter(contentBox);
                break;
            }

            case "Proveedores": {
                ProveedoresDAO proveedoresDAO = new ProveedoresDAO();
                List<Proveedores> proveedores = proveedoresDAO.readAll();

                originalItems.setAll(new ArrayList<>(proveedores));

                var camposAmigables = FriendlyNamesHelper.getFriendlyFieldNames(Proveedores.class);
                campoComboBox.getItems().setAll(camposAmigables.values());
                campoComboBox.setValue("Nombre");

                TableView<Proveedores> proveedoresTable = new TableView<>();

                TableLoader.load(
                        proveedoresTable,
                        proveedores,
                        Proveedores.class,
                        proveedor -> {
                            proveedoresDAO.deleteById(proveedor.getIdProveedor());
                            loadTable("Proveedores");
                        },
                        proveedor -> {
                            GenericEditDialog<Proveedores> dialog = new GenericEditDialog<>();
                            dialog.show(proveedor, Proveedores.class, false, () -> loadTable("Proveedores"));
                        }
                );

                tableView.getColumns().setAll(proveedoresTable.getColumns().toArray(new TableColumn[0]));
                tableView.setItems(FXCollections.observableArrayList(proveedores));

                VBox contentBox = new VBox(10, topBar, tableView);
                contentBox.setPadding(new Insets(20));
                VBox.setVgrow(tableView, Priority.ALWAYS);
                root.setCenter(contentBox);
                break;
            }

            case "Usuarios": {
                UsuariosDAO usuariosDAO = new UsuariosDAO();
                List<Usuarios> usuarios = usuariosDAO.readAll();

                // Guardamos los datos originales como Object para búsqueda
                originalItems.setAll(new ArrayList<>(usuarios));

                // Configuramos el ComboBox de búsqueda con los campos disponibles
                var camposAmigables = FriendlyNamesHelper.getFriendlyFieldNames(Usuarios.class);
                campoComboBox.getItems().setAll(camposAmigables.values());
                campoComboBox.setValue("Nombre");

                // Creamos tabla temporal para trabajar con TableLoader
                TableView<Usuarios> usuarioTable = new TableView<>();

                TableLoader.load(
                        usuarioTable,
                        usuarios,
                        Usuarios.class,
                        usuario -> {
                            UsuariosDAO dao = new UsuariosDAO();
                            dao.deleteById(usuario.getIdUsuarios());
                            loadTable("Usuarios");
                            tableView.refresh();
                        },
                        usuario -> {
                            GenericEditDialog<Usuarios> dialog = new GenericEditDialog<>();
                            dialog.show(usuario, Usuarios.class, false, () -> loadTable("Usuarios"));
                            tableView.refresh();
                        }
                );

                // Se transfieren columnas y datos a la tabla principal
                tableView.getColumns().setAll(usuarioTable.getColumns().toArray(new TableColumn[0]));
                tableView.setItems(FXCollections.observableArrayList(usuarios));

                // Mostrar tabla en pantalla
                VBox contentBox = new VBox(10, topBar, tableView);
                contentBox.setPadding(new Insets(20));
                VBox.setVgrow(tableView, Priority.ALWAYS);
                root.setCenter(contentBox);
                break;
            }
            case "Productos": {
                ProductosDAO productosDAO = new ProductosDAO();
                List<Productos> productos = productosDAO.readAll();

                originalItems.setAll(new ArrayList<>(productos));

                var camposAmigables = FriendlyNamesHelper.getFriendlyFieldNames(Productos.class);
                campoComboBox.getItems().setAll(camposAmigables.values());
                campoComboBox.setValue("Nombre");

                TableView<Productos> productoTable = new TableView<>();

                TableLoader.load(
                        productoTable,
                        productos,
                        Productos.class,
                        producto -> {
                            ProductosDAO dao = new ProductosDAO();
                            dao.deleteById(producto.getId_Prod());
                            loadTable("Productos");
                        },
                        producto -> {
                            GenericEditDialog<Productos> dialog = new GenericEditDialog<>();
                            dialog.show(producto, Productos.class, false, () -> loadTable("Productos"));
                        }
                );

                tableView.getColumns().setAll(productoTable.getColumns().toArray(new TableColumn[0]));
                tableView.setItems(FXCollections.observableArrayList(productos));

                VBox contentBox = new VBox(10, topBar, tableView);
                contentBox.setPadding(new Insets(20));
                VBox.setVgrow(tableView, Priority.ALWAYS);
                root.setCenter(contentBox);
                break;
            }

            case "Almacen": {
                AlmacenDAO almacenDAO = new AlmacenDAO();
                List<Almacen> almacenes = almacenDAO.readAll();

                originalItems.setAll(new ArrayList<>(almacenes));

                var camposAmigables = FriendlyNamesHelper.getFriendlyFieldNames(Almacen.class);
                campoComboBox.getItems().setAll(camposAmigables.values());
                campoComboBox.setValue("Nombre");

                TableView<Almacen> almacenTable = new TableView<>();

                TableLoader.load(
                        almacenTable,
                        almacenes,
                        Almacen.class,
                        almacen -> {
                            AlmacenDAO dao = new AlmacenDAO();
                            dao.deleteById(almacen.getId_Almacen());
                            loadTable("Almacen");
                        },
                        almacen -> {
                            GenericEditDialog<Almacen> dialog = new GenericEditDialog<>();
                            dialog.show(almacen, Almacen.class, false, () -> loadTable("Almacen"));
                        }
                );
                tableView.getColumns().setAll(almacenTable.getColumns().toArray(new TableColumn[0]));
                tableView.setItems(FXCollections.observableArrayList(almacenes));

                VBox contentBox = new VBox(10, topBar, tableView);
                contentBox.setPadding(new Insets(20));
                VBox.setVgrow(tableView, Priority.ALWAYS);
                root.setCenter(contentBox);
                break;
            }

            case "Pedidos": {
                CabPedidosDAO cabPedidosDAO = new CabPedidosDAO();
                List<CabPedidos> pedidos = cabPedidosDAO.readAll();

                originalItems.setAll(new ArrayList<>(pedidos));

                var camposAmigables = FriendlyNamesHelper.getFriendlyFieldNames(CabPedidos.class);
                campoComboBox.getItems().setAll(camposAmigables.values());
                campoComboBox.setValue("Pedido");

                TableView<CabPedidos> pedidosTable = new TableView<>();

                TableLoader.load(
                        pedidosTable,
                        pedidos,
                        CabPedidos.class,
                        pedido -> {
                            try{
                                // Primero borramos todas las líneas del pedido
                                LineasPedDAO lineasPedDAO = new LineasPedDAO();
                                List<LineasPed> lineas  = lineasPedDAO.readAll().stream()
                                        .filter(l -> l.getCabPedidos().getNumPed().equals(pedido.getNumPed()))
                                        .toList();
                                for(LineasPed linea : lineas){
                                    lineasPedDAO.deleteById(linea.getIdLineaPed());
                                }

                                // Luego borramos la cabecera del pedido
                                cabPedidosDAO.delete(pedido.getNumPed());

                                //pedidosTable.getItems().remove(pedido);
                                loadTable("Pedidos");
                                AlertaUtils.mostrar("Pedido borrado correctamente");
                            }catch (Exception ex){
                                ex.printStackTrace();
                                AlertaUtils.mostrar("Error al borrar pedido");
                            }
                        },
                        null // Lugar para otro botón
                );

                tableView.getColumns().setAll(pedidosTable.getColumns().toArray(new TableColumn[0]));
                tableView.setItems(FXCollections.observableArrayList(pedidos));

                VBox contentBox = new VBox(10, topBar, tableView);
                contentBox.setPadding(new Insets(20));
                VBox.setVgrow(tableView, Priority.ALWAYS);
                root.setCenter(contentBox);
                break;
            }

            case "Facturas": {
                CabFacturasDAO cabFacturasDAO = new CabFacturasDAO();
                List<CabFacturas> facturas = cabFacturasDAO.readAll();

                originalItems.setAll(new ArrayList<>(facturas));

                var camposAmigables = FriendlyNamesHelper.getFriendlyFieldNames(CabFacturas.class);
                campoComboBox.getItems().setAll(camposAmigables.values());
                campoComboBox.setValue("Cliente");

                TableView<CabFacturas> facturasTable = new TableView<>();

                TableLoader.load(
                        facturasTable,
                        facturas,
                        CabFacturas.class,
                        factura -> {
                            // Eliminación completa de factura y líneas
                            try {
                                LineasFactDAO lineasFactDAO = new LineasFactDAO();
                                List<LineasFact> lineas = lineasFactDAO.readAll().stream()
                                        .filter(l -> l.getFactura().getNumFact().equals(factura.getNumFact()))
                                        .toList();
                                for (LineasFact linea : lineas) {
                                    lineasFactDAO.deleteById(linea.getIdLineaFact());
                                }

                                cabFacturasDAO.delete(factura.getNumFact());
                                loadTable("Facturas");
                                //facturasTable.getItems().remove(factura);                                
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                AlertaUtils.mostrar("Error al borrar factura");
                            }
                        },
                        null
                );

                tableView.getColumns().setAll(facturasTable.getColumns().toArray(new TableColumn[0]));
                tableView.setItems(FXCollections.observableArrayList(facturas));

                VBox contentBox = new VBox(10, topBar, tableView);
                contentBox.setPadding(new Insets(20));
                VBox.setVgrow(tableView, Priority.ALWAYS);
                root.setCenter(contentBox);
                break;
            }

            case "Stock": {
                ProductosAlmacenDAO dao = new ProductosAlmacenDAO();
                List<ProductosAlmacen> stock = dao.readAll();

                System.out.println("Total registros del stock: " + stock.size());

                originalItems.setAll(new ArrayList<>(stock));

                var camposAmigables = FriendlyNamesHelper.getFriendlyFieldNames(ProductosAlmacen.class);
                campoComboBox.getItems().setAll(camposAmigables.values());
                campoComboBox.setValue("Producto");

                TableView<ProductosAlmacen> stockTable = new TableView<>();

                TableLoader.load(
                        stockTable,
                        stock,
                        ProductosAlmacen.class,
                        null, // No se permite borrar directamente
                        null  // Ni editar desde esta vista
                );

                // Copiamos columnas de la tabla temporal a la principal
                tableView.getColumns().setAll(stockTable.getColumns().toArray(new TableColumn[0]));
                tableView.setItems(FXCollections.observableArrayList(stock));

                VBox contentBox = new VBox(10, topBar, tableView);
                contentBox.setPadding(new Insets(20));
                VBox.setVgrow(tableView, Priority.ALWAYS);
                root.setCenter(contentBox);
                break;
            }
            
            default:
                System.out.println("Tabla no implementada aún");
        }
    }
}
