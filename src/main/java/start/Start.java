package start;

import bll.ClientBLL;
import model.Client;
import presentation.Controller;
import presentation.View;

public class Start {
    public static void main(String[] args) {
        View mainView= new View();
        Controller mainViewController= new Controller(mainView);
    }
}
