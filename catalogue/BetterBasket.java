package catalogue;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * A collection of products, a parent of {@link Basket}.
 * Provides utility methods to help keep the products organised.
 *
 * @author Jay McVeigh
 * @version 1.0
 */
public class BetterBasket extends Basket implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Adds a product to the basket.
     * <br>
     * Automatically merges duplicate items.
     *
     * @param pr A product to be added to the basket
     * @return true if successfully adds the product
     */
    @Override
    public boolean add(Product pr) {
        for (Product product : this) {
            if (product.getProductNum().equals(pr.getProductNum())) {
                product.setQuantity(product.getQuantity() + pr.getQuantity());
                return true;
            }
        }
        return super.add(pr);
    }

    /**
     * Sorts the current basket from highest to lowest product number.
     */
    public void sort() {
        this.sort(Comparator.comparing(Product::getProductNum));
    }

}
