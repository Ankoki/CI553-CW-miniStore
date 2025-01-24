package catalogue.test;

import catalogue.BetterBasket;
import catalogue.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BetterBasketTest {

    private final BetterBasket basket = new BetterBasket();
    private Product cabbage, carrot, onion;

    @Before
    public void initiate() {
        this.basket.clear();
        this.cabbage = new Product("V153", "Washed Cabbage", 1.0, 1);
        this.carrot = new Product("V158", "Juicy Carrot", 1.0, 1);
        this.onion = new Product("V141", "Tart Onion", 2.0, 1);
    }

    @Test
    public void testAutoMerge() {
        this.initiate();
        this.basket.add(cabbage);
        this.basket.add(carrot);
        this.basket.add(onion);
        this.basket.add(onion);
        this.basket.add(carrot);
        Assert.assertEquals(3, this.basket.size());
        for (Product product : basket)
            Assert.assertEquals(product.getProductNum().equals("V153") ? 1 : 2, product.getQuantity());
    }

    @Test
    public void testSort() {
        this.initiate();
        this.basket.add(cabbage);
        this.basket.add(carrot);
        this.basket.add(onion);
        Assert.assertEquals(basket.get(0).getProductNum(), cabbage.getProductNum());
        basket.sort();
        Assert.assertEquals(basket.get(0).getProductNum(), onion.getProductNum());
        Assert.assertEquals(basket.get(1).getProductNum(), cabbage.getProductNum());
        Assert.assertEquals(basket.get(2).getProductNum(), carrot.getProductNum());
    }

}
