package ViewController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import InventorySystem.InventoryApplication;
import Model.Part;
import Model.Product;
import Model.Inventory;
import Model.InhousePart;
import static Model.Inventory.deletePart;
import static Model.Inventory.deleteProduct;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import static Model.Inventory.getPartIDCount;
import static Model.Inventory.validateDeletePart;
import static Model.Inventory.validateDeleteProduct;
import Model.OutsourcedPart;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *
 * @author lasac
 */
public class MainScreenController implements Initializable {
    
    /*private Label label;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    */
    
    @FXML private Label mainScreenLabel;  
    @FXML private Button exitButton;
    
    // ****************************************Parts Search****************************************
    @FXML private Button partsSearchButton;
    @FXML private TextField partsSearchBox;
    
    // ****************************************Parts Buttons****************************************
    @FXML private Button partsAddButton;
    @FXML private Button partsModifyButton;
    @FXML private Button partsDeleteButton;
    
    // ****************************************Products Search****************************************
    @FXML private Button productsSearchButton;
    @FXML private TextField productsSearchBox;
    
    // ****************************************Products Buttons****************************************
    @FXML private Button productsAddButton;
    @FXML private Button productsModifyButton;
    @FXML private Button productsDeleteButton;
    
    // ****************************************Parts Table****************************************
    @FXML private TableView<Part> partsList;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partQtyColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    
    // ****************************************Products Table****************************************
    @FXML private TableView<Product> productList;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productQtyColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    
    
    
    
    private static Part modifyPart;
    
    private static int modifyPartIndex;
    
    public static int changePartIndex(){
        return modifyPartIndex;
    }
    private static Product modifyProduct;
    
    private static int modifyProductIndex;
    
    public static int changeProductIndex(){
        return modifyProductIndex;
    }
    
    public MainScreenController(){
    }
    
    
    
    @FXML void exitAction(ActionEvent event) throws IOException{
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Exit Confirmation.");
        alert.setHeaderText("Exit Confirmation.");
        alert.setContentText("Do you wish to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            System.exit(0);
        }
    }
    
    
    
    // ****************************************Parts Button Actions****************************************    
    
    @FXML void partSearchAction(ActionEvent event){
        String term = partsSearchBox.getText();
        ObservableList<Part> foundParts = Inventory.searchPart(term);
        if(foundParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No matching part was found.");
            alert.setHeaderText("No Names matching " + term);
            alert.showAndWait();
        }
        else{
            partsList.setItems((ObservableList<Part>) foundParts);
        }
    }
    
   @FXML void  partsAddAction(ActionEvent event) throws IOException{                        //NEED TO ADD METHODS FOR THESE
       openAddPartScreen(event);
   }
    
   @FXML void partsModifyAction(ActionEvent event) throws IOException{                     //NEED TO ADD METHODS FOR THESE                                    
       openModifyPartScreen(event);
   } 
    
   @FXML void partsDeleteAction(ActionEvent event) throws IOException{
       Part part = partsList.getSelectionModel().getSelectedItem();
       if(validateDeletePart(part)){
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Delete Error");
           alert.setHeaderText("This part cannot be removed.");
           alert.setContentText("This part is currently used for an existing product.");
           alert.showAndWait();
       }
       else{
           Alert alert = new Alert(AlertType.CONFIRMATION);
           alert.initModality(Modality.NONE);
           alert.setTitle("Delete Part");
           alert.setHeaderText("Please Confirm:");
           alert.setContentText("Are you sure you want to delete " + part.getPartName() + "?");
           Optional<ButtonType> result = alert.showAndWait();
           if(result.get() == ButtonType.OK){
               deletePart(part);
               updatePartTable();                                                           //NEED TO ADD METHODS FOR THESE
               System.out.println(part.getPartName() + " was successfully removed.");
           }
           else{
               System.out.println(part.getPartName() + " was not removed.");
           }
       }
   }
   
   
   
    
    
    // ****************************************Products Button Actions****************************************    
    
    @FXML void productSearchAction(ActionEvent event){
        String term = productsSearchBox.getText();
        ObservableList<Product> foundProducts = Inventory.searchProduct(term);
        if(foundProducts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No matching product was found.");
            alert.setHeaderText("No Names matching " + term);
            alert.showAndWait();
        }
        else{
            productList.setItems(foundProducts);            
        }
    }
    
    @FXML void  productsAddAction(ActionEvent event) throws IOException{                        //NEED TO ADD METHODS FOR THESE
       openAddProductScreen(event);
   }
    
   @FXML void productsModifyAction(ActionEvent event) throws IOException{                     //NEED TO ADD METHODS FOR THESE                                    
       openModifyProductScreen(event);
   } 
    
