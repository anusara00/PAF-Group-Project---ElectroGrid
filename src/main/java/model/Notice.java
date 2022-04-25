package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Notice { //A common method to connect to the DB
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


public String insertNotice(String title, String description, String details, String date){ 

	String output = ""; 
	
	try
	{ 
		Connection con = connect(); 
		
		if (con == null) 
		{
			return "Error while connecting to the database for inserting."; 
			
		} 
		// create a prepared statement
		
		String query = " insert into notices (`noticeID`,`noticeTitle`,`noticeDescription`,`noticeDetails`,`noticeDate`)"+" values (?, ?, ?, ?, ?)"; 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		preparedStmt.setInt(1, 0); 
		preparedStmt.setString(2, title); 
		preparedStmt.setString(3, description); 
		preparedStmt.setString(4, details); 
		preparedStmt.setString(5, date); 
		// execute the statement

		preparedStmt.execute(); 
		con.close(); 
		output = "Inserted successfully"; 
	} 
	
	catch (Exception e) 
	{ 
		output = "Error while inserting the notice."; 
		System.err.println(e.getMessage()); 
	} 
	return output; 
} 



public String readNotices(){ 

	String output = ""; 
	
	try{ 
			Connection con = connect(); 
			if (con == null){
				
				return "Error while connecting to the database for reading."; 
				
				} 
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Notice Title</th><th>Notice Description</th>" +
					"<th>Notice Details</th>" + 
					"<th>Notice Date</th>" +
					"<th>Update</th><th>Remove</th></tr>"; 

			String query = "select * from notices"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			// iterate through the rows in the result set
			while (rs.next()) 
			{ 
					String noticeID = Integer.toString(rs.getInt("noticeID")); 
					String noticeTitle = rs.getString("noticeTitle"); 
					String noticeDescription = rs.getString("noticeDescription"); 
					String noticeDetails = rs.getString("noticeDetails"); 
					String noticeDate = rs.getString("noticeDate"); 
					// Add into the html table
					output += "<tr><td>" + noticeTitle + "</td>"; 
					output += "<td>" + noticeDescription + "</td>"; 
					output += "<td>" + noticeDetails + "</td>"; 
					output += "<td>" + noticeDate + "</td>"; 
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
							+ "<td><form method='post' action='inquirys.jsp'>"
							+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
							+ "<input name='itemID' type='hidden' value='" + noticeID 
							+ "'>" + "</form></td></tr>"; 
			} 
				con.close(); 
				// Complete the html table
				output += "</table>"; 
		} 
		catch (Exception e){ 
					output = "Error while reading the notices."; 
					System.err.println(e.getMessage()); 
		} 
		return output; 
		} 


public String updateNotice(String ID, String title, String description, String details, String date){ 

	String output = ""; 
	
	try{ 
			Connection con = connect(); 
			if (con == null){
				return "Error while connecting to the database for updating.";
				} 
			// create a prepared statement
			String query = "UPDATE notices SET noticeTitle=?,noticeDescription=?,noticeDetails=?,noticeDate=? WHERE noticeID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setString(1, title); 
			preparedStmt.setString(2, description); 
			preparedStmt.setString(3, details); 
			preparedStmt.setString(4, date); 
			preparedStmt.setInt(5, Integer.parseInt(ID)); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			output = "Updated successfully"; 
			
	} 
	
	catch (Exception e){ 
		
		output = "Error while updating the notice."; 
		System.err.println(e.getMessage()); 
		
	} 
	
	return output; 
} 


public String deleteNotice(String noticeID){ 

	String output = ""; 
	
	try{ 
		Connection con = connect(); 
		
		if (con == null){
			return "Error while connecting to the database for deleting."; 
			} 
		// create a prepared statement
		String query = "delete from notices where noticeID=?"; 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(noticeID)); 
		// execute the statement
		preparedStmt.execute(); 
		con.close(); 
		output = "Deleted successfully"; 
	} 
	
	catch (Exception e){ 
		output = "Error while deleting the notice."; 
		System.err.println(e.getMessage()); 
	} 
	return output; 
} 




} 