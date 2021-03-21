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

/** modifyProductController allows modification of individual products*/
public class modifyProductController implements Initializable {

    @FXML private Label modProductLabel;
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
    //parts table configuration
    @FXML private TableView<Part> partsView;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvLvlCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private TextField partSearch;
    private Product selected;

    public ObservableList<Part> getParts(){
        return Inventory.getAllParts();
    }

    /**associatedParts table configuration
     *
     */
    @FXML private TableView<Part> associatedPartsView;
    @FXML private TableColumn<Part, Integer> productIDCol;
    @FXML private TableColumn<Part, String> productNameCol;
    @FXML private TableColumn<Part, Integer> productInvLvlCol;
    @FXML private TableColumn<Part, Double> productPriceCol;
    @Override
    public void initialize(URL url,ResourceBundle rb){
        modProductLabel.setStyle("-fx-font-weight:bold");
        pane.setStyle("-fx-border-radius:10px;-fx-border-color:black");
        partSearch.setPromptText("Search by Product Id or Name");
        id.setDisable(true);
        try {
            selected = Data.getDataProduct();
            id.setText(String.valueOf(selected.getId()));
            name.setText(String.valueOf(selected.getName()));
            inv.setText(String.valueOf(selected.getStock()));
            price.setText(String.valueOf(selected.getPrice()));
            max.setText(String.valueOf(selected.getMax()));
            min.setText(String.valueOf(selected.getMin()));
        } catch(Exception e){
            System.out.println(e);
        }
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
        associatedPartsView.setItems(Data.getDataProduct().getAllAssociatedParts());
        partsView.getSelectionModel();
        partsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        associatedPartsView.getSelectionModel();
        associatedPartsView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        /**search for parts*/
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
    /**add part to product*/
    public void addPart(ActionEvent event) throws IOException {
        Product product = Data.getDataProduct();
        ObservableList<Part> list = associatedPartsView.getItems();
        ObservableList<Part> parts = partsView.getSelectionModel().getSelectedItems();
        for(Part part:parts){
            product.addAssociatedPart(part);
        }
        associatedPartsView.setItems(list);
    }
    /**remove part from product*/
    public void removePart(ActionEvent event){
        try {
            Product product = Data.getDataProduct();
            int index =Inventory.getAllProducts().indexOf(product);
            Part part = associatedPartsView.getSelectionModel().getSelectedItem();
            product.deleteAssociatedParts(part);
            Inventory.updateProduct(index,product);
            Data.setDataProduct(product);
        }catch(Exception e){
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error Alert");
            String s ="Delete failed.";
            alert.setContentText(s);
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Confirmed!");
        String s ="Delete was successful.";
        alert.setContentText(s);
        alert.show();
    }
    /** cancel and return to main screen*/
    public void cancel(ActionEvent event) throws IOException {
        Parent productFormInHouseView = FXMLLoader.load(getClass().getResource("build.fxml"));
        productFormInHouseView.setStyle("-fx-border-style:solid;-fx-border-color:blue;-fx-border-width:5px");
        Scene inHouseProductScene = new Scene(productFormInHouseView);

        Stage productWinInHouse = (Stage)((Node)event.getSource()).getScene().getWindow();
        productWinInHouse.setScene(inHouseProductScene);
        productWinInHouse.show();
    }
    /** save product*/
    public void save(ActionEvent event) throws IOException {
        try {
            int partInv = (Integer.parseInt(inv.getText()));
            int partMin = (Integer.parseInt(min.getText()));
            int partMax = (Integer.parseInt(max.getText()));
            if((partInv < partMin) || (partInv > partMax)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error Alert");
                String s ="min must be less than max and inventory must be between min and max values";
                alert.setContentText(s);
                alert.show();
                return;
            }
            Product product = Data.getDataProduct();
            product.setName(name.getText());
            product.setStock(Integer.parseInt(inv.getText()));
            product.setPrice(Double.parseDouble(price.getText()));
            product.setMax(Integer.parseInt(max.getText()));
            product.setMin(Integer.parseInt(min.getText()));
            int index =Inventory.getAllProducts().indexOf(product);
            Inventory.updateProduct(index,product);

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
