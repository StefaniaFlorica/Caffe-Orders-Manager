package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *Clasa pentru controlul view-ului principal.
 */
public class Controller {
    /**
     * Variabile instanta:
     * @param view obiect de tipul View
     */
    private View view;

    /**
     * Contructor care atribuie parametrul view1 campului view si adauga ActionListeneri butoanelor din view
     * @param view1
     */
    public Controller(View view1)
    {
        view=view1;
        view.addClientMenuListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new TableViewController(new TableView(Class.forName("model.Client")),view);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        view.addProductMenuListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new TableViewController(new TableView(Class.forName("model.Product")),view);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        view.addComandaMenuListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new TableViewController(new TableView(Class.forName("model.Comanda")),view);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
