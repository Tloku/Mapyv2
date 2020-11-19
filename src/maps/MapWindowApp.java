package maps;

import javax.swing.*;

/**
 * Zawartość: Klasa MapWindowApp
 * Autor: Dominik Tłokiński
 * Nr indeksu: 252689
 * Data: październik 2020 r.
 */


public class MapWindowApp extends JFrame {
    private static final String ABOUT_APP_MESSAGE =
            "Program Map - wersja okienkowa\n" +
                    "Autor: Dominik Tłokiński\n" +
                    "Data: październik 2020 r.\n";



    public Map currentMap;

    JButton newButton = new JButton("Nowa mapa");
    JButton modifyButton = new JButton("Zmodyfikuj");
    JButton saveToFileButton = new JButton("Zapisz do pliku");
    JButton printFromFileButton = new JButton("Wczytaj z pliku");
    JButton deleteButton = new JButton("Usuń");
    JButton authorInfoButton = new JButton("O autorze");
    JButton exitButton = new JButton("Wyjdź");
    JButton saveToBinaryFileButton  = new JButton("Zapisz do pliku binarnego");
    JButton printFromBinaryFileButton  = new JButton("Wczytaj z pliku binarnego");

    JLabel mapNameLabel = new JLabel("Nazwa mapy");
    JLabel mapWidthLabel = new JLabel("Szerokość");
    JLabel mapHeightLabel = new JLabel("Wysokość");
    JLabel mapScaleLabel = new JLabel("Skala");
    JLabel mapPublisherLabel = new JLabel("Wydawca");
    JLabel mapPrizeLabel = new JLabel("Cena");

    static JTextField mapNameTextField = new JTextField(10);
    static JTextField mapWidthTextField = new JTextField(10);
    static JTextField mapHeightTextField = new JTextField(10);
    static JTextField mapScaleTextField = new JTextField(10);
    static JTextField mapPublisherTextField = new JTextField(10);
    static JTextField mapPrizeTextField = new JTextField(10);

    JMenuBar menuBar = new JMenuBar();
    JMenu mapBar = new JMenu("Mapy");
    JMenu fileBar = new JMenu("Plik");
    JMenu infoBar = new JMenu("Info");
    JMenu exitBar = new JMenu("Wyjście");
    JMenuItem newMapBar = new JMenuItem("Nowa");
    JMenuItem modifyMapBar = new JMenuItem("Zmodyfikuj");
    JMenuItem deleteMapBar = new JMenuItem("Usuń");
    JMenuItem saveMapBar = new JMenuItem("Zapisz");
    JMenuItem saveBinaryMapBar = new JMenuItem("Zapisz binarnie");
    JMenuItem printMapBar = new JMenuItem("Wczytaj");
    JMenuItem printBinaryMapBar = new JMenuItem("Wczytaj z binarnego");
    JMenuItem authorInfoBar = new JMenuItem("O autorze");
    JMenuItem appInfoBar = new JMenuItem("O aplikacji");
    JMenuItem exitItemBar = new JMenuItem("Wyjdź");


    public MapWindowApp() {
        initComponents();
    }

    public static void main(String[] args) {
        new MapWindowApp();
    }

    private void initComponents() {
        this.setTitle("Okienkowe Mapy");

        addButtonsLabelsAndTextFields();
        setBars();
        this.pack();
        this.setJMenuBar(menuBar);
        this.setLocationRelativeTo(null);
        this.setSize(400, 400);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
    }


