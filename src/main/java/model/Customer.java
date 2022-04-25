package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Customer {
	
	//A common method to connect to the DB
			private Connection connect(){ 
				
							Connection con = null; 
							
							try{ 
									Class.forName("com.mysql.jdbc.Driver"); 
	 
									//Provide the correct details: DBServer/DBName, username, password 
									con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", ""); 
							} 
							catch (Exception e) {
								e.printStackTrace();
								} 
							
							return con; 
				} 
			
			
			public String insertCustomer(String userid, String cusname, String accountno, String email, String phoneno, String address){ 
				
						String output = ""; 
						
						try
						{ 
							Connection con = connect(); 
							
							if (con == null) 
							{
								return "Error while connecting to the database for inserting."; 
								
							} 
							// create a prepared statement
							
							String query = " insert into customer (`UserID`,`Cus_name`,`AccountNo`,`Email`,`PhoneNo`,`Address`)"+" values (?, ?, ?, ?, ?, ?)"; 
							PreparedStatement preparedStmt = con.prepareStatement(query); 
							// binding values
							preparedStmt.setString(1, userid); 
							preparedStmt.setString(2, cusname); 
							preparedStmt.setString(3, accountno); 
							preparedStmt.setString(4, email); 
							preparedStmt.setString(5, phoneno); 
							preparedStmt.setString(6, address); 
							// execute the statement
	 
							preparedStmt.execute(); 
							con.close(); 
							output = "Inserted successfully"; 
						} 
						
						catch (Exception e) 
						{ 
							output = "Error while inserting the customer."; 
							System.err.println(e.getMessage()); 
						} 
						return output; 
				} 
			
			
			
				public String readCustomers(){ 
					
						String output = ""; 
						
						try{ 
								Connection con = connect(); 
								if (con == null){
									
									return "Error while connecting to the database for reading."; 
									
									} 
								// Prepare the html table to be displayed
								output = "<table border='1'><tr><th>User ID</th>" + "<th>Customer Full Name</th>" + "<th>Account Number</th>" +
										"<th>Customer Email</th>" + 
										"<th>Customer Phone No</th>" +
										"<th>Customer Address</th>" +
										"<th>Update</th><th>Remove</th></tr>"; 
	 
								String query = "select * from customer"; 
								Statement stmt = con.createStatement(); 
								ResultSet rs = stmt.executeQuery(query); 
								// iterate through the rows in the result set
								while (rs.next()) 
								{ 
										String UserID = rs.getString("UserID"); 
										String Cus_name = rs.getString("Cus_name"); 
										String AccountNo = rs.getString("AccountNo"); 
										String Email = rs.getString("Email"); 
										String PhoneNo = rs.getString("PhoneNo"); 
										String Address = rs.getString("Address"); 
										// Add into the html table
										output += "<tr><td>" + UserID + "</td>";
										output += "<td>" + Cus_name + "</td>"; 
										output += "<td>" + AccountNo + "</td>"; 
										output += "<td>" + Email + "</td>"; 
										output += "<td>" + PhoneNo + "</td>"; 
										output += "<td>" + Address + "</td>";
										// buttons
										output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
												+ "<td><form method='post' action='customers.jsp'>"
												+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
												+ "<input name='UserID' type='hidden' value='" + UserID 
												+ "'>" + "</form></td></tr>"; 
								} 
									con.close(); 
									// Complete the html table
									output += "</table>"; 
							} 
							catch (Exception e){ 
										output = "Error while reading the customers."; 
										System.err.println(e.getMessage()); 
							} 
							return output; 
							} 
				
				
				public String updateCustomer(String userid, String cusname, String accountno, String email, String phoneno, String address){ 
					
						String output = ""; 
						
						try{ 
								Connection con = connect(); 
								if (con == null){
									return "Error while connecting to the database for updating.";
									} 
								// create a prepared statement
								String query = "UPDATE customer SET Cus_name=?,AccountNo=?,Email=?,PhoneNo=?,Address=? WHERE UserID=?"; 
								PreparedStatement preparedStmt = con.prepareStatement(query); 
								// binding values
								preparedStmt.setString(1, cusname); 
								preparedStmt.setString(2, accountno); 
								preparedStmt.setString(3, email); 
								preparedStmt.setString(4, phoneno); 
								preparedStmt.setString(5, address); 
								preparedStmt.setString(6, userid); 
								// execute the statement
								preparedStmt.execute(); 
								con.close(); 
								output = "Updated successfully"; 
						} 
						
						catch (Exception e){ 
							
							output = "Error while updating the customer."; 
							System.err.println(e.getMessage()); 
							
						} 
						
						return output; 
				} 
				
				
				public String deleteCustomer(String UserID){ 
					
						String output = ""; 
						
						try{ 
							Connection con = connect(); 
							
							if (con == null){
								return "Error while connecting to the database for deleting."; 
								} 
							// create a prepared statement
							String query = "delete from customer where UserID=?"; 
							PreparedStatement preparedStmt = con.prepareStatement(query); 
							// binding values 
							preparedStmt.setString(1, UserID); 
							// execute the statement
							preparedStmt.execute(); 
							con.close(); 
							output = "Deleted successfully"; 
						}
						
						catch (Exception e){ 
							output = "Error while deleting the customer."; 
							System.err.println(e.getMessage()); 
						} 
						return output; 
				} 
				
				
				
				

}
