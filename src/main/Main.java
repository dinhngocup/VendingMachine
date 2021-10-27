package main;

import model.PurchaseRequest;
import vendingmachine.VendingMachine;

public class Main {

	public static void main(String[] args) {
		 
		VendingMachine vendingMachine = new VendingMachine();
		while (true) {
			vendingMachine.printMenu();
			boolean isValidateNote = vendingMachine.insertNote();
			if (isValidateNote) {
				boolean continueToPurchase = true;
				
				// keep purchasing when vending machine is not enough matching money
				while(continueToPurchase) {
					PurchaseRequest request = vendingMachine.chooseProduct();
					if (request != null) {
						continueToPurchase = vendingMachine.releaseProductAndRemainingChange(request);
					} else {
						continueToPurchase = false;
					}
					
				}
			}

		}
	}
	

}
