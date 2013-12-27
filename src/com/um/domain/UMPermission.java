package com.um.domain;

/**
 * UmPermission entity. @author MyEclipse Persistence Tools
 */

public class UMPermission implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "com.um.domain.UMPermission";
	// Fields

	private String id;
	private String name;
	private String iconCls;
	private String code;
	private String description;
	private Boolean validate = true;
	private String createDate;
	private String createTime;

	// Constructors

	/** default constructor */
	public UMPermission() {
	}

	/** minimal constructor */
	public UMPermission(String name, String code, Boolean validate,
			String createDate, String createTime) {
		this.name = name;
		this.code = code;
		this.validate = validate;
		this.createDate = createDate;
		this.createTime = createTime;
	}

	/** full constructor */
	public UMPermission(String name, String iconCls, String code,
			String description, Boolean validate, String createDate,
			String createTime) {
		this.name = name;
		this.iconCls = iconCls;
		this.code = code;
		this.description = description;
		this.validate = validate;
		this.createDate = createDate;
		this.createTime = createTime;
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

	public String getIconCls() {
		return this.iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getValidate() {
		return this.validate;
	}

	public void setValidate(Boolean validate) {
		this.validate = validate;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}