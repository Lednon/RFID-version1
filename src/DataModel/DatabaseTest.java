/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fresh
 */
public class DatabaseTest {
    public static void main(String[] args){
        DataBase db = new DataBase();
        try {
            db.connect();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            System.out.println("find tag"+db.returnTagVerification(""));
            db.returnUnsoldProductTagIDDetail();
            for (Product type: db.getProduct()){
                System.out.println(type.getProductName()+"  "+type.getTagID());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.disconnect();
    }
}
