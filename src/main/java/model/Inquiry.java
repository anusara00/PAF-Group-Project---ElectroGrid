package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Inquiry { //A common method to connect to the DB
	private Connection connect(){ 
		
		Connection con = null; 
		
		try{ 
				Class.forName("com.mysql.jdbc.Driver"); 

				//Provide the correct details: DBServer/DBName, username, password 
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:4306/inquiry_db", "root", ""); 
		} 
		catch (Exception e) {
			e.printStackTrace();
			} 
		
		return con; 
} 


public String insertInquiry(String type, String email, String name, String msg){ 

	String output = ""; 
	
	try
	{ 
		Connection con = connect(); 
		
		if (con == null) 
		{
			return "Error while connecting to the database for inserting."; 
			
		} 
		// create a prepared statement
		
		String query = " insert into inquirys (`inquiryID`,`inquiryType`,`inquiryEmail`,`inquiryName`,`inquiryMsg`)"+" values (?, ?, ?, ?, ?)"; 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		preparedStmt.setInt(1, 0); 
		preparedStmt.setString(2, type); 
		preparedStmt.setString(3, email); 
		preparedStmt.setString(4, name); 
		preparedStmt.setString(5, msg); 
		// execute the statement

		preparedStmt.execute(); 
		con.close(); 
		output = "Inserted successfully"; 
	} 
	
	catch (Exception e) 
	{ 
		output = "Error while inserting the item."; 
		System.err.println(e.getMessage()); 
	} 
	return output; 
} 



public String readInquirys(){ 

	String output = ""; 
	
	try{ 
			Connection con = connect(); 
			if (con == null){
				
				return "Error while connecting to the database for reading."; 
				
				} 
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Inquiry Type</th><th>Inquiry Email</th>" +
					"<th>Inquiry Name</th>" + 
					"<th>Inquiry Message</th>" +
					"<th>Update</th><th>Remove</th></tr>"; 

			String query = "select * from inquirys"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			// iterate through the rows in the result set
			while (rs.next()) 
			{ 
					String inquiryID = Integer.toString(rs.getInt("inquiryID")); 
					String inquiryType = rs.getString("inquiryType"); 
					String inquiryEmail = rs.getString("inquiryEmail"); 
					String inquiryName = rs.getString("inquiryName"); 
					String inquiryMsg = rs.getString("inquiryMsg"); 
					// Add into the html table
					output += "<tr><td>" + inquiryType + "</td>"; 
					output += "<td>" + inquiryEmail + "</td>"; 
					output += "<td>" + inquiryName + "</td>"; 
					output += "<td>" + inquiryMsg + "</td>"; 
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
							+ "<td><form method='post' action='inquirys.jsp'>"
							+ "</br><input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
							+ "<input name='itemID' type='hidden' value='" + inquiryID 
							+ "'>" + "</form></td></tr>"; 
			} 
				con.close(); 
				// Complete the html table
				output += "</table>"; 
		} 
		catch (Exception e){ 
					output = "Error while reading the items."; 
					System.err.println(e.getMessage()); 
		} 
		return output; 
		} 


public String updateInquiry(String ID, String type, String email, String name, String msg){ 

	String output = ""; 
	
	try{ 
			Connection con = connect(); 
			if (con == null){
				return "Error while connecting to the database for updating.";
				} 
			// create a prepared statement
			String query = "UPDATE inquirys SET inquiryType=?,inquiryEmail=?,inquiryName=?,inquiryMsg=? WHERE inquiryID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setString(1, type); 
			preparedStmt.setString(2, email); 
			preparedStmt.setString(3, name); 
			preparedStmt.setString(4, msg); 
			preparedStmt.setInt(5, Integer.parseInt(ID)); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			output = "Updated successfully"; 
			
	} 
	
	catch (Exception e){ 
		
		output = "Error while updating the inquiry."; 
		System.err.println(e.getMessage()); 
		
	} 
	
	return output; 
} 


public String deleteInquiry(String inquiryID){ 

	String output = ""; 
	
	try{ 
		Connection con = connect(); 
		
		if (con == null){
			return "Error while connecting to the database for deleting."; 
			} 
		// create a prepared statement
		String query = "delete from inquirys where inquiryID=?"; 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(inquiryID)); 
		// execute the statement
		preparedStmt.execute(); 
		con.close(); 
		output = "Deleted successfully"; 
	} 
	
	catch (Exception e){ 
		output = "Error while deleting the item."; 
		System.err.println(e.getMessage()); 
	} 
	return output; 
} 




} 