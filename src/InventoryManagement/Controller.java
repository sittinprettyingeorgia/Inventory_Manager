package InventoryManagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.stage.Window;

/** main controller for the program.
 * controls navigation to add/modify part forms and is the initial controller for main page*/

public class Controller implements Initializable {

    @FXML private Label IMLabel;
    @FXML private Label parts;
    @FXML private Label products;
    @FXML private Pane partsPane;
    @FXML private Pane productPane;
    @FXML private GridPane pane;
    /**parts table configuration*/
    @FXML private TableView<Part> partsView;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvLvlCol;
    @FXML private TableColumn<Part, Double> partPriceCol;


    /**products table configuration*/
    @FXML private TableView<Product> productsView;
    @FXML private TableColumn<Part, Integer> productIDCol;
    @FXML private TableColumn<Part, String> productNameCol;
    @FXML private TableColumn<Part, Integer> productInvLvlCol;
    @FXML private TableColumn<Part, Double> productPriceCol;
    @FXML private TextField partSearch;
    @FXML private TextField productSearch;

    @FXML private Button exit;
    @FXML private Button add;

    /**table data for parts table*/
    public ObservableList<Part> getParts(){
        if(Inventory.getAllParts().isEmpty()) {
            Inventory.addPart(new InHouse(1, "mach09", 1, 1, 1, 15, 1));
            Inventory.addPart(new Outsourced(2, "jun67", 1, 1, 1, 15, "holder"));
            Inventory.addPart(new InHouse(3, "mach08", 1, 1, 1, 15, 1));
            Inventory.addPart(new Outsourced(4, "jun98", 1, 1, 1, 15, "holder"));
            Inventory.addPart(new InHouse(5, "can", 1, 1, 1, 15, 1));
            Inventory.addPart(new Outsourced(6, "ban", 1, 1, 1, 15, "holder"));
            Inventory.addPart(new InHouse(7, "can2", 1, 1, 1, 15, 1));
            Inventory.addPart(new Outsourced(8, "ban2", 1, 1, 1, 15, "holder"));
        }
        return Inventory.getAllParts();
    }
    /**table data for products table*/
    public ObservableList<Product> getProducts(){
        if(Inventory.getAllProducts().isEmpty()) {
            Inventory.addProduct(new Product(1, "mach09", 1, 1, 12, 111));
            Inventory.addProduct(new Product(2, "jun67", 1, 1, 12, 111));
            Inventory.addProduct(new Product(3, "mach08", 1, 1, 12, 111));
            Inventory.addProduct(new Product(4, "jun98", 1, 1, 12, 111));
            Inventory.addProduct(new Product(5, "can", 1, 1, 12, 111));
            Inventory.addProduct(new Product(6, "ban", 1, 1, 12, 111));
            Inventory.addProduct(new Product(7, "can2", 1, 1, 12, 111));
            Inventory.addProduct(new Product(8, "ban2", 1, 1, 12, 111));
        }
        return Inventory.getAllProducts();
    }

