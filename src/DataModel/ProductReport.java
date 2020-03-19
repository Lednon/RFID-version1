package DataModel;

/**
 *
 * @author Fresh
 */
public class ProductReport {
    private String tagID, tagName, status, identifyingAntenna;
    
    public ProductReport(String tagID,String tagName,String status,String identifyingAntenna){
        this.tagID = tagID;
        this.tagName = tagName;
        this.status = status;
        this.identifyingAntenna = identifyingAntenna;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getIdentifyingAntenna() {
        return identifyingAntenna;
    }

    public void setIdentifyingAntenna(String identifyingAntenna) {
        this.identifyingAntenna = identifyingAntenna;
    }
}
