package de.wwu.skype;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLAccess {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public void dbConnect() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://localhost/skype?" + "user=root&password=admin");
		} catch (Exception e) {
			throw e;
		}
	}

	public void writeDataBase(String time, String userId, String message, String chatId, String userNickName) throws Exception {

		if (connect == null) {
			dbConnect();
		}
		preparedStatement = connect.prepareStatement("insert into  skype.chat values (?, ?, ?, ?, ?)");
		
		preparedStatement.setString(1, userId);
		preparedStatement.setString(2, message);
		preparedStatement.setString(3, chatId);
		preparedStatement.setString(4, time);
		preparedStatement.setString(5, userNickName);
		preparedStatement.executeUpdate();

	}

	public String readDataBase(String chatId) throws Exception {
		if (connect == null)
			dbConnect();
		preparedStatement = connect
				.prepareStatement("SELECT time, userNickName, message from skype.chat where chatId = ?");
		preparedStatement.setString(1, chatId);
		resultSet = preparedStatement.executeQuery();

		return (writeResultSet(resultSet));
	}
	
	public void deleteStatement() throws Exception {
		if (connect == null)
			dbConnect();
		preparedStatement = connect
				.prepareStatement("delete from skype.chat; ");
		preparedStatement.executeUpdate();
	}	
	

	private String writeResultSet(ResultSet resultSet) throws SQLException {
		String res = "Chat history: <br><br>";
		while (resultSet.next()) {
			String time = resultSet.getString("time");
			String userNickName = resultSet.getString("userNickName");
			String message = resultSet.getString("message");
			System.out.println(userNickName + " : " + message);

			res = res + "<b>" + userNickName + "</b> (" + time + "):  " + message + "<br>";
		}
		return res;
	}

}
