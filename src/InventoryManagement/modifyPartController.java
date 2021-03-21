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

/** modifyPartController class allows modification of individual parts*/
public class modifyPartController implements Initializable{
    private Part selectedPart;
    @FXML private Label modPartLabel;
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
    
    /**RUNTIME ERROR:NULLPOINTEREXCEPTION
     *
     * Diagnosed: Try/catch block was used to find the exception type after
     * new code was added and runtime error was found. getScene().getWindow() was returning a null pointer.
     *The data was not transferring properly between controllers.
     * Solution: Created a data class to pass data between controllers.
     */
    //load data from selected part
    public void initialize(URL url,ResourceBundle rb){
        modPartLabel.setStyle("-fx-font-weight:bold");
        group = new ToggleGroup();
        this.inHouse.setToggleGroup(group);
        this.outsourced.setToggleGroup(group);
        id.setDisable(true);
         try {
            selectedPart = Data.getDataPart();
            id.setText(String.valueOf(selectedPart.getId()));
            name.setText(String.valueOf(selectedPart.getName()));
            inv.setText(String.valueOf(selectedPart.getStock()));
            price.setText(String.valueOf(selectedPart.getPrice()));
            max.setText(String.valueOf(selectedPart.getMax()));
            min.setText(String.valueOf(selectedPart.getMin()));
            if (selectedPart instanceof InHouse) {
                varies.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
                label.setText("Machine ID");
                inHouse.setSelected(true);
                inHouse.requestFocus();
            } else {
                varies.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
                label.setText("Company Name");
                outsourced.setSelected(true);
                outsourced.requestFocus();
            }
        } catch(Exception e){
             System.out.println(e);
        }
    }

    /**change view between inhouse and outsourced parts*/
    public void changePartView() {
        if(this.group.getSelectedToggle().equals(outsourced)) {
            label.setText("Company Name");
        }
        if(this.group.getSelectedToggle().equals(inHouse)) label.setText("Machine ID");
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

    /** test input and provide confirmations/errors for exceptions*/
    public void save(ActionEvent event) throws IOException {
        try {
            Part part = Data.getDataPart();
            part.setId(Integer.parseInt(id.getText()));
            part.setName(name.getText());
            part.setStock(Integer.parseInt(inv.getText()));
            part.setPrice(Double.parseDouble(price.getText()));
            part.setMax(Integer.parseInt(max.getText()));
            part.setMin(Integer.parseInt(min.getText()));
            if (label.getText()=="Machine ID") {
                ((InHouse)part).setMachineId(Integer.parseInt(varies.getText()));
                int index=Inventory.getAllParts().indexOf(part);
                Inventory.updatePart(index,part);
            } else {
                ((Outsourced)part).setCompanyName(varies.getText());
                int index=Inventory.getAllParts().indexOf(part);
                Inventory.updatePart(index,part);
            }

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
