package maps;

import java.io.*;

/**
 *  Zawartość: Klasa enum Scale, Publisher, klasa MapException, klasa Map
 *  Autor: Dominik Tłokiński
 *  Nr indeksu: 252689
 *  Data: październik 2020
 */

enum Scale
{
    UNKNOWN("-----"), 
    EXTRA_SMALL("2000"),
    SMALL("1900"),
    MEDIUM("50"),
    LARGE("10"),
    ONE_TO_ONE("1");
    
    String scaleType;
   
    private Scale(String scaleType){
        this.scaleType = scaleType;
    }
    
    @Override
    public String toString() {
        return scaleType;
    }
}

enum Publisher
{
    UNKNOWN("----"),
    COMP("Compass"),
    JELK("Jelkart"),
    EKO("Eko-map"),
    PLAS("Plastic"),
    MIZIELINSCY("Aleksandra i Daniel Mizielińscy");

    String publisherName;

    private Publisher(String publisherName){
        this.publisherName = publisherName;
    }

    @Override
    public String toString() {
        return publisherName;
    }
}


class MapException extends Exception {

    public MapException(String message) {
        super(message);
    }
}

public class Map implements Serializable, Comparable
{
    private String mapName;
    private Scale mapScale;
    private Publisher mapPublisher;
    private int mapHeight;
    private int mapWidth;
    private float mapPrize;

    public Map(String name, String height, String width, String prize) throws MapException {
        setName(name);
        setHeight(height);
        setWidth(width);
        setPrize(prize);
        mapScale = Scale.UNKNOWN;
        mapPublisher = Publisher.UNKNOWN;
    }

    public String getName() {
        return mapName;
    }

    public void setName(String mapName) throws MapException {
        if (mapName == null || mapName.equals(""))
            throw new MapException("Nazwa mapy nie została wpisana!");
        this.mapName = mapName;
    }

    public Scale getScale() {
        return mapScale;
    }

    public void setScale(Scale mapScale) {
        this.mapScale = mapScale;
    }

    public void setScale(String scaleName) throws MapException {
        if (scaleName == null || scaleName.equals("")) {
            this.mapScale = Scale.UNKNOWN;
        }

        for (Scale mapScale : Scale.values()) {
            if (mapScale.scaleType.equals(scaleName)) {
                this.mapScale = mapScale;
                return;
            }
        }

        throw new MapException("Nieznana skala mapy.");
    }

    public Publisher getPublisher() {
        return mapPublisher;
    }

    public void setPublisher(Publisher mapPublisher) {
        this.mapPublisher = mapPublisher;
    }

    public void setPublisher(String publisher) throws MapException {
        if (publisher == null || publisher.equals("")) {
            this.mapPublisher = Publisher.UNKNOWN;
        }

        for (Publisher mapPublisher : Publisher.values()) {
            if (mapPublisher.publisherName.equals(publisher)) {
                this.mapPublisher = mapPublisher;
                return;
            }
        }
        throw new MapException("Nieznany wydawca mapy.");
    }

    public int getWidth() {
        return mapWidth;
    }

    public void setWidth(int mapWidth) throws MapException {
        if (mapWidth < 0 || mapWidth > 1000)
            throw new MapException("Została podana nieprawidłowa szerokość mapy.");
        this.mapWidth = mapWidth;
    }

    public void setWidth(String mapWidth) throws MapException {
        if (mapWidth == null || mapWidth.equals(""))
            setWidth(0);

        try {
            setWidth(Integer.parseInt(mapWidth));
        } catch (NumberFormatException e) {
            throw new MapException("Nie rozpoznano liczby");
        }
    }

    public int getHeight() {
        return mapHeight;
    }

    public void setHeight(int mapHeight) throws MapException {
        if (mapHeight < 0 || mapHeight > 1000)
            throw new MapException("Została podana nieprawidłowa wysokość mapy.");
        this.mapHeight = mapHeight;
    }

    public void setHeight(String mapHeight) throws MapException {
        if (mapHeight == null || mapHeight.equals(""))
            setHeight(0);

        try {
            setHeight(Integer.parseInt(mapHeight));
        } catch (NumberFormatException e) {
            throw new MapException("Nie rozpoznano liczby");
        }
    }

    public float getPrize() {
        return mapPrize;
    }

    public void setPrize(float mapPrize) throws MapException {
        if (mapPrize < 0 || mapPrize > 10000)
            throw new MapException("Została podana nieprawidłowa cena mapy.");
        this.mapPrize = mapPrize;
    }

    public void setPrize(String mapPrize) throws MapException {
        if (mapPrize == null || mapPrize.equals(""))
            setPrize(0);
        try {
            setPrize(Float.parseFloat(mapPrize));
        } catch (NumberFormatException e) {
            throw new MapException("Nie rozpoznano liczby");
        }
    }

    @Override
    public String toString() {
        return "Nazwa mapy: " + mapName + ", wydawcy: " + mapPublisher;
    }

    public static void printToFile(PrintWriter writer, Map map) {
        writer.println(map.getName() + "," + map.getHeight() + "," + map.getWidth() + "," + map.getPrize() + "," + map.getScale() + "," + map.getPublisher());
    }


    public static void printToFile(Map map, String fileName) throws MapException {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            printToFile(writer, map);
        } catch (FileNotFoundException e) {
            throw new MapException("Nie znaleziono pliku o nazwie: " + fileName);
        }
    }

    public static Map readFromFile(BufferedReader reader) throws MapException {
        try {
            String line = reader.readLine();
            String[] txt = line.split(",");
            Map map = new Map(txt[0], txt[1], txt[2], txt[3]);
            map.setScale(txt[4]);
            map.setPublisher(txt[5]);
            reader.close();
            MapWindowApp.setTextFields(map);
            return map;
        } catch (IOException e) {
            throw new MapException("Nie udało się odczytać danych z pliku.");
        }
    }

    public static Map readFromFile(String fileName) throws MapException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
            Map map = Map.readFromFile(reader);
            return map;
        } catch (FileNotFoundException e) {
            throw new MapException("Nie odnaleziono pliku " + fileName + "!");
        } catch (IOException e) {
            throw new MapException("Wystapil blad podczas proby odczytu z pliku");
        }
    }


    public static Map readFromBinaryFile() {
        Map map = null;
        try{
            ObjectInputStream inS = new ObjectInputStream(new FileInputStream("PlikBinarny.bin"));
            map = (Map) inS.readObject();
            MapWindowApp.setTextFields(map);
            inS.close();
        }
        catch(IOException | ClassNotFoundException e)
        {
            e.getMessage();
        }
        return map;
    }

    public static void printToBinaryFile(Map map)
    {
        try {
            ObjectOutputStream outS = new ObjectOutputStream(new FileOutputStream("PlikBinarny.bin"));
            outS.writeObject(map);
            outS.close();
        } catch (IOException e) {
            e.getMessage();
        }

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}











