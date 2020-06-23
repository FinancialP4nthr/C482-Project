/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.InhousePart;
import Model.Inventory;
import static Model.Inventory.getAllParts;
import Model.OutsourcedPart;
import Model.Part;
import static ViewController.MainScreenController.changePartIndex;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lasac
 */
public class ModifyPartScreenController implements Initializable {

    @FXML
    private RadioButton OutsourcedRadio;
    @FXML
    private RadioButton InHouseRadio;
    @FXML
    private TextField modifyPartIDInput;
    @FXML
    private TextField modifyPartNameInput;
    @FXML
    private TextField modifyPartQtyInput;
    @FXML
    private TextField modifyPartPriceInput;
    @FXML
    private TextField modifyPartMinInput;
    @FXML
    private TextField modifyPartMaxInput;
    @FXML
    private Label modifyDynamicLabel;
    @FXML
    private TextField modifyDynamicInput;
    @FXML
    private Button modifyPartSaveButton;
    @FXML
    private Button modifyPartCancelButton;

    private boolean isOutsourced;
    private String excMessage = new String();
    private int partID;
    int partIndex = changePartIndex();
    
    @FXML void modifyPartInHouseRadio(ActionEvent event) throws IOException{
        isOutsourced = false;
        OutsourcedRadio.setSelected(false);
        modifyDynamicLabel.setText("Machine ID");
        modifyDynamicInput.setText("");
        modifyDynamicInput.setPromptText("Machine ID");
    }
    
    @FXML void modifyPartOutsourcedRadio(ActionEvent event) throws IOException{
        isOutsourced = true;
        InHouseRadio.setSelected(false);
        modifyDynamicLabel.setText("Company Name");
        modifyDynamicInput.setText("");
        modifyDynamicInput.setPromptText("Company Name");
    }
    
    
    @FXML
    void modifyPartSaveAction(ActionEvent event) throws IOException {
    String partName = modifyPartNameInput.getText();
    String partQty = modifyPartQtyInput.getText();
    String partPrice = modifyPartPriceInput.getText();
    String partMax = modifyPartMaxInput.getText();
    String partMin = modifyPartMinInput.getText();
    String partDynamic = modifyDynamicInput.getText();
    
    try{
        excMessage = Part.isPartValid(partName, Integer.parseInt(partMax), Integer.parseInt(partMin), Integer.parseInt(partQty),(int)Double.parseDouble(partPrice), excMessage);
       if(excMessage.length() > 0){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("ModifyPartError");
        alert.setContentText(excMessage);
        alert.showAndWait();
        excMessage = "";
    }
    else {
        if(isOutsourced == false){
            System.out.println("Part name: " + partName);
            InhousePart inPart = new InhousePart();
            inPart.setPartID(partID);
            inPart.setPartName(partName);
            inPart.setPartQty(Integer.parseInt(partQty));
            inPart.setPartPrice(Double.parseDouble(partPrice));
            inPart.setPartMax(Integer.parseInt(partMax));
            inPart.setPartMin(Integer.parseInt(partMin));
            inPart.setMachineID(Integer.parseInt(partDynamic));
            Inventory.updatePart(partIndex, inPart);
            }
        else{
            OutsourcedPart outPart = new OutsourcedPart();
            outPart.setPartID(partID);
            outPart.setPartName(partName);
            outPart.setPartQty(Integer.parseInt(partQty));
            outPart.setPartPrice(Double.parseDouble(partPrice));
            outPart.setPartMax(Integer.parseInt(partMax));
            outPart.setPartMin(Integer.parseInt(partMin));           
            outPart.setCompanyName(partDynamic);
            Inventory.updatePart(partIndex, outPart);            
        }
       }
        Parent partSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(partSave);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }    
    catch (NumberFormatException e) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Error");
    alert.setHeaderText("Modifty Part Error.");
    alert.setContentText("The form contains invalid input.");
    alert.showAndWait();
}    
    }
    
    
 

    
    @FXML
    void modifyPartCancelAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel Modify Confirmation.");
        alert.setContentText("Are you sure you want to cancel part modification?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    Parent partCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                    Scene scene = new Scene(partCancel);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.show();
                }
                else {
                    System.out.println("Cancelled.");
                }
                      


    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part part = getAllParts().get(partIndex);
        partID = getAllParts().get(partIndex).getPartID();
        modifyPartIDInput.setText("Auto Generated: " + partID);
        modifyPartNameInput.setText(part.getPartName());
        modifyPartQtyInput.setText(Integer.toString(part.getPartQty()));
        modifyPartPriceInput.setText(Double.toString(part.getPartPrice()));
        modifyPartMinInput.setText(Integer.toString(part.getPartMin()));
        modifyPartMaxInput.setText(Integer.toString(part.getPartMax()));
        if(part instanceof InhousePart){
            modifyDynamicLabel.setText("Machine ID");
            modifyDynamicInput.setText(Integer.toString(((InhousePart) getAllParts().get(partIndex)).getMachineID()));
            InHouseRadio.setSelected(true);
        }
        else{
            modifyDynamicLabel.setText("Company Name");
            modifyDynamicInput.setText(((OutsourcedPart) getAllParts().get(partIndex)).getCompanyName());
            OutsourcedRadio.setSelected(true);
        }
    }    

  


    
}