    @Override
    /**initializa main controller for main stage*/
    public void initialize(URL url,ResourceBundle rb){
        /**inline styling of elements*/
        parts.setStyle("-fx-font-weight:bold");
        products.setStyle("-fx-font-weight:bold");
        IMLabel.setStyle("-fx-font-weight:bold;-fx-font-size:Large");
        partsPane.setStyle("-fx-border-radius:10px;-fx-border-color:black");
        productPane.setStyle("-fx-border-radius:10px;-fx-border-color:black");

        partSearch.setPromptText("Search for part by ID or Name");
        productSearch.setPromptText("Search for product by ID or Name");
        /**build parts table*/
        partIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        /**builds product table*/
        productIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productInvLvlCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        partsView.setItems(getParts());
        productsView.setItems(getProducts());
        partsView.getSelectionModel();
        partsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        productsView.getSelectionModel();
        productsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        /**search parts on enter key*/
        partSearch.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                String search = partSearch.getText();
                if(search.isEmpty()) partsView.setItems(Inventory.getAllParts());
                try {
                    Part intRes = Inventory.lookupPart(Integer.parseInt(search));
                    if(intRes != null){
                        partsView.getSelectionModel().select(intRes);
                        return;
                    }
                    ObservableList<Part> stringRes = Inventory.lookupPart(search);
                    if(stringRes.isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error Alert");
                        String s ="The part was not found.";
                        alert.setContentText(s);
                        alert.show();
                    }
                    else {
                        partsView.setItems(stringRes);
                    }
                }catch(Exception e){
                    ObservableList<Part> stringRes = Inventory.lookupPart(search);
                    if(stringRes.isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error Alert");
                        String s ="The part was not found.";
                        alert.setContentText(s);
                        alert.show();
                    }
                    else {
                        partsView.setItems(stringRes);
                    }
                }

            }

        });
        /**search products on enter key*/
        productSearch.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                String search = productSearch.getText();
                if(search.isEmpty())productsView.setItems(Inventory.getAllProducts());
                try {
                    Product intRes = Inventory.lookupProduct(Integer.parseInt(search));
                    if(intRes != null){
                        productsView.getSelectionModel().select(intRes);
                        return;
                    }
                    ObservableList<Product> stringRes = Inventory.lookupProduct(search);
                    if(stringRes.isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error Alert");
                        String s ="The product was not found.";
                        alert.setContentText(s);
                        alert.show();
                    }
                    else {
                        productsView.setItems(stringRes);
                    }
                }catch(Exception e){
                    ObservableList<Product> stringRes = Inventory.lookupProduct(search);
                    if(stringRes.isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error Alert");
                        String s ="The part was not found.";
                        alert.setContentText(s);
                        alert.show();
                    }
                    else{
                        productsView.setItems(stringRes);
                    }
                }
            }

        });

    }

    /**change screen to add part(InHouseView)*/
    public void addPart(ActionEvent event) throws IOException {
        Parent partFormInHouseView = FXMLLoader.load(getClass().getResource("addPart.fxml"));
        partFormInHouseView.setStyle("-fx-border-style:solid;-fx-border-color:blue;-fx-border-width:1px");
        Scene inHouseScene = new Scene(partFormInHouseView);

        Stage partWinInHouse = (Stage)((Node)event.getSource()).getScene().getWindow();
        partWinInHouse.setScene(inHouseScene);
        partWinInHouse.show();
    }
    /**change screen to modify part view(InHouse)*/
    public void modifyPart(ActionEvent event) throws IOException {
        ObservableList<Part> list = partsView.getSelectionModel().getSelectedItems();
        if(list.size() != 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error Alert");
            String s ="Please select ONE part to modify.";
            alert.setContentText(s);
            alert.show();
            return;
        }else{
            Data.setDataPart(list.get(0));
        }
        Node node = (Node)event.getSource();
        Stage partWinInHouse = (Stage)node.getScene().getWindow();
        Parent partFormInHouseView = FXMLLoader.load(getClass().getResource("modifyPart.fxml"));
        partFormInHouseView.setStyle("-fx-border-style:solid;-fx-border-color:blue;-fx-border-width:1px");
        Scene inHouseScene = new Scene(partFormInHouseView);
        partWinInHouse.setScene(inHouseScene);
        partWinInHouse.show();
    }

    public boolean deletePart() {
        boolean res = false;
        Part target =partsView.getSelectionModel().getSelectedItem();
        ObservableList<Part> parts = partsView.getItems();
        if(parts.remove(target))res=true;

        //display the dialog box
        if(!res){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error Alert");
            String s ="There was an issue deleting the part.";
            alert.setContentText(s);
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error Alert");
            String s ="Delete was successful.";
            alert.setContentText(s);
            alert.show();
        }
        return res;
    }

    /** RUNTIME ERROR: add/modify buttons only work for the part tableView section and not for the productView section.
     *  Diagnosis: getResource for product add/modify buttons had specified the wrong FXML file to load from
     *  Solution: provided correct file name.
     * */
    /**change screen to add product(InHouseView)*/
    public void addProduct(ActionEvent event) throws IOException {
        Parent productFormInHouseView = FXMLLoader.load(getClass().getResource("addProduct.fxml"));
        productFormInHouseView.setStyle("-fx-border-style:solid;-fx-border-color:blue;-fx-border-width:1px");
        Scene inHouseProductScene = new Scene(productFormInHouseView);
        Stage productWinInHouse = (Stage)((Node)event.getSource()).getScene().getWindow();
        productWinInHouse.setScene(inHouseProductScene);
        productWinInHouse.show();
    }
    /**change screen to modify product view(InHouse)*/
    public void modifyProduct(ActionEvent event) throws IOException {
        ObservableList<Product> list = productsView.getSelectionModel().getSelectedItems();
        if(list.size()!= 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error Alert");
            String s ="Please select ONE product to modify.";
            alert.setContentText(s);
            alert.show();
            return;
        }else{
            Data.setDataProduct(list.get(0));
        }
        Parent productFormInHouseView = FXMLLoader.load(getClass().getResource("modifyProduct.fxml"));
        productFormInHouseView.setStyle("-fx-border-style:solid;-fx-border-color:blue;-fx-border-width:1px");
        Scene inHouseProductScene = new Scene(productFormInHouseView);
        Stage productWinInHouse = (Stage)((Node)event.getSource()).getScene().getWindow();
        productWinInHouse.setScene(inHouseProductScene);
        productWinInHouse.show();
    }
    /**delete a product*/
    public boolean deleteProduct() {
            boolean res = false;
            ObservableList<Product> products = productsView.getSelectionModel().getSelectedItems();
            ObservableList<Product> list = productsView.getItems();
            /** verify products are deleted correctly and test input*/
            if(products.size()!=1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error Alert");
                String s ="Please select ONE product to delete.";
                alert.setContentText(s);
                alert.show();
                return false;
            } else{
                if(products.get(0).getAllAssociatedParts().size()>0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error Alert");
                    String s ="You may not delete a product that is associated with a part.";
                    alert.setContentText(s);
                    alert.show();
                    return false;
                }
                if(list.remove(products.get(0)))res=true;
            }

            /**display dialog box*/
            if(!res){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error Alert");
                String s ="There was an issue deleting the part.";
                alert.setContentText(s);
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Confirmed!");
                String s ="Delete was successful.";
                alert.setContentText(s);
                alert.show();
            }
         return res;
    }

    public void exit(){
        Platform.exit();
        System.exit(0);
    }

}
