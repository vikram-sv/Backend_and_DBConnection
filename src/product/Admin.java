package product;

import java.sql.*;

public class Admin {
    static String DB_URL = "jdbc:mysql://localhost:3306/loginsystem";
    static String DB_USER = "root";
    static String DB_PASSWORD = "root";

    public boolean login(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM login WHERE name = ? and password = ? and level='admin'";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username); // Sets the username in the query in place of "username = ?"
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false; // Username or password not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Couldn't Connect");
            return false; // Error occurred during login
        }
    }

    // To add a product by admin
    public void addProduct(int productId, String productName, int minSellQty, int price, int qty) {
        try (Connection con2 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO product VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con2.prepareStatement(query);
            stmt.setInt(1, productId);
            stmt.setString(2, productName);
            stmt.setInt(3, minSellQty);
            stmt.setInt(4, price);
            stmt.setInt(5, qty);

            stmt.executeUpdate();
            System.out.println("**Product is added Successfully**");
        } 
        
        catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Couldn't Connect"); // Error occurred during login
        }
    }
    
    
    //To view a product details
    public void viewAllProducts() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM product";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            //To beautify the display
            System.out.println();
            System.out.println("------ PRODUCTS ------");

            //After setting the values set the input 
            //While loop - to execute all the tables in the sql
            //rs.next ---> represents the true statement because it contains some values
            //If it is empty it directly rs.next() become false.
            while (rs.next()) {
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");
                int minSellQty = rs.getInt("minSellQty");
                int price = rs.getInt("price");
                int qty = rs.getInt("qty");

                System.out.print(productId + "| ");
                System.out.print(productName + "| ");
                System.out.print(minSellQty + "| ");
                System.out.print(price+"| ");
                System.out.println(qty);
                System.out.println("-----------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't Connect");
        }
    }
}