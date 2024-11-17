package dao;
import entity.EntityInvoiceDetail;
import entity.EntityProduct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<EntityProduct> getAllProducts() {
        List<EntityProduct> productList = new ArrayList<>();
        
        try {
            String hostname = "localhost";
            String sqlInstanceName = "LENOVOLOQ";
            String sqlDatabase = "POS";
            String sqlUser = "Sa";
            String sqlPassword = "123";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String connectURL = "jdbc:sqlserver://" + hostname + ":1433" 
                    + ";instance=" + sqlInstanceName + ";databaseName=" + sqlDatabase;
            
            Connection conn = DriverManager.getConnection(connectURL, sqlUser, sqlPassword);
            
            String sql = "SELECT IDPRODUCT, NAMEPRODUCT, PRICE FROM PRODUCT";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                EntityProduct product = new EntityProduct(
                    rs.getInt("IDPRODUCT"), 
                    rs.getString("NAMEPRODUCT"), 
                    rs.getDouble("PRICE")
                );
                productList.add(product);
            }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return productList;
    }
    
    public EntityProduct getProductById(int id) {
        EntityProduct product = null;
        
        try {
            String hostname = "localhost";
            String sqlInstanceName = "LENOVOLOQ";
            String sqlDatabase = "POS";
            String sqlUser   = "Sa";
            String sqlPassword = "123";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String connectURL = "jdbc:sqlserver://" + hostname + ":1433" 
                        + ";instance=" + sqlInstanceName 
                        + ";databaseName=" + sqlDatabase 
                        + ";encrypt=true;trustServerCertificate=true;";
            
            Connection conn = DriverManager.getConnection(connectURL, sqlUser , sqlPassword);
            
            String sql = "SELECT IDPRODUCT, NAMEPRODUCT, PRICE FROM PRODUCT WHERE IDPRODUCT = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id); // Thiết lập giá trị cho tham số trong câu truy vấn
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                product = new EntityProduct(
                    rs.getInt("IDPRODUCT"), 
                    rs.getString("NAMEPRODUCT"), 
                    rs.getDouble("PRICE")
                );
            }
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return product;
    }
    
    
    public void saveInvoice(double total, double tax, double grandTotal, List<EntityInvoiceDetail> invoiceDetails) {
        Connection conn = null;
        PreparedStatement pstmtInvoice = null;
        PreparedStatement pstmtDetail = null;

        try {
            String hostname = "localhost";
            String sqlInstanceName = "LENOVOLOQ";
            String sqlDatabase = "POS";
            String sqlUser  = "Sa";
            String sqlPassword = "123";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //String connectURL = "jdbc:sqlserver://" + hostname + ":1433" + ";instance=" + sqlInstanceName + ";databaseName=" + sqlDatabase;
            String connectURL = "jdbc:sqlserver://" + hostname + ":1433" 
                        + ";instance=" + sqlInstanceName 
                        + ";databaseName=" + sqlDatabase 
                        + ";encrypt=true;trustServerCertificate=true;";
            conn = DriverManager.getConnection(connectURL, sqlUser , sqlPassword);
        
            // Lưu hóa đơn
            String sqlInvoice = "INSERT INTO Invoice (total, tax, grand_total) VALUES (?, ?, ?)";
            pstmtInvoice = conn.prepareStatement(sqlInvoice, Statement.RETURN_GENERATED_KEYS);
            pstmtInvoice.setDouble(1, total);
            pstmtInvoice.setDouble(2, tax);
            pstmtInvoice.setDouble(3, grandTotal);
            pstmtInvoice.executeUpdate();
        
            // Lấy ID của hóa đơn vừa lưu
            ResultSet generatedKeys = pstmtInvoice.getGeneratedKeys();
            int invoiceId = 0;
            if (generatedKeys.next()) {
                invoiceId = generatedKeys.getInt(1);
            }
        
            // Lưu chi tiết hóa đơn
            String sqlDetail = "INSERT INTO InvoiceDetail (invoice_id, product_name, quantity, price) VALUES (?, ?, ?, ?)";
            pstmtDetail = conn.prepareStatement(sqlDetail);
        
            for (EntityInvoiceDetail detail : invoiceDetails) {
                pstmtDetail.setInt(1, invoiceId);
                pstmtDetail.setString(2, detail.getProductName());
                pstmtDetail.setInt(3, detail.getQuantity());
                pstmtDetail.setDouble(4, detail.getPrice());
                pstmtDetail.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmtDetail != null) pstmtDetail.close();
                if (pstmtInvoice != null) pstmtInvoice.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}