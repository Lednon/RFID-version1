import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class TagReportListenerImplementation implements TagReportListener{
    
    private volatile List<Tag> tags;
    private  ArrayList<String> tagID;
    private  ArrayList<String> firstTimeSeen, lastTiimeSeen;
    private  Map<String, String> antenna;
    
    public TagReportListenerImplementation(){
        
        tagID = new ArrayList<>();
        antenna = new HashMap<String, String>();
    }
   
    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {
        setTags(report.getTags());
        
        //loop returning entire tag object data
        for (Tag t : getTags()) {
            setTagID(t.getEpc().toString());
            setAntenna(t.getEpc().toString(), ""+t.getAntennaPortNumber());
            
            System.out.println("#RFI--Scanned EPC: " + t.getEpc() +" First seen time: "+ t.getFirstSeenTime().getLocalDateTime()+" Last seen time "+t.getLastSeenTime().getLocalDateTime());
        }
        System.out.println("#RFI--retrieved ID: "+ getTagID());
        System.out.println("#RFI--Size of recieved data: "+ getTags().size());
    }
    
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    public void clearTag(){
        getTags().clear();
    }

    public ArrayList<String> getTagID() {
        return tagID;
    }

    public void setTagID(String tagData) {
        this.tagID.add(tagData);
    }

    public Map<String, String> getAntenna() {
        return antenna;
    }

    public void setAntenna(String key, String value) {
        this.antenna.put(key, value);
    }
    public void clearAntenna(){
        getAntenna().clear();
    }
}

