package de.wwu.skype;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;

import com.skype.SkypeException;

@Path("bot/{expertNickName}/{customerNickName}/{emailToAddress}")
public class BotRest{
 
 //http://localhost:8080/SkypeBotDemo-0.1/api/bot/axelia55
 @GET
 @Produces(MediaType.APPLICATION_JSON)
 public String CallBot(@PathParam("expertNickName") String expertNickName, 
		 @PathParam("customerNickName") String customerNickName,
		 @PathParam("emailToAddress") String emailToAddress) throws SkypeException {
	 
	 StartChat runBot = new StartChat();
	 runBot.init(customerNickName);
     
	 Constants.SKYPE_EXPERT_NICKNAME = expertNickName;
	 Constants.EMAIL_TO_ADDRESS = emailToAddress;
	 Constants.SKYPE_CUSTOMER_NICKNAME = customerNickName;
     return "OK";
 }
}
