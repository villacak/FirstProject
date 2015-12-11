package com.target.first.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the price database table.
 * 
 */
@Entity
@NamedQuery(name="Price.findAll", query="SELECT p FROM Price p")
public class Price implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRICE_PK_GENERATOR", sequenceName="PRICESEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRICE_PK_GENERATOR")
	private int pk;

	private BigDecimal price;

	@Column(name="product_id")
	private int productId;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="priceBean")
	private List<Product> products;

	public Price() {
	}

	public int getPk() {
		return this.pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setPriceBean(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setPriceBean(null);

		return product;
	}

}