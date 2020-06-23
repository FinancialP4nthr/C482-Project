/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import Model.InhousePart;
import Model.OutsourcedPart;
import Model.Inventory;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.ValidationException;

/**
 *
 * @author lasac
 */
public class AddPartScreenController implements Initializable {
    
    @FXML private RadioButton outsourcedRadio;
    @FXML private RadioButton inhouseRadio;
    @FXML private TextField partIDInput;
    @FXML private TextField partNameInput;
    @FXML private TextField partQtyInput;
    @FXML private TextField partPriceInput;
    @FXML private TextField partMinInput;
    @FXML private TextField partMaxInput;
    @FXML private Label dynamicInputLabel;
    @FXML private TextField dynamicTextInput;
    
    private boolean isOutsourced;
    private String excMessage = new String();
    private int partID;


@FXML void addPartInHouseRB(ActionEvent event){
isOutsourced = false;
dynamicInputLabel.setText("Machine ID");
dynamicTextInput.setPromptText("Machine ID");
inhouseRadio.setSelected(true);
outsourcedRadio.setSelected(false);
}

@FXML void addPartOutsourcedRB(ActionEvent event){
isOutsourced = true;
dynamicInputLabel.setText("Company Name");
dynamicTextInput.setPromptText("Company Name");
outsourcedRadio.setSelected(true);
inhouseRadio.setSelected(false);
}




@FXML void addPartSaveAction(ActionEvent event) throws IOException{
String partName = partNameInput.getText();
String partQty = partQtyInput.getText();
String partPrice = partPriceInput.getText();
String partMax = partMaxInput.getText();
String partMin = partMinInput.getText();
String partDynamic = dynamicTextInput.getText();

try{
    excMessage = Part.isPartValid(partName, Double.parseDouble(partPrice), Integer.parseInt(partQty), Integer.parseInt(partMin), Integer.parseInt(partMax), excMessage);
    if(excMessage.length() > 0){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error");
           alert.setHeaderText("Part Add Error.");
           alert.setContentText(excMessage);
           alert.showAndWait();
           excMessage = "";
        }
    
    else{
        if(isOutsourced == false) {
        System.out.println("Part name: " + partName);
        InhousePart inPart = new InhousePart();
        inPart.setPartID(partID);
        inPart.setPartName(partName);
        inPart.setPartQty(Integer.parseInt(partQty));
        inPart.setPartPrice(Double.parseDouble(partPrice));
        inPart.setPartMin(Integer.parseInt(partMin));
        inPart.setPartMax(Integer.parseInt(partMax));
        inPart.setMachineID(Integer.parseInt(partDynamic));
        Inventory.addPart(inPart);
        }
        else{
        OutsourcedPart outPart = new OutsourcedPart();
        outPart.setPartID(partID);
        outPart.setPartName(partName);
        outPart.setPartQty(Integer.parseInt(partQty));
        outPart.setPartPrice(Double.parseDouble(partPrice));
        outPart.setPartMin(Integer.parseInt(partMin));
        outPart.setPartMax(Integer.parseInt(partMax));
        outPart.setCompanyName(partDynamic);
        Inventory.addPart(outPart);
}
Parent partSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
Scene scene = new Scene(partSave);
Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
window.setScene(scene);
window.show();
}
}
catch (NumberFormatException e) {
Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Error");
alert.setHeaderText("Add Part Error");
alert.setContentText("The form contains invalid input.");
alert.showAndWait();
}
}




@FXML void addPartCancelAction(ActionEvent event) throws IOException{
Alert alert = new Alert(AlertType.CONFIRMATION);
alert.initModality(Modality.NONE);
alert.setTitle("Please Confirm");
alert.setHeaderText("Confirm Delete Action.");
alert.setContentText("Are you sure you want to delete the part " + partNameInput.getText() + "?");
Optional<ButtonType> result = alert.showAndWait();

if(result.get() == ButtonType.OK){
Parent partCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
Scene scene = new Scene(partCancel);
Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
window.setScene(scene);
window.show();
}
else {
System.out.println("Cancelled.");
}
}


@Override public void initialize(URL url, ResourceBundle rb){
partID = Inventory.getPartIDCount();
partIDInput.setText("Auto Generated: " + partID);
}


}