package com.example.demo.back.model.person;

import javax.persistence.*;

import com.example.demo.front.model.person.Person;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the password database table.
 *
 */
@Entity
@NamedQuery(name = "Password.findAll", query = "SELECT p FROM Password p")
public class Password implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PASSWORD_BUSINESSENTITYID_GENERATOR", allocationSize = 1, sequenceName = "PASSWORD_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PASSWORD_BUSINESSENTITYID_GENERATOR")
	private Integer businessentityid;

	private Timestamp modifieddate;

	private String passwordhash;

	private String passwordsalt;

	private Integer rowguid;

	// bi-directional one-to-one association to Person
	@OneToOne
	@JoinColumn(name = "businessentityid")
	private Person person;

	public Password() {
	}

	public Integer getBusinessentityid() {
		return this.businessentityid;
	}

	public Timestamp getModifieddate() {
		return this.modifieddate;
	}

	public String getPasswordhash() {
		return this.passwordhash;
	}

	public String getPasswordsalt() {
		return this.passwordsalt;
	}

	public Person getPerson() {
		return this.person;
	}

	public Integer getRowguid() {
		return this.rowguid;
	}

	public void setBusinessentityid(Integer businessentityid) {
		this.businessentityid = businessentityid;
	}

	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setPasswordhash(String passwordhash) {
		this.passwordhash = passwordhash;
	}

	public void setPasswordsalt(String passwordsalt) {
		this.passwordsalt = passwordsalt;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setRowguid(Integer rowguid) {
		this.rowguid = rowguid;
	}

}