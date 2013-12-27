package com.um.domain;

public class UMRoleUMUser {

	public static final String BEAN_NAME = "com.um.domain.UMRoleUMUser";
	private String id;
	private UMRole role;
	private UMUser user;
	public UMRoleUMUser() {
		super();
	}
	public UMRoleUMUser(String id, UMRole role, UMUser user) {
		super();
		this.id = id;
		this.role = role;
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public UMRole getRole() {
		return role;
	}
	public UMUser getUser() {
		return user;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setRole(UMRole role) {
		this.role = role;
	}
	public void setUser(UMUser user) {
		this.user = user;
	}
	
	
}
