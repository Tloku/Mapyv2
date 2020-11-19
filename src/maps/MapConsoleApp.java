package maps;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Zawartość: Klasa MapConsoleApp
 * Autor: Dominik Tłokiński
 * Nr indeksu: 252689
 * Data: październik 2020 r.
 */

public class MapConsoleApp {

    public static final String AUTHOR =
            "Imię: Dominik \n" +
            "Nazwisko: Tłokiński \n" +
            "Kierunek: ITE\n " +
            "Numer indeksu: 252689 \n";

    private static final String GREETING_MESSAGE =
            "Program Map - wersja konsolowa\n" +
            "Autor: Dominik Tłokiński\n" +
            "Data: październik 2020 r.\n";

    private static final String MENU =
            "\n----Menu główne-----\n" +
            "1 - Podaj dane nowej mapy. \n" +
            "2 - Usuń dane mapy. \n" +
            "3 - Modyfikuj dane mapy. \n" +
            "4 - Wczytaj dane mapy z pliku. \n" +
            "5 - Zapisz dane mapy do pliku. \n" +
            "6 - Wypisz dane autora. \n" +
            "0 - Zakończ Program. \n";

    private static final String CHANGE_MENU =
            "Co zmienić? \n " +
            "1. Zmień nazwe mapy. \n" +
            "2. Zmień szerokość mapy. \n" +
            "3. Zmień wysokość mapy. \n" +
            "4. Zmień cene mapy. \n" +
            "5. Zmień skale mapy. \n" +
            "6. Zmień wydawce mapy. \n" +
            "0. Powrót do menu głównego. \n";

    private static ConsoleUserDialog UI = new ConsoleUserDialog();

    public static void main(String[] args) throws MapException {
        MapConsoleApp application = new MapConsoleApp();
        application.runMainLoop();
    }

    private Map currentMap = null;

    public void runMainLoop() throws MapException {
        UI.printMessage(GREETING_MESSAGE);
        while (true) {
            UI.clearConsole();
            showCurrentMap();
            try {

                switch (UI.enterInt(MENU + "-->")) {
                    case 1:
                        currentMap = createNewMap();
                        break;
                    case 2:
                        currentMap = null;
                        UI.printInfoMessage("Dane aktualnej mapy zostały usunięte");
                        break;
                    case 3:
                        if (currentMap == null) throw new MapException("Aktualna mapa nie istnieje.");
                        changeMapData(currentMap);
                        break;
                    case 4: {
                        String fileName = UI.enterString("Podaj nazwe pliku: ");
                        currentMap = Map.readFromFile(fileName);
                        UI.printInfoMessage("Dane aktualnej mapy zostały wczytane z pliku " + fileName);
                        }break;
                    case 5: {
                        String fileName = UI.enterString("Podaj nazwe pliku: ");
                        Map.printToFile(currentMap, fileName);
                        UI.printInfoMessage("Dane aktualnej mapy zostały zapisane do pliku " + fileName);
                        }break;
                    case 6:
                        UI.printMessage(AUTHOR);
                        break;
                    case 0:
                        UI.printInfoMessage("\n Program zakończył działanie!");
                        System.exit(0);
                        break;
                }
            } catch (MapException e) {
                UI.printErrorMessage(e.getMessage());
            }
        }
    }

    void showCurrentMap() {
        showMap(currentMap);
    }

    static void showMap(Map map) {
        StringBuilder mapText = new StringBuilder();

        if (map != null) {
            mapText.append("Aktualna mapa: \n");
            mapText.append("        Nazwa: " + map.getName() + "\n");
            mapText.append("     Wysokość: " + map.getHeight() + "\n");
            mapText.append("    Szerokość: " + map.getWidth() + "\n");
            mapText.append("         Cena: " + map.getPrize() + "\n");
            mapText.append("        Skala: " + map.getScale() + "\n");
            mapText.append("      Wydawca: " + map.getPublisher() + "\n");
        } else
            mapText.append("Brak danych mapy.\n");
        UI.printMessage(mapText.toString());
    }

    static Map createNewMap() {
        String mapName = UI.enterString("Podaj nazwe mapy: ");
        String mapHeight = UI.enterString("Podaj wysokość mapy: ");
        String mapWidth = UI.enterString("Podaj szerokość mapy: ");
        String mapPrize = UI.enterString("Podaj cene mapy: ");
        UI.printMessage("Wybierz jedną ze skal: " + Arrays.deepToString(Scale.values()));
        String mapScale = UI.enterString("Podaj skalę: ");
        UI.printMessage("Wybierz wydawnictwo: " + Arrays.deepToString(Publisher.values()));
        String mapPublisher = UI.enterString("Podaj wydawnictwo: ");
        Map map = null;
        try {
            map = new Map(mapName, mapHeight, mapWidth, mapPrize);
            map.setScale(mapScale);
            map.setPublisher(mapPublisher);
            
        } catch (MapException e) {
            UI.printErrorMessage(e.getMessage());
        }
        return map;
    }

    static void changeMapData(Map map) {

        while(true) {

            UI.clearConsole();
            showMap(map);

            try{
                switch(UI.enterInt(CHANGE_MENU + "-->")) {
                    case 1:
                        map.setName(UI.enterString("Podaj nową nazwe mapy: "));
                        break;
                    case 2:
                        map.setWidth(UI.enterInt("Podaj nową szerokość mapy: "));
                        break;
                    case 3:
                        map.setHeight(UI.enterInt("Podaj nową wysokość mapy: "));
                        break;
                    case 4:
                        map.setPrize(UI.enterFloat("Podaj nową cene mapy"));
                        break;
                    case 5:
                        UI.printMessage("Wybierz jedną ze skal: " + Arrays.deepToString(Scale.values()));
                        map.setScale(UI.enterString("Podaj nową skale mapy"));
                        break;
                    case 6:
                        UI.printMessage("Wybierz jednego z wydawców: " + Arrays.deepToString(Publisher.values()));
                        map.setPublisher(UI.enterString("Podaj nową wydawce: "));
                        break;
                    default:
                        return;
                }
            } catch (MapException e) {
               UI.printErrorMessage(e.getMessage());
            }


        }
    }

}








































