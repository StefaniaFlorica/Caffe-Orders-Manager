package dao;

import model.Product;

import java.util.List;
/**
 * Clasa mosteneste clasa AbstractDAO, iar tipul generic este inlocuit cu tipul clasei ProductNu are variabile-instanta, doar suprascrie metodele din clasa-parinte cu apeluri "super"
 */
public class ProductDAO extends AbstractDAO<Product>{
    /**
     * Metoda suprascrisa pentru findAll()
     * @return lista cu obiecte de tipul Product
     */
    @Override
    public List<Product> findAll() {
        return super.findAll();
    }
    /**
     * Metoda suprascrisa pentru findById()
     * @param id obiect de tip int
     * @return obiect de tipul Product
     */
    @Override
    public Product findById(int id) {
        return super.findById(id);
    }
    /**
     * Metoda suprascrisa pentru insert()
     * @param product obiect de tipul Product
     * @return obiect de tipul Product
     */
    @Override
    public Product insert(Product product) {
        return super.insert(product);
    }
    /**
     * Metoda suprascrisa pentru update()
     * @param product obiect de tipul Product
     * @return obiect de tipul Product
     */
    @Override
    public Product update(Product product) {
        return super.update(product);
    }
}
