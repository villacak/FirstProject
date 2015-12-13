package com.target.first.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRODUCT_PK_GENERATOR", sequenceName="PRODUCTSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCT_PK_GENERATOR")
	private int pk;

	private int id;

	private String name;

	//bi-directional many-to-one association to Category
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="category")
	private Category categoryBean;

	//bi-directional many-to-one association to Price
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="price")
	private Price priceBean;

	public Product() {
	}

	public int getPk() {
		return this.pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategoryBean() {
		return this.categoryBean;
	}

	public void setCategoryBean(Category categoryBean) {
		this.categoryBean = categoryBean;
	}

	public Price getPriceBean() {
		return this.priceBean;
	}

	public void setPriceBean(Price priceBean) {
		this.priceBean = priceBean;
	}

}