package coredata.model;

import java.util.Date;

public class TopicModel extends BaseModel {
	private static final long serialVersionUID = 1L;

	private int topicId;

	private int forumId;

	private String topicTitle;

	private String desc;

	private String message;

	private String status;

	private String views;

	private String reply;

	private String ip;

	private String path;

	private String email;

	private String fileName;

	private String messageClob;

	private String statusAxis;

	private String statusFlag;

	private String usernameLdap;
	
	private String statusPermission;
	
	private String statusNotify;
	
	private Date lastReplyTime;

	public String getStatusPermission() {
		return statusPermission;
	}

	public void setStatusPermission(String statusPermission) {
		this.statusPermission = statusPermission;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageClob() {
		return messageClob;
	}

	public void setMessageClob(String messageClob) {
		this.messageClob = messageClob;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusAxis() {
		return statusAxis;
	}

	public void setStatusAxis(String statusAxis) {
		this.statusAxis = statusAxis;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public String getUsernameLdap() {
		return usernameLdap;
	}

	public void setUsernameLdap(String usernameLdap) {
		this.usernameLdap = usernameLdap;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public Date getLastReplyTime() {
		return lastReplyTime;
	}

	public void setLastReplyTime(Date lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}

	public String getStatusNotify() {
		return statusNotify;
	}

	public void setStatusNotify(String statusNotify) {
		this.statusNotify = statusNotify;
	}
}
