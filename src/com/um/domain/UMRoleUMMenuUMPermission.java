package com.um.domain;

/**
 * UmRoleMenuPermission entity. @author MyEclipse Persistence Tools
 */

public class UMRoleUMMenuUMPermission implements java.io.Serializable {

	public static final String BEAN_NAME = "com.um.domain.UMRoleUMMenuUMPermission";
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String roleId;
	private String menuPermissionId;

	// Constructors

	/** default constructor */
	public UMRoleUMMenuUMPermission() {
	}

	/** full constructor */
	public UMRoleUMMenuUMPermission(String id, String roleId,
			String menuPermissionId) {
		this.id = id;
		this.roleId = roleId;
		this.menuPermissionId = menuPermissionId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuPermissionId() {
		return this.menuPermissionId;
	}

	public void setMenuPermissionId(String menuPermissionId) {
		this.menuPermissionId = menuPermissionId;
	}

}