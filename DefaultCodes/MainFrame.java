/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DataModel.DataBase;
import DataModel.Product;
import DataModel.ProductReport;
import DataModel.TableModel;
import Listeners.PreferenceListener;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.OctaneSdkException;
import com.impinj.octane.Tag;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hosea
 */
public class MainFrame extends javax.swing.JFrame {

    public ArrayList<String> myArray = new ArrayList<>();
    private RFReader deviceReader;
    private TableModel tableModel;
    private DataBase database;
    private boolean running = true;
    private String databaseURL;
    private Alarm alarm;
    private ArrayList<String> missingDataLog; 
    
    private  Map<String, String> copiedAntenna;
    
    private JPopupMenu popupMenu; 
    private JMenuItem clearItem;
    
    //creating preference String key Description.
    private String dbURLDesc = "db URL", dbNameDesc = " db Name", userNameDesc = "User Name", passwordDesc = "db Password",
            parentTableDesc = "Parent Table", childTableDesc = "Child Table", ipAddressDesc = "Ip Address", portDesc = "Port Number", noAntennaDesc = "Antenna Number";
    
    
    //Setting Preference
    PreferenceListener preferenceListener;
    
    private ArrayList<ProductReport> tableData = new ArrayList<>();
    /**
     * Creates new form MainJFrame
     */
    public MainFrame() {
        initComponents();  
        setTitle("Tamper Proof System");
        setLocationRelativeTo(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        try {
            setIconImage(ImageIO.read(getClass().getResource("Icons/logo.png")));
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //setResizable(false);
        //retriever = new StoreDataRetriever();
        database = new DataBase();
        tableModel = new TableModel();
        table.setModel(tableModel);
        alarm = new Alarm();
        copiedAntenna = new HashMap<>();
        
        popupMenu = new JPopupMenu();
        clearItem = new JMenuItem("clear error");
        clearItem.setIcon(new ImageIcon(getClass().getResource("Icons/icons8_ccleaner_20px.png")));
        clearItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInfo();
            }
        });
        popupMenu.add(clearItem);
        
        clearInfo();
        
        //creating setting preference
        setPreferenceListener(new PreferenceListener() {
            @Override
            public void databasePreference(String link, String dbName, String username, String password, int port, String parentTable, String childTable) {
                database.setPreferences(dbURLDesc, link);
                database.setPreferences(dbNameDesc, dbName);
                database.setPreferences(userNameDesc, username);
                database.setPreferences(passwordDesc, password);
                database.setPreferences(portDesc, port);
                database.setPreferences(parentTableDesc, parentTable);
                database.setPreferences(childTableDesc, childTable);
            }

            @Override
            public void devicePreference(String ipAddress, int antennaNumber) {
                database.setPreferences(ipAddressDesc, ipAddress);
                database.setPreferences(noAntennaDesc, antennaNumber);
            }
        });
        setDefaultSetting(database.getPreferences(dbURLDesc, ""), 
                database.getPreferences(dbNameDesc, ""), database.getPreferences(userNameDesc, ""), 
                database.getPreferences(passwordDesc, ""), database.getPreferences(portDesc, 3306),
                database.getPreferences(parentTableDesc, ""), database.getPreferences(childTableDesc, ""), 
                database.getPreferences(ipAddressDesc, ""), database.getPreferences(noAntennaDesc, 1));
        
        //connecting store database
        try {
            connectDatabase();
            dbStatusLabel.setForeground(Color.GREEN);
            dbStatusLabel.setText("Connected");
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            dbStatusLabel.setForeground(Color.RED);
            dbStatusLabel.setText("Not Connected");
            displayInfo("\n"+ex.toString());
        }
        checkSystemCredentials(database.getPreferences(dbURLDesc, ""), 
                database.getPreferences(dbNameDesc, ""), database.getPreferences(userNameDesc, ""), 
                database.getPreferences(passwordDesc, ""), database.getPreferences(portDesc, 3306),
                database.getPreferences(parentTableDesc, ""), database.getPreferences(childTableDesc, ""), 
                database.getPreferences(ipAddressDesc, ""));
        
        infoArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(infoArea, e.getX(), e.getY());
                }
            }
            
        });
    }
    
    public void getAntennaData(){
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label1 = new java.awt.Label();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        toolPanel = new javax.swing.JPanel();
        stopButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        infoArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        readerStatusLabel = new javax.swing.JLabel();
        opLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dbStatusLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        defaultButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        portSpinner = new javax.swing.JSpinner();
        urlField = new javax.swing.JTextField();
        userNameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        dbNameField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        childTableNameField = new javax.swing.JTextField();
        parentTableNameField = new javax.swing.JTextField();
        showCheckBox = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        antennaSpinner = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ipAddressField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        label1.setText("Enter service URL");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(14, 115, 168));
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(1170, 700));
        setMinimumSize(new java.awt.Dimension(1170, 700));
        setSize(new java.awt.Dimension(1170, 700));

        jTabbedPane2.setBackground(new java.awt.Color(220, 220, 220));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(null);

        toolPanel.setBackground(new java.awt.Color(246, 255, 214));
        toolPanel.setMaximumSize(new java.awt.Dimension(770, 120));
        toolPanel.setMinimumSize(new java.awt.Dimension(770, 120));

        stopButton.setBackground(new java.awt.Color(170, 0, 43));
        stopButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        stopButton.setForeground(new java.awt.Color(255, 0, 16));
        stopButton.setText("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        startButton.setBackground(new java.awt.Color(206, 255, 170));
        startButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        startButton.setForeground(new java.awt.Color(16, 85, 0));
        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout toolPanelLayout = new javax.swing.GroupLayout(toolPanel);
        toolPanel.setLayout(toolPanelLayout);
        toolPanelLayout.setHorizontalGroup(
            toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(464, Short.MAX_VALUE))
        );
        toolPanelLayout.setVerticalGroup(
            toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel3.add(toolPanel);
        toolPanel.setBounds(0, 0, 820, 140);

        table.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        table.setForeground(new java.awt.Color(0, 120, 215));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tag Id", "Product  Name", "Status"
            }
        ));
        table.setToolTipText("");
        table.setGridColor(new java.awt.Color(246, 255, 214));
        table.setRowHeight(30);
        jScrollPane3.setViewportView(table);

        jPanel3.add(jScrollPane3);
        jScrollPane3.setBounds(0, 140, 820, 530);

        infoArea.setEditable(false);
        infoArea.setColumns(20);
        infoArea.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        infoArea.setForeground(new java.awt.Color(255, 0, 16));
        infoArea.setRows(5);
        jScrollPane2.setViewportView(infoArea);

        jPanel3.add(jScrollPane2);
        jScrollPane2.setBounds(830, 140, 520, 530);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Device Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Emoji", 1, 15), new java.awt.Color(0, 2, 85))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(166, 140));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Reader Status :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Scan Operation :");

        readerStatusLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        readerStatusLabel.setForeground(new java.awt.Color(255, 0, 16));
        readerStatusLabel.setText("Not Connected");

        opLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        opLabel.setForeground(new java.awt.Color(255, 0, 16));
        opLabel.setText("Not Active");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Store DB Status:");

        dbStatusLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbStatusLabel.setForeground(new java.awt.Color(255, 0, 16));
        dbStatusLabel.setText("Not Connected");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(opLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(readerStatusLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dbStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                .addContainerGap(264, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(readerStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1);
        jPanel1.setBounds(830, 0, 520, 140);

        jTabbedPane2.addTab("DEVICE SCANNER HOME", jPanel3);

        jPanel4.setBackground(new java.awt.Color(220, 220, 220));

        cancelButton.setBackground(new java.awt.Color(170, 0, 43));
        cancelButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 0, 16));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        saveButton.setBackground(new java.awt.Color(206, 255, 170));
        saveButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        saveButton.setForeground(new java.awt.Color(16, 85, 0));
        saveButton.setText("Save Setting");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        defaultButton.setBackground(new java.awt.Color(82, 106, 255));
        defaultButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        defaultButton.setForeground(new java.awt.Color(54, 70, 170));
        defaultButton.setText("Set Defaults");
        defaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultButtonActionPerformed(evt);
            }
        });

        jPanel2.setLayout(null);

        jLabel4.setBackground(new java.awt.Color(220, 220, 220));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(49, 54, 61));
        jLabel4.setText("DB User Name :");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(60, 180, 120, 28);

        portSpinner.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        portSpinner.setModel(new javax.swing.SpinnerNumberModel(3306, 0, 9000, 1));
        jPanel2.add(portSpinner);
        portSpinner.setBounds(190, 350, 146, 40);

        urlField.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        urlField.setEnabled(false);
        jPanel2.add(urlField);
        urlField.setBounds(190, 60, 460, 28);

        userNameField.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        jPanel2.add(userNameField);
        userNameField.setBounds(190, 180, 280, 28);

        passwordField.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        jPanel2.add(passwordField);
        passwordField.setBounds(190, 250, 280, 28);

        jLabel6.setBackground(new java.awt.Color(220, 220, 220));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(49, 54, 61));
        jLabel6.setText("Child DB table name : ");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(60, 480, 170, 28);

        dbNameField.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        jPanel2.add(dbNameField);
        dbNameField.setBounds(190, 120, 280, 28);

        jLabel3.setBackground(new java.awt.Color(220, 220, 220));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(49, 54, 61));
        jLabel3.setText("Port Number :");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(60, 360, 113, 28);

        jLabel5.setBackground(new java.awt.Color(220, 220, 220));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(49, 54, 61));
        jLabel5.setText("Database URL:");
        jLabel5.setEnabled(false);
        jPanel2.add(jLabel5);
        jLabel5.setBounds(60, 60, 113, 28);

        jLabel12.setBackground(new java.awt.Color(220, 220, 220));
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(49, 54, 61));
        jLabel12.setText("Password :");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(60, 250, 90, 28);

        jLabel13.setBackground(new java.awt.Color(220, 220, 220));
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(49, 54, 61));
        jLabel13.setText("Parent DB table name : ");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(60, 420, 170, 28);

        childTableNameField.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        jPanel2.add(childTableNameField);
        childTableNameField.setBounds(250, 480, 220, 28);

        parentTableNameField.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        jPanel2.add(parentTableNameField);
        parentTableNameField.setBounds(250, 420, 220, 28);

        showCheckBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        showCheckBox.setForeground(new java.awt.Color(49, 54, 61));
        showCheckBox.setText("Show Password");
        showCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showCheckBoxActionPerformed(evt);
            }
        });
        jPanel2.add(showCheckBox);
        showCheckBox.setBounds(190, 290, 280, 23);

        jLabel14.setBackground(new java.awt.Color(220, 220, 220));
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(49, 54, 61));
        jLabel14.setText("DB  Name :");
        jPanel2.add(jLabel14);
        jLabel14.setBounds(60, 120, 100, 28);

        jTabbedPane1.addTab("Database Settings", jPanel2);

        jPanel5.setLayout(null);

        antennaSpinner.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        antennaSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 0, 20, 1));
        jPanel5.add(antennaSpinner);
        antennaSpinner.setBounds(240, 120, 146, 40);

        jLabel9.setBackground(new java.awt.Color(220, 220, 220));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(49, 54, 61));
        jLabel9.setText("Reader Ip Address :");
        jPanel5.add(jLabel9);
        jLabel9.setBounds(70, 50, 160, 28);

        jLabel10.setBackground(new java.awt.Color(220, 220, 220));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(49, 54, 61));
        jLabel10.setText("Number of Antennas :");
        jPanel5.add(jLabel10);
        jLabel10.setBounds(70, 130, 160, 28);

        ipAddressField.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        ipAddressField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ipAddressFieldKeyTyped(evt);
            }
        });
        jPanel5.add(ipAddressField);
        ipAddressField.setBounds(240, 50, 280, 28);

        jTabbedPane1.addTab("Antenna Setting", jPanel5);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 15)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(0, 120, 255));
        jTextArea1.setRows(5);
        jTextArea1.setText("Setting Information:\n\nPlease note that all settings be set appropriately and accordingly.\n\nDatabase url: This is the url that points to the database. \nThis field can only be set by providing the database name and \nthe port number. These info then enables the database url to be \ngenerated automatically when settings is saved.\n\nDatabase Username & Password : Like every system, there are \ncredentials that permit or grant access to the user.\nThe username and password therefore grants the software \naccess into the database. Please ensure you get the correct\ninformation else the software will be restricted from accessing the\ndatabase; This will be problematic.!\n\nStore Parent table: The store parent table is the parent table \nthat holds the entire information for every product in the store.\nE.g items\n\nChild Parent table: The store child table is the child table that\ninherits from the parent table certain information\n(tag id, tag type e.t.c) about every product in the store.\nE.g itemunits. Please ensure that the names\nof this tables are provided according to which is parent and child.\n\nDevice IP Address. This is the IP address of the reader device. \nEnsure the correct IP address of the device is provided.\n");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(648, Short.MAX_VALUE)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(defaultButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addGap(4, 4, 4))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(defaultButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("SETTINGS", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        
        stopButton.setEnabled(false);
        stopButton.setForeground(new Color(255, 0, 16));
        startButton.setEnabled(true);
        setRunning(false);
        setReaderInfo("Not Connected", false);
        clearInfo();
        //System.out.println("#Main--Arrived store data............"+retriever.returnStoreData()+"\n");
    }//GEN-LAST:event_stopButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        
        setRunning(true);
        stopButton.setEnabled(true);
        stopButton.setForeground(new Color(255, 255, 255));
        startButton.setEnabled(false);
        clearInfo();
        
        try {
            connectDatabase();
            dbStatusLabel.setForeground(Color.GREEN);
            dbStatusLabel.setText("Connected");
            JOptionPane.showMessageDialog(this, "Successfully connected to database.", "DB connection Message", JOptionPane.INFORMATION_MESSAGE);
            
            deviceReader = new RFReader();
            
            ///////////////////////////////////////////////////////////////////////////////////////////
            
            /*This code runs the first scan instance in order to retrieve the tag scan device location*/
          
            deviceReader.connect(database.getPreferences(ipAddressDesc, ""));
            JOptionPane.showMessageDialog(this, "Successfully connected to the Reader and now scanning.", "Reader connection Message", JOptionPane.INFORMATION_MESSAGE);    
            setReaderInfo("Connected", true);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                deviceReader.stopReader();
                deviceReader.disconnectReader();
            } catch (OctaneSdkException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                displayInfo("\n"+ex.toString());
                displayInfo("\n"+ex.toString());
            }
            copiedAntenna = deviceReader.getAntenna();
            
            ///////////////////////////////////////////////////////////////////////////////////////////
            
            execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            setReaderInfo("Not Connected", false);
            dbStatusLabel.setForeground(Color.RED);
            dbStatusLabel.setText("Not Connected");
            displayInfo("\n"+ex.toString());
        } catch (OctaneSdkException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Unable to connect to the reader, please check the device connection.", "Reader connection Message", JOptionPane.INFORMATION_MESSAGE);
            displayInfo("\n"+ex.toString());
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        
        String ip_pattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        
        Pattern pattern = Pattern.compile(ip_pattern);
        Matcher regexMatcher = pattern.matcher(ipAddressField.getText());
        if(!regexMatcher.matches() && !ipAddressField.getText().equals("")){
            JOptionPane.showMessageDialog(jPanel2, "Make sure the IP is entered correctly ", "Unable to save Settings.", JOptionPane.INFORMATION_MESSAGE);
        }else{
            saveSettingPreference();
            setDefaultSetting(database.getPreferences(dbURLDesc, ""), 
                    database.getPreferences(dbNameDesc, ""), database.getPreferences(userNameDesc, ""), 
                    database.getPreferences(passwordDesc, ""), database.getPreferences(portDesc, 3306),
                    database.getPreferences(parentTableDesc, ""), database.getPreferences(childTableDesc, ""), 
                    database.getPreferences(ipAddressDesc, ""), database.getPreferences(noAntennaDesc, 1));
            JOptionPane.showMessageDialog(jPanel2, "System Settings was successfully set.", "Setting Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void defaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultButtonActionPerformed
        // TODO add your handling code here:
        setDefaultSetting("", "", "", "", 3306, "", "", "", 1);
        saveSettingPreference();
        JOptionPane.showMessageDialog(jPanel2, "System Settings was successfully reset to default.", "Setting Message", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_defaultButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        setDefaultSetting(database.getPreferences(dbURLDesc, ""), 
                database.getPreferences(dbNameDesc, ""), database.getPreferences(userNameDesc, ""), 
                database.getPreferences(passwordDesc, ""), database.getPreferences(portDesc, 3306),
                database.getPreferences(parentTableDesc, ""), database.getPreferences(childTableDesc, ""), 
                database.getPreferences(ipAddressDesc, ""), database.getPreferences(noAntennaDesc, 1));
        
        JOptionPane.showMessageDialog(jPanel2, "Settings cancelled. Note that previous data will be loaded.", "Setting Message", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void showCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showCheckBoxActionPerformed
        // TODO add your handling code here:
        if(showCheckBox.isSelected()){
            passwordField.setEchoChar((char)0);
        }else {
            passwordField.setEchoChar('.');
        }
    }//GEN-LAST:event_showCheckBoxActionPerformed

    private void ipAddressFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ipAddressFieldKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || c== KeyEvent.VK_PERIOD || c== KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_ipAddressFieldKeyTyped
    
    // Device and Database Methods
    private void execute(){
        SwingWorker<Void,Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                
                while(isRunning()){
                    deviceReader.connect(database.getPreferences(ipAddressDesc, ""));
                    getTableData().clear();
                    
                    Thread.sleep(3000);
                    
                    try {
                        deviceReader.stopReader();
                        deviceReader.disconnectReader();
                    } catch (OctaneSdkException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        displayInfo("\n"+ex.toString());
                        displayInfo("\n"+ex.toString());
                    }

                    System.out.println("#Main--Arrived tag id: "+deviceReader.getTagData());

                    for(String eachID: deviceReader.getTagData()){
                        try {
                            int outcome  = database.returnTagVerification(eachID);
                            if(outcome == 0){
                                System.out.println("Foreign tag: "+eachID+" not in store database");
                            }else{
                                //return unsold product tag detail
                                database.returnUnsoldProductTagIDDetail();
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                            displayInfo("\n"+ex.toString());
                        }
                    }
                    //System.out.println("Size of store data: "+database.getProduct().size());
                    compareData(deviceReader.getTagData(), database.getProduct());
                    setupTablelStream();
                    
                    if(isRunning()==false){
                        getTableData().clear();
                        setupTablelStream();
                        clearInfo();
                    }
                }
                return null; 
            }
        };
        worker.execute();
    }
    public void connectDatabase() throws SQLException{
        database.connect();
    }
    public void disconnectDatabase(){
        database.disconnect();
    }
    public void  setupTablelStream(){
        tableModel.setProductData(getTableData());
        updateTable();
    }
    public void updateTable(){
        tableModel.fireTableDataChanged();
    }
    public void clearData(){
        database.clearData();
    }
    public ArrayList<ProductReport> getTableData() {
        return tableData;
    }
    public void setTableData(ProductReport report) {
        this.tableData.add(report);
    }
    public ArrayList<String> getMissingDataLog() {
        return missingDataLog;
    }
    public void setMissingDataLog(String info) {
        this.missingDataLog.add(info);
    }
    
    public void compareData(ArrayList<String> tagID, ArrayList<Product> storeData){
        
        for(int i=0; i<storeData.size(); i++){
            if(tagID.contains(storeData.get(i).getTagID())){
                
                ProductReport report = new ProductReport(storeData.get(i).getTagID(), storeData.get(i).getProductName(), "Available", deviceReader.getAntenna().get(storeData.get(i).getTagID()));
                setTableData(report);
            }else{
                ProductReport report = new ProductReport(storeData.get(i).getTagID(), storeData.get(i).getProductName(), "Not Available", copiedAntenna.get(storeData.get(i).getTagID()));
                setTableData(report);
                createAlarm();
                displayInfo(storeData.get(i).getTagID()+" "+ storeData.get(i).getProductName()+" is Missing at antenna :"+copiedAntenna.get(storeData.get(i).getTagID()));
            }
        }
    }
    public boolean isRunning() {
        return running;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public void createDatabaseURL(String dbName, int portNumber) {
        this.databaseURL = "jdbc:mysql://localhost:"+portNumber+"/"+dbName;
    }
    
    //System preference code
    public void setPreferenceListener(PreferenceListener preferenceListener){
        this.preferenceListener = preferenceListener;
    }
    public void setDefaultSetting(String link, String dbName, String username, String passsword, int port, String parentTable, String childTable, String ipAddress, int antennaNumber){
        
        urlField.setText(link);
        dbNameField.setText(dbName);
        userNameField.setText(username);
        passwordField.setText(passsword);
        portSpinner.setValue(port);
        parentTableNameField.setText(parentTable);
        childTableNameField.setText(childTable);
        ipAddressField.setText(ipAddress);
        antennaSpinner.setValue(antennaNumber);
    }
    public void saveSettingPreference(){
        if(preferenceListener != null){
            
            String dbName = dbNameField.getText();
            String username = userNameField.getText();

            //retrieving password from JPasswordField
            char passwordCharcter[] = passwordField.getPassword();
            String password = "";
            for(char c: passwordCharcter){
                password += c;
            }

            int port = (int)portSpinner.getValue();
            createDatabaseURL(dbName, port);
            String dbURL = getDatabaseURL();
            String parentTable = parentTableNameField.getText();
            String childTable = childTableNameField.getText();
            String ip = ipAddressField.getText();
            int antennaNumber = (int)antennaSpinner.getValue(); 
            
            preferenceListener.databasePreference(dbURL, dbName, username, password, port, parentTable, childTable);
            preferenceListener.devicePreference(ip, antennaNumber);
        }
    }
    
    //System check methods
    public void setReaderInfo(String readerStatus, boolean value){
        if(readerStatus.equals("Connected")&&value==true){
            readerStatusLabel.setForeground(Color.GREEN);
            readerStatusLabel.setText(readerStatus);
            opLabel.setForeground(Color.GREEN);
            opLabel.setText("Active");
        }else if(readerStatus.equals("Not Connected")&&value==false){
            readerStatusLabel.setForeground(Color.RED);
            readerStatusLabel.setText(readerStatus);
            opLabel.setForeground(Color.RED);
            opLabel.setText("Not Active");
        }
    }
    public void checkSystemCredentials(String link, String dbName, String username, String password, int port, String parentTable, String childTable, String ipAddress){
        displayInfo("\nKindly regard the information below");
        if(!link.contains(dbName)){
            displayInfo("Database name has not been set.!"+link+"?");
        }else{
            displayInfo("Database URL set.");
        }
        if(username.equals("")){
            displayInfo("Database login credential not set: Username?.");
        }else{
            displayInfo("Username is set.");
        }
        if(password.equals("")){
            displayInfo("Database login credential not set: Password?.");
        }else{
            displayInfo("Password is set.");
        }
        if(port==0){
            displayInfo("Set a valid port number.!");
        }else{
            displayInfo("Port number valid.");
        }
        if(parentTable.equals("")){
            displayInfo("Database Parent table not set!");
        }else{
            displayInfo("Parent table set.");
        }
        if(childTable.equals("")){
            displayInfo("Database Child table not set!");
        }else{
            displayInfo("Child table set.");
        }
        if(ipAddress.equals("")){
            displayInfo("Device IP not set!");
        }else{
            displayInfo("IP set.");
        }
        displayInfo("\nDo Ensure that all settings are entered correctly before \nstarting the device fro scanning.");
    }
    public void displayInfo(String info){
        infoArea.append(info+" \n");
    }
    public void clearInfo(){
        infoArea.setText("");
        infoArea.setText("System Error Information:\n");
    }
    public void createAlarm(){
        Thread alarmRinger = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    alarm.playAlarm(alarm.getAlarmFile());
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    displayInfo(ex.getMessage());
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        alarmRinger.start();
    }
    public void stopAlarm(){
        alarm.stopAlarm();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner antennaSpinner;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField childTableNameField;
    private javax.swing.JTextField dbNameField;
    private javax.swing.JLabel dbStatusLabel;
    private javax.swing.JButton defaultButton;
    private javax.swing.JTextArea infoArea;
    private javax.swing.JTextField ipAddressField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea1;
    private java.awt.Label label1;
    private javax.swing.JLabel opLabel;
    private javax.swing.JTextField parentTableNameField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JSpinner portSpinner;
    private javax.swing.JLabel readerStatusLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JCheckBox showCheckBox;
    private javax.swing.JButton startButton;
    public javax.swing.JButton stopButton;
    private javax.swing.JTable table;
    private javax.swing.JPanel toolPanel;
    private javax.swing.JTextField urlField;
    private javax.swing.JTextField userNameField;
    // End of variables declaration//GEN-END:variables
    private ImpinjReader reader;
    private DefaultTableModel dtm;
    private JTable tbl;
}
