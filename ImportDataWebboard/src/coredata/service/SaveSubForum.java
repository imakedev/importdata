package coredata.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import coredata.model.SubForumModel;

public class SaveSubForum implements Runnable {
	private List list;

	private String threadName;

	private String url = "jdbc:oracle:thin:@172.17.1.106:1521:wpsdb";

	private String userid = "oracle";

	private String password = "password";

	private String schema = "ORACLE";

	private PreparedStatement pstmt;

	public SaveSubForum(List list, String threadName) {
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

//						String query = "INSERT INTO "+schema+".WEB_BOARD_FORUM_SUB_COMMON(WBFSI_ID,WBFI_ID,WBFSI_NAME,WBFSI_DESC,WBFSI_USER_CREATE_ID,WBFSI_CREATE_TIME,WBFSI_UPDATE_TIME) VALUES(?,?,?,?,?,?,?)";
						String query = "INSERT INTO "+schema+".WEB_BOARD_FORUM_SUB_INTRA(WBFSI_ID,WBFI_ID,WBFSI_NAME,WBFSI_DESC,WBFSI_USER_CREATE_ID,WBFSI_CREATE_TIME,WBFSI_UPDATE_TIME) VALUES(?,?,?,?,?,?,?)";
						
						SubForumModel subForumModel = (SubForumModel)list.get(i);
						pstmt = connn.prepareStatement(query);
						pstmt.setInt(1, subForumModel.getForumId());
						pstmt.setInt(2, subForumModel.getCatId());
						pstmt.setString(3, subForumModel.getForumName());
						pstmt.setString(4, subForumModel.getForumDesc());
						pstmt.setString(5, subForumModel.getUserCreated());
						pstmt.setTimestamp(6, new Timestamp(subForumModel.getCreatedTime().getTime()));
						pstmt.setTimestamp(7, new Timestamp(subForumModel.getUpdatedTime().getTime()));
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
