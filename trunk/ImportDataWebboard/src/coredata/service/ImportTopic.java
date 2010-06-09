package coredata.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import coredata.model.TopicModel;

public class ImportTopic {
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
			String cmd = "select v.*, FROM_UNIXTIME(topic_time) as frang from Vw_Topic v";
//			String cmd = "select v.*, FROM_UNIXTIME(topic_time) as frang from Vw_Topic2 v where v.topic_id=1797";
//			String cmd = "select v.*, FROM_UNIXTIME(topic_time) as frang from Vw_Topic2 v where v.topic_id=1805";
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
				
				
//				String wbtiMessage = rSet.getString("post_text");
				TopicModel topicModel = new TopicModel();
				
				topicModel.setDesc(rSet.getString("post_subject"));
				topicModel.setEmail("");
				topicModel.setFileName("");
				topicModel.setForumId(rSet.getInt("forum_id"));
				topicModel.setIp("");
				topicModel.setMessage(rSet.getString("post_subject"));
				topicModel.setMessageClob(wbtiMessage);
				topicModel.setPath("");
				topicModel.setReply(rSet.getString("topic_replies"));
				topicModel.setStatus("1");
				topicModel.setStatusAxis("");
				topicModel.setStatusFlag("");
				topicModel.setTopicId(rSet.getInt("topic_id"));
				topicModel.setTopicTitle(rSet.getString("topic_title"));
				topicModel.setStatusPermission("1");
				topicModel.setStatusNotify("0");
				topicModel.setLastReplyTime(new Date());
				
				if(rSet.getString("post_username") != null && rSet.getString("post_username").trim().length() > 0) {
					topicModel.setUserCreated(rSet.getString("post_username"));
				} else {
					topicModel.setUserCreated(rSet.getString("username"));
				}
				
				Date date = null;
				try {
					date = format.parse(rSet.getString("frang"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				topicModel.setCreatedTime(date);
				topicModel.setUpdatedTime(date);
				topicModel.setLastReplyTime(date);
				topicModel.setUsernameLdap("");
				topicModel.setViews(rSet.getString("topic_views"));
				Viewlist.add(topicModel);

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
			System.err.println(" SQLException " + e.getCause());
		}
		return resultList;

	}
	
	private static void sendData(List viewResult) {
		for (int i = 0; i < viewResult.size(); i++) {
			SaveTopic save = new SaveTopic((List)viewResult.get(i), "SyncData["+i+"]");
			Thread thread = new Thread(save,"SyncData["+i+"]");
			thread.start();
		}
		
	}
	public static void main(String[] args) {
		List list = viewReport();
		System.out.println(list.size());
//		sendData(list);
//		String f1 = "D M d, y G:I a";
//		String f2 = "d M Y H:i";
//		String f3 = "ddmmyyhhmm";
//		DateFormat dateFormat = new SimpleDateFormat(f3);
//		
//		try {
////			 Tue Aug 30, 2005 10:16 am
//			String string = "1125371780";
//			Date date = new Date(Long.parseLong("1125371780"));
//			Calendar calendar = new GregorianCalendar(Locale.US);
//			calendar.set(2005, 7, 30);
//			System.out.println("Calendar ==> "+calendar.getTime());
//			System.out.println("Date ==> "+date);
//			Date d = dateFormat.parse("1125371780");
////			Date d = dateFormat.parse("1275466673312");
//			System.out.println(d);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Date date = new Date();
////		String s = dateFormat.format(date);
//		System.out.println(date+"\t"+date.getTime());
	}
}
