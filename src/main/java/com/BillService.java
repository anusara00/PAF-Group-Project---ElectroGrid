package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Bill;

@Path("/Bill")
public class BillService { 
	
	Bill billObj = new Bill(); 
	
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readBills() 
	 { 
		return billObj.readBills(); 
	}
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertBill(@FormParam("AccountNo") String AccountNo, 
							 @FormParam("Cus_name") String Cus_name, 
							 @FormParam("Units") String Units, 
							 @FormParam("Total_amount") String Total_amount,
							 @FormParam("Bill_Date") String Bill_Date){ 
		
		String output = billObj.insertBill(AccountNo, Cus_name, Units, Total_amount, Bill_Date); 
		return output; 
		
	}
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateBill(String billData) 
	{ 
		//Convert the input string to a JSON object 
		JsonObject itemObject = new JsonParser().parse(billData).getAsJsonObject(); 
		//Read the values from the JSON object
		String billID = itemObject.get("billID").getAsString(); 
		String AccountNo = itemObject.get("AccountNo").getAsString(); 
		String Cus_name = itemObject.get("Cus_name").getAsString(); 
		String Units = itemObject.get("Units").getAsString(); 
		String Total_amount = itemObject.get("Total_amount").getAsString(); 
		String Bill_Date = itemObject.get("Bill_Date").getAsString(); 
		String output = billObj.updateBill(billID, AccountNo, Cus_name, Units, Total_amount, Bill_Date); 
		return output; 
		
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteBill(String billData) 
	{ 
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(billData, "", Parser.xmlParser()); 
	 
		//Read the value from the element <itemID>
		String billID = doc.select("billID").text(); 
		String output = billObj.deleteBill(billID); 
		return output; 
	}

	
			
}