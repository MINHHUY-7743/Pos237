package dao;
import entity.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        
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
                Product product = new Product(
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
    
    public Product getProductById(int id) {
        Product product = null;
        
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
                product = new Product(
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
}