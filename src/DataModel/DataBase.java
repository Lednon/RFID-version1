package DataModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author Fresh
 */
public class DataBase {
    private Connection connection;
    private ArrayList<Product> product;
    private int tagSearchValue, port;
    private Preferences preferences;
    private String databaseURL,databaseName, username, password, parentTable, childTable;
    private String dbURLDesc = "db URL", dbNameDesc = " db Name", userNameDesc = "User Name", passwordDesc = "db Password",
            parentTableDesc = "Parent Table", childTableDesc = "Child Table", portDesc = "Port Number";
    
    public DataBase(){
        //preferences = Preferences.userRoot().node("db");
        preferences = Preferences.userNodeForPackage(Preferences.class);
        product = new ArrayList<>();
        
        setDatabaseURL(getPreferences(dbURLDesc, ""));
        setDatabaseName(getPreferences(dbNameDesc, ""));
        setPort(getPreferences(portDesc, 3306));
        setUsername(getPreferences(userNameDesc, ""));
        setPassword(getPreferences(passwordDesc, ""));
        setParentTable(getPreferences(parentTableDesc, ""));
        setChildTable(getPreferences(childTableDesc, ""));
        
    }
    
    
    
    public String getDatabaseURL() {
        return databaseURL;
    }

    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getParentTable() {
        return parentTable;
    }

    public void setParentTable(String parentTable) {
        this.parentTable = parentTable;
    }

    public String getChildTable() {
        return childTable;
    }

    public void setChildTable(String childTable) {
        this.childTable = childTable;
    }
    
    
    
    //Querrying Software setting preferences
    public void setPreferences(String key, String value){
        preferences.put(key, value);
    }
    public void setPreferences(String key, int value){
        preferences.putInt(key, value);
    }
    public String getPreferences(String key, String customValue){
        return preferences.get(key, customValue);
    }
    public int getPreferences(String key, int customValue){
        return preferences.getInt(key, customValue);
    }

    public int getTagSearchValue() {
        return tagSearchValue;
    }

    public void setTagSearchValue(int tagSearchValue) {
        this.tagSearchValue = tagSearchValue;
    }
    
    public void setProduct(Product product){
        this.product.add(product);
    }
    
    public ArrayList<Product> getProduct(){
        return product;
    }
    
    public void clearData(){
        product.clear();
    }
    
    public void connect() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = getDatabaseURL();
            connection = DriverManager.getConnection(url, getUsername(), getPassword());
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int returnTagVerification(String ID) throws SQLException{
        String findTagSQL = "select count(*) as count from "+getPreferences(childTableDesc, "") +" where tagId=?";
        PreparedStatement findTagStatement = connection.prepareStatement(findTagSQL);
        findTagStatement.setString(1, ID);
        
        ResultSet findResult = findTagStatement.executeQuery();
        findResult.next();
        setTagSearchValue(findResult.getInt(1));
        
        //System.out.println("search: "+ID+" outcome: "+getTagSearchValue());
        findTagStatement.close();
        findResult.close();
        return getTagSearchValue();
    }
    
    public void returnProductTagIDDetail(String ID) throws SQLException{
        String loadString = "select itemIdfk, tagId, tagType, status  from "+getPreferences(childTableDesc, "") +" where tagId=?";
        PreparedStatement loadStatement = connection.prepareStatement(loadString);
        loadStatement.setString(1, ID);
        ResultSet result = loadStatement.executeQuery();
        
        while(result.next()){
            String tagID = result.getString("tagID");
            String productName = result.getString("itemName");
            
            Product productItem  = new Product(tagID, productName);
            this.product.add(productItem);
        }
        //System.out.println("returnProductTagIDDetail: "+getProduct().toArray());
        
        result.close();
        loadStatement.close();
    }
    
    //select itemunits.tagId, items.itemName from items inner join itemunits on (item.itemId = itemunits.itemIdfk) where itemunits.status = '0'
    
    public void returnUnsoldProductTagIDDetail() throws SQLException{
        clearData(); 
        
        String loadString = "select "+getPreferences(childTableDesc, "") +".tagId, "+getPreferences(parentTableDesc, "") +".itemName from "+getPreferences(parentTableDesc, "") +" inner join "+getPreferences(childTableDesc, "") +" on ("+getPreferences(parentTableDesc, "") +".itemId = "+getPreferences(childTableDesc, "") +".itemIdfk) where "+getPreferences(childTableDesc, "") +".status=0";
        //String loadString = "select itemunits.tagId, items.itemName from items inner join itemunits on (items.itemId = itemunits.itemIdfk) where itemunits.status=0";
        Statement loadStatement = connection.createStatement();
        
        ResultSet result = loadStatement.executeQuery(loadString);
        
        while(result.next()){
            String tagID = result.getString("tagID");
            String productName = result.getString("itemName");
            
            Product productItem  = new Product(tagID, productName);
            this.product.add(productItem);
        }
        //System.out.println("returnProductTagIDDetail: "+getProduct().toArray());
        
        result.close();
        loadStatement.close();
    }
    
    public void returnProducts() throws SQLException{
        clearData();
        
        String loadString = "select itemIdfk, tagId, tagType, status  from itemunits order by tagID";
        Statement loadStatement = connection.createStatement();
        
        ResultSet result = loadStatement.executeQuery(loadString);
        
        while(result.next()){
            String tagID = result.getString("tagID");
            String productName = result.getString("itemName");
            
            Product productItem  = new Product(tagID, productName);
            this.product.add(productItem);
        }
        /*Testing the dtaa
        System.out.println("returnProducts: "+getProduct().toArray());
        Product data = getProduct().get(5);
        System.out.println("ag ID: "+data.getTagID());
        */
        
        result.close();
        loadStatement.close();
    }
    
}
