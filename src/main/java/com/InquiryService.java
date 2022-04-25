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

import model.Inquiry;


@Path("/Inquiry") 
public class InquiryService { 
	
	Inquiry inquiryObj = new Inquiry(); 
	
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readInquirys() 
	 { 
		return inquiryObj.readInquirys(); 
	}
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertInquiry(@FormParam("inquiryType") String inquiryType, 
							 @FormParam("inquiryEmail") String inquiryEmail, 
							 @FormParam("inquiryName") String inquiryName, 
							 @FormParam("inquiryMsg") String inquiryMsg){ 
		
		String output = inquiryObj.insertInquiry(inquiryType, inquiryEmail, inquiryName, inquiryMsg); 
		return output; 
		
	}
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateInquiry(String inquiryData) 
	{ 
		//Convert the input string to a JSON object 
		JsonObject itemObject = new JsonParser().parse(inquiryData).getAsJsonObject(); 
		//Read the values from the JSON object
		String inquiryID = itemObject.get("inquiryID").getAsString(); 
		String inquiryType = itemObject.get("inquiryType").getAsString(); 
		String inquiryEmail = itemObject.get("inquiryEmail").getAsString(); 
		String inquiryName = itemObject.get("inquiryName").getAsString(); 
		String inquiryMsg = itemObject.get("inquiryMsg").getAsString(); 
		String output = inquiryObj.updateInquiry(inquiryID, inquiryType, inquiryEmail, inquiryName, inquiryMsg); 
		return output; 
		
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteInquiry(String inquiryData) 
	{ 
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(inquiryData, "", Parser.xmlParser()); 
	 
		//Read the value from the element <itemID>
		String inquiryID = doc.select("inquiryID").text(); 
		String output = inquiryObj.deleteInquiry(inquiryID); 
		return output; 
	}

	
			
}