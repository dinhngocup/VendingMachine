package vendingmachine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import model.Product;
import model.PurchaseRequest;

public class VendingMachineProducts {
	private HashMap<Product, Integer> productList;

	public VendingMachineProducts() {
		productList = new HashMap<>();
		for (Product product : Product.values()) {
			productList.put(product, 25);
		}

	}

	public HashMap<Product, Integer> getProductList() {
		return productList;
	}

	public void setProductList(HashMap<Product, Integer> productList) {
		this.productList = productList;
	}
	
	public PurchaseRequest checkProductStock(int userProductNumber, int userRemaining) {
		for (Map.Entry<Product, Integer> entry : productList.entrySet()) {
			Product product = entry.getKey();
			if (userProductNumber == product.getProductNumber() && userRemaining >= product.getPrice() && entry.getValue() > 0) {
				return new PurchaseRequest(product, userRemaining);
			}
		}
		return null;
	}
	
	public void pushProduct(Product product) {
		for (Map.Entry<Product, Integer> entry : productList.entrySet()) {
			if (product.getProductNumber() == entry.getKey().getProductNumber()) {
				System.out.println("Push product to user successfully");
				entry.setValue(entry.getValue() - 1);
				return;
			}
		}
	}
}
