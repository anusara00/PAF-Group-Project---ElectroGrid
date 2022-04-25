package com;

import model.Payment; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 




@Path("/Payments") 
public class PaymentService{ 
	
			Payment paymentObj = new Payment(); 
			
			
			@GET
			@Path("/") 
			@Produces(MediaType.TEXT_HTML) 
			public String readPayments() 
			 { 
				return paymentObj.readPayments(); 
			}
			
			@POST
			@Path("/") 
			@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
			@Produces(MediaType.TEXT_PLAIN) 
			public String insertPayment(@FormParam("AccountNo") String accountno, 
									    @FormParam("Cus_name") String cusname, 
									    @FormParam("Pay_date") String paydate, 
									    @FormParam("Total_amount") String totalamount){ 
				
				String output = paymentObj.insertPayment(accountno, cusname, paydate, totalamount); 
				return output; 
				
			}
			
			
			@PUT
			@Path("/") 
			@Consumes(MediaType.APPLICATION_JSON) 
			@Produces(MediaType.TEXT_PLAIN) 
			public String updatePayment(String paymentData) 
			{ 
				//Convert the input string to a JSON object 
				JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject(); 
				//Read the values from the JSON object
				String payid = paymentObject.get("Pay_ID").getAsString(); 
				String accountno = paymentObject.get("AccountNo").getAsString(); 
				String cusname = paymentObject.get("Cus_name").getAsString(); 
				String paydate = paymentObject.get("Pay_date").getAsString(); 
				String totalamount = paymentObject.get("Total_amount").getAsString(); 
				String output = paymentObj.updatePayment(payid, accountno, cusname, paydate, totalamount); 
				return output; 
				
			}
			
			
			@DELETE
			@Path("/") 
			@Consumes(MediaType.APPLICATION_XML) 
			@Produces(MediaType.TEXT_PLAIN) 
			public String deletePayment(String paymentData) 
			{ 
				//Convert the input string to an XML document
				Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser()); 
			 
				//Read the value from the element <Pay_ID>
				String payid = doc.select("Pay_ID").text(); 
				String output = paymentObj.deletePayment(payid); 
				return output; 
			}

			
					
}