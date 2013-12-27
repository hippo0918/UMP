package com.um.domain;

/**
 * UmRoleMenu entity. @author MyEclipse Persistence Tools
 */

public class UMRoleUMMenu {

	// Fields

	private String id;
	private String menuId;
	private String roleId;

	// Constructors

	/** default constructor */
	public UMRoleUMMenu() {
	}

	/** full constructor */
	public UMRoleUMMenu(String id, String menuId, String roleId) {
		this.id = id;
		this.menuId = menuId;
		this.roleId = roleId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}