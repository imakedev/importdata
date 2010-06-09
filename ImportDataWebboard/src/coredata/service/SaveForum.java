package coredata.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import coredata.model.ForumModel;

public class SaveForum implements Runnable {
	private List list;

	private String threadName;

	private String url = "jdbc:oracle:thin:@172.17.1.106:1521:wpsdb";

	private String userid = "oracle";

	private String password = "password";

	private String schema = "ORACLE";

	private PreparedStatement pstmt;

	public SaveForum(List list, String threadName) {
		this.list = list;
		this.threadName = threadName;
	}

	public void run() {

//		try {
//			boolean checkDelete = searchByHRForm();
//			if (checkDelete = false) {
//				searchByHRForm();
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

		synchronized (this) {

			Connection connn = null;
			PreparedStatement pstmt = null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				connn = DriverManager.getConnection(url, userid, password);
				System.out.println(connn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {

				if (true)

//					AppLogger.getLogger().debug(" Start Insert ");
				{
					for (int i = 0; i < list.size(); i++) {

//						String query = " INSERT INTO "
//								+ schema
//								+ ".KM_EMPLOYEE_VIEW("
//								+ "EMPLOYEE_ID,FIRSTNAME,LASTNAME,THAINAME,POSITION,LEVEL,EMAIL_ID,LOGON_NAME,SECTION_NAME,DEPT_NAME,DIVISION_NAME,SECTION_ID,DEPT_ID,DIVISION_ID,COMPANY,EXTENTION_NO,ROOM_NO) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
//						String query = "INSERT INTO "+schema+".WEB_BOARD_FORUM_COMMON(WBFI_ID,WBFI_NAME,WBFI_DESC,WBFI_USER_CREATE_ID,WBFI_CREATE_TIME,WBFI_UPDATE_TIME,WBFI_MODULATOR,WBFI_IMG_ID,WBFI_STATUS) VALUES(?,?,?,?,?,?,?,?,?)";
						String query = "INSERT INTO "+schema+".WEB_BOARD_FORUM_INTRA(WBFI_ID,WBFI_NAME,WBFI_DESC,WBFI_USER_CREATE_ID,WBFI_CREATE_TIME,WBFI_UPDATE_TIME,WBFI_MODULATOR,WBFI_IMG_ID,WBFI_STATUS) VALUES(?,?,?,?,?,?,?,?,?)";
						
						ForumModel forumModel = (ForumModel)list.get(i);
						pstmt = connn.prepareStatement(query);
						System.out.println(forumModel.getCatId());
						System.out.println(forumModel.getCatTitle());
						pstmt.setInt(1, forumModel.getCatId());
						pstmt.setString(2, forumModel.getCatTitle());
						pstmt.setString(3, forumModel.getCatDesc());
						pstmt.setString(4, forumModel.getUserCreated());
						pstmt.setTimestamp(5, new Timestamp(forumModel.getCreatedTime().getTime()));
						pstmt.setTimestamp(6, new Timestamp(forumModel.getUpdatedTime().getTime()));
						pstmt.setString(7, forumModel.getModulator());
						pstmt.setString(8, forumModel.getImgId());
						pstmt.setString(9, forumModel.getStatus());
//						ViewEmployeemodel model = (ViewEmployeemodel) list
//								.get(i);
//
//
//						pstmt.setString(1,
//								model.getEmployeeId() != null ? model
//										.getEmployeeId() : "-");
//						pstmt.setString(2, model.getFirstname() != null ? model
//								.getFirstname() : "-");
//						pstmt.setString(3, model.getLastname() != null ? model
//								.getLastname() : "-");
//						pstmt.setString(4, model.getThainame() != null ? model
//								.getThainame() : "-");
//						pstmt.setString(5, model.getPosition() != null ? model
//								.getPosition() : "-");
//						pstmt.setString(6, model.getLevel() != null ? model
//								.getLevel() : "-");
//						pstmt.setString(7, model.getEmailId() != null ? model
//								.getEmailId() : "-");
//						pstmt.setString(8, model.getLogonName() != null ? model
//								.getLogonName() : "-");
//						pstmt.setString(9,
//								model.getSectionName() != null ? model
//										.getSectionName() : "-");
//						pstmt.setString(10, model.getDeptName() != null ? model
//								.getDeptName() : "-");
//						pstmt.setString(11,
//								model.getDivisionName() != null ? model
//										.getDivisionName() : "-");
//						pstmt.setString(12,
//								model.getSectionId() != null ? model
//										.getSectionId() : "-");
//						pstmt.setString(13, model.getDeptId() != null ? model
//								.getDeptId() : "-");
//						pstmt.setString(14,
//								model.getDivisionId() != null ? model
//										.getDivisionId() : "-");
//						pstmt.setString(15, model.getCompany() != null ? model
//								.getCompany() : "-");
//						pstmt.setString(16,
//								model.getExtentionNo() != null ? model
//										.getExtentionNo() : "-");
//						pstmt.setString(17, model.getRoomNo() != null ? model
//								.getRoomNo() : "-");
						pstmt.execute();
						pstmt.close();

						try {
							connn.commit();
						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}

					}
//					AppLogger.getLogger().debug(
//							" INSERT SUCESS LIST AT = " + list.size());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
//				AppLogger.getLogger().debug("Closed connection : " + connn);
				connn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			} 
			finally {

				if (connn != null) {
					// logger.debug("Closing connection");
					try {

						connn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.err.println("SQLException " + e);
					}
				} else {
					// logger.debug("connection not open");
				}

			}
		}
	}

	private boolean searchByHRForm() {

		Connection connn = null;
		PreparedStatement pstmt = null;
		ResultSet rSet = null;

		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			connn = DriverManager.getConnection(url, userid, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String cmd = "select count(d.EMPLOYEE_ID)as amount_visitor from empinst.KM_EMPLOYEE_VIEW d";
		String result = null;

		try {
			pstmt = connn.prepareStatement(cmd);
			rSet = pstmt.executeQuery();

			while (rSet.next()) {

				result = rSet.getString("amount_visitor");

			}
			if (result != null && !result.equals("")) {
				String querydelete = "delete from empinst.KM_EMPLOYEE_VIEW";

				pstmt = connn.prepareStatement(querydelete);
				boolean check = pstmt.execute();
				connn.commit();
				return check;

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return true;

	}
}
