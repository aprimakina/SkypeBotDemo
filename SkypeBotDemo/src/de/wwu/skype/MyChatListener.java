package de.wwu.skype;

import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.Friend;
import com.skype.Skype;
import com.skype.SkypeException;
import java.text.SimpleDateFormat;

public class MyChatListener implements ChatMessageListener {
	private static MyChatListener instance;
	MySQLAccess dao = new MySQLAccess();
	ChatBot chatBot;

	private MyChatListener() {
	}
	
	public static MyChatListener getInstance() throws SkypeException {
		if (instance == null) {
			instance = new MyChatListener();
		}
		instance.chatBot = new ChatBot();
		return instance;
	}
	
	public static boolean myChatListenerExist() {
		if (instance == null)
			return(false);
		else return(true);
	}
	
	private void myListener(String myMessage, Chat myChats) throws Exception {

		String customerNickName = Constants.SKYPE_CUSTOMER_NICKNAME;
		String chatId = Skype.chat(customerNickName).getId();
		
		String customerFullName = Skype.getContactList().getFriend(customerNickName).getFullName();

		BotMessage botMessage = chatBot.botMessage(myMessage);

		Skype.chat(customerNickName).send(botMessage.message);

		if (botMessage.messageType == 1) {
			startNewChat(Constants.SKYPE_EXPERT_NICKNAME, customerNickName);
			
			try {
			sendChatHistory(chatId, customerNickName, customerFullName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			endBotChat();
		}

		if (botMessage.message == "Good work!") {
			endBotChat();
		}

	}

	@Override
	public void chatMessageReceived(ChatMessage recMessage) throws SkypeException {
		try {
			myListener(recMessage.getContent(), recMessage.getChat());
			System.out.println("\n" + recMessage.getSenderDisplayName() + " : " + recMessage.getContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new java.util.Date());
			dao.writeDataBase(timeStamp, recMessage.getSender().getId(), recMessage.getContent(),
					recMessage.getChat().getId(), recMessage.getSender().getFullName());

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void chatMessageSent(ChatMessage sentMessage) throws SkypeException {
		try {
			System.out.println("\n" + sentMessage.getSenderDisplayName() + " : " + sentMessage.getContent());
		} catch (final SkypeException ex) {
			ex.printStackTrace();
		}
		try {
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new java.util.Date());
			dao.writeDataBase(timeStamp, sentMessage.getSender().getId(), sentMessage.getContent(),
					sentMessage.getChat().getId(), sentMessage.getSender().getFullName());

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void startNewChat(String expertNickName, String customerNickName) throws SkypeException {

		Friend expert = Skype.getContactList().getFriend(expertNickName);
		Chat expertChat = expert.chat();

		expertChat.addUser(Skype.getContactList().getFriend(customerNickName));
		expertChat.send("Expert is ready to assist you");
	}

	public void endBotChat() {

		try {
			Thread.sleep(10000); // 1000 milliseconds is one second.
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		chatBot = new ChatBot();

		try {
			dao.deleteStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendChatHistory(String chatId, String customerNickName, String customerFullName) throws Exception {

		String mailContent = dao.readDataBase(chatId);
		String subject = "Chat history with customer " + customerFullName + " (" + customerNickName + ")";
		SendHTMLEmail.main(subject, chatId, mailContent);

	}
}
