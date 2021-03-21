package InventoryManagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;



import java.io.IOException;
/**addProductController adds products to the inventory and controls the addProduct form*/
public class addProductController implements Initializable{

    @FXML private Label addProductLabel;
    @FXML private Pane pane;
    @FXML private Button save;
    @FXML private Button cancel;
    @FXML private Button removeAssociated;
    @FXML private Button add;
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField inv;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;
    /**parts table configuration*/
    @FXML private TableView<Part> partsView;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvLvlCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private TextField partSearch;

    public ObservableList<Part> getParts(){
        return Inventory.getAllParts();
    }
    /**table data for products table*/
    public ObservableList<Product> getAssociatedParts(){
        return Inventory.getAllProducts();
    }
    /**associatedParts table configuration*/
    @FXML private TableView<Part> associatedPartsView;
    @FXML private TableColumn<Part, Integer> productIDCol;
    @FXML private TableColumn<Part, String> productNameCol;
    @FXML private TableColumn<Part, Integer> productInvLvlCol;
    @FXML private TableColumn<Part, Double> productPriceCol;

    /**initializing stage*/
    @Override
    public void initialize(URL url,ResourceBundle rb){
        addProductLabel.setStyle("-fx-font-weight:bold");
        pane.setStyle("-fx-border-radius:10px;-fx-border-color:black");
        partSearch.setPromptText("Search by Product Id or Name");
        id.setDisable(true);
        /**build parts table*/
        partIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        /**builds associatedParts table*/
        productIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productInvLvlCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        partsView.setItems(getParts());
        partsView.getSelectionModel();
        partsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
    }
    /**add product parts*/
    public void addPart(ActionEvent event) throws IOException {
        ObservableList<Part> list = associatedPartsView.getItems();
        ObservableList<Part> parts = partsView.getSelectionModel().getSelectedItems();
        for(Part part:parts){
            list.add(part);
        }
        associatedPartsView.setItems(list);
    }
    /**remove product parts*/
    public void removePart(ActionEvent event){
        ObservableList<Part> list = associatedPartsView.getItems();
        Part part = associatedPartsView.getSelectionModel().getSelectedItem();
        list.remove(part);
        associatedPartsView.setItems(list);
    }
    /**cancel and return to main screen*/
    public void cancel(ActionEvent event) throws IOException {
        Parent productFormInHouseView = FXMLLoader.load(getClass().getResource("build.fxml"));
        productFormInHouseView.setStyle("-fx-border-style:solid;-fx-border-color:blue;-fx-border-width:5px");
        Scene inHouseProductScene = new Scene(productFormInHouseView);

        Stage productWinInHouse = (Stage)((Node)event.getSource()).getScene().getWindow();
        productWinInHouse.setScene(inHouseProductScene);
        productWinInHouse.show();
    }
    /** save product and return to main screen*/
    public void save(ActionEvent event) throws IOException {
        /**verify input data with try/catch
         * prints dialog errors/confirmations*/
        try {

            int Id = Data.getProductIds()+1;
            Data.setProductIds(Id);
            String partName = name.getText();
            int partInv = Integer.parseInt(inv.getText());
            Double partPrice = Double.parseDouble(price.getText());
            int partMax = Integer.parseInt(max.getText());
            int partMin = Integer.parseInt(min.getText());
            Product product = new Product(Id,partName,partPrice,partInv,partMin,partMax);
            ObservableList<Part> parts = associatedPartsView.getItems();
            for(Part part: parts){
                product.addAssociatedPart(part);
            }
            Inventory.addProduct(product);
            Parent productFormInHouseView = FXMLLoader.load(getClass().getResource("build.fxml"));
            productFormInHouseView.setStyle("-fx-border-style:solid;-fx-border-color:blue;-fx-border-width:5px");
            Scene inHouseProductScene = new Scene(productFormInHouseView);

            Stage productWinInHouse = (Stage) ((Node) event.getSource()).getScene().getWindow();
            productWinInHouse.setScene(inHouseProductScene);
            productWinInHouse.show();
        }catch(Exception e){
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error Alert");
            String s ="Please check your inputs and retry";
            alert.setContentText(s);
            alert.show();
        }
    }
}
