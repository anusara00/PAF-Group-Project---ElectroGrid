package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {
	 //A common method to connect to the DB
		private Connection connect(){ 
			
						Connection con = null; 
						
						try{ 
								Class.forName("com.mysql.jdbc.Driver"); 
 
								//Provide the correct details: DBServer/DBName, username, password 
								con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/item_database", "root", ""); 
						} 
						catch (Exception e) {
							e.printStackTrace();
							} 
						
						return con; 
			} 
		
		
		public String insertPayment(String accountno, String cusname, String paydate, String totalamount){ 
			
					String output = ""; 
					
					try
					{ 
						Connection con = connect(); 
						
						if (con == null) 
						{
							return "Error while connecting to the database for inserting."; 
							
						} 
						// create a prepared statement
						
						String query = " insert into payments (`Pay_ID`,`AccountNo`,`Cus_name`,`Pay_date`,`Total_amount`)"+" values (?, ?, ?, ?, ?)"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						// binding values
						preparedStmt.setInt(1, 0); 
						preparedStmt.setString(2, accountno); 
						preparedStmt.setString(3, cusname); 
						preparedStmt.setString(4, paydate); 
						preparedStmt.setString(5, totalamount); 
						// execute the statement
 
						preparedStmt.execute(); 
						con.close(); 
						output = "Inserted successfully"; 
					} 
					
					catch (Exception e) 
					{ 
						output = "Error while inserting the payment."; 
						System.err.println(e.getMessage()); 
					} 
					return output; 
			} 
		
		
		
			public String readPayments(){ 
				
					String output = ""; 
					
					try{ 
							Connection con = connect(); 
							if (con == null){
								
								return "Error while connecting to the database for reading."; 
								
								} 
							// Prepare the html table to be displayed
							output = "<table border='1'><tr><th>Payment ID</th>" + "<th>Account No</th>" + "<th>Customer Full Name</th>" +
									"<th>Payment date</th>" + 
									"<th>Total Amount</th>" +
									"<th>Update</th><th>Remove</th></tr>"; 
 
							String query = "select * from payments"; 
							Statement stmt = con.createStatement(); 
							ResultSet rs = stmt.executeQuery(query); 
							// iterate through the rows in the result set
							while (rs.next()) 
							{ 
									String payid = Integer.toString(rs.getInt("Pay_ID")); 
									String accountno = rs.getString("AccountNo"); 
									String cusname = rs.getString("Cus_name"); 
									String paydate = rs.getString("Pay_date"); 
									String totalamount = rs.getString("Total_amount"); 
									// Add into the html table
									output += "<tr><td>" + payid + "</td>";
									output += "<td>" + accountno + "</td>"; 
									output += "<td>" + cusname + "</td>"; 
									output += "<td>" + paydate + "</td>"; 
									output += "<td>" + totalamount + "</td>"; 
									// buttons
									output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
											+ "<td><form method='post' action='payments.jsp'>"
											+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
											+ "<input name='payid' type='hidden' value='" + payid 
											+ "'>" + "</form></td></tr>"; 
							} 
								con.close(); 
								// Complete the html table
								output += "</table>"; 
						} 
						catch (Exception e){ 
									output = "Error while reading the payments."; 
									System.err.println(e.getMessage()); 
						} 
						return output; 
						} 
			
			
			public String updatePayment(String payid, String accountno, String cusname, String paydate, String totalamount){ 
				
					String output = ""; 
					
					try{ 
							Connection con = connect(); 
							if (con == null){
								return "Error while connecting to the database for updating.";
								} 
							// create a prepared statement
							String query = "UPDATE payments SET AccountNo=?,Cus_name=?,Pay_date=?,Total_amount=? WHERE Pay_ID=?"; 
							PreparedStatement preparedStmt = con.prepareStatement(query); 
							// binding values
							preparedStmt.setString(1, payid); 
							preparedStmt.setString(2, accountno); 
							preparedStmt.setString(3, cusname); 
							preparedStmt.setString(4, paydate); 
							preparedStmt.setString(4, totalamount); 
							preparedStmt.setInt(5, Integer.parseInt(payid)); 
							// execute the statement
							preparedStmt.execute(); 
							con.close(); 
							output = "Updated successfully"; 
					} 
					
					catch (Exception e){ 
						
						output = "Error while updating the payment."; 
						System.err.println(e.getMessage()); 
						
					} 
					
					return output; 
			} 
			
			
			public String deletePayment(String payid){ 
				
					String output = ""; 
					
					try{ 
						Connection con = connect(); 
						
						if (con == null){
							return "Error while connecting to the database for deleting."; 
							} 
						// create a prepared statement
						String query = "delete from payments where Pay_ID=?"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						// binding values
						preparedStmt.setInt(1, Integer.parseInt(payid)); 
						// execute the statement
						preparedStmt.execute(); 
						con.close(); 
						output = "Deleted successfully"; 
					} 
					
					catch (Exception e){ 
						output = "Error while deleting the payment."; 
						System.err.println(e.getMessage()); 
					} 
					return output; 
			} 
			
			
			
			
}
