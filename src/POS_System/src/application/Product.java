package application;

import java.io.Serializable;

public class Product implements Serializable{

	private String productName;
	private String productDescription;
	private double productPrice;
	private double productCost;
	private int productItemsInStock;

	public Product(String productName, String productDescription, double productPrice, double productCost, int productItemsInStock) {
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.productCost = productCost;
		this.productItemsInStock = productItemsInStock;
	}

	@Override
	public String toString() {
		return productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public double getProductCost() {
		return productCost;
	}

	public void setProductCost(double productCost) {
		this.productCost = productCost;
	}

	public int getProductItemsInStock() {
		return productItemsInStock;
	}

	public void setProductItemsInStock(int productItemsInStock) {
		this.productItemsInStock = productItemsInStock;
	}
}