    public void addButtonsLabelsAndTextFields() {
        newButton.addActionListener(e -> newButtonActionListener());
        modifyButton.addActionListener(e -> modifyButtonActionListener());
        saveToFileButton.addActionListener(e -> saveButtonActionListener());
        saveToBinaryFileButton.addActionListener(e -> saveBinaryButtonActionListener());
        printFromFileButton.addActionListener(e -> printButtonActionListener());
        printFromBinaryFileButton.addActionListener(e -> printBinaryButtonActionListener());
        deleteButton.addActionListener(e -> deleteButtonActionListener());
        authorInfoButton.addActionListener(e -> authorButtonActionListener());
        exitButton.addActionListener(e -> exitButtonActionListener());

        showCurrentMap();

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(
                        layout.createParallelGroup()
                                .addComponent(mapNameLabel).addComponent(mapWidthLabel).addComponent(mapHeightLabel).addComponent(mapScaleLabel)
                                .addComponent(mapPublisherLabel).addComponent(mapPrizeLabel)
                                .addComponent(newButton).addComponent(saveToFileButton).addComponent(deleteButton).addComponent(saveToBinaryFileButton).addComponent(exitButton)
                )
                .addGroup(
                        layout.createParallelGroup()
                                .addComponent(mapNameTextField).addComponent(mapWidthTextField).addComponent(mapHeightTextField).addComponent(mapScaleTextField)
                                .addComponent(mapPublisherTextField).addComponent(mapPrizeTextField)
                                .addComponent(modifyButton).addComponent(printFromFileButton).addComponent(printFromBinaryFileButton).addComponent(authorInfoButton)
                )
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(
                        layout.createParallelGroup().addComponent(mapNameLabel).addComponent(mapNameTextField)
                )
                .addGroup(
                        layout.createParallelGroup().addComponent(mapWidthLabel).addComponent(mapWidthTextField)
                )
                .addGroup(
                        layout.createParallelGroup().addComponent(mapHeightLabel).addComponent(mapHeightTextField)
                )
                .addGroup(
                        layout.createParallelGroup().addComponent(mapScaleLabel).addComponent(mapScaleTextField)
                )
                .addGroup(
                        layout.createParallelGroup().addComponent(mapPublisherLabel).addComponent(mapPublisherTextField)
                )
                .addGroup(
                        layout.createParallelGroup().addComponent(mapPrizeLabel).addComponent(mapPrizeTextField)
                )
                .addGroup(
                        layout.createParallelGroup().addComponent(newButton).addComponent(modifyButton)
                )
                .addGroup(
                        layout.createParallelGroup().addComponent(saveToFileButton).addComponent(printFromFileButton)
                )
                .addGroup(
                        layout.createParallelGroup().addComponent(deleteButton).addComponent(authorInfoButton)
                )
                .addGroup(
                        layout.createParallelGroup().addComponent(saveToBinaryFileButton).addComponent(printFromBinaryFileButton)
                )
                .addComponent(exitButton)
        );

