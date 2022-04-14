package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
/**
 * Clasa View implementeaza interfata grafica cu utilizatorul folosind instante ale claselor bibliotecii Java Swing.
 */
public class View extends JFrame {

    private JPanel contentPane;
    private JButton btnClientMenu;
    private JButton btnProductMenu;
    private JButton btnComandaMenu;
    /**
     * Contructor fara parametrii. Aici sunt initializate toate elementele principale ale interfetei. Este formata dintr-un JFrame, care contine
     * un JPanel, cu 3 JButtons.
     */
    public View() {
        setBackground(new Color(255, 245, 238));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 570, 245);
        setLocation(0,0);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 245, 238));
        panel.setBounds(0, 0, 558, 263);
        contentPane.add(panel);
        panel.setLayout(null);

        btnClientMenu = new JButton("Client Menu");
        btnClientMenu.setBackground(new Color(255, 218, 185));
        btnClientMenu.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnClientMenu.setBounds(54, 20, 170, 50);
        panel.add(btnClientMenu);

        btnProductMenu = new JButton("Product Menu");
        btnProductMenu.setBackground(new Color(255, 218, 185));
        btnProductMenu.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnProductMenu.setBounds(331, 20, 170, 50);
        panel.add(btnProductMenu);

        btnComandaMenu = new JButton("Place Order");
        btnComandaMenu.setBackground(new Color(255, 182, 193));
        btnComandaMenu.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnComandaMenu.setBounds(193, 120, 170, 74);
        panel.add(btnComandaMenu);
        this.setVisible(true);
    }
    /**
     * Metoda pentru adaugarea unei instante a clasei ActionListener butonului ClientMenu.
     * @param actionListener
     */
    public void addClientMenuListener(ActionListener actionListener)
    {
        btnClientMenu.addActionListener(actionListener);
    }
    /**
     * Metoda pentru adaugarea unei instante a clasei ActionListener butonului ProductMenu.
     * @param actionListener
     */
    public void addProductMenuListener(ActionListener actionListener)
    {
        btnProductMenu.addActionListener(actionListener);
    }
    /**
     * Metoda pentru adaugarea unei instante a clasei ActionListener butonului ComandaMenu.
     * @param actionListener
     */
    public void addComandaMenuListener(ActionListener actionListener)
    {
        btnComandaMenu.addActionListener(actionListener);
    }
}
    

