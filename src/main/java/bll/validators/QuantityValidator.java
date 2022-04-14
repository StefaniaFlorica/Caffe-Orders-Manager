package bll.validators;

import model.Comanda;
/**
 * Clasa validator care valideaza cantitatea dorita din produsul din comanda trimisa parametru in metoda validate().
 *
 */
public class QuantityValidator implements Validator<Comanda>{
    @Override
    /**
     *
     * Metoda valideaza cantitatea dorita din produsul din comanda trimisa ca parametru.
     * @param comanda obiect de tipul Comanda
     */
    public void validate(Comanda comanda) {

        if(comanda.getQuantity()<=0)
        {
            throw new IllegalArgumentException("Quantity has to be greater than 0!");
        }
    }
}
