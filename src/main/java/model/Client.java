package model;

/**
 * Clasa-model pentru tabelul din baza de date.
 * Are ca variabile-instanta obiecte corespunzatoare tipului de date stocate in tabelul din baza de date.
 */
public class Client {
    private int id;
    private String name;
    private String email;
    private String address;
    private int age;

    /**
     * Constructor fara parametrii
     */
    public Client()
    {

    }

    /**
     * Constructor care instantiaza variabilele instanta cu parametrii primiti.
     * @param id obiect de tipul int
     * @param name obiect de tipul String
     * @param email obiect de tipul String
     * @param address obiect de tipul String
     * @param age obiect de tipul int
     */
    public Client(int id, String name, String email,String address, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address=address;
        this.age = age;
    }

    /**
     * getter pentru id
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * setter oentru id
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
     * getter pentru email
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter pentru email
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter pentru varsta
     * @return int
     */
    public int getAge() {
        return age;
    }

    /**
     * setter pentru varsta
     * @param age int
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * getter pentru adresa
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * setter pentru adresa
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    /**
     *
     * Metoda toString()
     * @return String
     */

    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}
