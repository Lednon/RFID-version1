/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

/**
 *
 * @author Fresh
 */
public interface PreferenceListener {
    
    public void databasePreference(String link,String dbName, String username, String passsword, int port, String parentTable, String childTable);
    
    public void devicePreference(String ipAddress, int antennaNumber);
}
