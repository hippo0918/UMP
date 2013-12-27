package com.um.domain.model.dto;

import java.util.HashSet;
import java.util.Set;

import com.um.domain.UMPermission;

public class UMRoleDTO {
	
	public UMRoleDTO() {
		
	}
	
	public UMRoleDTO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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
		UMRoleDTO other = (UMRoleDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	private String id;
	private String description;
	private String name;
	private boolean validate;
	//关联的菜单
	private Set<UMMenuDTO> menus = new HashSet<UMMenuDTO>(0);
	
	public Set<UMMenuDTO> getMenus() {
		return menus;
	}
	public void setMenus(Set<UMMenuDTO> menus) {
		this.menus = menus;
	}
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
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
}
