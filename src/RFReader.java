/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.impinj.octane.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 *
 * @author Hosea
 */
public class RFReader {
    private ImpinjReader reader;
    private String ipAddress;
    private TagReportListenerImplementation TRI;

    public void connect(String ip) throws OctaneSdkException {

        setIPAddress(ip);
        reader = new ImpinjReader();

        System.out.println("Connecting");
        connectReader(getIPAddress());

        Settings settings = reader.queryDefaultSettings();

        ReportConfig report = settings.getReport();
        report.setIncludeAntennaPortNumber(true);
        report.setIncludeLastSeenTime(true);
        report.setIncludeFirstSeenTime(true);
        report.setIncludeSeenCount(true);
        

        report.setMode(ReportMode.BatchAfterStop);

        // The reader can be set into various modes in which reader
        // dynamics are optimized for specific regions and environments.
        // The following mode, AutoSetDenseReader, monitors RF noise and interference and then automatically
        // and continuously optimizes the reader's configuration
        settings.setReaderMode(ReaderMode.AutoSetDenseReaderDeepScan);
        
        // set some special settings for antenna 1
        AntennaConfigGroup antennas = settings.getAntennas();
        antennas.enableAll();
        //antennas.enableById(new short[]{1});
        antennas.getAntenna((short) 1).setIsMaxRxSensitivity(true);
        antennas.getAntenna((short) 1).setIsMaxTxPower(false);
        antennas.getAntenna((short) 1).setTxPowerinDbm(30.0);
        //antennas.getAntenna((short) 1).setRxSensitivityinDbm(0.0);
        System.out.println("Max rx is: "+ antennas.getAntenna((short) 1).getRxSensitivityinDbm());

        TRI = new TagReportListenerImplementation();
        reader.setTagReportListener(TRI);
        reader.applySettings(settings);
        reader.start();
    }
    
    //connect reader
    public void connectReader(String hostName) throws OctaneSdkException{
        reader.connect(hostName);
    }
    //stop reader
    public void stopReader() throws OctaneSdkException{
        reader.stop();
    }
    //disconnect reader
    public void disconnectReader(){
        reader.disconnect();
    }

    public String getIPAddress() {
        return ipAddress;
    }

    public void setIPAddress(String hostname) {
        this.ipAddress = hostname;
    }
    public void clearTagData(){
        TRI.clearTag();
    }
    public ArrayList<String> getTagData(){
        return TRI.getTagID();
    }
    public Map<String, String> getAntenna(){
        return TRI.getAntenna();
    }
}
