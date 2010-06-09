package coredata.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import coredata.model.SubForumModel;
import coredata.model.TopicModel;

public class SaveTopic implements Runnable {
	private List list;

	private String threadName;

	private String url = "jdbc:oracle:thin:@172.17.1.106:1521:wpsdb";

	private String userid = "oracle";

	private String password = "password";

	private String schema = "ORACLE";

	private PreparedStatement pstmt;

	public SaveTopic(List list, String threadName) {
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

//						String query = "INSERT INTO "+schema+".WEB_BOARD_TOPIC_COMMON(WBTI_ID,WBFSI_ID,WBTI_NAME,WBTI_DESC,WBTI_MESSAGE,WBTI_STATUS,WBTI_USER_CREATE_ID,WBTI_CREATE_TIME,WBTI_UPDATE_TIME,WBTI_VIEWS,WBTI_REPLIES,WBTI_IP,WBTI_PATH,WBTI_EMAIL,WBTI_FILE_NAME,WBTI_MESSAGE_CLOB,WBTI_STATUS_AXIS,WBTI_STATUS_FLAG,WBTI_USER_NAME_LDAP,WBTI_STATUS_PERMISSION,WBTI_STATUS_NOTIFY,WBTI_LAST_REPLY_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						String query = "INSERT INTO "+schema+".WEB_BOARD_TOPIC_INTRA(WBTI_ID,WBFSI_ID,WBTI_NAME,WBTI_DESC,WBTI_MESSAGE,WBTI_STATUS,WBTI_USER_CREATE_ID,WBTI_CREATE_TIME,WBTI_UPDATE_TIME,WBTI_VIEWS,WBTI_REPLIES,WBTI_IP,WBTI_PATH,WBTI_EMAIL,WBTI_FILE_NAME,WBTI_MESSAGE_CLOB,WBTI_STATUS_AXIS,WBTI_STATUS_FLAG,WBTI_USER_NAME_LDAP,WBTI_STATUS_PERMISSION,WBTI_STATUS_NOTIFY,WBTI_LAST_REPLY_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						TopicModel topicModel = (TopicModel)list.get(i);
						pstmt = connn.prepareStatement(query);
						pstmt.setInt(1, topicModel.getTopicId());
						pstmt.setInt(2, topicModel.getForumId());
						pstmt.setString(3, topicModel.getTopicTitle());
						pstmt.setString(4, topicModel.getDesc());
						pstmt.setString(5, topicModel.getMessage());
						pstmt.setString(6, topicModel.getStatus());
						pstmt.setString(7, topicModel.getUserCreated());
						pstmt.setTimestamp(8, new Timestamp(topicModel.getCreatedTime().getTime()));
						pstmt.setTimestamp(9, new Timestamp(topicModel.getUpdatedTime().getTime()));
						pstmt.setString(10, topicModel.getViews());
						pstmt.setString(11, topicModel.getReply());
						pstmt.setString(12, topicModel.getIp());
						pstmt.setString(13, topicModel.getPath());
						pstmt.setString(14, topicModel.getEmail());
						pstmt.setString(15, topicModel.getFileName());
						pstmt.setString(16, topicModel.getMessageClob());
						pstmt.setString(17, topicModel.getStatusAxis());
						pstmt.setString(18, topicModel.getStatusFlag());
						pstmt.setString(19, topicModel.getUsernameLdap());
						pstmt.setString(20, topicModel.getStatusPermission());
						pstmt.setString(21, topicModel.getStatusNotify());
						pstmt.setTimestamp(22, new Timestamp(topicModel.getLastReplyTime().getTime()));
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
