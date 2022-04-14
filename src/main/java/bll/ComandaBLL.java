package bll;

import bll.validators.QuantityValidator;
import bll.validators.StockValidator;
import bll.validators.Validator;
import dao.ComandaDAO;
import model.Client;
import model.Comanda;

import java.util.List;
import java.util.NoSuchElementException;
/**
 * Clasa ce face legatura intre layerul Presentation si layerul Data Access.
 * Are ca variabile-instanta:
 * ->un obiect de tip Validator
 * ->un obiect de tipul AbstractDAO (aici, ComandaDAO)
 */
public class ComandaBLL {
    Validator<Comanda> validator;
    ComandaDAO comandaDAO;
    /**
     * Constructor fara parametrii care instantiaza validatorului si comandaDAO.
     */
    public ComandaBLL()
    {
        validator= new QuantityValidator();
        comandaDAO=new ComandaDAO();
    }
    /**
     * Metoda apeleaza findById() pe obiectul comandaDAO.
     * Arunca o exceptie in cazul in care comandaul cu id-ul dat ca parametru nu a fost gasit.
     * @param id obiect de tip int
     * @return comanda gasita
     */

    public Comanda findComandaById(int id) {
        Comanda comanda = comandaDAO.findById(id);
        if (comanda == null) {
            throw new NoSuchElementException("The comanda with id =" + id + " was not found!");
        }
        return comanda;
    }
    /**
     * Metoda apeleaza findAll() pe obiectul comandaDAO.
     * Arunca o exceptie in cazul in care lista de comenzi este goala.
     * @return lista de obiecte de tip Comanda
     * @throws IllegalArgumentException
     */
    public List<Comanda> findAllComandas() throws IllegalArgumentException
    {
        List<Comanda> comandas=comandaDAO.findAll();

        if(comandas==null)
        {
            throw new NoSuchElementException("There are no comandas in the DB!");
        }
        for(Comanda comanda:comandas)
        {
            validator.validate(comanda);
        }

        return comandas;
    }
    public int getNextIndexComanda()
    {
        return comandaDAO.getNextIndex();
    }
    /**
     * Se valideaza insComanda, iar daca datele sunt corecte, se apeleaza metoda insert() pe comandaDAO.
     * Arunca o exceptie in cazul in care comandaul returnat de metoda insert() este null
     * @param insComanda
     * @return comanda inserata
     */
    public Comanda insertComanda(Comanda insComanda)
    {
        validator.validate(insComanda);
        //verific daca comanda de inserat are valori potrivite pt campurile sale
        Comanda myComanda=comandaDAO.insert(insComanda); //daca e ok, apelez pe singeton metoda insert
        if(myComanda==null)
        {
            throw new NoSuchElementException("Comanda was not inserted!");
        }

        return myComanda;
    }

    /**
     * Actualizeaza o comanda din BD, fara a schimba id-ul, pe care il consideram PK si nu poate fi modificat.
     * @param updComanda obiect de tipul comanda
     * @return comanda actualizata
     */
    public Comanda updateComanda(Comanda updComanda)
    {
        validator.validate(updComanda);
        //verific daca produsul de inserat are valori potrivite pt campurile sale
        Comanda myComanda=comandaDAO.update(updComanda); //daca e ok, apelez pe singeton metoda update
        if(myComanda==null)
        {
            throw new NoSuchElementException("Comanda was not updated!");
        }

        return myComanda;
    }

}
