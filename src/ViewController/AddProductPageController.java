/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.Inventory;
import static Model.Inventory.getAllParts;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lasac
 */
public class AddProductPageController implements Initializable {

    @FXML
    private TextField productIDInput;
    @FXML
    private TextField productNameInput;
    @FXML
    private TextField productQtyInput;
    @FXML
    private TextField productPriceInput;
    @FXML
    private TextField productMinInput;
    @FXML
    private TextField productMaxInput;
    @FXML
    private Button productSaveButton;
    @FXML
    private Button productCancelButton;
    @FXML
    private Button productDeleteButton;
    @FXML
    private Button productAddButton;
    @FXML
    private TableView<Part> productAddList;
    @FXML
    private TableColumn<Part, Integer> productAddIDColumn;
    @FXML
    private TableColumn<Part, String> productAddNameColumn;
    @FXML
    private TableColumn<Part, Integer> productAddQtyColumn;
    @FXML
    private TableColumn<Part, Double> productAddPriceColumn;
    @FXML
    private TableView<Part> productDeleteList;
    @FXML
    private TableColumn<Part, Integer> productDeleteIDColumn;
    @FXML
    private TableColumn<Part, String> productDeleteNameColumn;
    @FXML
    private TableColumn<Part, Integer> productDeleteQtyColumn;
    @FXML
    private TableColumn<Part, Double> productDeletePriceColumn;
    @FXML
    private TextField productSearchInput;
    @FXML
    private Button productSearchButton;

    private String excMessage = new String();
    private int productID;
    
    private final ObservableList<Part> currentParts = FXCollections.observableArrayList();
    
    
        @FXML
    void productSearchAction(ActionEvent event) {
        String term = productSearchInput.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(term);
        if(foundParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No matching part was found");
            alert.setHeaderText("Your search returned no partchs matching " + term);
            alert.showAndWait();
        }
            else{
                 productAddList.setItems(foundParts);   
                    }
        }
    
    

    @FXML
    void productAddAction(ActionEvent event) {
        Part part = productAddList.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updatePartListDelete();   
    }

    
    
        @FXML
    void productDeleteAction(ActionEvent event) {
        Part part = productAddList.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete");
        alert.setHeaderText("Confirm Delete");
        alert.setContentText("Are you sure you want to delete the part, " + part.getPartName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            System.out.println("Deletion was successful.");
            currentParts.remove(part);
        }
        else{
            System.out.println("Deletion was cancelled.");
        }
    }

    
    
        @FXML
    void productSaveAction(ActionEvent event) throws IOException{
        String productName = productNameInput.getText();
        String productQty = productQtyInput.getText();
        String productPrice = productPriceInput.getText();
        String productMax = productMaxInput.getText();
        String productMin = productMinInput.getText();
        
        
        try{
            excMessage = Product.isProductValid(productName, Double.parseDouble(productPrice), Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productQty), currentParts, excMessage);
            if(excMessage.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Product Add Error.");
                alert.setContentText(excMessage);
                alert.showAndWait();
                excMessage = "";
                      }
            else{
                System.out.println("Product name: " + productName);
                Product newProduct = new Product();
                newProduct.setProductID(productID);
                newProduct.setProductName(productName);
                newProduct.setProductPrice(Double.parseDouble(productPrice));
                newProduct.setProductQty(Integer.parseInt(productQty));
                newProduct.setProductMin(Integer.parseInt(productMin));
                newProduct.setProductMax(Integer.parseInt(productMax));
                newProduct.setAssociatedParts(currentParts);
                Inventory.addProduct(newProduct);
            }
            
            Parent modifyProductSaveParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(modifyProductSaveParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Product add error.");
            alert.setContentText("The form contains invalid input.");
            alert.showAndWait();
        }
    }
    
    
    
        @FXML
    void productCancelAction(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancellation Confirmation");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Parent cancelAddProduct = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(cancelAddProduct);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        else{
            System.out.println("Operation cancelled.");
        }
    }
    
        
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       productAddIDColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartID().asObject());
       productAddNameColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartName());
       productAddQtyColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartQty().asObject());
       productAddPriceColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartPrice().asObject());
       
       productDeleteIDColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartID().asObject());
       productDeleteNameColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartName());
       productDeleteQtyColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartQty().asObject());
       productDeletePriceColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartPrice().asObject());
       updatePartListAdd();
       updatePartListDelete();
       productID = Inventory.getPartIDCount();
       productIDInput.setText("Auto Generated: " + productID);
    }    



public void updatePartListAdd(){
    productAddList.setItems(getAllParts());
}


public void updatePartListDelete(){
    productDeleteList.setItems(currentParts);   
}




 
    
}
