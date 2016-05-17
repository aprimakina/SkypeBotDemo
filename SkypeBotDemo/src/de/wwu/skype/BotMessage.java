package de.wwu.skype;

public class BotMessage {
	String message;
	int messageType;
	int index;
	
    public void setMessage(String message) {
        this.message = message;
     }

     public String getMessage() {
        return message;
     }
     
     public void setMessageType(int messageType) {
         this.messageType = messageType;
      }

      public int getMessageType() {
         return messageType;
      }
      
      public void setMessageIndex(int index) {
          this.index = index;
       }

       public int getMessageIndex() {
          return index;
       }
}
