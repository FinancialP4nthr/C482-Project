/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author lasac
 */
public class OutsourcedPart extends Part {
    
    private StringProperty companyName;
    
    public OutsourcedPart(int partID, String partName, int partQty, double partPrice, int partMin, int partMax, String companyName) {
        this.partID = new SimpleIntegerProperty(partID);
        this.partName = new SimpleStringProperty(partName);
        this.partQty = new SimpleIntegerProperty(partQty);
        this.partPrice = new SimpleDoubleProperty(partPrice);
        this.partMin = new SimpleIntegerProperty(partMin);
        this.partMax = new SimpleIntegerProperty(partMax);
        this.companyName = new SimpleStringProperty(companyName);
    }
    
    public OutsourcedPart(){
        this.partID = new SimpleIntegerProperty(0);
        this.partName = new SimpleStringProperty("");
        this.partQty = new SimpleIntegerProperty(0);
        this.partPrice = new SimpleDoubleProperty(0);
        this.partMin = new SimpleIntegerProperty(0);
        this.partMax = new SimpleIntegerProperty(0);
        this.companyName = new SimpleStringProperty("");
    }
         
    public String getCompanyName() {
        return this.companyName.get();
    }
    
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
    
}
