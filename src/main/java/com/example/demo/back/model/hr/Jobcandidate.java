package com.example.demo.back.model.hr;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the jobcandidate database table.
 *
 */
@Entity
@NamedQuery(name = "Jobcandidate.findAll", query = "SELECT j FROM Jobcandidate j")
public class Jobcandidate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "JOBCANDIDATE_JOBCANDIDATEID_GENERATOR", allocationSize = 1, sequenceName = "JOBCANDIDATE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOBCANDIDATE_JOBCANDIDATEID_GENERATOR")
	private Integer jobcandidateid;

	private Timestamp modifieddate;

	private String resume;

	// bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name = "businessentityid")
	private Employee employee;

	public Jobcandidate() {
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public Integer getJobcandidateid() {
		return this.jobcandidateid;
	}

	public Timestamp getModifieddate() {
		return this.modifieddate;
	}

	public String getResume() {
		return this.resume;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setJobcandidateid(Integer jobcandidateid) {
		this.jobcandidateid = jobcandidateid;
	}

	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

}