package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care implementeaza interfata grafica cu utilizatorul, folosind elemente ale bibliotecii Java Swing. Contine concepte de Java Reflection
 * pentru a crea o interfata generica pentru orice clasa de tipul Client,Comanda sau Product.
 */
public class TableView extends JFrame{
    private Class type;
    private JPanel contentPane;
    private JPanel panelInfo;
    private JPanel panelTable;
    private JPanel panelBtn;
    private JButton[] buttons;
    private JLabel[] labels;
    private JTextField[] txtFields;
    private JTable table;

    /**
     * Constructor cu un parametru de tip Class.
     * Seteaza elementele principale ale interfetei grafice, folosind Java Reflection, adaugand JLabels si JTextFields in corespunzatoare Field-urile clasei Class trimisa ca parametru.
     * Sunt instantiate si restul componentelor principale ale GUI-ului, precum JPanels, JButtons si JTable.
     * @param type o instanta a clasei Class, facand referire la tipul clasei-model pentru care a fost creata interfata
     */

    public TableView(Class type)
    {
        this.type=type;
        this.setLocationRelativeTo(null);
        setBounds(100, 100, 600, 700);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(250, 240, 230));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panelInfo = new JPanel();
        panelInfo.setBackground(new Color(250, 235, 215));
        panelInfo.setBounds(0, 0, 300, 215);
        contentPane.add(panelInfo);
        panelInfo.setLayout(null);

        panelTable = new JPanel();
        panelTable.setBackground(new Color(250, 240, 230));
        panelTable.setBounds(0, 216, 600, 447);
        contentPane.add(panelTable);
        //panelTable.setLayout(null);

        panelBtn = new JPanel();
        panelBtn.setBackground(new Color(250, 250, 210));
        panelBtn.setBounds(298, 0, 302, 215);
        contentPane.add(panelBtn);
        panelBtn.setLayout(null);

