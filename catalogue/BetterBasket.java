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
     * Merges the items in this basket.
     */
    public void mergeItems() {
        List<Product> products = new ArrayList<>();
        Map<String, Integer> productMap = new HashMap<>();
        for (Product product : this) {
            boolean foundDupe = false;
            for (Product list : products) {
                if (product.getProductNum().equals(list.getProductNum())) {
                    foundDupe = true;
                    break;
                }
            }
            if (!foundDupe)
                products.add(product);
            productMap.put(product.getProductNum(), productMap.getOrDefault(product.getProductNum(), 0) + product.getQuantity());
        }
        this.clear();
        for (Product product : products) {
            product.setQuantity(productMap.get(product.getProductNum()));
            this.add(product);
        }
        System.out.println("MERGED");
    }

    /**
     * Sorts the current basket from highest lowest product number.
     */
    public void sort() {
        this.sort(Comparator.comparing(Product::getProductNum));
    }

}
