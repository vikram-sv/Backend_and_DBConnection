package product;

import java.sql.*;
import java.util.Scanner;

public class Agent {

	    public String DB_URL = "jdbc:mysql://localhost:3306/loginsystem";
	    public String DB_USER = "root";
	    public String DB_PASSWORD = "root";
	    
	    //To Login as Agent
	    public boolean Agentlogin(String username, String password) {
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
	            String query = "SELECT * FROM login WHERE name = ? and password = ? and level=\"agent\"";
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setString(1, username); //Sets the username in the query in place of "username = ?"
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
	    
	   //To made a choice for sell/Buy/logout
	    
	   public void choice() {
		   
		   //To display the choices
		  System.out.println("1. Buy");
		  System.out.println("2. Sell");
		  System.out.println("3. Logout");
		   
		  Scanner sc = new Scanner(System.in);
		  int val = sc.nextInt();
		  
		  switch(val) {
		  case 1:
			  System.out.println("----Add your Products----");
			  Admin admin = new Admin();

              // To add product details from the agent
              System.out.println("Enter Product ID: ");
              int pId = sc.nextInt();
              sc.nextLine();
              
              System.out.println("Enter Product Name: ");
              String pName = sc.nextLine();
              
              System.out.println("Enter Minimum Sell Quantity: ");
              int mSqty = sc.nextInt();
              
              System.out.println("Enter Price: ");
              int price = sc.nextInt();
              
              System.out.println("Enter Quantity: ");
              int qty = sc.nextInt();
              
              admin.addProduct(pId, pName, mSqty, price, qty);
              break;
              
		  case 2:
			  System.out.println("----Sell Products----");
			    System.out.println("Enter Product ID: ");
			    int sellProductId = sc.nextInt();
			    sc.nextLine();

			    System.out.println("Enter Quantity: ");
			    int sellQty = sc.nextInt();

			    // Check if the product exists and retrieve its price
			    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			        String query = "SELECT price, qty FROM product WHERE productId = ?";
			        PreparedStatement stmt = conn.prepareStatement(query);
			        stmt.setInt(1, sellProductId);
			        ResultSet rs = stmt.executeQuery();

			        if (rs.next()) {
			            int price1 = rs.getInt("price");
			            int availableQty = rs.getInt("qty");

			            if (sellQty <= availableQty) {
			                int totalPrice = price1 * sellQty;

			                // Update the quantity in the database
			                String updateQuery = "UPDATE product SET qty = ? WHERE productId = ?";
			                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
			                updateStmt.setInt(1, availableQty - sellQty);
			                updateStmt.setInt(2, sellProductId);
			                updateStmt.executeUpdate();

			                System.out.println("Product sold successfully. Total Price: " + totalPrice);
			            } else {
			                System.out.println("Insufficient quantity. Available quantity: " + availableQty);
			            }
			        } else {
			            System.out.println("Product not found.");
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			        System.out.println("Couldn't connect to the database.");
			    }
			    break;
			  
		  default:
			  System.out.println("Logout Successfull");
			  break;
		  }
	   }
}

