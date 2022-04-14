package model;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product()
    {

    }
    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
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
     * getter pentru nume
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * setter pentru nume
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter pentru pret
     * @return double
     */
    public double getPrice() {
        return price;
    }

    /**
     * setter pentru pret
     * @param price double
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getter pentru stoc
     * @return int
     */
    public int getStock() {
        return stock;
    }

    /**
     * setter pentru stoc
     * @param stock int
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * decrementeaza valoarea stocului cu "itemsBought" unitati
     * @param itemsBought int
     */
    public void decrementStock(int itemsBought)
    {
        stock-=itemsBought;
    }
}
