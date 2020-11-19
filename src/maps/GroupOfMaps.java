package maps;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;


/**
 * Zawartość: Klasa GroupOfMaps
 * Autor: Dominik Tłokiński
 * Nr indeksu: 252689
 * Data: listopad 2020 r.
 */

enum GroupType
{
    ARRAY_LIST("Lista Array list"),
    LINKED_LIST("Lista Linked List"),
    HASH_SET("Zbiór HashSet"),
    TREE_SET("Zbiór TreeSet"),
    VECTOR("Lista vector");

    String typeName;

    private GroupType(String typeName)
    {
        this.typeName = typeName;
    }

    public String toString()
    {
        return typeName;
    }

    public static GroupType findEqualType(String typeName)
    {
        for(GroupType type : values())
            if(type.typeName.equals(typeName))
                return type;

        return null;
    }

    public Collection<Map> createCollection() throws MapException
    {
        switch(this) {
            case ARRAY_LIST:
                return new ArrayList<Map>();
            case LINKED_LIST:
                return new LinkedList<Map>();
            case HASH_SET:
                return new HashSet<Map>();
            case TREE_SET:
                return new TreeSet<Map>();
            case VECTOR:
                return new Vector<Map>();
            default:
                throw new MapException("Nie istnieje podany typ kolekcji");
        }
    }

}


public class GroupOfMaps implements Iterable<Map>, Serializable
{
    private String groupName;
    private GroupType groupType;
    private Collection<Map> groupCollection;

    public GroupOfMaps(String groupName, GroupType groupType) throws MapException
    {
        this.groupName = groupName;
        if(groupType == null)
            throw new MapException("Nieprawidłowy typ kolekcji");

        this.groupType = groupType;

        groupCollection = groupType.createCollection();
    }

    public GroupOfMaps(String groupName, String groupTypeName) throws MapException
    {
        this.groupName = groupName;
        GroupType groupType = GroupType.findEqualType(groupTypeName);
        if(groupType == null)
            throw new MapException("Nieprawidłowy typ kolekcji");

        this.groupType = groupType;
        groupCollection = groupType.createCollection();
    }

