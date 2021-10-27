package vendingmachine;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import model.Product;
import model.PurchaseRequest;
import serviceInterface.PromoteService;
import serviceInterface.VendingMachineHandler;

public class VendingMachine implements VendingMachineHandler {
	public static final int PROMOTE_PROGRAM_SCHEDULE = 1000 * 3600 * 24;

	private VendingMachineProducts vendingMachineProducts;
	private VendingMachineBank vendingMachineBank;
	private int userRemaining;
	private int userProductNumber;
	private PromoteService promoteService;

	public VendingMachine() {
		initResourcesVendingMachine();

		Timer timer = new Timer();
		TimerTask t = new TimerTask() {
			@Override
			public void run() {
				// run daily promotion checking
				promoteService.dailyScript();
			}
		};

		timer.schedule(t, 0l, PROMOTE_PROGRAM_SCHEDULE);
	}

	@Override
	public void initResourcesVendingMachine() {
		vendingMachineProducts = new VendingMachineProducts();
		vendingMachineBank = new VendingMachineBank();
		promoteService = new PromoteServiceImpl();
	}

	@Override
	public void printMenu() {
		System.out.println("**********************");
		System.out.println("** Our Menu **");
		for (Product product : Product.values()) {
			System.out.println(
					"** " + product.getProductNumber() + ". " + product.name() + ": " + product.getPrice() + " đ**");
		}
		System.out.println("Press -1 to cancel purchase");
		System.out.println("**********************");
	}

	@Override
	public boolean insertNote() {
		Scanner userInput = new Scanner(System.in);

		while (true) {
			System.out.println("** Please insert money, we only accept notes of 10000, 20000, 50000, 100000**");
			userRemaining = inputNumber();
			if (userRemaining == -1) {
				System.out.println("Canceled purchase. Thank you!");
				return false;
			}
			if (vendingMachineBank.validateNote(userRemaining)) {
				return true;
			}
		}

	}

	@Override
	public PurchaseRequest chooseProduct() {
		System.out.println("** Please choose a product.**");

		while (true) {
			userProductNumber = inputNumber();

			// cancel purchase
			if (userProductNumber == -1) {
				if (vendingMachineBank.refund(userRemaining) != null) {
					System.out.println("Release remainging change successfully.");
				} else {
					System.out.println("*****************************************");
					System.out.println("** Vending machine is not enough matching money to refund.**");
					System.out.println("** Please contact hotline.**");
					System.out.println("** Sorry for this inconvenient.**");
					System.out.println("*****************************************");

				}
				return null;
			}
			PurchaseRequest purchaseRequest = vendingMachineProducts.checkProductStock(userProductNumber,
					userRemaining);

			// purchase successfully -> release product
			if (purchaseRequest != null) {
				return purchaseRequest;
			}
			System.out.println("Product is unavailabled or your remaining change is not enough: " + userRemaining);
		}

	}

	@Override
	public boolean releaseProductAndRemainingChange(PurchaseRequest userPurchaseRequest) {
		// push product and calculate user remaining
		userRemaining -= userPurchaseRequest.getProduct().getPrice();
		vendingMachineProducts.pushProduct(userPurchaseRequest.getProduct());

		Product freeProductIndex = promoteService.handlePromotion(vendingMachineProducts.getProductList(),
				userPurchaseRequest.getProduct());
		if (freeProductIndex != null) {
			vendingMachineProducts.pushProduct(freeProductIndex);
			System.out.println("Congratulations. You have just receive a free product.");
		}

		// no refund
		if (userRemaining == 0) {
			System.out.println("Thank you.");
			return false;
		} else {
			if (vendingMachineBank.refund(userRemaining) == null) {
				System.out.println(
						"** Vending machine is not enough matching money to refund. Please add more products.**");
				return true;
			}
			System.out.println("Release remainging change successfully.");
			return false;
		}
	}

	public int inputNumber() {
		int number = 0;

		boolean isNotNumber = false;
		do {
			Scanner userInput = new Scanner(System.in);
			String input;
			input = userInput.next();

			try {
				number = Integer.parseInt(input);
				isNotNumber = false;
			} catch (Exception e) {
				System.out.println("Input number only");
				isNotNumber = true;
			}

		} while (isNotNumber);
		return number;
	}

}
