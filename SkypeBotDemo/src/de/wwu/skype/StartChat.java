package de.wwu.skype;

import com.skype.Skype;
import com.skype.SkypeException;

public class StartChat {

	public void init(String customerNickName) throws SkypeException {
	
		if (MyChatListener.myChatListenerExist() == false) {
			Skype.addChatMessageListener(MyChatListener.getInstance());
			Skype.setDaemon(false);
		}
		
		ChatBot chatBot = new ChatBot();
		BotMessage botMessage = chatBot.botMessage("init");

		try {
			Skype.chat(customerNickName).send(botMessage.message);

		} catch (SkypeException e) {
			e.printStackTrace();
		}
	}
}
