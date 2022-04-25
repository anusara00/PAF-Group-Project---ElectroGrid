package com; 

import model.Customer; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 




@Path("/Customers") 
public class CustomerService{ 
	
			Customer customerObj = new Customer(); 
			
			
			@GET
			@Path("/") 
			@Produces(MediaType.TEXT_HTML) 
			public String readCustomers() 
			 { 
				return customerObj.readCustomers(); 
			}
			
			@POST
			@Path("/") 
			@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
			@Produces(MediaType.TEXT_PLAIN) 
			public String insertCustomer(@FormParam("UserID") String userid, 
									     @FormParam("Cus_name") String cusname, 
									     @FormParam("AccountNo") String accountno, 
									     @FormParam("Email") String email,
									     @FormParam("PhoneNo") String phoneno, 
									     @FormParam("Address") String address){ 
				
				String output = customerObj.insertCustomer(userid, cusname, accountno, email, phoneno, address); 
				return output; 
				
			}
			
			
			@PUT
			@Path("/") 
			@Consumes(MediaType.APPLICATION_JSON) 
			@Produces(MediaType.TEXT_PLAIN) 
			public String updateCustomer(String customerData) 
			{ 
				//Convert the input string to a JSON object 
				JsonObject customerObject = new JsonParser().parse(customerData).getAsJsonObject(); 
				//Read the values from the JSON object
				String UserID = customerObject.get("UserID").getAsString(); 
				String Cus_name = customerObject.get("Cus_name").getAsString(); 
				String AccountNo = customerObject.get("AccountNo").getAsString(); 
				String Email = customerObject.get("Email").getAsString(); 
				String PhoneNo = customerObject.get("PhoneNo").getAsString(); 
				String Address = customerObject.get("Address").getAsString();
				String output = customerObj.updateCustomer(UserID, Cus_name, AccountNo, Email, PhoneNo, Address); 
				return output; 
				
			}
			
			
			@DELETE
			@Path("/") 
			@Consumes(MediaType.APPLICATION_XML) 
			@Produces(MediaType.TEXT_PLAIN) 
			public String deleteCustomer(String customerData) 
			{ 
				//Convert the input string to an XML document
				Document doc = Jsoup.parse(customerData, "", Parser.xmlParser()); 
			 
				//Read the value from the element <itemID>
				String UserID = doc.select("UserID").text(); 
				String output = customerObj.deleteCustomer(UserID); 
				return output; 
			}

			
					
}
