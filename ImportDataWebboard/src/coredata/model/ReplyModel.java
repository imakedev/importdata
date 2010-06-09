package coredata.model;

public class ReplyModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	private int postId;

	private int topicId;

	private String message;

	private String status;

	private String ip;

	private String path;

	private String email;

	private String messageClob;

	private String statusFlag;

	private String usernameLdap;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUsernameLdap() {
		return usernameLdap;
	}

	public void setUsernameLdap(String usernameLdap) {
		this.usernameLdap = usernameLdap;
	}
}
