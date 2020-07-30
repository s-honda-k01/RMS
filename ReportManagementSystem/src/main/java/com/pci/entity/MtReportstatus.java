package com.pci.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the mt_reportstatus database table.
 * 
 */
@Entity
@Table(name="mt_reportstatus")
@NamedQuery(name="MtReportstatus.findAll", query="SELECT m FROM MtReportstatus m")
public class MtReportstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String reportstatuscode;

	private String reportstatus;

	//bi-directional many-to-one association to TrDailyreport
	@OneToMany(mappedBy="mtReportstatus")
	private List<TrDailyreport> trDailyreports;

	public MtReportstatus() {
	}

	public String getReportstatuscode() {
		return this.reportstatuscode;
	}

	public void setReportstatuscode(String reportstatuscode) {
		this.reportstatuscode = reportstatuscode;
	}

	public String getReportstatus() {
		return this.reportstatus;
	}

	public void setReportstatus(String reportstatus) {
		this.reportstatus = reportstatus;
	}

	public List<TrDailyreport> getTrDailyreports() {
		return this.trDailyreports;
	}

	public void setTrDailyreports(List<TrDailyreport> trDailyreports) {
		this.trDailyreports = trDailyreports;
	}

	public TrDailyreport addTrDailyreport(TrDailyreport trDailyreport) {
		getTrDailyreports().add(trDailyreport);
		trDailyreport.setMtReportstatus(this);

		return trDailyreport;
	}

	public TrDailyreport removeTrDailyreport(TrDailyreport trDailyreport) {
		getTrDailyreports().remove(trDailyreport);
		trDailyreport.setMtReportstatus(null);

		return trDailyreport;
	}

}