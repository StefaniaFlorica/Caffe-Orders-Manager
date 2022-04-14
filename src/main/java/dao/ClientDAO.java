package dao;

import model.Client;

import java.util.List;

/**
 * Clasa mosteneste clasa AbstractDAO, iar tipul generic este inlocuit cu tipul clasei Client
 * Nu are variabile-instanta, daor suprascrie metodele din clasa-parinte cu apeluri "super"
 */
public class ClientDAO extends AbstractDAO<Client> {
    /**
     * Metoda suprascrisa pentru findAll()
     * @return lista cu obiecte de tipul Client
     */
    @Override
    public List<Client> findAll() {
        return super.findAll();
    }

    /**
     * Metoda suprascrisa pentru findById()
     * @param id obiect de tip int
     * @return obiect de tipul Client
     */
    @Override
    public Client findById(int id) {
        return super.findById(id);
    }
    /**
     * Metoda suprascrisa pentru insert()
     * @param client obiect de tipul Client
     * @return obiect de tipul Client
     */
    @Override
    public Client insert(Client client) {
        return super.insert(client);
    }
    /**
     * Metoda suprascrisa pentru update()
     * @param client obiect de tipul Client
     * @return obiect de tipul Client
     */
    @Override
    public Client update(Client client) {
        return super.update(client);
    }


}
