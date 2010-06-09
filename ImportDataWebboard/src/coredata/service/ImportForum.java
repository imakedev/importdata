package coredata.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import coredata.model.ForumModel;

public class ImportForum {

	private static int MAX_CONTENT = 1000;

	private static Connection conn = null;

	private static PreparedStatement preStatement = null;

	private static ResultSet rSet = null;

	public static List viewReport() {

		List resultList = new ArrayList();
		List Viewlist = new ArrayList();

		String userid = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/ntc_webboard",
					userid, password);

			System.out.println(conn);
			String cmd = "select cat_id, cat_title from ib_categories";

			preStatement = conn.prepareStatement(cmd);

			rSet = preStatement.executeQuery();

			int i = 0;
			while (rSet.next()) {

				if (i % MAX_CONTENT == 0 && i != 0) {
					resultList.add(Viewlist);
					Viewlist = new ArrayList();
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				i++;
				
				ForumModel forumModel = new ForumModel();
				forumModel.setCatId(rSet.getInt("cat_id"));
				forumModel.setCatTitle(rSet.getString("cat_title"));
				forumModel.setCatDesc(rSet.getString("cat_title"));
				forumModel.setCreatedTime(new Date());
				forumModel.setImgId("");
				forumModel.setModulator("");
				forumModel.setStatus("");
				forumModel.setUpdatedTime(new Date());
				forumModel.setUserCreated("");
				Viewlist.add(forumModel);

			}

			if (i % MAX_CONTENT != 0) {

				resultList.add(Viewlist);

			}
			System.out.println(resultList.size());

			rSet.close();

			preStatement.close();

			conn.close();

		} catch (ClassNotFoundException e) {
			System.err.println(" ClassNotFoundException " + e.getCause());

		} catch (SQLException e) {
			System.err.println(" SQLException " + e.getCause());
		}
		return resultList;

	}
	
	public static void main(String[] args) {
		ImportForum forum = new ImportForum();
		List list = forum.viewReport();
		System.out.println(list.size());
		sendData(list);
	}
	
	private static void sendData(List viewResult) {
		for (int i = 0; i < viewResult.size(); i++) {
			SaveForum save = new SaveForum((List)viewResult.get(i), "SyncData["+i+"]");
			Thread thread = new Thread(save,"SyncData["+i+"]");
			thread.start();
		}
		
	}
}
