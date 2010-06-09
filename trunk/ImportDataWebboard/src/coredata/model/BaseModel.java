package coredata.model;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userCreated;

	private Date createdTime;

	private Date updatedTime;

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(String userCreated) {
		this.userCreated = userCreated;
	}
}