   @FXML void productsDeleteAction(ActionEvent event) throws IOException{
       Product product = productList.getSelectionModel().getSelectedItem();
       if(validateDeleteProduct(product)){
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Delete Error");
           alert.setHeaderText("This product cannot be removed.");
           alert.setContentText("This product currently contains at least one part.");
           alert.showAndWait();
       }
       else{
           Alert alert = new Alert(AlertType.CONFIRMATION);
           alert.initModality(Modality.NONE);
           alert.setTitle("Delete Product");
           alert.setHeaderText("Please Confirm:");
           alert.setContentText("Are you sure you want to delete " + product.getProductName() + "?");
           Optional<ButtonType> result = alert.showAndWait();
           if(result.get() == ButtonType.OK){
               deleteProduct(product);
               updateProductTable();                                                           //NEED TO ADD METHODS FOR THESE
               System.out.println(product.getProductName() + " was successfully removed.");
           }
           else{
               System.out.println(product.getProductName() + " was not removed.");
           }
       }
   }
    
    
    
    
      /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
   @Override
   public void initialize(URL url, ResourceBundle rb){
       partIDColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartID().asObject());
       partNameColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartName());
       partQtyColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartQty().asObject());
       partPriceColumn.setCellValueFactory(cellData -> cellData.getValue().propertyPartPrice().asObject());
       
       productIDColumn.setCellValueFactory(cellData -> cellData.getValue().propertyProductID().asObject());
       productNameColumn.setCellValueFactory(cellData -> cellData.getValue().propertyProductName());
       productQtyColumn.setCellValueFactory(cellData -> cellData.getValue().propertyProductQty().asObject());
       productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().propertyProductPrice().asObject());
       
       updatePartTable();
       updateProductTable();
   }
   
   

   public void updatePartTable() {
      partsList.setItems(getAllParts());
   }
   
   public void updateProductTable() {
       productList.setItems(getAllProducts());
   }
   
   
   
    // ****************************************Open Parts Windows****************************************
   
   private void openModifyPartScreen(ActionEvent event) throws IOException {
       modifyPart = partsList.getSelectionModel().getSelectedItem();
       modifyPartIndex = getAllParts().indexOf(modifyPart);
       Parent modifyPartParent = FXMLLoader.load(getClass().getResource("ModifyPartScreen.fxml"));
       Scene modifyPartScene = new Scene(modifyPartParent);
       Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       modifyPartStage.setScene(modifyPartScene);
       modifyPartStage.show();               
   }
   
   private void openAddPartScreen(ActionEvent event) throws IOException {
       Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));
       Scene addPartScene = new Scene(addPartParent);
       Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       addPartStage.setScene(addPartScene);
       addPartStage.show();
   }
   
   // ****************************************Open Products Windows****************************************
    
   private void openModifyProductScreen(ActionEvent event) throws IOException {
    modifyProduct = productList.getSelectionModel().getSelectedItem();
    modifyProductIndex = getAllProducts().indexOf(modifyProduct);
    Parent modifyProductParent = FXMLLoader.load(getClass().getResource("ModifyProductPage.fxml"));
    Scene modifyProductScene = new Scene(modifyProductParent);
    Stage modifyProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    modifyProductStage.setScene(modifyProductScene);
    modifyProductStage.show();
   }
       
   private void openAddProductScreen(ActionEvent event) throws IOException {    
       Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProductPage.fxml"));
       Scene addProductScene = new Scene(addProductParent);
       Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       addProductStage.setScene(addProductScene);
       addProductStage.show();
   }
   
   
   
   
   
   
   
   
   public void setMainApp(InventoryApplication mainApp) {
       updatePartTable();
       updateProductTable();
   }
   
   private void defaultParts() {
       int partID = Inventory.getPartIDCount();
       Inventory.addPart(new InhousePart(partID, "Part A", 7, 35.99, 1, 20, 45));
       Inventory.addPart(new OutsourcedPart(getPartIDCount(), "Part B", 5, 49.99, 3, 30, "Generic Co."));
      }
   
   private void defaultProducts() {
       int productID = Inventory.getProductIDCount();
       ObservableList<Part> noParts = FXCollections.observableArrayList();
       Product firstProduct = new Product(productID, "No Parts", 6, 999.99, 1, 110);
       firstProduct.setAssociatedParts(noParts);
       Inventory.addProduct(firstProduct);
       
       ObservableList<Part> checkPart = Inventory.lookupPart("Part A");
       Product secondProduct = new Product(Inventory.getProductIDCount(), "Product 1", 10, 333.22, 1, 10);
       secondProduct.setAssociatedParts(checkPart);
       Inventory.addProduct(secondProduct);
   }
   
   
   public void setDefaultValues(){
       defaultParts();
       defaultProducts();
   }
   
}
    




     /*   partsList.setItems(Inventory.getAllParts());
        
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partQtyColumn.setCellValueFactory(new PropertyValueFactory<>("partQty"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        
        
        productList.setItems(Inventory.getAllProducts());
        
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productQtyColumn.setCellValueFactory(new PropertyValueFactory<>("productQty"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    
}
*/
