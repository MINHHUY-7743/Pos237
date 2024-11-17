package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import entity.EntityInvoice;
import entity.EntityInvoiceDetail;

public class InvoiceDAO {
    private Connection getConnection() throws SQLException {
        String hostname = "localhost";
        String sqlInstanceName = "LENOVOLOQ";
        String sqlDatabase = "POS";
        String sqlUser = "Sa";
        String sqlPassword = "123";

        String connectURL = "jdbc:sqlserver://" + hostname + ":1433" 
                + ";instance=" + sqlInstanceName 
                + ";databaseName=" + sqlDatabase 
                + ";encrypt=true;trustServerCertificate=true;";
        
        return DriverManager.getConnection(connectURL, sqlUser, sqlPassword);
    }

    public List<EntityInvoice> getInvoicesByDateAndTime(Date date, String timeRange) throws SQLException {
        List<EntityInvoice> invoices = new ArrayList<>();
        String sql = "SELECT id, date, grand_total FROM Invoice WHERE CAST(date AS DATE) = ? ";
        
        // Thêm điều kiện lọc theo giờ
        switch(timeRange) {
            case "10h":
                sql += "AND DATEPART(HOUR, date) >= 10 AND DATEPART(HOUR, date) < 13";
                break;
            case "13h":
                sql += "AND DATEPART(HOUR, date) >= 13 AND DATEPART(HOUR, date) < 18";
                break;
            case "18h":
                sql += "AND DATEPART(HOUR, date) >= 18 AND DATEPART(HOUR, date) < 21";
                break;
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Chuyển đổi java.util.Date sang java.sql.Date
            pstmt.setDate(1, new java.sql.Date(date.getTime()));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EntityInvoice invoice = new EntityInvoice(
                        rs.getInt("id"),
                        rs.getDate("date"),
                        rs.getDouble("total"),    
                        rs.getDouble("tax"),                      
                        rs.getDouble("grand_total")
                            
                            
                    );
                    invoices.add(invoice);
                }
            }
        }
        return invoices;
    }

    public List<EntityInvoiceDetail> getInvoiceDetailsByInvoiceId(int invoiceId) throws SQLException {
        List<EntityInvoiceDetail> details = new ArrayList<>();
        String sql = "SELECT product_name, quantity, price FROM InvoiceDetail WHERE invoice_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, invoiceId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EntityInvoiceDetail detail = new EntityInvoiceDetail(
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                    );
                    details.add(detail);
                }
            }
        }
        return details;
    }
}