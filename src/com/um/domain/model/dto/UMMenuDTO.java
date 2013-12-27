package com.um.domain.model.dto;

import java.util.HashSet;
import java.util.Set;

import com.um.domain.UMPermission;

public class UMMenuDTO {

	//关联的权限
	private Set<UMPermission> permissions = new HashSet<UMPermission>(0);
	// Fields

	/**
	 * 
	 */
	private String id;

	private String name;


	private String pid;
	private String validate;

	private String iconCls;
	private String url;
	private String act;
	// Constructors
	/** default constructor */
	public UMMenuDTO() {
	}
	/** minimal constructor */
	public UMMenuDTO(String id, String name, String pid, String validate,
			String url, String act) {
		this.id = id;
		this.name = name;
		this.pid = pid;
		this.validate = validate;
		this.url = url;
		this.act = act;
	}
	/** full constructor */
	public UMMenuDTO(String id, String name, String pid, String validate,
			String iconCls, String url, String act) {
		this.id = id;
		this.name = name;
		this.pid = pid;
		this.validate = validate;
		this.iconCls = iconCls;
		this.url = url;
		this.act = act;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UMMenuDTO other = (UMMenuDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getIconCls() {
		return this.iconCls;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	// Property accessors

	public Set<UMPermission> getPermissions() {
		return permissions;
	}

	public String getPid() {
		return this.pid;
	}

	public String getUrl() {
		return this.url;
	}

	public String getValidate() {
		return this.validate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPermissions(Set<UMPermission> permissions) {
		this.permissions = permissions;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}

}
