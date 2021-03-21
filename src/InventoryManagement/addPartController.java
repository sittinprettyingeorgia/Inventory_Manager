package InventoryManagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

/** addPartController adds parts to the Inventory and controls add part form*/
public class addPartController implements Initializable{


    @FXML private Label addPartLabel;
    @FXML private RadioButton inHouse;
    @FXML private RadioButton outsourced;
    @FXML private Button save;
    @FXML private Button cancel;
    @FXML private Label label;
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField inv;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField varies;
    private ToggleGroup group;

    @Override
    /**initializa stage*/
    public void initialize(URL url,ResourceBundle rb) {
        addPartLabel.setStyle("-fx-font-weight:bold");
        group = new ToggleGroup();
        this.inHouse.setToggleGroup(group);
        this.outsourced.setToggleGroup(group);
        id.setDisable(true);
    }
    /**change views from inhouse to outsourced parts*/
    public void changePartView() {
        if(this.group.getSelectedToggle().equals(outsourced)) {
            label.setText("Company Name");
        }
        if(this.group.getSelectedToggle().equals(inHouse)) label.setText("Machine ID");
    }
    /**return to main screen*/
    public void cancel(ActionEvent event) throws IOException {
        Parent productFormInHouseView = FXMLLoader.load(getClass().getResource("build.fxml"));
        productFormInHouseView.setStyle("-fx-border-style:solid;-fx-border-color:blue;-fx-border-width:5px");
        Scene inHouseProductScene = new Scene(productFormInHouseView);

        Stage productWinInHouse = (Stage)((Node)event.getSource()).getScene().getWindow();
        productWinInHouse.setScene(inHouseProductScene);
        productWinInHouse.show();
    }
    /**save part and return to main screen*/
    public void save(ActionEvent event) throws IOException {
        /** try/catch to test inputs and verify unique ids*/
        try {
            if(!inHouse.isSelected() && !outsourced.isSelected() ){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error Alert");
                String s ="Please select InHouse or Outsourced part option";
                alert.setContentText(s);
                alert.show();
                return;
            }
            int Id = Data.getPartIds()+1;
            Data.setPartIds(Id);
            String partName = name.getText();
            int partInv = Integer.parseInt(inv.getText());
            Double partPrice = Double.parseDouble(price.getText());
            int partMax = Integer.parseInt(max.getText());
            int partMin = Integer.parseInt(min.getText());
            if((partMin>partMax) || (partInv<partMin) || (partInv>partMax)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error Alert");
                String s ="min must be less than max and inventory must be between min and max values";
                alert.setContentText(s);
                alert.show();
                return;
            }
            String last = varies.getText();
            if (inHouse.isSelected()) {
                /** machineId data checked by parsing Int. if inappropriate value is found an exception is thrown
                 * to the catch function.
                 */

                int machId = Integer.valueOf(last);
                Part part = new InHouse(Id, partName, partPrice, partInv, partMin, partMax, machId);
                Inventory.addPart(part);
            } else {
                Part part = new Outsourced(Id, partName, partPrice, partInv, partMin, partMax, last);
                Inventory.addPart(part);
            }
            /**Revert back to main page*/
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
