package model;
/**
 * Clasa-model pentru tabelul din baza de date.
 * Are ca variabile-instanta obiecte corespunzatoare tipului de date stocate in tabelul din baza de date.
 */
public class Comanda {
    private int id;
    private int idClient;
    private int idProduct;
    private double priceProduct;
    private int quantity;
    /**
     * Constructor fara parametrii
     */
    public Comanda(){}
    /**
     * Constructor care instantiaza variabilele instanta cu parametrii primiti.
     * @param id obiect de tipul int
     * @param idClient obiect de tipul int
     * @param idProduct obiect de tipul int
     * @param priceProduct obiect de tipul double
     * @param quantity obiect de tipul int
     */
    public Comanda(int id, int idClient, int idProduct, double priceProduct, int quantity) {
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.priceProduct = priceProduct;
        this.quantity = quantity;
    }

    /**
     * getter pentru id
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * setter pentru id
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter pentru idClient
     * @return int
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     * setter pentru idClient
     * @param idClient int
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    /**
     * getter pentru idProduct
     * @return int
     */
    public int getIdProduct() {
        return idProduct;
    }

    /**
     * setter pentru idProduct
     * @param idProduct int
     */
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    /**
     * getter pentru pret
     * @return double
     */
    public double getPriceProduct() {
        return priceProduct;
    }

    /**
     * setter pentru pret
     * @param priceProduct double
     */
    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }

    /**
     * getter pentru cantitate
     * @return int
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * setter pentru cantitate
     * @param quantity int
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