        labels= new JLabel[type.getDeclaredFields().length];
        Field[] fields= type.getDeclaredFields();
        txtFields= new JTextField[fields.length];
        int priceComanda=0;
        for(int i=0;i<fields.length;i++)
        {
            if(!(type.getSimpleName().equals("Comanda")&&(fields[i].getName().equals("priceProduct")||fields[i].getName().equals("id"))))
            {
                labels[i]=new JLabel(fields[i].getName());
                txtFields[i]=new JTextField();
                txtFields[i].setBounds(130, 30+(i-priceComanda)*30, 160, 20);
                panelInfo.add(txtFields[i]);
                txtFields[i].setColumns(10);
                // System.out.println(labels[i].getText());
                labels[i].setBounds(40, 30+(i-priceComanda)*30, 80, 15);
                panelInfo.add(labels[i]);
            }
            else
            {
                priceComanda=1;
            }
        }
        table= new JTable();
        panelTable.add(new JScrollPane(table));
        computeButtons(type);
        this.setVisible(true);
    }

    /**
     * Getter pentru varibila- instanta type de tipul Class
     * @return varibila-instanta "type" de tipul Class
     */
    public Class getClassType() {
        return type;
    }

    /**
     * Creeaza, folosind Java Reflection, un model de tabel cu coloane ce reflecta numele field-urilor de tipul T, si linii ce contin datele corespunzatoare indicilor din lista de obiecte trimisa ca parametru.
     * @param objects lista de obiecte
     * @param <T> tip generic al listei de obiecte
     * @return obiect de tipul DefaultTabelModel
     */
    public <T>DefaultTableModel genericTableBuilder(List<T> objects)
    {
        Class type=objects.get(0).getClass();
        Field[] fields=objects.get(0).getClass().getDeclaredFields();
        String[] columns = new String[fields.length];
        for(int i=0;i<fields.length;i++)
        {
            columns[i]=fields[i].getName();
        }
        Object[] row = new Object[fields.length];
        DefaultTableModel model= new DefaultTableModel(columns,0);
        System.out.println(objects.size());
        for (int j=0;j<objects.size();j++)
        {
            for(int i=0;i<fields.length;i++)
            {
                try {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fields[i].getName(),type);
                    Method getter= propertyDescriptor.getReadMethod();
                    Object cellValue=getter.invoke(objects.get(j));
                    row[i]=String.valueOf(cellValue);

                } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(row);
        }
        return model;
    }

    /**
     * Metoda pentru adaugarea unei instante a clasei ActionListener butonului Insert.
     * @param actionListener
     */
    public void addInsertListener(ActionListener actionListener)
    {
        buttons[1].addActionListener(actionListener);
    }
    /**
     * Metoda pentru adaugarea unei instante a clasei ActionListener butonului Update.
     * @param actionListener
     */
    public void addUpdateListener(ActionListener actionListener)
    {
        if(!type.getSimpleName().equals("Comanda"))
            buttons[2].addActionListener(actionListener);
    }
    /**
     * Metoda pentru adaugarea unei instante a clasei ActionListener butonului Delete.
     * @param actionListener
     */
    public void addDeleteListener(ActionListener actionListener)
    {
        if(!type.getSimpleName().equals("Comanda"))
            buttons[3].addActionListener(actionListener);
    }
    /**
     * Metoda pentru adaugarea unei instante a clasei ActionListener butonului View All.
     * @param actionListener
     */
    public void addViewAllListener(ActionListener actionListener)
    {
        buttons[0].addActionListener(actionListener);
    }

    public static void adjustColumnWidth(JTable table)
    {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < table.getColumnCount(); i++) {
            DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
            TableColumn col = colModel.getColumn(i);
            int width = 0;

            TableCellRenderer renderer = col.getHeaderRenderer();
            for (int r = 0; r < table.getRowCount(); r++) {
                renderer = table.getCellRenderer(r, i);
                Component comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, i), false, false, r, i);
                width = Math.max(width, comp.getPreferredSize().width);
            }
            col.setPreferredWidth(width + 10);
        }
    }

    /**
     * Adauga butoane un anumit numar de butoane la GUI in functie de instanta clasei Class trimisa ca parametru
     * @param type obiect de tipul Class
     */
    public void computeButtons(Class type)
    {
        if(type.getSimpleName().equals("Comanda"))
        {
            buttons= new JButton[2];
            buttons[1]= new JButton("Place Order");
            buttons[1].setBounds(80, 50, 180, 25);
            buttons[1].setBackground(new Color(255, 255, 240));
            panelBtn.add(buttons[1]);

            buttons[0]=new JButton("View Order History");
            buttons[0].setBounds(80, 80, 180, 25);
            buttons[0].setBackground(new Color(255, 255, 240));
            panelBtn.add(buttons[0]);
        }
        else
        {
            buttons= new JButton[4];

            buttons[1]= new JButton("Insert "+type.getSimpleName());
            buttons[1].setBounds(80, 30, 180, 30);
            buttons[1].setBackground(new Color(255, 255, 240));
            panelBtn.add(buttons[1]);

            buttons[2]=new JButton("Update "+type.getSimpleName());
            buttons[2].setBounds(80, 70, 180, 30);
            buttons[2].setBackground(new Color(255, 255, 240));
            panelBtn.add(buttons[2]);

            buttons[3]=new JButton("Delete "+type.getSimpleName());
            buttons[3].setBounds(80, 110, 180, 30);
            buttons[3].setBackground(new Color(255, 255, 240));
            panelBtn.add(buttons[3]);

            buttons[0]=new JButton("View all "+type.getSimpleName()+"s");
            buttons[0].setBounds(80, 150, 180, 30);
            buttons[0].setBackground(new Color(255, 255, 240));
            buttons[0].setForeground(new Color(139, 0, 139));
            panelBtn.add(buttons[0]);
        }
    }

    /**
     * Preia textul introdus de utilizator in JTextField-urile din interfata (sub forma de obiecte de tip String) si creeaza o lista cu obiectele de tip String obtinute.
     * @return lista cu obiecte de tipul String
     */
    public List<String> getText()
    {
        List<String> values=new ArrayList<String>();
        int indexPrice=-1;
        int indexID=-1;
        if(type.getSimpleName().equals("Comanda"))
        {
            Field[] fields= type.getDeclaredFields();
            for(int i=0;i<fields.length;i++)
            {
                if(fields[i].getName().equals("priceProduct"))
                    indexPrice=i;
                else if(fields[i].getName().equals("id"))
                    indexID=i;
            }

        }
        for(int i=0;i<type.getDeclaredFields().length;i++)
        {
            if(i==indexPrice || i==indexID)
                values.add("-1");
            else
                values.add(txtFields[i].getText());
        }
        return values;
    }

    /**
     * Seteaza modelul variabilei-instanta de tipul JTable, apeland metoda "genericTableBuilder(objects)"
     * @param objects lista de obiecte
     * @param <T> tip generic al listei de obiecte
     */
    public <T> void generateTable(List<T> objects)
    {
        table.setModel(genericTableBuilder(objects));
        table.repaint();
        table.revalidate();
        //adjustColumnWidth(table);
    }

}
