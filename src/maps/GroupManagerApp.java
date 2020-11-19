package maps;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Zawartość: Klasa GroupManagerApp i ViewGroupList
 * Autor: Dominik Tłokiński
 * Nr indeksu: 252689
 * Data: listopad 2020 r.
 */

public class GroupManagerApp extends JFrame
{
    private static final String ALL_GROUPS_FILE = "Lista_grup.bin";
    private static final String GREETING_MESSAGE =
            "Program do zarządzania grupami osób - wersja okienkowa\n\n" +
                    "Autor: Dominik Tłokiński\n" +
                    "Nr indeksu: 252689\n" +
                    "Data:  listopad 2020 r.\n";


    public JButton newGroupButton = new JButton("Nowa grupa");
    public JButton modifyGroupObjectButton = new JButton("Modyfikuj");
    public JButton deleteObjectButton = new JButton("Usuń");
    public JButton saveToBinaryButton = new JButton("Zapisz(Binarnie)");
    public JButton readFromBinaryButton = new JButton("Wczytaj(Binarnie)");
    public JButton saveToFileButton = new JButton("Zapisz do pliku");
    public JButton readFromFileButton = new JButton("Wczytaj z pliku");

    JMenuBar menuBar = new JMenuBar();
    JMenu changeMenu = new JMenu("Grupa");
    JMenu saveReadMenu = new JMenu("Zapisz/wczytaj");
    JMenu aboutAuthorMenu = new JMenu("O programie");

    JMenuItem newGroupMenu = new JMenuItem("Utwórz grupe");
    JMenuItem modifyGroupObjectMenu = new JMenuItem("Zmodyfikuj grupe");
    JMenuItem deleteObjectMenu = new JMenuItem("Usuń grupe");
    JMenuItem saveToBinaryMenu = new JMenuItem("Zapisz do pliku binarnego");
    JMenuItem readFromBinaryMenu = new JMenuItem("Wczytaj z pliku binarnego");
    JMenuItem saveToFileMenu = new JMenuItem("Zapisz do pliku");
    JMenuItem readFromFileMenu = new JMenuItem("Wczytaj z pliku");
    JMenuItem aboutMeMenu = new JMenuItem("O autorze");

    ViewGroupList viewGroupList;

    private List<GroupOfMaps> currentList = new ArrayList<GroupOfMaps>();

    public static void main(String[] args)
    {
        new GroupManagerApp();
    }

