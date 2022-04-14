package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

/**
 *Clasa de tip Data-Access-Object, folosita ca intermediar intre Business Logic si Database Connection.Are ca avriabile-instanta:,obiectul LOGGER, folosit pentru logging si eventuale depanari ulterioare, in caz ca va fi nevoie,obiectul instanta a clasei Class, in care se retine tipul clasei din care este instantiat obiectul pentru care sunt efectuate interogarile la BD. Se obtine folosind Java Reflection si implementeaza design patter-ul Singleton
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Constructor fara parametrii.Obtine tipul generic T al obiectului "type", folosing Java Reflection
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        System.out.println(sb.toString());
        return sb.toString();
    }
    /**
     * Method for creating a select-all-table-entries from a table(to be sent as parameter to a Connection.prepareStatement() method)
     * @return a String representing the SELECT * FROM "table_name" query
     */

    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * Method for creating INSERT query using Java Reflection
     * @return a String representing the query to be executed
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" VALUES (");
        for(int i=0;i<type.getDeclaredFields().length;i++)
            sb.append("?"+((i==type.getDeclaredFields().length-1)?");":","));
        return sb.toString();
    }
    /**
     * Method for creating UPDATE query using Java Reflection
     * @return a String representing the query to be executed
     */
    private String createUpdateQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        Field[] fields= type.getDeclaredFields();
        for(int i=1;i<type.getDeclaredFields().length;i++)
            sb.append(fields[i].getName()+"=?"+((i==type.getDeclaredFields().length-1)?"":","));
        sb.append(" WHERE "+field+"=?;");
        return sb.toString();
    }

    /**
     * Method for creating INSERT query using Java Reflection
     * @return a String representing the query to be executed
     */
    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE "+field+"=?;");
        return sb.toString();
    }

    /**
     * Metoda executa query-ul generat de metoda createSelectQuery() si creeaza o lista cu obiectele rezultate in urma apelului metodei createObjects(ResultSet resultSet)
     * @return lista de obiecte de tipul T
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    public Class<T> getType()
    {
        return type;
    }
    /**
     * Metoda executa query-ul generat de metoda createSelectQuery(Strind field) si creeaza o lista cu obiectele rezultate in urma apelului metodei createObjects(ResultSet resultSet)
     * @return un obiect de tipul T
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            //System.out.println(statement.toString());

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * Metoda executa query-ul generat de metoda createDeleteQuery()
     * @return id-ul entry-ului sters din tabel
     */
    public int delete(int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int value = statement.executeUpdate();
            return value;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }

    /**
     * Metoda creeaza o lista de obiecte de tipul T, folosind datele stocate in obiectul de tip ResultSet trimis ca parametruSe foloseste Java Reflection pentru a extrage Field-urile clasei de tipul T si pentru a obtine metodele setter pentru a crea un nou obiect cu valorile stocate in obeictul de tip ResultSet trimis ca parametru
     * @param resultSet obiect de tipul ResultSet
     * @return lista de obiecte de tipul T
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException |IllegalAccessException|SecurityException|IllegalArgumentException|InvocationTargetException|SQLException|IntrospectionException  e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * Metoda executa query-ul generat de metoda createInsertQuery().Arunca exceptii de tipul SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException in cazul unor inconsistente ale datelor
     * @return obiectul de tipul T inserat
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery();
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            Field [] tFields = t.getClass().getDeclaredFields();
            for(int i=1;i<=statement.getParameterMetaData().getParameterCount();i++)
            {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(tFields[i-1].getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                statement.setObject(i,method.invoke(t));
            }
            System.out.println(statement.toString());
            int val = statement.executeUpdate();
            return t;
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * Metoda executa query-ul generat de metoda createUpdateQuery().Arunca exceptii de tipul SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException in cazul unor inconsistente ale datelor
     * @return obiectul de tipul T inserat
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createUpdateQuery("id");
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            Field [] tFields = t.getClass().getDeclaredFields();
            for(int i=0;i<statement.getParameterMetaData().getParameterCount();i++)
            {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(tFields[i].getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                if(i==0){
                    statement.setObject(statement.getParameterMetaData().getParameterCount(),method.invoke(t));
                }
                else
                    statement.setObject(i,method.invoke(t));
            }
            int value= statement.executeUpdate();

            return t;
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
