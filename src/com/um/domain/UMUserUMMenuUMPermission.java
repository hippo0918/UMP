package com.um.domain;

/**
 * UmUserMenuPermission entity. @author MyEclipse Persistence Tools
 */

public class UMUserUMMenuUMPermission implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BEAN_NAME = "com.um.domain.UmUserUMMenuUMPermission";
	// Fields

	private String id;
	private String userId;
	private String menuPermissionId;

	// Constructors

	/** default constructor */
	public UMUserUMMenuUMPermission() {
	}

	/** full constructor */
	public UMUserUMMenuUMPermission(String id, String userId,
			String menuPermissionId) {
		this.id = id;
		this.userId = userId;
		this.menuPermissionId = menuPermissionId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMenuPermissionId() {
		return this.menuPermissionId;
	}

	public void setMenuPermissionId(String menuPermissionId) {
		this.menuPermissionId = menuPermissionId;
	}

}