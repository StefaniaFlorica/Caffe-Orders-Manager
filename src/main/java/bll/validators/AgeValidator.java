package bll.validators;
import model.Client;
/**
 * Clasa validator care valideaza varsta unui client primis ca parametru in metoda validate().
 * Are ca variabila-instanta o constanta de tip int care retine varsta minima a unui client.
 */
public class AgeValidator implements Validator<Client>{
    private static final int MINIMUM_AGE=18;
    @Override
    /**
     *
     * Metoda valideaza varsta clientului trimis ca parametru.
     * @param client obiect de tipul Client
     */
    public void validate(Client client) {
        if(client.getAge()<MINIMUM_AGE)
            throw new IllegalArgumentException("Client is underage!");
    }
}
