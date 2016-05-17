package de.wwu.skype;

public class ChatBot {

	String[][] chatBotCustom = {

			{ "no" }, { "Hello, \n"
					+ "Have you already tried to empty and refill the water trank of your coffee maker? Did it help?" },

			{ "no" }, { "Could you please press the ABC button on your coffee-machine. And write done when you are finished?" },
			// default
			{ "You are now connected to an expert who will provide you the further assistance." } };
	
	String[][] chatBotGeneral = { 
			{ "init" }, {
			"Dear Customer, we've got the information that... Here is an article that can help you: http://kb... Did it help to solve the issue?" },

			// standard greetings
			{ "hi", "hello" }, { "Hello!" },

			{ "yes" }, { "Good work!" },
			// default
			{ "You are now connected to an expert who will provide you the further assistance." } };

	public BotMessage botMessage(String recMessage) {

		BotMessage botMessage = new BotMessage();

		botMessage = botMessage(chatBotGeneral, recMessage);
		if (botMessage.messageType != 1) {
			return botMessage;
		} 
		
		botMessage = botMessage(chatBotCustom, recMessage);
		if (botMessage.messageType != 1) {
			System.out.println(chatBotCustom[botMessage.index][0]);	
			chatBotCustom[botMessage.index][0] = "removed";
		}
		return botMessage;
		
	}

	public BotMessage botMessage(String[][] chatBot, String recMessage) {
		
		String quote=recMessage;
		quote.trim();
		byte response=0;
		String answer = "";
		
		BotMessage botMessage = new BotMessage();

		//-----check for matches----
		int j=0;//which group we're checking
		while(response==0){
			if(inArray(quote.toLowerCase(),chatBot[j*2])){
				response=2;
				int r=(int)Math.floor(Math.random()*chatBot[(j*2)+1].length);
				answer = chatBot[(j*2)+1][r];
			}
			j++;
			if(j*2==chatBot.length-1 && response==0){
				response=1;
			}
		}
		
		//-----default--------------
		if(response==1){
			int r=(int)Math.floor(Math.random()*chatBot[chatBot.length-1].length);
			answer  = chatBot[chatBot.length-1][r];
		}
		botMessage.message = answer;
		botMessage.messageType = response;
		botMessage.index = (j-1)*2;

		return (botMessage);
	}
	
	public boolean inArray(String in, String[] str) {
		boolean match = false;
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals(in)) {
				match = true;
			}
		}
		return match;
	}

}