        mapNameTextField.setEditable(false);
        mapWidthTextField.setEditable(false);
        mapHeightTextField.setEditable(false);
        mapScaleTextField.setEditable(false);
        mapPublisherTextField.setEditable(false);
        mapPrizeTextField.setEditable(false);
    }

    public void setBars() {
        menuBar.add(mapBar);
        menuBar.add(fileBar);
        menuBar.add(infoBar);
        menuBar.add(exitBar);

        mapBar.add(newMapBar);
        mapBar.add(modifyMapBar);
        mapBar.add(deleteMapBar);

        fileBar.add(saveMapBar);
        fileBar.add(printMapBar);
        fileBar.add(saveBinaryMapBar);
        fileBar.add(printBinaryMapBar);

        infoBar.add(authorInfoBar);
        infoBar.add(appInfoBar);
        exitBar.add(exitItemBar);

        newMapBar.addActionListener(e -> newButtonActionListener());
        modifyMapBar.addActionListener(e -> modifyButtonActionListener());
        deleteMapBar.addActionListener(e -> deleteButtonActionListener());
        saveMapBar.addActionListener(e -> saveButtonActionListener());
        saveBinaryMapBar.addActionListener(e -> saveBinaryButtonActionListener());
        printMapBar.addActionListener(e -> printButtonActionListener());
        printBinaryMapBar.addActionListener(e -> printBinaryButtonActionListener());
        authorInfoBar.addActionListener(e -> authorButtonActionListener());
        appInfoBar.addActionListener(e -> appButtonActionListener());
        exitItemBar.addActionListener(e -> exitButtonActionListener());

        mapBar.setMnemonic('m');
        newMapBar.setMnemonic('n');
        modifyMapBar.setMnemonic('o');
        deleteMapBar.setMnemonic('u');
        saveMapBar.setMnemonic('s');
        saveBinaryMapBar.setMnemonic('b');
        printMapBar.setMnemonic('w');
        printBinaryMapBar.setMnemonic('g');
        infoBar.setMnemonic('i');
        authorInfoBar.setMnemonic('a');
        appInfoBar.setMnemonic('p');
        exitBar.setMnemonic('y');
        exitItemBar.setMnemonic('j');
        fileBar.setMnemonic('k');

        newMapBar.setToolTipText("Otwiera nowe okno do stworzenia mapy");
        modifyMapBar.setToolTipText("Otwiera nowe okno do modyfikacji mapy");
        deleteMapBar.setToolTipText("Usuwa aktualną mapę");
        saveMapBar.setToolTipText("Zapisuje aktualną mapę do pliku");
        saveBinaryMapBar.setToolTipText("Zapisuje aktualną mapę do pliku binarnego");
        printMapBar.setToolTipText("Wczytuje aktualną mapę z pliku");
        printBinaryMapBar.setToolTipText("Wczytuje aktualną mapę z pliku binarnego");
        authorInfoBar.setToolTipText("Informacje o autorze");
        appInfoBar.setToolTipText("Informacje o aplikacji");
        exitItemBar.setToolTipText("Zamyka aplikacje");
    }

    public void showCurrentMap()
    {
        if(currentMap == null)
        {
            mapNameTextField.setText("");
            mapWidthTextField.setText("");
            mapHeightTextField.setText("");
            mapScaleTextField.setText("");
            mapPublisherTextField.setText("");
            mapPrizeTextField.setText("");
        }
        else{
            mapNameTextField.setText(currentMap.getName());
            mapWidthTextField.setText(String.valueOf(currentMap.getWidth()));
            mapHeightTextField.setText(String.valueOf(currentMap.getHeight()));
            mapHeightTextField.setText(String.valueOf(currentMap.getHeight()));
            mapScaleTextField.setText(currentMap.getScale().toString());
            mapPublisherTextField.setText(currentMap.getPublisher().toString());
            mapPrizeTextField.setText(String.valueOf(currentMap.getPrize()));
        }
    }

    void newButtonActionListener()
    {
        currentMap = MapWindowDialog.createNewMap(this);
    }

    void modifyButtonActionListener()
    {
        if (currentMap == null)
            JOptionPane.showMessageDialog(rootPane, "Nie ma obiektu do zmodyfikowania");
        else
            MapWindowDialog.changeMapData(this, currentMap);
    }

   void saveButtonActionListener(){
        String fileName = "";
        while (fileName == null || fileName.equals(""))
            fileName = JOptionPane.showInputDialog("Podaj nazwe pliku, do którego chcesz zapisać obiekt");

        try {
            if (currentMap == null)
                JOptionPane.showMessageDialog(rootPane, "Nie ma mapy do zapisania!");

            Map.printToFile(currentMap, fileName);
        } catch(MapException e) {
            JOptionPane.showMessageDialog(rootPane, "Nie udało się wczytać do pliku");
       }
    }

    void saveBinaryButtonActionListener()
    {
        if(currentMap == null)
            JOptionPane.showMessageDialog(rootPane,"Nie ma mapy do zapisania!");

        Map.printToBinaryFile(currentMap);
    }

    void printButtonActionListener()
    {
        String fileName = "";
        while (fileName == null || fileName.equals(""))
            fileName = JOptionPane.showInputDialog("Podaj nazwe pliku, z którego chcesz wpisać obiekt");

        try {
            currentMap = Map.readFromFile(fileName);
        } catch (MapException ex) {
            JOptionPane.showMessageDialog(rootPane, "Nie udało się odczytać pliku");
        }
    }

    void printBinaryButtonActionListener()
    {
        currentMap = Map.readFromBinaryFile();
    }

    void deleteButtonActionListener()
    {
        currentMap = null;
        setTextFieldsNull();
    }

    void authorButtonActionListener()
    {
        JOptionPane.showMessageDialog(rootPane, MapConsoleApp.AUTHOR);
    }

    void appButtonActionListener()
    {
        JOptionPane.showMessageDialog(rootPane, ABOUT_APP_MESSAGE);
    }

    void exitButtonActionListener()
    {
        System.exit(0);
    }

    static void setTextFields(Map map)
    {
        mapNameTextField.setText(map.getName());
        mapWidthTextField.setText(String.valueOf(map.getWidth()));
        mapHeightTextField.setText(String.valueOf(map.getHeight()));
        mapScaleTextField.setText(String.valueOf(map.getScale()));
        mapPublisherTextField.setText(String.valueOf(map.getPublisher()));
        mapPrizeTextField.setText(String.valueOf(map.getPrize()));
    }
    static void setTextFieldsNull()
    {
        mapNameTextField.setText("");
        mapWidthTextField.setText("");
        mapHeightTextField.setText("");
        mapScaleTextField.setText("");
        mapPublisherTextField.setText("");
        mapPrizeTextField.setText("");
    }

}
