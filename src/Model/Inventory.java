/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.Inventory.getAllParts;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author lasac
 */
public class Inventory {
    public static ObservableList<Part> parts = FXCollections.observableArrayList();
    public static ObservableList<Product> products = FXCollections.observableArrayList();
    public static int partIDCount = 0;
    public static int productIDCount = 0;
    
    
    
    
    
    
    
    // ****************************************Parts Inventory****************************************    
    
    public static int getPartIDCount(){
        partIDCount++;
        return partIDCount;
    }
    
    public static void addPart(Part part){
        parts.add(part);
    }
    
    public static void deletePart(Part part){
        parts.remove(part);
    }
    
    public static ObservableList<Part> getAllParts(){
        return parts;
    }
    
    public static boolean validateDeletePart(Part part){
        boolean inProduct = false;
        for (Product product : products){
            if(product.getAssociatedParts().contains(part)){
                inProduct = true;
            }
        }
        return inProduct;
    }
    
    public static ObservableList<Part> searchPart(String searchParts){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        
        if(searchParts.length() == 0){
            foundParts = parts;
        }
        else{
            for(int i = 0; i <parts.size(); i++){
                if (parts.get(i).getPartName().toLowerCase().contains(searchParts.toLowerCase())){
                    foundParts.add(parts.get(i));
                }
            }
        }
        return foundParts;
    }
    
    public static void updatePart(int indexNum, Part part){
        parts.set(indexNum, part);
    }
    
    public static boolean isInt(String input){
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
    
    
    
    
    
    
    
    
    // ****************************************Product Inventory****************************************
    
    public static int getProductIDCount() {
        productIDCount++;
        return productIDCount;
    }
    
    public static void addProduct (Product product){
        products.add(product);
    }
    
    public static void deleteProduct(Product product){
        products.remove(product);
    }
    
    public static ObservableList<Product> getAllProducts(){
        return products;
    }
    
    public static boolean validateDeleteProduct(Product product){
        return product.getAssociatedParts() == null;
    }
    
    public static ObservableList<Product> searchProduct(String searchProducts){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        
        if(searchProducts.length() == 0){
            foundProducts = products;
        }
        else{
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getProductName().toLowerCase().contains(searchProducts.toLowerCase())){
                    foundProducts.add(products.get(i));
                }
            }
        }
        return foundProducts;
    }
    
    public static void updateProduct(int indexNum, Product product){
        products.set(indexNum, product);
    }

    public static ObservableList lookupPart(String searchInput) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        if(searchInput.length() == 0){
            foundParts = parts;
        }
        else {
            for(int i = 0; i < parts.size(); i++){
                if(parts.get(i).getPartName().toLowerCase().contains(searchInput.toLowerCase())){
                    foundParts.add(parts.get(i));
                }
            }
        }
        return foundParts;
    }
}
