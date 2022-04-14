package dao;

import connection.ConnectionFactory;
import model.Comanda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Clasa mosteneste clasa AbstractDAO, iar tipul generic este inlocuit cu tipul clasei Comanda
 * Nu are variabile-instanta, daor suprascrie metodele din clasa-parinte cu apeluri "super"
 */
public class ComandaDAO extends AbstractDAO<Comanda> {

    public int getNextIndex()
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT MAX(id) from Comanda";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1)+1;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, getType().getName() + "DAO:getNextIndex " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 1;
    }
    /**
     * Metoda suprascrisa pentru findAll()
     * @return lista cu obiecte de tipul Comanda
     */
    @Override
    public List<Comanda> findAll() {
        return super.findAll();
    }
    /**
     * Metoda suprascrisa pentru findById()
     * @param id obiect de tip int
     * @return obiect de tipul Comanda
     */
    @Override
    public Comanda findById(int id) {
        return super.findById(id);
    }
    /**
     * Metoda suprascrisa pentru insert()
     * @param comanda obiect de tipul Comanda
     * @return obiect de tipul Comanda
     */
    @Override
    public Comanda insert(Comanda comanda) {
        return super.insert(comanda);
    }
    /**
     * Metoda suprascrisa pentru update()
     * @param comanda obiect de tipul Comanda
     * @return obiect de tipul Comanda
     */
    @Override
    public Comanda update(Comanda comanda) {
        return super.update(comanda);
    }
}
