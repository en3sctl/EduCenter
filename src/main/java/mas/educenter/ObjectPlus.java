package mas.educenter;

import java.io.*;
import java.util.*;

// Base class for extent management - based on lecture (Wyklad 03)
public abstract class ObjectPlus implements Serializable {

    private static Map<Class<? extends ObjectPlus>, List<ObjectPlus>> allExtents = new HashMap<>();

    public ObjectPlus() {
        List<ObjectPlus> extent;
        Class theClass = this.getClass();

        if (allExtents.containsKey(theClass)) {
            extent = allExtents.get(theClass);
        } else {
            extent = new ArrayList<>();
            allExtents.put(theClass, extent);
        }

        extent.add(this);
    }

    // Saves all extents to stream
    public static void writeExtents(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allExtents);
    }

    // Loads all extents from stream
    @SuppressWarnings("unchecked")
    public static void readExtents(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allExtents = (Map<Class<? extends ObjectPlus>, List<ObjectPlus>>) stream.readObject();
    }

    // Returns extent for given class
    @SuppressWarnings("unchecked")
    public static <T> Iterable<T> getExtent(Class<T> type) throws ClassNotFoundException {
        List<ObjectPlus> extent = allExtents.get(type);
        if (extent == null) {
            throw new ClassNotFoundException("Unknown extent: " + type.getName());
        }
        return (Iterable<T>) extent;
    }

    // Prints all objects in the extent of given class
    public static void showExtent(Class<? extends ObjectPlus> theClass) {
        List<ObjectPlus> extent = allExtents.get(theClass);
        System.out.println("--- Extent of " + theClass.getSimpleName() + " ---");
        if (extent == null) {
            System.out.println("(no extent registered)");
        } else {
            for (ObjectPlus obj : extent) {
                System.out.println(obj);
            }
        }
        System.out.println("--- End of extent ---\n");
    }
}
