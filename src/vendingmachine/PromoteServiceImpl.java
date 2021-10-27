package vendingmachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;

import model.Product;
import model.PromoteProgram;
import serviceInterface.PromoteService;

public class PromoteServiceImpl implements PromoteService {
	public static final int LIMIT_BUDGET = 50000;
	public static final float DEFAUL_WIN_RATE = 0.1f;
	public static final float CONSECUTIVE_PURCHASE = 3;

	private PromoteProgram promoteProgram;

	public PromoteServiceImpl() {
		promoteProgram = new PromoteProgram();
		promoteProgram.setBudget(LIMIT_BUDGET);
		promoteProgram.setProductId(0);
		promoteProgram.setWinRate(DEFAUL_WIN_RATE);
	}

	@Override
	public boolean updatePromoteInfo(int productId) {
		if (promoteProgram.getProductId() != productId) {
			promoteProgram.setConsecutivePurchase(1);
			promoteProgram.setProductId(productId);
			return false;
		}

		int consecutivePurchase = promoteProgram.getConsecutivePurchase();
		if (consecutivePurchase < CONSECUTIVE_PURCHASE - 1) {
			promoteProgram.setConsecutivePurchase(consecutivePurchase + 1);
			return false;
		} else {
			// reset consecutive
			promoteProgram.setConsecutivePurchase(0);
			return randomProduct(promoteProgram);
		}
	}

	public boolean randomProduct(PromoteProgram promoteProgram) {
		double number = Math.random();
		if (number <= promoteProgram.getWinRate()) {
			return true;
		}
		return false;
	}

	@Override
	public void handleWinPrice(int prize) {
		promoteProgram.setBudget(promoteProgram.getBudget() - prize);
	}

	public PromoteProgram getPromoteProgram() {
		return promoteProgram;
	}

	public void setPromoteProgram(PromoteProgram promoteProgram) {
		this.promoteProgram = promoteProgram;
	}

	@Override
	public Product handlePromotion(HashMap<Product, Integer> listProduct, Product userProduct) {
		if (promoteProgram.getBudget() != 0) {
			boolean isWinPrize = updatePromoteInfo(userProduct.getProductNumber());
			if (isWinPrize) {
				return getFreeProduct(listProduct, promoteProgram.getBudget());
			}
		}
		return null;
	}

	public Product getFreeProduct(HashMap<Product, Integer> listProduct, int limitPrize) {
		for (Map.Entry<Product, Integer> entry : listProduct.entrySet()) {
			int productPrice = entry.getKey().getPrice();
			if (productPrice <= limitPrize) {
				handleWinPrice(productPrice);
				return entry.getKey();
			}
		}
		return null;
	}

	@Override
	public void dailyScript() {
		// reset budget
		promoteProgram.setBudget(50000);

		if (promoteProgram.getWinRate() >= 1) {
			// no user win a free product
			if (promoteProgram.getBudget() == LIMIT_BUDGET) {
				promoteProgram.setWinRate(1);
			} else {
				promoteProgram.setWinRate(0.1f);
			}
			return;
		}

		// cannot reach limit budget
		if (promoteProgram.getBudget() > 0) {
			// increase win rate 50%
			promoteProgram.setWinRate(promoteProgram.getWinRate() * 1.5f);
		}
	}

}
