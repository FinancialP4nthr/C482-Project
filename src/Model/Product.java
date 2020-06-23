/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.*;
/**
 *
 * @author lasac
 */
public class Product {
    
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private final IntegerProperty productID;
    private final StringProperty productName;
    private final IntegerProperty productQty;
    private final DoubleProperty productPrice;
    private final IntegerProperty productMin;
    private final IntegerProperty productMax;
    
    public Product(){
        productID = new SimpleIntegerProperty(0);
        productName = new SimpleStringProperty("");
        productQty = new SimpleIntegerProperty(0);
        productPrice = new SimpleDoubleProperty(0);
        productMin = new SimpleIntegerProperty(0);
        productMax = new SimpleIntegerProperty(0);
    }
    
    public Product(int productID, String productName, int productQty, double productPrice, int productMin, int productMax){
        
        this.productID = new SimpleIntegerProperty(productID);
        this.productName = new SimpleStringProperty(productName);
        this.productQty = new SimpleIntegerProperty(productQty);
        this.productPrice = new SimpleDoubleProperty(productPrice);
        this.productMin = new SimpleIntegerProperty(productMin);
        this.productMax = new SimpleIntegerProperty(productMax);
        
    }

    public IntegerProperty propertyProductID(){
        return productID;
    }
    
    public StringProperty propertyProductName(){
        return productName;
    }
    
    public IntegerProperty propertyProductQty(){
        return productQty;        
    }
    
    public DoubleProperty propertyProductPrice(){
        return productPrice;
    }
    
    
    public int getProductID() {
        return this.productID.get();
    }
    
    public void setProductID(int productID) {
        this.productID.set(productID);
    }
    
        public String getProductName() {
        return this.productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }
    
        public int getProductQty() {
        return this.productQty.get();
    }

    public void setProductQty(int productQty) {
        this.productQty.set(productQty);
    }

    public double getProductPrice() {
        return this.productPrice.get();
    }

    public void setProductPrice(double productPrice) {
        this.productPrice.set(productPrice);
    }
    
        public int getProductMin() {
        return this.productMin.get();
    }

    public void setProductMin(int productMin) {
        this.productMin.set(productMin);
    }
        
    public int getProductMax() {
        return this.productMax.get();
    }

    public void setProductMax(int productMax) {
        this.productMax.set(productMax);
    }

    public void addAssociatedParts(Part associatedPart){
        associatedParts.add(associatedPart);
    }
    
    public ObservableList<Part> getAssociatedParts(){
        return associatedParts;
    }
    
    public void setAssociatedParts(ObservableList<Part> associatedParts){
        this.associatedParts = associatedParts;
    }
    
    
    public void addAssociatedPart(Part associatedPart){
        this.associatedParts.add(associatedPart);
    }
    
    public boolean deleteAssociatedPart(int partID){
        for(Part p : associatedParts){
            if(p.getPartID() == partID){
                associatedParts.remove(p);
                return true;
            }
        }
        return false;
    }
    
    public Part searchAssociatedPart(int partID){
        for (Part p : associatedParts) {
            if(p.getPartID() == partID){
                return p;
            }
        }
        return null;
    }
    
    
    
    
    
    public static String isProductValid(String productName, double productPrice, int productQty, int productMin, int productMax, ObservableList<Part> assocatedParts, String errorMessage){
        double partsSum = 0.00;
        for(int i = 0; i < associatedParts.size(); i++){
            partsSum += associatedParts.get(i).getPartPrice();
        }
            if(productName.equals("")){
                errorMessage = errorMessage + "Product name field cannot be blank.";
            }
            else if(productPrice < 0){
                errorMessage = errorMessage + "Product price cannot be less than $0.00.";
            }
            else if(productPrice < partsSum){
                errorMessage = errorMessage + "The Product's price must be greater than the sum of the price of the parts.";
            }
            else if(associatedParts.size() < 1){
                errorMessage = errorMessage + "All products must contain at lease one part.";
            }
            else if(productQty < 1){
                errorMessage = errorMessage + "On hand quantity cannot be negative.";
            }
            else if(productMin > productMax){
                errorMessage = errorMessage + "Item minimum must be less than item maximum.";
            }
            else if(productQty < productMin || productQty > productMax){
                errorMessage = errorMessage + "Inventory on-hand quantity must be between the minimum and maximum values.";
            }          
      return errorMessage;
    
    }



 
 
}