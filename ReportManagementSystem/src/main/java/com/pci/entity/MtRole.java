package com.pci.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the mt_role database table.
 * 
 */
@Entity
@Table(name="mt_role")
@NamedQuery(name="MtRole.findAll", query="SELECT m FROM MtRole m")
public class MtRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String rolecode;

	private String rolename;

	//bi-directional many-to-one association to MtUser
	@OneToMany(mappedBy="mtRole")
	private List<MtUser> mtUsers;

	public MtRole() {
	}

	public String getRolecode() {
		return this.rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public List<MtUser> getMtUsers() {
		return this.mtUsers;
	}

	public void setMtUsers(List<MtUser> mtUsers) {
		this.mtUsers = mtUsers;
	}

	public MtUser addMtUser(MtUser mtUser) {
		getMtUsers().add(mtUser);
		mtUser.setMtRole(this);

		return mtUser;
	}

	public MtUser removeMtUser(MtUser mtUser) {
		getMtUsers().remove(mtUser);
		mtUser.setMtRole(null);

		return mtUser;
	}

}