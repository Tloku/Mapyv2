package maps;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupOfMapsWindowDialog extends JDialog
{

    private final String DEFAULT_NAME = "Nazwa domyslna";


    JLabel groupNameLabel = new JLabel("Nazwa grupy");
    JLabel groupTypeLabel = new JLabel("Typ grupy");
    JTextField groupNameTextField = new JTextField("");
    JTextField groupTypeTextField = new JTextField("");

    JButton newMapButton = new JButton("Dodaj nową mapę");
    JButton modifyMapButton = new JButton("Modyfikuj mapę");
    JButton deleteMapButton = new JButton("Usuń mapę");
    JButton saveToFileButton = new JButton("Zapisz do pliku");
    JButton readFromFileButton = new JButton("Wczytaj osobę z pliku");

    private GroupOfMaps currentGroup;
    JPanel panel = new JPanel();
    JMenuBar menuBar = new JMenuBar();
    JMenu sortMenu = new JMenu("Sortuj");
    JMenu changeMapMenu = new JMenu("Mapa");
    JMenu changeGroupMenu = new JMenu("Grupa");
    JMenuItem addNewMapMenu = new JMenuItem("Dodaj mape: ");
    JMenuItem addModifyMapMenu = new JMenuItem("Modyfikuj: ");
    JMenuItem deleteMapMenu = new JMenuItem("Usuń");
    JMenuItem saveToFileMapMenu = new JMenuItem("Zapisz do pliku");
    JMenuItem readFromFileMapMenu = new JMenuItem("Wczytaj z pliku");

    JMenuItem sortByMapNamesMenu = new JMenuItem("Sortuj wg nazw map");
    JMenuItem sortByMapHeightMenu = new JMenuItem("Sortuj wg wysokości");
    JMenuItem sortByMapWidthMenu = new JMenuItem("Sortuj wg szerokości");
    JMenuItem sortByMapPrizeMenu = new JMenuItem("Sortuj wg ceny");
    JMenuItem sortByMapPublisherMenu = new JMenuItem("Sortuj wg wydawcy");
    JMenuItem sortByMapScaleMenu = new JMenuItem("Sortuj wg skali");

    JMenuItem changeGroupNameMenu = new JMenuItem("Zmnień nazwe grupy");
    JMenuItem changeGroupTypeMenu = new JMenuItem("Zmnień typ grupy");

    ViewGroupOfMaps viewGroupOfMaps;


    public static void main(String[] args)
    {
        try
        {
            GroupOfMaps currentGroup = new GroupOfMaps("Grupa testowa", GroupType.VECTOR);
            currentGroup.addToGroupCollection(new Map("przykład", "20", "20", "14.3"));

            new GroupOfMapsWindowDialog(null, currentGroup);
        }
        catch (MapException e)
        {
            e.printStackTrace();
        }
    }


    public GroupOfMapsWindowDialog(Window parent, GroupOfMaps iterator)
    {
        super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
        initComponents(iterator);
    }

    private static String enterGroupName(Window parent)
    {
        return JOptionPane.showInputDialog(parent, "Podaj nazwę dla tej grupy");
    }

    private static GroupType chooseGroupType(Window parent, GroupType currentType)
    {
        Object[] groupTypeValues = GroupType.values();
        GroupType type = (GroupType)JOptionPane.showInputDialog(parent, "Wybierz typ kolekcji", "Zmień typ kolekcji", 3, null, groupTypeValues, currentType);
        return type;
    }

    public static void changeGroupData(GroupManagerApp parent, GroupOfMaps group)
    {
        new GroupOfMapsWindowDialog(parent, group);
    }

    public static GroupOfMaps createNewGroupOfMaps(GroupManagerApp parent) throws MapException
    {
        String name = enterGroupName(parent);

        GroupOfMaps newMapGroup;

        if((name == null) || (name.equals("")))
            return null;

        GroupType type = chooseGroupType(parent, null);

        if(type == null)
            return null;

        try
        {
            newMapGroup = new GroupOfMaps(name, type);
        }
        catch (MapException e)
        {
            throw new MapException("Nie udalo się utworzyć nowej grupy");
        }

        GroupOfMapsWindowDialog dialog = new GroupOfMapsWindowDialog(parent, newMapGroup);
        return dialog.currentGroup;
    }

    private void initComponents(GroupOfMaps group)
    {
        this.currentGroup = group;
        setPanelAndButtons();
        setBars();
        this.setDefaultCloseOperation(2);
        this.setSize(450,450);
        this.setResizable(false);
        this.setLocationRelativeTo(getParent());
        this.setVisible(true);
    }

    private void setPanelAndButtons()
    {
        viewGroupOfMaps = new ViewGroupOfMaps(currentGroup, 400, 250);

        panel.add(groupNameLabel);
        panel.add(groupNameTextField);
        panel.add(groupTypeLabel);
        panel.add(groupTypeTextField);
        panel.add(viewGroupOfMaps);
        panel.add(newMapButton);
        panel.add(modifyMapButton);
        panel.add(deleteMapButton);
        panel.add(saveToFileButton);
        panel.add(readFromFileButton);

        groupNameTextField.setEditable(false);
        groupTypeTextField.setEditable(false);
        groupNameTextField.setText(currentGroup.getGroupName());
        groupTypeTextField.setText(currentGroup.getGroupType().toString());

        this.getContentPane().add(panel);

        viewGroupOfMaps.refreshView();

        newMapButton.addActionListener(e-> addNewMapActionListener());
        modifyMapButton.addActionListener(e-> addModifyMapActionListener());
        deleteMapButton.addActionListener(e-> deleteMapActionListener());
        saveToFileButton.addActionListener(e-> saveToFileActionListener());
        readFromFileButton.addActionListener(e-> readFromFileActionListener());
    }


    private void setBars()
    {
        this.setJMenuBar(menuBar);
        menuBar.add(changeMapMenu);
        menuBar.add(sortMenu);
        menuBar.add(changeGroupMenu);

        changeMapMenu.add(addNewMapMenu);
        changeMapMenu.add(addModifyMapMenu);
        changeMapMenu.add(deleteMapMenu);
        changeMapMenu.add(saveToFileMapMenu);
        changeMapMenu.add(readFromFileMapMenu);

        sortMenu.add(sortByMapNamesMenu);
        sortMenu.add(sortByMapHeightMenu);
        sortMenu.add(sortByMapWidthMenu);
        sortMenu.add(sortByMapPrizeMenu);
        sortMenu.add(sortByMapPublisherMenu);
        sortMenu.add(sortByMapScaleMenu);

        changeGroupMenu.add(changeGroupNameMenu);
        changeGroupMenu.add(changeGroupTypeMenu);

        addNewMapMenu.addActionListener(e-> addNewMapActionListener());
        addModifyMapMenu.addActionListener(e-> addModifyMapActionListener());
        deleteMapMenu.addActionListener(e-> deleteMapActionListener());
        saveToFileMapMenu.addActionListener(e-> saveToFileActionListener());
        readFromFileMapMenu.addActionListener(e-> readFromFileActionListener());

        sortByMapNamesMenu.addActionListener(e-> sortByMapNameActionListener());
        sortByMapHeightMenu.addActionListener(e-> sortByMapHeightActionListener());
        sortByMapWidthMenu.addActionListener(e-> sortByMapWidthActionListener());
        sortByMapPrizeMenu.addActionListener(e-> sortByMapPrizeActionListener());
        sortByMapPublisherMenu.addActionListener(e-> sortByMapPublisherActionListener());
        sortByMapScaleMenu.addActionListener(e-> sortByMapScaleActionListener());

        changeGroupNameMenu.addActionListener(e-> {
            try
            {
                changeGroupNameActionListener();
            }
            catch (MapException ex)
            {
                ex.getMessage();
            }
        });
        changeGroupTypeMenu.addActionListener(e-> {
            try
            {
                changeGroupTypeActionListener();
            }
            catch (MapException ex)
            {
                ex.getMessage();
            }
        });
    }

    private void addNewMapActionListener()
    {
        Map newMap = MapWindowDialog.createNewMap(this);
        if(newMap != null)
            currentGroup.addToGroupCollection(newMap);
    }

    private void addModifyMapActionListener()
    {
        int index = viewGroupOfMaps.getSelectedIndex();
        if(index >= 0)
        {
            Iterator<Map> iterator = currentGroup.iterator();
            while(index-- > 0)
                iterator.next();

            MapWindowDialog.changeMapData(this, (Map)iterator.next());
        }
    }

    private void deleteMapActionListener()
    {
        int index = viewGroupOfMaps.getSelectedIndex();
        if(index >= 0) {
            Iterator<Map> iterator = currentGroup.iterator();
            while (index-- >= 0)
                iterator.next();

            iterator.remove();
        }
    }

    private void saveToFileActionListener()
    {
        int index = viewGroupOfMaps.getSelectedIndex();

        if(index >= 0)
        {
            Iterator<Map> iterator = currentGroup.iterator();
            while(index-- > 0)
                iterator.next();

            Map map = (Map)iterator.next();
            try {
                Map.printToFile(map, "WczytywanieMapyDoGrupy.txt");
            } catch (MapException ex) {
                ex.getMessage();
            }
        }
    }

    private void readFromFileActionListener()
    {
        try{
            Map map = Map.readFromFile("WczytywanieMapyDoGrupy.txt");
            currentGroup.addToGroupCollection(map);
        }
        catch(MapException ex)
        {
            ex.getMessage();
        }
    }


    private void sortByMapNameActionListener()
    {
        try {
            currentGroup.sortByMapName();
        }
        catch (MapException ex) {
            ex.getMessage();
        }
    }

    private void sortByMapHeightActionListener()
    {
        try {
            currentGroup.sortByMapHeight();
        }
        catch (MapException ex) {
            ex.getMessage();
        }
    }

    private void sortByMapWidthActionListener()
    {
        try {
            currentGroup.sortByMapWidth();
        }
        catch (MapException ex) {
            ex.getMessage();
        }
    }

    private void sortByMapPrizeActionListener()
    {
        try {
            currentGroup.sortByMapPrize();
        }
        catch (MapException ex) {
            ex.getMessage();
        }
    }

    private void sortByMapPublisherActionListener()
    {
        try {
            currentGroup.sortByMapPublisher();
        }
        catch (MapException ex) {
            ex.getMessage();
        }
    }

    private void sortByMapScaleActionListener()
    {
        try {
            currentGroup.sortByMapScale();
        }
        catch (MapException ex) {
            ex.getMessage();
        }
    }

    private void changeGroupNameActionListener() throws MapException {
        String newName = enterGroupName(this);
        if(newName == null)
        {
            currentGroup.setGroupName(DEFAULT_NAME);
            groupNameTextField.setText(DEFAULT_NAME);
            throw new MapException("Podana nazwa grupy jest pusta!");
        }

        currentGroup.setGroupName(newName);
        groupNameTextField.setText(newName);
    }

    private void changeGroupTypeActionListener() throws MapException
    {
        GroupType type = chooseGroupType(this, currentGroup.getGroupType());

        if(type == null)
        {
            currentGroup.setGroupType(GroupType.ARRAY_LIST);
            groupTypeTextField.setText(GroupType.ARRAY_LIST.toString());
            throw new MapException("Podany typ grupy jest nieprawidłowy! Zostanie ustawiony domyślny typ grupy (Array_list)");
        }

        currentGroup.setGroupType(type);
        groupTypeTextField.setText(type.toString());
    }
}

