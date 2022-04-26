package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bill { //A common method to connect to the DB
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




public String insertBill(String accnum, String cusname, String unit, String totamount, String billdate){

String output = "";

try
{
Connection con = connect();

if (con == null)
{
return "Error while connecting to the database for inserting.";

}
// create a prepared statement

String query = " insert into bills (`billID`,`AccountNo`,`Cus_name`,`Units`,`Total_amount`,`Bill_Date`)"+" values (?, ?, ?, ?, ?, ?)";
PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
preparedStmt.setInt(1, 0);
preparedStmt.setString(2, accnum);
preparedStmt.setString(3, cusname);
preparedStmt.setString(4, unit);
preparedStmt.setString(5, totamount);
preparedStmt.setString(6, billdate);
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



public String readBills(){ 

	String output = ""; 
	
	try{ 
			Connection con = connect(); 
			if (con == null){
				
				return "Error while connecting to the database for reading."; 
				
				} 
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Bill_id</th><th>AccountNo</th><th>Cus_name</th>" +
					"<th>Units</th>" + 
					"<th>Total Amount</th>" +
					"<th>Bill_Date</th>" +
					"<th>Update</th><th>Remove</th></tr>"; 

			String query = "select * from bills"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			// iterate through the rows in the result set
			while (rs.next()) 
			{ 
					String billID = Integer.toString(rs.getInt("billID")); 
					String AccountNo = rs.getString("AccountNo"); 
					String Cus_name = rs.getString("Cus_name"); 
					String Units = rs.getString("Units"); 
					String Total_amount = rs.getString("Total_amount"); 
					String Bill_Date = rs.getString("Bill_Date"); 
					// Add into the html table
					output += "<tr><td>" + billID + "</td>"; 
					output += "<td>" + AccountNo + "</td>"; 
					output += "<td>" + Cus_name + "</td>"; 
					output += "<td>" + Units + "</td>"; 
					output += "<td>" + Total_amount + "</td>"; 
					output += "<td>" + Bill_Date + "</td>"; 
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
							+ "<td><form method='post' action='inquirys.jsp'>"
							+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
							+ "<input name='itemID' type='hidden' value='" + billID 
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





public String updateBill(String ID, String accnum, String cusname, String unit, String totamount, String billdate){ 
	
	String output = ""; 
	
	try{ 
			Connection con = connect(); 
			if (con == null){
				return "Error while connecting to the database for updating.";
				} 
			// create a prepared statement
			String query = "UPDATE bills SET AccountNo=?,Cus_name=?,Units=?,Total_amount=?,Bill_Date=? WHERE billID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setString(1, accnum); 
			preparedStmt.setString(2, cusname); 
			preparedStmt.setString(3, unit); 
			preparedStmt.setString(4, totamount); 
			preparedStmt.setString(5, billdate); 
			preparedStmt.setInt(6, Integer.parseInt(ID));
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




public String deleteBill(String billID){ 

	String output = ""; 
	
	try{ 
		Connection con = connect(); 
		
		if (con == null){
			return "Error while connecting to the database for deleting."; 
			} 
		// create a prepared statement
		String query = "delete from bills where billID=?"; 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(billID)); 
		// execute the statement
		preparedStmt.execute(); 
		con.close(); 
		output = "Deleted successfully"; 
	} 
	
	catch (Exception e){ 
		output = "Error while deleting the bill."; 
		System.err.println(e.getMessage()); 
	} 
	return output; 
} 




} 