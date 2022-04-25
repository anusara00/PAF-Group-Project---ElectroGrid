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

import model.Notice;

@Path("/Notice")
public class NoticeService { 
	
	Notice noticeObj = new Notice(); 
	
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readNotices() 
	 { 
		return noticeObj.readNotices(); 
	}
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertNotice(@FormParam("noticeTitle") String noticeTitle, 
							 @FormParam("noticeDescription") String noticeDescription, 
							 @FormParam("noticeDetails") String noticeDetails, 
							 @FormParam("noticeDate") String noticeDate){ 
		
		String output = noticeObj.insertNotice(noticeTitle, noticeDescription, noticeDetails, noticeDate); 
		return output; 
		
	}
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateNotice(String noticeData) 
	{ 
		//Convert the input string to a JSON object 
		JsonObject itemObject = new JsonParser().parse(noticeData).getAsJsonObject(); 
		//Read the values from the JSON object
		String noticeID = itemObject.get("noticeID").getAsString(); 
		String noticeTitle = itemObject.get("noticeTitle").getAsString(); 
		String noticeDescription = itemObject.get("noticeDescription").getAsString(); 
		String noticeDetails = itemObject.get("noticeDetails").getAsString(); 
		String noticeDate = itemObject.get("noticeDate").getAsString(); 
		String output = noticeObj.updateNotice(noticeID, noticeTitle, noticeDescription, noticeDetails, noticeDate); 
		return output; 
		
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteNotice(String noticeData) 
	{ 
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(noticeData, "", Parser.xmlParser()); 
	 
		//Read the value from the element <itemID>
		String noticeID = doc.select("noticeID").text(); 
		String output = noticeObj.deleteNotice(noticeID); 
		return output; 
	}

	
			
}