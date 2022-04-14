package bll.validators;

import model.Product;
/**
 * Clasa validator care valideaza stocul produsul trimis parametru in metoda validate().
 *
 */
public class StockValidator implements Validator<Product>{
    @Override
    /**
     *
     * Metoda valideaza stocul produsului trimis ca parametru.
     * @param product obiect de tipul Comanda
     */
    public void validate(Product product) {
        if(product.getStock()<=0)
            throw new IllegalArgumentException("There needs to be at least one item of "+product.getName());
    }
}
