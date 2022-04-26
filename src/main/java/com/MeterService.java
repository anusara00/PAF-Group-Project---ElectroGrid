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

import model.Meter;

@Path("/Meter")
public class MeterService { 
	
	Meter meterObj = new Meter(); 
	
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readMeters() 
	 { 
		return meterObj.readMeters(); 
	}
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertMeter(@FormParam("AccountNo") String AccountNo, 
							 @FormParam("Cus_name") String Cus_name, 
							 @FormParam("Units") String Units, 
							 @FormParam("Total_amount") String Total_amount){ 
		
		String output = meterObj.insertMeter(AccountNo, Cus_name, Units, Total_amount); 
		return output; 
		
	}
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateMeter(String noticeData) 
	{ 
		//Convert the input string to a JSON object 
		JsonObject itemObject = new JsonParser().parse(noticeData).getAsJsonObject(); 
		//Read the values from the JSON object
		String MeterH_ID = itemObject.get("MeterH_ID").getAsString(); 
		String AccountNo = itemObject.get("AccountNo").getAsString(); 
		String Cus_name = itemObject.get("Cus_name").getAsString(); 
		String Units = itemObject.get("Units").getAsString(); 
		String Total_amount = itemObject.get("Total_amount").getAsString(); 
		String output = meterObj.updateMeter(MeterH_ID, AccountNo, Cus_name, Units, Total_amount); 
		return output; 
		
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteMeter(String meterData) 
	{ 
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(meterData, "", Parser.xmlParser()); 
	 
		//Read the value from the element <itemID>
		String MeterH_ID = doc.select("MeterH_ID").text(); 
		String output = meterObj.deleteMeter(MeterH_ID); 
		return output; 
	}

	
			
}