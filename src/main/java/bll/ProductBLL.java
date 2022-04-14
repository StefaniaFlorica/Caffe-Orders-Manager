package bll;

import bll.validators.StockValidator;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Client;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;
/**
 * Clasa ce face legatura intre layerul Presentation si layerul Data Access.
 * Are ca variabile-instanta:
 * ->un obiect de tip Validator
 * ->un obiect de tipul AbstractDAO (aici, ProductDA0)
 */
public class ProductBLL {
    Validator<Product> validator;
    ProductDAO productDAO;
    /**
     * Constructor fara parametrii care instantiaza vaalidatorul pentru stoc si variabila-instanta productDAO.
     */
    public ProductBLL()
    {
        validator= new StockValidator();
        productDAO=new ProductDAO();
    }
    /**
     * Metoda apeleaza findById() pe obiectul produsDAO.
     * Arunca o exceptie in cazul in care produsul cu id-ul dat ca parametru nu a fost gasit.
     * @param id obiect de tip int
     * @return produsul gasit
     */

    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return product;
    }
    /**
     * Metoda apeleaza findAll() pe obiectul produsDAO.
     * Arunca o exceptie in cazul in care lista de produsii este goala.
     * @return lista de obiecte de tip Product
     * @throws IllegalArgumentException
     */
    public List<Product> findAllProducts() throws IllegalArgumentException
    {
        List<Product> products=productDAO.findAll();

        if(products==null)
        {
            throw new NoSuchElementException("There are no products in the DB!");
        }
        for(Product product:products)
        {
            validator.validate(product);
        }

        return products;
    }
    /**
     * Se valideaza insProduct, iar daca datele sunt corecte, se apeleaza metoda insert() pe produsDAO.
     * Arunca o exceptie in cazul in care produsul returnat de metoda insert() este null
     * @param insProduct
     * @return produsul inserat
     */
    public Product insertProduct(Product insProduct)
    {
        validator.validate(insProduct);
        //verific daca produsul de inserat are valori potrivite pt campurile sale
        Product myProduct=productDAO.insert(insProduct); //daca e ok, apelez pe singeton metoda insert
        if(myProduct==null)
        {
            throw new NoSuchElementException("Product was not inserted!");
        }

        return myProduct;
    }

    /**
     * Actualizeaza un produs in baza de date,exceptand coloana pentru id (consideram ca este PK si nu poate fi modificata)
     * @param updProduct obiect de tipul Product
     * @return produsul actualizat
     */
    public Product updateProduct(Product updProduct)
    {
        validator.validate(updProduct);
        //verific daca produsul de inserat are valori potrivite pt campurile sale
        Product myProduct=productDAO.update(updProduct); //daca e ok, apelez pe singeton metoda update
        if(myProduct==null)
        {
            throw new NoSuchElementException("Product was not updated!");
        }

        return myProduct;
    }
    /**
     * Metoda sterge un produs dupa id ul trimis ca parametru, apeland metoda delete pe obiectul produsDAO.
     * @param id obiect de tip int
     * @return id ul produsului sters
     */
    public int deleteProduct(int id)
    {
        int result=productDAO.delete(id);
        //daca metoda delete(id) returneaza -1, inseamna ca nu a fost gasit produsul cu id-ul dat, deci stergerea nu a avut loc
        if(result==0)
        {
            throw new NoSuchElementException("Product with id = "+id+" was not found!");
        }
        return result;
    }
}
