package presentation;

import bll.ClientBLL;
import bll.ComandaBLL;
import bll.ProductBLL;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.Client;
import model.Comanda;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa-controller pentru interfata grafica cu utilizatorul, instanta a clasei TableView.Are ca varibile-instanta:un obiect de tipul TableView(interfata care trebuie controlata),un obiect de tipul View(referinta a intefetei principale, prin intermediul careia s-a creat obiectul TableView, la apasarea unuia dintre butoane,trei instante, ce respecta design pattern-ul Singleton, ale claselor-model
 */
public class TableViewController {
    private TableView tableView;
    private View mainView;
    private static ClientBLL clientBLL=new ClientBLL();
    private static ProductBLL productBLL=new ProductBLL();
    private static ComandaBLL comandaBLL= new ComandaBLL();

    /**
     * Constructor care instantiaza varibilele-instanta tabelView si mainView cu referintele obiectelor trimise ca parametriiApeleaza metodele pentru adaugare de ActionListeners butoanelor din tableView
     * @param tableView1
     * @param mainView1
     */
    public TableViewController(TableView tableView1,View mainView1)
    {
        tableView=tableView1;
        mainView=mainView1;
        try {
            tableView.addInsertListener(new InsertListener());
        }
        catch (IllegalArgumentException exception)
        {
            JOptionPane.showMessageDialog(mainView,exception.getMessage());
        }
        tableView.addDeleteListener(new DeleteListener());
        tableView.addUpdateListener(new UpdateListener());
        tableView.addViewAllListener(new ViewListener());
    }

    /**
     * Metoda care foloseste biblioteca iTextPDF pentru crearea unui fisier .pdf pentru generarea unui bon pentru fiecare comanda plasata.
     * @param comanda obiect de tipul Comanda
     */
    public void generateBill(Comanda comanda)
    {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Bill #"+comanda.getId()+".pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Client client=clientBLL.findClientById(comanda.getIdClient());
        Product product=productBLL.findProductById(comanda.getIdProduct());
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
        Paragraph para1= new Paragraph("Bill #"+comanda.getId()+"\n",font);
        Paragraph para2= new Paragraph("Customer: "+client.getName()+" (id="+client.getId()+") "+"\n", font);
        Paragraph para3= new Paragraph(product.getName()+"        $"+product.getPrice()+"        x"+comanda.getQuantity()+"        "+(comanda.getQuantity()*product.getPrice())+"\n", font);
        Paragraph para4= new Paragraph("Thank you for supporting us! See you next time!"+"\n", font);
        try {
            document.add(para1);
            document.add(para2);
            document.add(para3);
            document.add(para4);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    /**
     * Clasa implemeteaza interfata ActionListener.
     */
    class InsertListener implements ActionListener{

        /**
         * Metoda suprascrisa a interfetei ActionListener, care defineste comportamentul aplicatiei la apasarea butonului Insert.Se apeleaza metode ale claselor BLL(folosind variabilele-instanta ale claselor respective) care faciliteaza accesul si operatiile cu baza de date
         * @param e obiect de tipul ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(tableView.getClassType().getSimpleName().equals("Comanda"))
            {
                try{
                    List<String> txtFields=tableView.getText();
                    //int id=Integer.parseInt(txtFields.get(0));
                    int id=comandaBLL.getNextIndexComanda();
                    int idClient=Integer.parseInt(txtFields.get(1));
                    int idProduct=Integer.parseInt(txtFields.get(2));
                    Product p=productBLL.findProductById(idProduct); //caut produsul cumparat (cu id ul dat)
                    double priceProduct=p.getPrice();
                    int quantity=Integer.parseInt(txtFields.get(4));
                    if(p.getStock()>=quantity)
                    {   Comanda myOrder=new Comanda(id,idClient,idProduct,priceProduct,quantity);
                        comandaBLL.insertComanda(myOrder);
                        generateBill(myOrder);
                    }
                    else
                    {
                        throw new IllegalArgumentException("Not enough items of selected product!");
                    }
                    p.setStock(p.getStock()-quantity); //modific stocul produsului
                    productBLL.updateProduct(p); //update in tabel
                    tableView.generateTable(comandaBLL.findAllComandas()); //afisez tabelul
                }
                catch (IllegalArgumentException ex)
                {
                    JOptionPane.showMessageDialog(tableView,ex.getMessage());
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(tableView,"Values do not match! \nPlease try again");
                }

            }
            else if(tableView.getClassType().getSimpleName().equals("Client"))
            {
                try {
                    List<String> txtFields = tableView.getText();
                    int id = Integer.parseInt(txtFields.get(0));
                    String name = txtFields.get(1);
                    String email = txtFields.get(2);
                    String address = txtFields.get(3);
                    int age = Integer.parseInt(txtFields.get(4));
                    clientBLL.insertClient(new Client(id, name, email, address, age));
                    System.out.println("Am inserat clientul cu id " + id);
                    tableView.generateTable(clientBLL.findAllClients());
                }
                catch (IllegalArgumentException ex)
                {
                    JOptionPane.showMessageDialog(tableView,ex.getMessage());
                }
                catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(tableView,"Values do not match! \nPlease try again");
                }
            }
            else
            {
                try{
                    List<String> txtFields=tableView.getText();
                    int id=Integer.parseInt(txtFields.get(0));
                    String name=txtFields.get(1);
                    double price=Double.parseDouble(txtFields.get(2));
                    int stock= Integer.parseInt(txtFields.get(3));

                    productBLL.insertProduct(new Product(id,name,price,stock));
                    tableView.generateTable(productBLL.findAllProducts());
                }
                catch (IllegalArgumentException ex)
                {
                    JOptionPane.showMessageDialog(tableView,ex.getMessage());
                }
                catch(Exception exception)
                {
                    JOptionPane.showMessageDialog(tableView,"Values do not match! \nPlease try again");
                }
            }
        }
    }
    /**
     * Clasa implemeteaza interfata ActionListener.
     */
    class DeleteListener implements ActionListener{
        /**
         * Metoda suprascrisa a interfetei ActionListener, care defineste comportamentul aplicatiei la apasarea butonului Delete.Se apeleaza metode ale claselor BLL(folosind variabilele-instanta ale claselor respective) care faciliteaza accesul si operatiile cu baza de date
         * @param e obiect de tipul ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(tableView.getClassType().getSimpleName().equals("Comanda"))
            {
                JOptionPane.showMessageDialog(null,"You can't delete an order!");
            }
            else if(tableView.getClassType().getSimpleName().equals("Client"))
            {
                try {
                    List<String> txtFields = tableView.getText();
                    int id = Integer.parseInt(txtFields.get(0));
                    clientBLL.deleteClient(id);
                    tableView.generateTable(clientBLL.findAllClients());
                }catch(NoSuchElementException ex)
                {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }

            }
            else
            {
                try{
                    List<String> txtFields=tableView.getText();
                    int id=Integer.parseInt(txtFields.get(0));
                    productBLL.deleteProduct(id);
                    tableView.generateTable(productBLL.findAllProducts());
                }
                catch(NoSuchElementException ex)
                {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        }
    }
    /**
     * Clasa implemeteaza interfata ActionListener.
     */
    class UpdateListener implements ActionListener{
        /**
         * Metoda suprascrisa a interfetei ActionListener, care defineste comportamentul aplicatiei la apasarea butonului Update.Se apeleaza metode ale claselor BLL(folosind variabilele-instanta ale claselor respective) care faciliteaza accesul si operatiile cu baza de date
         * @param e obiect de tipul ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                List<String> txtFields=tableView.getText();
                if(tableView.getClassType().getSimpleName().equals("Comanda"))
                {

                    try {
                        int id = Integer.parseInt(txtFields.get(0));
                        int idClient = Integer.parseInt(txtFields.get(1));
                        int idProduct = Integer.parseInt(txtFields.get(2));
                        Product p = productBLL.findProductById(idProduct); //caut produsul cumparat (cu id ul dat)
                        double priceProduct = p.getPrice();
                        int quantity = Integer.parseInt(txtFields.get(4));
                        comandaBLL.updateComanda(new Comanda(id, idClient, idProduct, priceProduct, quantity));
                        tableView.generateTable(comandaBLL.findAllComandas());
                    }
                    catch(NoSuchElementException ex)
                    {
                        JOptionPane.showMessageDialog(null,ex.getMessage());
                    }
                }
                else if(tableView.getClassType().getSimpleName().equals("Client"))
                {
                    try
                    {
                        int id=Integer.parseInt(txtFields.get(0));
                        String name=txtFields.get(1);
                        String email=txtFields.get(2);
                        String address=txtFields.get(3);
                        int age= Integer.parseInt(txtFields.get(4));
                        clientBLL.updateClient(new Client(id,name,email,address,age));
                        tableView.generateTable(clientBLL.findAllClients());
                    }
                    catch(NoSuchElementException ex)
                    {
                        JOptionPane.showMessageDialog(null,ex.getMessage());
                    }

                }
                else
                {
                    try {
                        int id = Integer.parseInt(txtFields.get(0));
                        String name = txtFields.get(1);
                        double price = Double.parseDouble(txtFields.get(2));
                        int stock = Integer.parseInt(txtFields.get(3));
                        productBLL.updateProduct(new Product(id, name, price, stock));
                        tableView.generateTable(productBLL.findAllProducts());
                    }
                    catch(NoSuchElementException ex)
                    {
                        JOptionPane.showMessageDialog(null,ex.getMessage());
                    }
                }
            }
            catch(Exception exception)
            {
                JOptionPane.showMessageDialog(tableView,"Values do not match! \nPlease try again");
            }

        }
    }
    /**
     * Clasa implemeteaza interfata ActionListener.
     */
    class ViewListener implements ActionListener{

        /**
         * Metoda suprascrisa a interfetei ActionListener, care defineste comportamentul aplicatiei la apasarea butonului ViewAll.Se apeleaza metode ale claselor BLL(folosind variabilele-instanta ale claselor respective) care faciliteaza accesul si operatiile cu baza de date
         * @param e obiect de tipul ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(tableView.getClassType().getSimpleName().equals("Comanda"))
            {
                tableView.generateTable(comandaBLL.findAllComandas());
            }
            else if(tableView.getClassType().getSimpleName().equals("Client"))
            {
                tableView.generateTable(clientBLL.findAllClients());
            }
            else
            {
                tableView.generateTable(productBLL.findAllProducts());
            }
        }
    }

}
