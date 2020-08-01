package com.pci.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the mt_user database table.
 * 
 */
@Entity
@Table(name="mt_user")
@NamedQuery(name="MtUser.findAll", query="SELECT m FROM MtUser m")
public class MtUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String usercode;

	private String firstname;

	private String lastname;

	private String password;

	//bi-directional many-to-one association to MtCompany
	@ManyToOne
	@JoinColumn(name="COMPANYCODE")
	private MtCompany mtCompany;

	//bi-directional many-to-one association to MtRole
	@ManyToOne
	@JoinColumn(name="ROLECODE")
	private MtRole mtRole;

	//bi-directional many-to-one association to TrDailyreport
	@OneToMany(mappedBy="mtUser1")
	private List<TrDailyreport> trDailyreports1;

	//bi-directional many-to-one association to TrDailyreport
	@OneToMany(mappedBy="mtUser2")
	private List<TrDailyreport> trDailyreports2;
	
	@Transient
	private String currentPassword;
	@Transient
	private String newPassword;
	@Transient
	private String newPasswordAgain;

	public MtUser() {
	}
	
	public MtUser(String usercode, String lastname, String firstname, String password, MtCompany mtCompany,
			MtRole mtRole) {
		this.usercode = usercode;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.mtCompany = mtCompany;
		this.mtRole = mtRole;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MtCompany getMtCompany() {
		return this.mtCompany;
	}

	public void setMtCompany(MtCompany mtCompany) {
		this.mtCompany = mtCompany;
	}

	public MtRole getMtRole() {
		return this.mtRole;
	}

	public void setMtRole(MtRole mtRole) {
		this.mtRole = mtRole;
	}

	public List<TrDailyreport> getTrDailyreports1() {
		return this.trDailyreports1;
	}

	public void setTrDailyreports1(List<TrDailyreport> trDailyreports1) {
		this.trDailyreports1 = trDailyreports1;
	}

	public TrDailyreport addTrDailyreports1(TrDailyreport trDailyreports1) {
		getTrDailyreports1().add(trDailyreports1);
		trDailyreports1.setMtUser1(this);

		return trDailyreports1;
	}

	public TrDailyreport removeTrDailyreports1(TrDailyreport trDailyreports1) {
		getTrDailyreports1().remove(trDailyreports1);
		trDailyreports1.setMtUser1(null);

		return trDailyreports1;
	}

	public List<TrDailyreport> getTrDailyreports2() {
		return this.trDailyreports2;
	}

	public void setTrDailyreports2(List<TrDailyreport> trDailyreports2) {
		this.trDailyreports2 = trDailyreports2;
	}

	public TrDailyreport addTrDailyreports2(TrDailyreport trDailyreports2) {
		getTrDailyreports2().add(trDailyreports2);
		trDailyreports2.setMtUser2(this);

		return trDailyreports2;
	}

	public TrDailyreport removeTrDailyreports2(TrDailyreport trDailyreports2) {
		getTrDailyreports2().remove(trDailyreports2);
		trDailyreports2.setMtUser2(null);

		return trDailyreports2;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordAgain() {
		return newPasswordAgain;
	}

	public void setNewPasswordAgain(String newPasswordAgain) {
		this.newPasswordAgain = newPasswordAgain;
	}
}