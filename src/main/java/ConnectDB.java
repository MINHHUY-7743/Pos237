
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Huy
 */
public class ConnectDB {
    public static void main(String[] args[]) throws ClassNotFoundException, SQLException{        
        try{
            String hostname = "localhost";
            String sqlInstanceName = "LENOVOLOQ";
            String sqlDatabase = "POS";
            String sqlUser = "Sa";
            String sqlPassword = "123";
        
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        
            String connectURL = "jdbc:sqlserver://" + hostname + ":1433" 
                    + ";instance=" + sqlInstanceName + ";databaseName=" + sqlDatabase;
                
            Connection conn = DriverManager.getConnection(connectURL, sqlUser, sqlPassword);
            System.out.println("Connect to database successful!!");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
}
