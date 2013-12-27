package com.um.domain;

/**
 * UmMenuPermission entity. @author MyEclipse Persistence Tools
 */

public class UMMenuUMPermission implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BEAN_NAME = "com.um.domain.UMMenuUMPermission";
	// Fields

	private String id;
	private String permissionId;
	private String menuId;

	// Constructors

	/** default constructor */
	public UMMenuUMPermission() {
	}

	/** full constructor */
	public UMMenuUMPermission(String id, String permissionId, String menuId) {
		this.id = id;
		this.permissionId = permissionId;
		this.menuId = menuId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}