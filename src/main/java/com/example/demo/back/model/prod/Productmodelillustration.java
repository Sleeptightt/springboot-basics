package com.example.demo.back.model.prod;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the productmodelillustration database table.
 *
 */
@Entity
@NamedQuery(name = "Productmodelillustration.findAll", query = "SELECT p FROM Productmodelillustration p")
public class Productmodelillustration implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductmodelillustrationPK id;

	private Timestamp modifieddate;

	// bi-directional many-to-one association to Illustration
	@ManyToOne
	@JoinColumn(name = "illustrationid", insertable = false, updatable = false)
	private Illustration illustration;

	// bi-directional many-to-one association to Productmodel
	@ManyToOne
	@JoinColumn(name = "productmodelid", insertable = false, updatable = false)
	private Productmodel productmodel;

	public Productmodelillustration() {
	}

	public ProductmodelillustrationPK getId() {
		return this.id;
	}

	public Illustration getIllustration() {
		return this.illustration;
	}

	public Timestamp getModifieddate() {
		return this.modifieddate;
	}

	public Productmodel getProductmodel() {
		return this.productmodel;
	}

	public void setId(ProductmodelillustrationPK id) {
		this.id = id;
	}

	public void setIllustration(Illustration illustration) {
		this.illustration = illustration;
	}

	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setProductmodel(Productmodel productmodel) {
		this.productmodel = productmodel;
	}

}