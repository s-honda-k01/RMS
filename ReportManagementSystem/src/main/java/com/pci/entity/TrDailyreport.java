package com.pci.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tr_dailyreport database table.
 * 
 */
@Entity
@Table(name="tr_dailyreport")
@NamedQuery(name="TrDailyreport.findAll", query="SELECT t FROM TrDailyreport t")
public class TrDailyreport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dailyreportid;

	@Lob
	private String plan;

	@Temporal(TemporalType.DATE)
	private Date regdate;

	@Lob
	private String remark;

	@Lob
	private String result;

	//bi-directional many-to-one association to MtReportstatus
	@ManyToOne
	@JoinColumn(name="REPORTSTATUSCODE")
	private MtReportstatus mtReportstatus;

	//bi-directional many-to-one association to MtUser
	@ManyToOne
	@JoinColumn(name="USERCODE")
	private MtUser mtUser1;

	//bi-directional many-to-one association to MtUser
	@ManyToOne
	@JoinColumn(name="TRAINERCODE")
	private MtUser mtUser2;
	
	@Transient
	private String regdateString;

	public TrDailyreport() {
	}

	public TrDailyreport(Date regdate, MtUser mtUser1,String todayString) {
		this.regdate = regdate;
		this.mtUser1 = mtUser1;
		this.regdateString= todayString;
	}

	
	public TrDailyreport(MtUser mtUser1, String regdateString) {
		this.mtUser1 = mtUser1;
		this.regdateString = regdateString;
		this.regdate=java.sql.Date.valueOf(regdateString);
	}

	public int getDailyreportid() {
		return this.dailyreportid;
	}

	public void setDailyreportid(int dailyreportid) {
		this.dailyreportid = dailyreportid;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Date getRegdate() {
		return this.regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public MtReportstatus getMtReportstatus() {
		return this.mtReportstatus;
	}

	public void setMtReportstatus(MtReportstatus mtReportstatus) {
		this.mtReportstatus = mtReportstatus;
	}

	public MtUser getMtUser1() {
		return this.mtUser1;
	}

	public void setMtUser1(MtUser mtUser1) {
		this.mtUser1 = mtUser1;
	}

	public MtUser getMtUser2() {
		return this.mtUser2;
	}

	public void setMtUser2(MtUser mtUser2) {
		this.mtUser2 = mtUser2;
	}

	public String getRegdateString() {
		return regdateString;
	}

	public void setRegdateString(String regdateString) {
		this.regdateString = regdateString;
	}

}