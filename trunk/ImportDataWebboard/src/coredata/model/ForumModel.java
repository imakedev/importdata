package coredata.model;

public class ForumModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	private int catId;

	private String catTitle;

	private String catDesc;

	private String modulator;

	private String imgId;

	private String status;

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getCatTitle() {
		return catTitle;
	}

	public void setCatTitle(String catTitle) {
		this.catTitle = catTitle;
	}

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getModulator() {
		return modulator;
	}

	public void setModulator(String modulator) {
		this.modulator = modulator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