class ViewGroupOfMaps extends JScrollPane
{
    private GroupOfMaps groupOfMaps;
    private JTable table;
    private DefaultTableModel tableModel;
    String[] columnNames = {"Nazwa mapy", "Wysokość", "Szerokość", "Skala", "Wydawnictwo", "Cena"};

    public ViewGroupOfMaps(GroupOfMaps groupOfMaps, int width, int height)
    {
        this.groupOfMaps = groupOfMaps;
        setPreferredSize(new Dimension(width,height));
        setBorder(BorderFactory.createTitledBorder("Lista map"));
        tableModel =new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel)
        {
            public boolean isCellEditable(int rowIndex, int colIndex)
            {
                return false;
            }
        };

        table.setSelectionMode(0);
        table.setRowSelectionAllowed(true);
        setViewportView(table);
    }

    void refreshView()
    {
        tableModel.setRowCount(0);
        for(Map map : groupOfMaps)
        {
            String[] row = {
                    map.getName(), String.valueOf(map.getHeight()), String.valueOf(map.getWidth()), map.getScale().toString(), map.getPublisher().toString(), String.valueOf(map.getPrize())
            };
            tableModel.addRow(row);
        }
    }

    int getSelectedIndex()
    {
        int index = table.getSelectedRow();

        if(index < 0)
            JOptionPane.showMessageDialog(this, "Zadna mapa nie jest zaznaczona", "Błąd", 0);
        else
            return index;

        return 0;
    }
}