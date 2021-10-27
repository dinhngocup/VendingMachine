package model;

public enum Note {
	TWO_HUNDRED(200000), ONE_HUNDRED(100000), FIFTY_THOUSAND(50000), TWENTY_THOUSAND(20000), TEN_THOUSAND(10000);

	private final int cost;

	public int getCost() {
		return cost;
	}

	private Note(int cost) {
		this.cost = cost;
	}

}