    public void setGroupName(String groupName) throws MapException
    {
        if(groupName == null && groupName.equals(""))
            throw new MapException("Nie podano nazwy grupy!");

        this.groupName = groupName;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public GroupType getGroupType()
    {
        return groupType;
    }

    public void setGroupType(String groupTypeName) throws MapException
    {
        for(GroupType groupType : GroupType.values())
            if(groupType.toString().equals(groupTypeName))
            {
                setGroupType(groupType);
                return;
            }

        throw new MapException("Nie ma takiego typu kolekcji!");
    }

    public void setGroupType(GroupType groupType) throws MapException
    {
        if(groupType == null)
            throw new MapException("Typ kolekcji nie został określony.");

        if(this.groupType == groupType)
            return;

        Collection<Map> oldGroupCollection = groupCollection;
        groupCollection = groupType.createCollection();
        this.groupType = groupType;

        for(Map map : oldGroupCollection)
            groupCollection.add(map);
    }

    public boolean addToGroupCollection(Map map)
    {
        return groupCollection.add(map);
    }

    public Iterator<Map> getGroupCollectioniterator()
    {
        return groupCollection.iterator();
    }

    public int getGroupCollectionsize()
    {
        return groupCollection.size();
    }

    public void sortByMapName() throws MapException
    {
        if(groupType == GroupType.HASH_SET || groupType == GroupType.TREE_SET)
            throw new MapException("Kolekcje typu Set nie mogą być sortowane");

        Collections.sort((List<Map>)groupCollection);
    }

    public void sortByMapHeight() throws MapException
    {
        if(groupType == GroupType.HASH_SET || groupType == GroupType.TREE_SET)
            throw new MapException("Kolekcje typu Set nie mogą być sortowane");

        Collections.sort((List<Map>) groupCollection, new Comparator<Map>() {
            @Override
            public int compare(Map o1, Map o2) {
                if(o1.getHeight() < o2.getHeight())
                    return -1;
                if(o1.getHeight() > o2.getHeight())
                    return 1;

                return 0;
            }
        });
    }

    public void sortByMapWidth() throws MapException
    {
        if(groupType == GroupType.HASH_SET || groupType == GroupType.TREE_SET)
            throw new MapException("Kolekcje typu Set nie mogą być sortowane");

        Collections.sort((List<Map>) groupCollection, new Comparator<Map>() {
            @Override
            public int compare(Map o1, Map o2) {
                if(o1.getWidth() < o2.getWidth())
                    return -1;
                if(o1.getWidth() > o2.getWidth())
                    return 1;

                return 0;
            }
        });
    }

    public void sortByMapPrize() throws MapException
    {
        if(groupType == GroupType.HASH_SET || groupType == GroupType.TREE_SET)
            throw new MapException("Kolekcje typu Set nie mogą być sortowane");

        Collections.sort((List<Map>) groupCollection, new Comparator<Map>() {
            @Override
            public int compare(Map o1, Map o2) {
                if(o1.getPrize() < o2.getPrize())
                    return -1;
                if(o1.getPrize() > o2.getPrize())
                    return 1;

                return 0;
            }
        });
    }

    public void sortByMapPublisher() throws MapException
    {
        if(groupType == GroupType.HASH_SET || groupType == GroupType.TREE_SET)
            throw new MapException("Kolekcje typu Set nie mogą być sortowane");

        Collections.sort((List<Map>) groupCollection, new Comparator<Map>()
        {
            @Override
            public int compare(Map o1, Map o2) {
                return o1.getPublisher().toString().compareTo(o2.getPublisher().toString());
            }
        });
    }

    public void sortByMapScale() throws MapException
    {
        if(groupType == GroupType.HASH_SET || groupType == GroupType.TREE_SET)
            throw new MapException("Kolekcje typu Set nie mogą być sortowane");

        Collections.sort((List<Map>) groupCollection, new Comparator<Map>()
        {
            @Override
            public int compare(Map o1, Map o2) {
                return o1.getScale().toString().compareTo(o2.getScale().toString());
            }
        });
    }

    @Override
    public String toString()
    {
        return "Nazwa grupy: " + groupName + ", Typ: " + groupType;
    }

    public static void printToFile(PrintWriter writer, GroupOfMaps group)
    {
        writer.println(group.getGroupName());
        writer.println(group.getGroupType());

        for(Map map : group.groupCollection)
            Map.printToFile(writer, map);
    }

    public static void printToFile(String fileName, GroupOfMaps group) throws MapException
    {
        try(PrintWriter writer = new PrintWriter(fileName))
        {
            printToFile(writer, group);
        }
        catch(FileNotFoundException e)
        {
            throw new MapException("Nie udalo sie zapisać do pliku " + fileName);
        }
    }

    public static GroupOfMaps readFromFile(BufferedReader reader) throws MapException
    {
        try{
            String groupNameS = reader.readLine();
            String groupTypeS = reader.readLine();

            GroupOfMaps groupOfMaps = new GroupOfMaps(groupNameS, groupTypeS);
            Map map;

            while((map = Map.readFromFile(reader)) != null)
                groupOfMaps.groupCollection.add(map);

            return groupOfMaps;
        }
        catch(Exception e)
        {
            throw new MapException("Nie udało się odczytać danych z pliku");
        }
    }

    public static GroupOfMaps readFromFile(String fileName) throws MapException
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(new File(fileName))))
        {
            return readFromFile(reader);
        }
        catch(IOException e)
        {
            throw new MapException("Nie udalo się otworzyc pliku");
        }
    }

    public static GroupOfMaps readFromBinaryFile() throws MapException
    {
        GroupOfMaps group = null;
        try{
            ObjectInputStream inS = new ObjectInputStream(new FileInputStream("PlikBinarnyGrup.bin"));
            group = (GroupOfMaps) inS.readObject();
            inS.close();
        }
        catch(IOException | ClassNotFoundException e)
        {
            e.getMessage();
        }
        return group;
    }

    public static void printToBinaryFile(GroupOfMaps group) throws MapException
    {
        try {
            ObjectOutputStream outS = new ObjectOutputStream(new FileOutputStream("PlikBinarnyGrup.bin"));
            outS.writeObject(group);
            outS.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }



    @Override
    public Iterator<Map> iterator()
    {
        return groupCollection.iterator();
    }
}





























