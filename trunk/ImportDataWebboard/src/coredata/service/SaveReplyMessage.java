package coredata.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import coredata.model.ReplyModel;
import coredata.model.SubForumModel;
import coredata.model.TopicModel;

public class SaveReplyMessage implements Runnable {
	private List list;

	private String threadName;

	private String url = "jdbc:oracle:thin:@172.17.1.106:1521:wpsdb";

	private String userid = "oracle";

	private String password = "password";

	private String schema = "ORACLE";

	private PreparedStatement pstmt;

	public SaveReplyMessage(List list, String threadName) {
		this.list = list;
		this.threadName = threadName;
	}

	public void run() {

		synchronized (this) {

			Connection connn = null;
			PreparedStatement pstmt = null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				connn = DriverManager.getConnection(url, userid, password);
				System.out.println(connn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try {

				if (true)
				{
					for (int i = 0; i < list.size(); i++) {

//						String query = "INSERT INTO "+schema+".WEB_BOARD_REPLY_COMMON(WBRI_ID,WBTI_ID,WBRI_MESSAGE,WBRI_STATUS,WBRI_USER_CREATE_ID,WBRI_CREATE_TIME,WBRI_UPDATE_TIME,WBRI_IP,WBRI_PATH,WBRI_EMAIL,WBRI_MESSAGE_CLOB,WBRI_FLAG_STATUS,WBRI_USER_NAME_LDAP) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
						String query = "INSERT INTO "+schema+".WEB_BOARD_REPLY_INTRA(WBRI_ID,WBTI_ID,WBRI_MESSAGE,WBRI_STATUS,WBRI_USER_CREATE_ID,WBRI_CREATE_TIME,WBRI_UPDATE_TIME,WBRI_IP,WBRI_PATH,WBRI_EMAIL,WBRI_MESSAGE_CLOB,WBRI_FLAG_STATUS,WBRI_USER_NAME_LDAP) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
						ReplyModel replyModel = (ReplyModel)list.get(i);
						pstmt = connn.prepareStatement(query);
						pstmt.setInt(1, replyModel.getPostId());
						pstmt.setInt(2, replyModel.getTopicId());
						pstmt.setString(3, replyModel.getMessage());
						pstmt.setString(4, replyModel.getStatus());
						pstmt.setString(5, replyModel.getUserCreated());
						pstmt.setTimestamp(6, new Timestamp(replyModel.getCreatedTime().getTime()));
						pstmt.setTimestamp(7, new Timestamp(replyModel.getUpdatedTime().getTime()));
						pstmt.setString(8, replyModel.getIp());
						pstmt.setString(9, replyModel.getPath());
						pstmt.setString(10, replyModel.getEmail());
						pstmt.setString(11, replyModel.getMessageClob());
						pstmt.setString(12, replyModel.getStatusFlag());
						pstmt.setString(13, replyModel.getUsernameLdap());
						pstmt.execute();
						pstmt.close();	

						try {
							connn.commit();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				connn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			} 
			finally {

				if (connn != null) {
					try {
						connn.close();
					} catch (SQLException e) {
						System.err.println("SQLException " + e);
					}
				} 

			}
		}
	}
}
