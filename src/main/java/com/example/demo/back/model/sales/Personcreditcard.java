package com.example.demo.back.model.sales;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the personcreditcard database table.
 *
 */
@Entity
@NamedQuery(name = "Personcreditcard.findAll", query = "SELECT p FROM Personcreditcard p")
public class Personcreditcard implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PersoncreditcardPK id;

	private Timestamp modifieddate;

	// bi-directional many-to-one association to Creditcard
	@ManyToOne
	@JoinColumn(name = "creditcardid", insertable = false, updatable = false)
	private Creditcard creditcard;

	public Personcreditcard() {
	}

	public Creditcard getCreditcard() {
		return this.creditcard;
	}

	public PersoncreditcardPK getId() {
		return this.id;
	}

	public Timestamp getModifieddate() {
		return this.modifieddate;
	}

	public void setCreditcard(Creditcard creditcard) {
		this.creditcard = creditcard;
	}

	public void setId(PersoncreditcardPK id) {
		this.id = id;
	}

	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}

}