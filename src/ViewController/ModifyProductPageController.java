/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import Model.Inventory;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import Model.Part;
import Model.Product;
import static ViewController.MainScreenController.changeProductIndex;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lasac
 */
public class ModifyProductPageController implements Initializable {

    @FXML
    private VBox modifyProduct;
    @FXML
    private TextField modifyProductIDInput;
    @FXML
    private TextField modifyProductNameInput;
    @FXML
    private TextField modifyProductQtyInput;
    @FXML
    private TextField modifyProductPriceInput;
    @FXML
    private TextField modifyProductMinInput;
    @FXML
    private TextField modifyProductMaxInput;
    @FXML
    private Button modifyProductSaveButton;
    @FXML
    private Button modifyProductCancelButton;
    @FXML
    private Button modifyProductDeleteButton;
    @FXML
    private Button modifyProductAddButton;
    @FXML
    private TableView<Part> modifyProductAddList;
    @FXML
    private TableColumn<Part, Integer> modifyProductAddIDColumn;
    @FXML
    private TableColumn<Part, String> modifyProductAddNameColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductAddQtyColumn;
    @FXML
    private TableColumn<Part, Double> modifyProductAddPriceColumn;
    @FXML
    private TableView<Part> modifyProductDeleteList;
    @FXML
    private TableColumn<Part, Integer> modifyProductDeleteIDColumn;
    @FXML
    private TableColumn<Part, String> modifyProductDeleteNameColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductDeleteQtyColumn;
    @FXML
    private TableColumn<Part, Double> modifyProductDeletePriceColumn;
    @FXML
    private TextField modifyProductSearchInput;
    @FXML
    private Button modifyProductSearchButton;

    private ObservableList<Part> modifyCurrentParts = FXCollections.observableArrayList();
    
    private String excMessage = new String();
    private int productID;
    private final int productIndex = changeProductIndex();
  
    
    
    
        @FXML
        void modifyProductSearchAction(ActionEvent event) {
        String input = modifyProductSearchInput.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(input);
        if(foundParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Part Found");
            alert.setHeaderText("No Parts were found that match the search " + input);
            alert.showAndWait();
        }
            else{
                    modifyProductAddList.setItems((ObservableList<Part>) foundParts);
                    }
        }
    
    
    



   @FXML
    void modifyProductAddAction(ActionEvent event) {
        Part part = modifyProductAddList.getSelectionModel().getSelectedItem();
        modifyCurrentParts.add(part);
        updateModifyPartListDelete();
    }

    
    
    
        @FXML
    void modifyProductDeleteAction(ActionEvent event) {
        Part part = modifyProductDeleteList.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete " + part.getPartName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            System.out.println("Deletion was completed successfully.");
            modifyCurrentParts.remove(part);
        }
        else{
            System.out.println("Delete operation was cancelled.");
        }
    }
    
    
    
        @FXML
    void modifyProductCancelAction(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel Confirmation.");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Parent modifyProductCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene (modifyProductCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();    
        }
        else{
            System.out.println("Operation cancelled.");
        }
    }
    
    
    
    
        @FXML
    void modifyProductSaveAction(ActionEvent event) throws IOException{
        String productName = modifyProductNameInput.getText();
        String productQty = modifyProductQtyInput.getText();
        String productPrice = modifyProductPriceInput.getText();
        String productMin = modifyProductMinInput.getText();
        String productMax = modifyProductMaxInput.getText();
        
        try{
            excMessage = Product.isProductValid(productName, Double.parseDouble(productPrice), Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productQty), modifyCurrentParts, excMessage);
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
                newProduct.setProductQty(Integer.parseInt(productQty));
                newProduct.setProductPrice(Double.parseDouble(productPrice));       
                newProduct.setProductMin(Integer.parseInt(productMin));
                newProduct.setProductMax(Integer.parseInt(productMax));
                newProduct.setAssociatedParts(modifyCurrentParts);
                Inventory.updateProduct(productIndex, newProduct);
                
                Parent modifyProductSaveParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(modifyProductSaveParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
            }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Modify Product Error.");
            alert.setContentText("The form contains invalit input.");
            alert.showAndWait();
        }
    }

    
    
    

    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product product = getAllProducts().get(productIndex);
        productID = getAllProducts().get(productIndex).getProductID();
        
        modifyProductIDInput.setText("Auto Generated: " + productID);
        modifyProductNameInput.setText(product.getProductName());
        modifyProductQtyInput.setText(Integer.toString(product.getProductQty()));
        modifyProductPriceInput.setText(Double.toString(product.getProductPrice()));
        modifyProductMinInput.setText(Integer.toString(product.getProductMin()));
        modifyProductMaxInput.setText(Integer.toString(product.getProductMax()));
        modifyCurrentParts = product.getAssociatedParts();
        
       modifyProductAddIDColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartID().asObject());
       modifyProductAddNameColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartName());
       modifyProductAddQtyColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartQty().asObject());
       modifyProductAddPriceColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartPrice().asObject());
       modifyProductDeleteIDColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartID().asObject());
       modifyProductDeleteNameColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartName());
       modifyProductDeleteQtyColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartQty().asObject());
       modifyProductDeletePriceColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartPrice().asObject());
       updateModifyPartListAdd();
       updateModifyPartListDelete();
}

public void updateModifyPartListAdd(){
    modifyProductAddList.setItems(getAllParts());
}

public void updateModifyPartListDelete(){
    modifyProductDeleteList.setItems(modifyCurrentParts);
}

}

