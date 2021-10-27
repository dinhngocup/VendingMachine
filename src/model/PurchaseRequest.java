package model;

public class PurchaseRequest {
	private Product product;
	private int note;

	public PurchaseRequest() {
	}

	public PurchaseRequest(Product product, int note) {
		super();
		this.product = product;
		this.note = note;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	
	

	
}
