package com.um.domain;

import java.util.HashSet;
import java.util.Set;

public class UMRole {


	public static final String BEAN_NAME = "com.um.domain.UMRole";
	/**
	 * 
	 */
	public UMRole(String id, String description, String name, boolean validate,
			Set<UMRoleUMUser> umRoleumUser) {
		super();
		this.id = id;
		this.description = description;
		this.name = name;
		this.validate = validate;
		this.umRoleumUser = umRoleumUser;
	}

	public Set<UMRoleUMUser> getUmRoleumUser() {
		return umRoleumUser;
	}

	public void setUmRoleumUser(Set<UMRoleUMUser> umRoleumUser) {
		this.umRoleumUser = umRoleumUser;
	}

	private String id;//角色id
	private String description;//角色描述
	private String name;//角色名称
	private boolean validate = true;//是否合法
	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	private Set<UMRoleUMUser> umRoleumUser = new HashSet<UMRoleUMUser>(0); 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public UMRole() {
		super();
	}
}
