package serviceInterface;

import java.util.HashMap;

import model.Product;

public interface PromoteService {
	public boolean updatePromoteInfo(int productId);
	public void handleWinPrice(int prize);
	public Product handlePromotion(HashMap<Product, Integer> listProduct, Product userProduct);
	public void dailyScript();
}
