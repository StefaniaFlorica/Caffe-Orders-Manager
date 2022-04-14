package bll;

import bll.validators.AgeValidator;
import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa ce face legatura intre layerul Presentation si layerul Data Access.
 * Are ca variabile-instanta:
 * ->o lista de obiecte de tip Validator
 * ->un obiect de tipul AbstractDAO (aici, ClientDA0)
 */
public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    /**
     * Constructor fara parametrii care insatntiaza lista de validatori si creeaza cate o instanta a fiecarei clase-validator corespunzatoare clasei Client
     * Instantiaza clientDAO
     */
    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        //validators.add(new IdValidator<Client>());
        validators.add(new EmailValidator());
        validators.add(new AgeValidator());

        clientDAO = new ClientDAO();
    }

    /**
     * Metoda apeleaza findById() pe obiectul clientDAO.
     * Arunca o exceptie in cazul in care clientul cu id-ul dat ca parametru nu a fost gasit.
     * @param id obiect de tip int
     * @return clientul gasit
     */

    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }

    /**
     * Metoda valideaza campurile unui obiect de tip Client trimis ca parametru.
     * @param client obiect de tip Client
     * @throws IllegalArgumentException
     */
    public void validateClient(Client client) throws IllegalArgumentException
    {
        for(Validator<Client> valid:validators)
        {
            valid.validate(client);
        }
    }

    /**
     * Metoda apeleaza findAll() pe obiectul clientDAO.
     * Arunca o exceptie in cazul in care lista de clientii este goala.
     * @return lista de obiecte de tip Client
     * @throws IllegalArgumentException
     */
    public List<Client> findAllClients() throws IllegalArgumentException
    {
        List<Client> clients=clientDAO.findAll();

        if(clients==null)
        {
            throw new NoSuchElementException("There are no clients in the DB!");
        }
        for(Client client:clients)
        {
            validateClient(client);
        }

        return clients;
    }

    /**
     * Metoda validateClient(), iar daca datele sunt corecte, se apeleaza metoda insert() pe clientDAO.
     * Arunca o exceptie in cazul in care clientul returnat de metoda insert() este null
     * @param insClient
     * @return clientul inserat
     */
    public Client insertClient(Client insClient)
    {
        validateClient(insClient);
        //verific daca clientul de inserat are valori potrivite pt campurile sale
        Client myClient=clientDAO.insert(insClient); //daca e ok, apelez pe singeton metoda insert
        if(myClient==null)
        {
            throw new NoSuchElementException("Client was not inserted!");
        }

        return myClient;
    }

    /**
     * Updates a client's fields in the DB, except for the id column.
     * @param updClient
     * @return
     */
    public Client updateClient(Client updClient)
    {
        validateClient(updClient);
        //verific daca clientul de inserat are valori potrivite pt campurile sale
        Client myClient=clientDAO.update(updClient); //daca e ok, apelez pe singeton metoda update
        if(myClient==null)
        {
            throw new NoSuchElementException("Something went wrong.Client was not updated!");
        }

        return myClient;
    }

    /**
     * Metoda sterge un client dupa id ul trimis ca parametru, apeland metoda delete pe obiectul clientDAO.
     * @param id obiect de tip int
     * @return id ul clientului sters
     */
    public int deleteClient(int id)
    {
        int result=clientDAO.delete(id);
        //daca metoda delete(id) returneaza -1, inseamna ca nu a fost gasit clientul cu id-ul dat, deci stergerea nu a avut loc
        if(result==-1)
        {
            throw new NoSuchElementException("Client with id = "+id+" was not found!");
        }
        return result;
    }
}
