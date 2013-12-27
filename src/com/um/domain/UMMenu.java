package com.um.domain;

/**
 * UmMenu entity. @author MyEclipse Persistence Tools
 */

public class UMMenu implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BEAN_NAME = "com.um.domain.UMMenu";
	// Fields

	private String id;
	private String name;
	private String pid;
	private String validate = "1";
	private String iconCls;
	private String url;
	private String createTime;
	private String createDate;
	private String act;
	// Constructors

	/** default constructor */
	public UMMenu() {
	}

	/** minimal constructor */
	public UMMenu(String name, String pid, String validate, String url,
			String createTime, String createDate, String act) {
		this.name = name;
		this.pid = pid;
		this.validate = validate;
		this.url = url;
		this.createTime = createTime;
		this.createDate = createDate;
		this.act = act;
	}

	/** full constructor */
	public UMMenu(String name, String pid, String validate, String iconCls,
			String url, String createTime, String createDate, String act) {
		this.name = name;
		this.pid = pid;
		this.validate = validate;
		this.iconCls = iconCls;
		this.url = url;
		this.createTime = createTime;
		this.createDate = createDate;
		this.act = act;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getValidate() {
		return this.validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getIconCls() {
		return this.iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UMMenu other = (UMMenu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}
}