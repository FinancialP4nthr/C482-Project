/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.control.TextField;
import Model.Inventory;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author lasac
 */
public abstract class Part {
    protected IntegerProperty partID;
    protected StringProperty partName;
    protected IntegerProperty partQty;
    protected DoubleProperty partPrice;
    protected IntegerProperty partMin;
    protected IntegerProperty partMax;
    /*private String companyNameOrMachineID;
    boolean inHouse = true;
    int associatedPartID = -1;
    
    public static int numPart;*/
    
    public Part(){
        partID = new SimpleIntegerProperty();
        partName = new SimpleStringProperty();
        partQty = new SimpleIntegerProperty();
        partPrice = new SimpleDoubleProperty();
        partMin = new SimpleIntegerProperty();
        partMax = new SimpleIntegerProperty();
    }
    
    /*public Part(int partID, String partName, int partQty, double partPrice, int partMax, int partMin) {
        
        this.partID = ++numPart;
        this.partName = partName;
        this.partQty = partQty;
        this.partPrice = partPrice;
        this.partMax = partMax;
        this.partMin = partMin;
        this.companyNameOrMachineID = companyNameOrMachineID;
        this.inHouse = inHouse;
        this.associatedPartID = associatedPartID; */


    public IntegerProperty propertyPartID(){
        return partID;
    }
    
    public StringProperty propertyPartName(){
        return partName;
    }
    
    public DoubleProperty propertyPartPrice(){
        return partPrice;
    }
    
    public IntegerProperty propertyPartQty(){
        return partQty;
    }

    
     public int getPartID() {
        return this.partID.get();
    }

    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public String getPartName() {
        return this.partName.get();
    }

    public void setPartName(String partName) {
        this.partName.set(partName);
    }

    public int getPartQty() {
        return this.partQty.get();
    }

    public void setPartQty(int partQty) {
        this.partQty.set(partQty);
    }

    public double getPartPrice() {
        return this.partPrice.get();
    }

    public void setPartPrice(double partPrice) {
        this.partPrice.set(partPrice);
    }  

    public int getPartMin() {
        return this.partMin.get();
    }

    public void setPartMin(int partMin) {
        this.partMin.set(partMin);
    }
    
    public int getPartMax() {
        return this.partMax.get();
    }

    public void setPartMax(int partMax) {
        this.partMax.set(partMax);
    }

   /* public String getCompanyNameOrMachineID() {
        return companyNameOrMachineID;
    }

    public void setCompanyNameOrMachineID(String companyNameOrMachineID) {
        this.companyNameOrMachineID = companyNameOrMachineID;
    }
        public int getAssociatedPartID() {
        return associatedPartID;
    }

    public void setAssociatedPartID(int associatedPartID) {
        this.associatedPartID = associatedPartID;
    }

    public boolean isInHouse() {
        return inHouse;
    }

    public void setInHouse(boolean inHouse) {
        this.inHouse = inHouse;
    }*/

    
    public static String isPartValid(String partName, double partPrice, int partQty, int partMin, int partMax, String errorMessage){
        if(partName.equals("")){
            errorMessage = errorMessage + "The name field cannot be empty.";           
        }
        else if(partPrice <= 0){
            errorMessage = errorMessage + "Part price cannot be less than $0.00.";
        }
        else if(partQty <= 0){
            errorMessage = errorMessage + "Part on-hand cannot be negative or zero.";
        }
        else if(partMax < partMin){
            errorMessage = errorMessage + "Item minimum number of units must be less than item maximum.";
        }
        else if(partQty < partMin || partMax < partQty){
            errorMessage = errorMessage + "Inventory on-hand quantity must be between the minimum and maximum inventory thresholds.";
        }
        return errorMessage;
    }
    /*
    public boolean isPartValid() throws ValidationException {

        //Prohibit Unnamed Products - "Rule of Acquistion #239: Never be afraid to mislabel a product."
        if (getPartName().equals("")) { //Exception Controls Set 2
            throw new ValidationException("The name field cannot be empty.");
        }

        //Ensure price is greater than zero - "Rule of Acquistion #37: If it's free, take it and worry about hidden costs later."
        if (getPartPrice() < 0) { //Exception Controls Set 2
            throw new ValidationException("Part price cannot be less than $0.00.");
        }

        //Ensure positive inventory level - "Rule of Acquistion #242: More is good... all is better."
        if (getPartQty() < 0) { //Exception Controls Set 1
            throw new ValidationException("Part on-hand cannot be negative.");
        }

        //Prohibit Out of Stock Inventory level - "Rule of Acquistion #109: Dignity and an empty sack is worth the sack."
        if (getPartMin() < 0) { //Exception Controls Set 1
            throw new ValidationException("The minimum inventory oh-hand quantity must be greater than zero units.");
        }

        //Ensure maximum inventory is greater than minimum inventory - "Rule of Acquistion #125: You can't make a deal if you're dead."
        if (getPartMin() > getPartMax()) { //Exception Controls Set 1
            throw new ValidationException("Item minimum number of units must be less than item maximum.");
        }

        //Ensure current inventory quantity falls between the defined Minimum and Maximum values - "Rule of Acquistion #126: Count it."
        if (getPartQty() < getPartMin() || getPartQty() > getPartMax()) { //Exception Controls Set 1
            throw new ValidationException("Inventory on-hand quantity must be between the minimum and maximum inventory thresholds.");
        }

        return true;
    }*/
}
