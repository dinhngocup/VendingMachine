package model;

public class PromoteProgram {

	private int productId;
	private int consecutivePurchase;
	private int budget;
	private float winRate;

	public PromoteProgram(int productId, int consecutivePurchase, int budget, float winRate) {
		super();
		this.productId = productId;
		this.consecutivePurchase = consecutivePurchase;
		this.budget = budget;
		this.winRate = winRate;
	}
	
	public PromoteProgram() {
	}
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getConsecutivePurchase() {
		return consecutivePurchase;
	}

	public void setConsecutivePurchase(int consecutivePurchase) {
		this.consecutivePurchase = consecutivePurchase;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public float getWinRate() {
		return winRate;
	}

	public void setWinRate(float winRate) {
		this.winRate = winRate;
	}

}
