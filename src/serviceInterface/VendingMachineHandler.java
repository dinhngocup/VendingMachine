package serviceInterface;

import model.PurchaseRequest;

public interface VendingMachineHandler {
	public void initResourcesVendingMachine();

	public void printMenu() ;
	
	public boolean insertNote();
	
	public PurchaseRequest chooseProduct();
	
	public boolean releaseProductAndRemainingChange(PurchaseRequest userPurchaseRequest);
}
