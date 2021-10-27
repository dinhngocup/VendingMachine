package model;

public enum Product {
	COKE(1, 10000), PEPSI(2, 20000), SODA(3, 30000);

	private final int productNumber;
	private final int price;

	public int getProductNumber() {
		return productNumber;
	}

	public int getPrice() {
		return price;
	}

	private Product(int productNumber, int price) {
		this.productNumber = productNumber;
		this.price = price;
	}
}
