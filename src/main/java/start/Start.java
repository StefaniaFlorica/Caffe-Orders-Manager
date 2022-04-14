package start;

import bll.ClientBLL;
import model.Client;
import presentation.Controller;
import presentation.View;

/**
 * Clasa ccare contine metoda main.
 */
public class Start {
    /**
     * Metoda main in care se creeaza un obiect de tip View si i le atribuie un controller.
     * @param args
     */
    public static void main(String[] args) {
        View mainView= new View();
        Controller mainViewController= new Controller(mainView);
    }
}