    public GroupManagerApp()
    {
        setTitle("Zarządzanie grupami osób");
        addButtons();
        setMenuBar();
        viewGroupList.refreshView();
        pack();
        setLocationRelativeTo(null);
        setSize(600, 600);
        setResizable(false);
        setDefaultCloseOperation(3);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowClosed(e);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                try
                {
                    saveGroupListToFile(ALL_GROUPS_FILE);
                    JOptionPane.showMessageDialog(null, "Dane zostały zapisane do pliku: " + ALL_GROUPS_FILE);
                }
                catch (MapException ex) {
                    ex.getMessage();
                }
            }
        });

        try
        {
            loadGroupListFromFile();
            JOptionPane.showMessageDialog(null, "Dane zostały wczytane z pliku " + ALL_GROUPS_FILE);
        }
        catch (MapException e)
        {
            e.getMessage();
        }

        setVisible(true);
    }

    private void addButtons()
    {
        viewGroupList = new ViewGroupList(currentList, 400, 250);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(viewGroupList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(newGroupButton).addComponent(modifyGroupObjectButton).addComponent(readFromFileButton).addComponent(deleteObjectButton)
                        )
                        .addGroup(
                                layout.createParallelGroup()
                                    .addComponent(saveToBinaryButton).addComponent(readFromBinaryButton).addComponent(saveToFileButton)
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(viewGroupList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(
                                layout.createParallelGroup().addComponent(newGroupButton).addComponent(saveToBinaryButton)
                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(modifyGroupObjectButton).addComponent(readFromBinaryButton)
                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(readFromFileButton).addComponent(saveToFileButton)
                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(deleteObjectButton)
                        )

        );

        newGroupButton.addActionListener(e -> {
            try {
                newGroupActionListener();
            } catch (MapException ex) {
                JOptionPane.showMessageDialog(rootPane, "Nie udalo się utworzyc grupy");
            }
        });
        modifyGroupObjectButton.addActionListener(e -> modifyGroupActionListener());
        deleteObjectButton.addActionListener(e -> deleteGroupActionListener());
        saveToBinaryButton.addActionListener(e -> saveToBinaryActionListener());
        readFromBinaryButton.addActionListener(e -> readFromBinaryActionListener());
        saveToFileButton.addActionListener(e -> saveToFileActionListener());
        readFromFileMenu.addActionListener(e -> readFromFileActionListener());
    }

    private void setMenuBar()
    {
        this.setJMenuBar(menuBar);
        menuBar.add(changeMenu);
        menuBar.add(saveReadMenu);
        menuBar.add(aboutAuthorMenu);

        changeMenu.add(newGroupMenu);
        changeMenu.add(modifyGroupObjectMenu);
        changeMenu.add(deleteObjectMenu);

        saveReadMenu.add(saveToBinaryMenu);
        saveReadMenu.add(readFromBinaryMenu);
        saveReadMenu.add(saveToFileMenu);
        saveReadMenu.add(readFromFileMenu);

        aboutAuthorMenu.add(aboutMeMenu);

        newGroupMenu.addActionListener(e -> {
            try {
                newGroupActionListener();
            } catch (MapException ex) {
                JOptionPane.showMessageDialog(rootPane, "Nie udalo się utworzyc grupy");
            }
        });
        modifyGroupObjectMenu.addActionListener(e -> modifyGroupActionListener());
        deleteObjectMenu.addActionListener(e -> deleteGroupActionListener());
        saveToBinaryMenu.addActionListener(e -> saveToBinaryActionListener());
        readFromBinaryMenu.addActionListener(e -> readFromBinaryActionListener());
        saveToFileMenu.addActionListener(e -> saveToFileActionListener());
        readFromFileMenu.addActionListener(e -> readFromFileActionListener());
        aboutMeMenu.addActionListener(e -> aboutMeActionListener());
    }

    public void newGroupActionListener() throws MapException
    {
        GroupOfMaps group = GroupOfMapsWindowDialog.createNewGroupOfMaps(this);
        if(group != null)
            currentList.add(group);
    }

    public void modifyGroupActionListener()
    {
        int index = viewGroupList.getSelectedIndex();
        if(index >= 0)
        {
            Iterator<GroupOfMaps> iterator = currentList.iterator();
            while(index-- > 0)
                iterator.next();

           new GroupOfMapsWindowDialog(this, iterator.next());
        }
    }

    public void deleteGroupActionListener()
    {
        int index = viewGroupList.getSelectedIndex();
        if(index >= 0)
        {
            Iterator<GroupOfMaps> iterator = currentList.iterator();
            while (index-- >= 0)
                iterator.next();

            iterator.remove();
        }
    }

    public void saveToFileActionListener()
    {
        int index = viewGroupList.getSelectedIndex();

        if(index >= 0)
        {
            Iterator<GroupOfMaps> iterator = currentList.iterator();
            while(index-- > 0)
                iterator.next();
            GroupOfMaps group = iterator.next();

            try {
                GroupOfMaps.printToFile("Zapisywanie grup.txt", group);
            } catch (MapException e) {
                JOptionPane.showMessageDialog(rootPane, "Nie udało się zapisać do pliku");
            }
        }
    }

    public void readFromFileActionListener()
    {
        try
        {
            GroupOfMaps group = GroupOfMaps.readFromFile("Zapisywanie grup.txt");
            currentList.add(group);
        }
        catch(MapException e)
        {
            JOptionPane.showMessageDialog(rootPane, "Nie udało się odczytać z pliku");
        }
    }

    public void saveToBinaryActionListener()
    {
        int index = viewGroupList.getSelectedIndex();

        if(index >= 0)
        {
            Iterator<GroupOfMaps> iterator = currentList.iterator();
            while(index-- > 0)
                iterator.next();
            GroupOfMaps group = iterator.next();

            try {
                GroupOfMaps.printToBinaryFile(group);
            } catch (MapException e) {
                JOptionPane.showMessageDialog(rootPane, "Nie udało się zapisać do pliku");
            }
        }
    }

    public void readFromBinaryActionListener()
    {
        try
        {
            GroupOfMaps group = GroupOfMaps.readFromBinaryFile();
            currentList.add(group);
        }
        catch(MapException e)
        {
            JOptionPane.showMessageDialog(rootPane, "Nie udało się odczytać z pliku");
        }
    }

    private void aboutMeActionListener()
    {
        JOptionPane.showMessageDialog(rootPane, GREETING_MESSAGE);
    }

    void loadGroupListFromFile() throws MapException
    {
        try(ObjectInputStream inS = new ObjectInputStream(new FileInputStream(ALL_GROUPS_FILE)))
        {
            currentList = (List<GroupOfMaps>)inS.readObject();
        }
        catch(FileNotFoundException e)
        {
            throw new MapException("Nie udało się odczytać danych z pliku.");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    void saveGroupListToFile(String allGroupsFile) throws MapException
    {
        try(ObjectOutputStream outS = new ObjectOutputStream(new FileOutputStream(allGroupsFile)))
        {
            outS.writeObject(currentList);
        }
        catch(Exception e)
        {
            throw new MapException("Nie udało się wczytać danych do pliku.");
        }
    }

}

class ViewGroupList extends JScrollPane
{
    private List<GroupOfMaps> list;
    private JTable table;
    private DefaultTableModel tableModel;
    String[] columnNames = { "Nazwa grupy", "Typ kolekcji", "Liczba osób" };
    public ViewGroupList(List<GroupOfMaps> list, int width, int height)
    {
        this.list = list;

        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createTitledBorder("Lista grup"));

        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        setViewportView(table);
    }

    public void refreshView()
    {
        tableModel.setRowCount(0);

        for(GroupOfMaps group : list)
            if(group != null)
            {
                String[] row = {group.getGroupName(), group.getGroupType().toString(), String.valueOf(group.getGroupCollectionsize())};
                tableModel.addRow(row);
            }
    }

    public int getSelectedIndex()
    {
        int indexOfRow = table.getSelectedRow();
        if(indexOfRow < 0)
            JOptionPane.showMessageDialog(this, "Żadna grupa nie jest zaznaczona");

        return indexOfRow;
    }

}
