package com.pci.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the mt_company database table.
 * 
 */
@Entity
@Table(name="mt_company")
@NamedQuery(name="MtCompany.findAll", query="SELECT m FROM MtCompany m")
public class MtCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String companycode;

	private String companyname;

	//bi-directional many-to-one association to MtUser
	@OneToMany(mappedBy="mtCompany")
	private List<MtUser> mtUsers;

	public MtCompany() {
	}

	public String getCompanycode() {
		return this.companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public List<MtUser> getMtUsers() {
		return this.mtUsers;
	}

	public void setMtUsers(List<MtUser> mtUsers) {
		this.mtUsers = mtUsers;
	}

	public MtUser addMtUser(MtUser mtUser) {
		getMtUsers().add(mtUser);
		mtUser.setMtCompany(this);

		return mtUser;
	}

	public MtUser removeMtUser(MtUser mtUser) {
		getMtUsers().remove(mtUser);
		mtUser.setMtCompany(null);

		return mtUser;
	}

}