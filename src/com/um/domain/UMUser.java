package com.um.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UMUser 与    UMRole 之间的关联是: UMUserUMRole(UMUser user，UMRole role)
 * 应该改成：UMUserUMRole(String userId，String roleId)
 * 不过改动太大。。还是用回原来的。
 * */
public class UMUser {

	public static final String BEAN_NAME = "com.um.domain.UMUser";
	private String id;
	private Date createDate;//创建时间
	private String name;//名字
	private String gender;//性别
	private String userno;//工号
	private boolean validate = true;//是否合法
	private String password;
	private String admin;
	private Set<UMRoleUMUser> umRoleumUser = new HashSet<UMRoleUMUser>(0);
	public UMUser() {
		super();
	}
	public UMUser(String id, Date createDate, String name, String gender,
			String userno, boolean validate, String password, String admin) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.name = name;
		this.gender = gender;
		this.userno = userno;
		this.validate = validate;
		this.password = password;
		this.admin = admin;
	}
	public String getAdmin() {
		return admin;
	} 
	
	public Date getCreateDate() {
		return createDate;
	}
	public String getGender() {
		return gender;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public Set<UMRoleUMUser> getUmRoleumUser() {
		return umRoleumUser;
	}
	public String getUserno() {
		return userno;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUmRoleumUser(Set<UMRoleUMUser> umRoleumUser) {
		this.umRoleumUser = umRoleumUser;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
}
