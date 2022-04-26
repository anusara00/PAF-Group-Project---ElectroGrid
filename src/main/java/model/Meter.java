package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Meter { //A common method to connect to the DB
	private Connection connect(){ 
		
		Connection con = null; 
		
		try{ 
				Class.forName("com.mysql.jdbc.Driver"); 

				//Provide the correct details: DBServer/DBName, username, password 
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/inquiry_db", "root", ""); 
		} 
		catch (Exception e) {
			e.printStackTrace();
			} 
		
		return con; 
} 


public String insertMeter(String accno, String cusname, String unit, String totamount){ 

	String output = ""; 
	
	try
	{ 
		Connection con = connect(); 
		
		if (con == null) 
		{
			return "Error while connecting to the database for inserting."; 
			
		} 
		// create a prepared statement
		
		String query = " insert into meters (`MeterH_ID`,`AccountNo`,`Cus_name`,`Units`,`Total_amount`)"+" values (?, ?, ?, ?, ?)"; 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		preparedStmt.setInt(1, 0); 
		preparedStmt.setString(2, accno); 
		preparedStmt.setString(3, cusname); 
		preparedStmt.setString(4, unit); 
		preparedStmt.setString(5, totamount); 
		// execute the statement

		preparedStmt.execute(); 
		con.close(); 
		output = "Inserted successfully"; 
	} 
	
	catch (Exception e) 
	{ 
		output = "Error while inserting the meter."; 
		System.err.println(e.getMessage()); 
	} 
	return output; 
} 



public String readMeters(){ 

	String output = ""; 
	
	try{ 
			Connection con = connect(); 
			if (con == null){
				
				return "Error while connecting to the database for reading."; 
				
				} 
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>MeterH_ID</th><th>AccountNo</th><th>Cus_name</th>" +
					"<th>Units</th>" + 
					"<th>Total_amount</th>" +
					"<th>Update</th><th>Remove</th></tr>"; 

			String query = "select * from meters"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			// iterate through the rows in the result set
			while (rs.next()) 
			{ 
					String MeterH_ID = Integer.toString(rs.getInt("MeterH_ID")); 
					String AccountNo = rs.getString("AccountNo"); 
					String Cus_name = rs.getString("Cus_name"); 
					String Units = rs.getString("Units"); 
					String Total_amount = rs.getString("Total_amount"); 
					// Add into the html table
					output += "<tr><td>" + MeterH_ID + "</td>"; 
					output += "<td>" + AccountNo + "</td>"; 
					output += "<td>" + Cus_name + "</td>"; 
					output += "<td>" + Units + "</td>"; 
					output += "<td>" + Total_amount + "</td>"; 
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
							+ "<td><form method='post' action='inquirys.jsp'>"
							+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
							+ "<input name='itemID' type='hidden' value='" + MeterH_ID 
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


public String updateMeter(String ID, String accno, String cusname, String unit, String totamount){ 

	String output = ""; 
	
	try{ 
			Connection con = connect(); 
			if (con == null){
				return "Error while connecting to the database for updating.";
				} 
			// create a prepared statement
			String query = "UPDATE meters SET AccountNo=?,Cus_name=?,Units=?,Total_amount=? WHERE MeterH_ID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setString(1, accno); 
			preparedStmt.setString(2, cusname); 
			preparedStmt.setString(3, unit); 
			preparedStmt.setString(4, totamount); 
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


public String deleteMeter(String MeterH_ID){ 

	String output = ""; 
	
	try{ 
		Connection con = connect(); 
		
		if (con == null){
			return "Error while connecting to the database for deleting."; 
			} 
		// create a prepared statement
		String query = "delete from meters where MeterH_ID=?"; 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(MeterH_ID)); 
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