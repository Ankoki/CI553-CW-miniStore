package catalogue.test;

import catalogue.BetterBasket;
import catalogue.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BetterBasketTest {

    private final BetterBasket basket = new BetterBasket();
    private Product cabbage, carrot, onion;

    @Before
    public void initiate() {
        this.cabbage = new Product("V153", "Washed Cabbage", 1.0, 1);
        this.carrot = new Product("V158", "Juicy Carrot", 1.0, 1);
        this.onion = new Product("V141", "Tart Onion", 2.0, 1);
        this.basket.addAll(List.of(cabbage, carrot, carrot, carrot, onion));
        this.basket.add(new Product("V141", "Tart Onion", 2.0, 3));
    }

    @Test
    public void testMerge() {
        Assert.assertEquals(6, basket.size());
        basket.mergeItems();
        Assert.assertEquals(3, basket.size());
        for (Product product : basket)
            Assert.assertEquals(product.getProductNum().equals("V153") ? 1 :
                    product.getProductNum().equals("V158") ? 3 : 4, product.getQuantity());
    }

    @Test
    public void testSort() {
        Assert.assertEquals(basket.get(0).getProductNum(), cabbage.getProductNum());
        basket.sort();
        Assert.assertEquals(basket.get(0).getProductNum(), onion.getProductNum());
        Assert.assertEquals(basket.get(2).getProductNum(), cabbage.getProductNum());
        Assert.assertEquals(basket.get(3).getProductNum(), carrot.getProductNum());
    }


}
