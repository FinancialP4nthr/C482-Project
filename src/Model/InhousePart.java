/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author lasac
 */
public class InhousePart extends Part{
    
    private IntegerProperty machineID;

    public InhousePart(int partID, String partName, int partQty, double partPrice, int partMin, int partMax, int machineID) {
        this.partID = new SimpleIntegerProperty(partID);
        this.partName = new SimpleStringProperty(partName);
        this.partQty = new SimpleIntegerProperty(partQty);
        this.partPrice = new SimpleDoubleProperty(partPrice);
        this.partMin = new SimpleIntegerProperty(partMin);
        this.partMax = new SimpleIntegerProperty(partMax); 
        this.machineID = new SimpleIntegerProperty(machineID);
    }
    
    public InhousePart() {
        this.partID = new SimpleIntegerProperty(0);
        this.partName = new SimpleStringProperty("");
        this.partQty = new SimpleIntegerProperty(0);
        this.partPrice = new SimpleDoubleProperty(0);
        this.partMin = new SimpleIntegerProperty(0);
        this.partMax = new SimpleIntegerProperty(0); 
        this.machineID = new SimpleIntegerProperty(0);
    }
    

    public int getMachineID() {
        return this.machineID.get();
    }
    
    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
    
}
