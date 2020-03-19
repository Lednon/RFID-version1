package DataModel;

/**
 *
 * @author Fresh
 */
public class Product {
    private String tagID, productName;
    
    public Product(String tagID,String name){
        this.tagID = tagID;
        this.productName = name;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
