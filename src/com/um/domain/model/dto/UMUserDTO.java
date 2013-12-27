package com.um.domain.model.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UMUserDTO {

	private String id;
	private Date createDate;//创建时间
	private String name;//名字
	private String gender;//性别
	private String userno;//工号
	private boolean validate;//是否合法
	private String password;
	private String passwordAgain;
	private String admin;
	
	//关联的角色
	private Set<UMRoleDTO> roleDtos = new HashSet<UMRoleDTO>(0);
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UMUserDTO other = (UMUserDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
	public String getPasswordAgain() {
		return passwordAgain;
	}
	public Set<UMRoleDTO> getRoleDtos() {
		return roleDtos;
	}
	
	public String getUserno() {
		return userno;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	public boolean isValidate() {
		return validate;
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
	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}
	public void setRoleDtos(Set<UMRoleDTO> roleDtos) {
		this.roleDtos = roleDtos;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
}
