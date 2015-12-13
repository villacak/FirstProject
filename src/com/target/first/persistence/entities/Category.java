package com.target.first.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CATEGORY_PK_GENERATOR", sequenceName="CATEGORYSEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORY_PK_GENERATOR")
	private int pk;

	private String description;

	//bi-directional many-to-one association to Product
	@JsonBackReference
	@OneToMany(mappedBy="categoryBean")
	private List<Product> products;

	public Category() {
	}

	public int getPk() {
		return this.pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setCategoryBean(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setCategoryBean(null);

		return product;
	}

}