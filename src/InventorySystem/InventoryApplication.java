/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventorySystem;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author lasac
 */
public class InventoryApplication extends Application {
    

    @Override
    public void start(Stage stage) throws Exception {
        
        Inventory inv = new Inventory();
        addTestData(inv);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(InventoryApplication.class.getResource("/ViewController/MainScreen.fxml"));
        Parent main = loader.load();
        
        Scene scene = new Scene(main);
        
        stage.setScene(scene);
        stage.show();
    }
    
    
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InventoryApplication.launch(args);
    }

public void addTestData(Inventory _inv) {
        //Add InHouse Parts
        //Part(int _id, String _name, double _price, int _stock, int _min, int _max){}; //constructor.
        //public InHouse(int _id, String _name, double _price, int _stock, int _min, int _max, int _machineId)
        Part a = new InhousePart(1, "Part A", 10, 2.99, 5, 100, 222);
        Part b1 = new InhousePart(3, "Part B1", 11, 4.99, 5, 100, 111);
        Part b2 = new InhousePart(2, "Part B2", 9, 3.99, 5, 100, 333);
        _inv.addPart(a);
        _inv.addPart(b1);
        _inv.addPart(b2);
        _inv.addPart(new InhousePart(4, "Part C1", 15, 5.99, 5, 100, 444));
        _inv.addPart(new InhousePart(5, "Part C2", 5, 6.99, 5, 100, 555));
        //Add Outsourced Parts
        //Part(int _id, String _name, double _price, int _stock, int _min, int _max){}; //constructor.
        //public OutsourcePart(int _id, String _name, double _price, int _stock, int _min, int _max, String _companyName){
        Part d1 = new OutsourcedPart(6, "Part D1", 10, 2.99, 5, 100, "Part Co.");
        Part d2 = new OutsourcedPart(7, "Part D2", 9, 3.99, 5, 100, "Part Co.");
        Part f = new OutsourcedPart(8, "Part F", 10, 2.99, 5, 100, "Manufacturing Co.");
        _inv.addPart(d1);
        _inv.addPart(d2);
        _inv.addPart(f);
        _inv.addPart(new OutsourcedPart(9, "Part G1", 10, 2.99, 5, 100, "Manufacturing Co."));
        _inv.addPart(new OutsourcedPart(10, "Part G2", 10, 2.99, 5, 100, "Business Co."));
        //Add Products
        //public Product(int _id, String _name, double _price, int _stock, int _min, int _max){}; //constructor
        Product prod1 = new Product(1, "Product 1", 20, 9.99, 5, 100);
        _inv.addProduct(prod1);
        prod1.addAssociatedPart(a);
        prod1.addAssociatedPart(d1);
        Product prod2 = new Product(2, "Product 2", 22, 9.99, 5, 100);
        _inv.addProduct(prod2);
        prod1.addAssociatedPart(b1);
        prod1.addAssociatedPart(d2);
        Product prod3 = new Product(3, "Product 3", 30, 9.99, 5, 100);
        _inv.addProduct(prod3);
        prod1.addAssociatedPart(b2);
        prod1.addAssociatedPart(f);
        _inv.addProduct(new Product(4, "Product 4", 20, 29.99, 5, 100));
        _inv.addProduct(new Product(5, "Product 5", 9, 29.99, 5, 100));
    }


}

