package maps;

import javax.swing.*;
import java.awt.*;

/**
 * Zawartość: Klasa MapWindowDialog
 * Autor: Dominik Tłokiński
 * Nr indeksu: 252689
 * Data: październik 2020 r.
 */


public class MapWindowDialog extends JDialog
{
    private Map map;
    MapWindowApp mapApp;

    JLabel mapNameLabel = new JLabel("Nazwa mapy");
    JLabel mapWidthLabel = new JLabel("Szerokość");
    JLabel mapHeightLabel = new JLabel("Wysokość");
    JLabel mapScaleLabel = new JLabel("Skala");
    JLabel mapPublisherLabel = new JLabel("Wydawca");
    JLabel mapPrizeLabel = new JLabel("Cena");

    JTextField mapNameTextField = new JTextField(10);
    JTextField mapWidthTextField = new JTextField(10);
    JTextField mapHeightTextField = new JTextField(10);
    JComboBox<Scale>  mapScaleJBox = new JComboBox<Scale>(Scale.values());
    JComboBox<Publisher>  mapPublisherJBox = new JComboBox<Publisher>(Publisher.values());
    JTextField mapPrizeTextField = new JTextField(10);

    JButton okButton = new JButton("Ok");
    JButton cancelButton = new JButton("Anuluj");


    MapWindowDialog(Window parent, Map map)
    {
        super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
        mapApp = (MapWindowApp)parent;
        initComponents(map);
        if(map != null)
            this.map = map;
    }

    MapWindowDialog(GroupOfMapsWindowDialog parent, Map map)
    {
        super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
        initComponents(map);
        if(map != null)
            this.map = map;
    }

    private void initComponents(Map map)
    {
        if(map == null)
        {
            this.setTitle("Nowa mapa");
        }
        else{
            this.setTitle(map.getName());
            mapNameTextField.setText(map.getName());
            mapWidthTextField.setText(String.valueOf(map.getWidth()));
            mapHeightTextField.setText(String.valueOf(map.getHeight()));
            mapScaleJBox.setSelectedItem(map.getScale());
            mapPublisherJBox.setSelectedItem(map.getPublisher());
            mapPrizeTextField.setText(String.valueOf(map.getPrize()));
        }

        okButton.addActionListener(e -> okButtonActionListener());
        cancelButton.addActionListener(e -> cancelButtonActionListener());

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(mapNameLabel).addComponent(mapWidthLabel).addComponent(mapHeightLabel).addComponent(mapScaleLabel)
                                        .addComponent(mapPublisherLabel).addComponent(mapPrizeLabel).addComponent(okButton)

                        )
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(mapNameTextField).addComponent(mapWidthTextField).addComponent(mapHeightTextField).addComponent(mapScaleJBox)
                                        .addComponent(mapPublisherJBox).addComponent(mapPrizeTextField)
                                        .addComponent(cancelButton)
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
                                layout.createParallelGroup().addComponent(mapScaleLabel).addComponent(mapScaleJBox)
                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(mapPublisherLabel).addComponent(mapPublisherJBox)
                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(mapPrizeLabel).addComponent(mapPrizeTextField)
                        )
                        .addGroup(
                                layout.createParallelGroup().addComponent(okButton).addComponent(cancelButton)
                        )
        );

        this.setSize(200,200);
        this.pack();
        this.setLocationRelativeTo(getParent());
        this.setDefaultCloseOperation(2);
        this.setVisible(true);
    }



    public static Map createNewMap(Window parent)
    {
       MapWindowDialog dialog = new MapWindowDialog(parent, null);
       return dialog.map;
    }

    public static Map createNewMap(GroupOfMapsWindowDialog parent)
    {
        MapWindowDialog dialog = new MapWindowDialog(parent, null);
        return dialog.map;
    }


    public static void changeMapData(Window parent, Map map)
    {
        new MapWindowDialog(parent, map);
    }

    public void okButtonActionListener()
    {
        try{
            if(map == null)
                map = new Map(mapNameTextField.getText(), mapHeightTextField.getText(), mapWidthTextField.getText(), mapPrizeTextField.getText());
            else{
                map.setName(mapNameTextField.getText());
                map.setHeight(Integer.parseInt(mapHeightTextField.getText()));
                map.setWidth(Integer.parseInt(mapWidthTextField.getText()));
                map.setPrize(Float.parseFloat(mapPrizeTextField.getText()));
            }
            map.setScale((Scale) mapScaleJBox.getSelectedItem());
            map.setPublisher((Publisher) mapPublisherJBox.getSelectedItem());

            if(mapApp != null)
                mapApp.currentMap = map;

            MapWindowApp.setTextFields(map);

            this.setVisible(false);
        }
        catch(MapException ex)
        {
            JOptionPane.showMessageDialog(getParent(), "Nie udało się edytować mapy");
        }
    }

    void cancelButtonActionListener()
    {
        dispose();
    }

}
