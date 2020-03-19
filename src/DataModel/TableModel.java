/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Fresh
 */
public class TableModel extends AbstractTableModel{

    private String[] columnName = {"Tag Id", "Product Name", "Antenna Locating Tag", "Status"};
    public ArrayList<ProductReport> reportData = new ArrayList<>();

    public ArrayList<ProductReport> getProductData() {
        return reportData;
    }

    public void setProductData(ArrayList<ProductReport> productData) {
        this.reportData = productData;
    }
    @Override
    public int getRowCount() {
        return reportData.size();
    }

    @Override
    public int getColumnCount() {
        return columnName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProductReport report = reportData.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return report.getTagID();
            case 1:
                return report.getTagName();
            case 2:
                return  report.getIdentifyingAntenna();
            case 3:
                return report.getStatus();
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }
    
    
}
