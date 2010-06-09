package coredata.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import coredata.model.ReplyModel;

public class ImportReplyMessage {
	private static int MAX_CONTENT = 1000;

	private static Connection conn = null;

	private static PreparedStatement preStatement = null;

	private static ResultSet rSet = null;
	
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");

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
			String cmd = "select v.*, FROM_UNIXTIME(post_time) as pt, FROM_UNIXTIME(post_edit_time) as et from vw_reply v";
//			String cmd = "select v.*, FROM_UNIXTIME(post_time) as pt, FROM_UNIXTIME(post_edit_time) as et from vw_reply v where v.post_id=146";
			preStatement = conn.prepareStatement(cmd);

			rSet = preStatement.executeQuery();
			System.out.println(cmd);
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
				
				ReplyModel replyModel = new ReplyModel();
				
				Date post_date = new Date();
				try {
					if(rSet.getString("pt") != null && rSet.getString("pt").trim().length() > 0) 
						post_date = format.parse(rSet.getString("pt"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				String wbtiMessage = StringEscapeUtils.escapeHtml(rSet.getString("post_text"));
//				System.out.println("wbtiMessage ==> "+wbtiMessage);
//				System.out.println("wbtiMessage2 ==> "+rSet.getString("post_text"));
				replyModel.setCreatedTime(post_date);
				replyModel.setEmail(rSet.getString("user_email"));
				
				String ip = "";
				if(rSet.getString("poster_ip") != null || rSet.getString("poster_ip").trim().length() > 0) {
					String ip_enc = rSet.getString("poster_ip");
					String ip1 = ip_enc.substring(0, 2);
					String ip2 = ip_enc.substring(2, 4);
					String ip3 = ip_enc.substring(4, 6);
					String ip4 = ip_enc.substring(6, 8);
					ip = Integer.valueOf(ip1, 16)+"."+Integer.valueOf(ip2, 16)+"."+Integer.valueOf(ip3, 16)+"."+Integer.valueOf(ip4, 16);
					System.out.println(ip);
				}
				
				replyModel.setIp(ip);
				replyModel.setMessage(rSet.getString("post_subject"));
				replyModel.setPath("");
				replyModel.setPostId(rSet.getInt("post_id"));
				replyModel.setStatus("1");
				replyModel.setStatusFlag("");
				replyModel.setTopicId(rSet.getInt("topic_id"));
				
//				System.out.println("post_text ==> "+rSet.getString("post_text"));
				String wbtiMessage1 = StringEscapeUtils.escapeJava(rSet.getString("post_text"));
//				System.out.println("wbtiMessage ==> "+wbtiMessage1);
//				System.out.println("##################################### "+wbtiMessage1.indexOf("\\n"));
//				wbtiMessage1 = wbtiMessage1.replaceAll("\\", "");
				wbtiMessage1 = wbtiMessage1.replace("\\r", "");
				wbtiMessage1 = wbtiMessage1.replace("\\n", "<br>");
//				System.out.println("wbtiMessage1 ==> "+wbtiMessage1);
				String wbtiMessage = StringEscapeUtils.unescapeJava(wbtiMessage1);
//				System.out.println("wbtiMessage2 ==> "+wbtiMessage);
				
				replyModel.setMessageClob(wbtiMessage);
				Date edit_date = new Date();
				try {
					if(rSet.getString("et") != null && rSet.getString("et").trim().length() > 0) 
						edit_date = format.parse(rSet.getString("et"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				replyModel.setUpdatedTime(edit_date);
				
				if(rSet.getString("post_username") != null && rSet.getString("post_username").trim().length() > 0) {
					replyModel.setUserCreated(rSet.getString("post_username"));
				} else {
					replyModel.setUserCreated(rSet.getString("username"));
				}
				
				replyModel.setUsernameLdap("");
				
				Viewlist.add(replyModel);

			}

			if (i % MAX_CONTENT != 0) {

				resultList.add(Viewlist);

			}
			System.out.println("Total record ==> "+i);
			System.out.println(resultList.size());

			rSet.close();

			preStatement.close();

			conn.close();

		} catch (ClassNotFoundException e) {
			System.err.println(" ClassNotFoundException " + e.getCause());

		} catch (SQLException e) {
//			System.err.println(" SQLException " + e.getCause());
			e.printStackTrace();
		}
		return resultList;

	}
	
	private static void sendData(List viewResult) {
		for (int i = 0; i < viewResult.size(); i++) {
			SaveReplyMessage save = new SaveReplyMessage((List)viewResult.get(i), "SyncData["+i+"]");
			Thread thread = new Thread(save,"SyncData["+i+"]");
			thread.start();
		}
		
	}
	public static void main(String[] args) {
		List list = viewReport();
		System.out.println(list.size());
//		sendData(list);
		
		
	}
}
