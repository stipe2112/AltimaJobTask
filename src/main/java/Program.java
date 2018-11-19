import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * Class with main method where problem solution was implemented
 *
 * @author Stipan Petrovic
 */
public class Program {

    /**
     * Main method which is called upon running the program
     */
    public static void main(String[] args) {
        List<String> allLines;
        try {
            allLines = Files.readAllLines(Paths.get("./src/main/resources/child-parent.txt"), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.err.println("IOException while trying to open the input file..");
            return;
        }

        //Using HashMap to store Person objects so that contains() method complexity is at minimum
        HashMap<String, Person> allPersons = new HashMap<String, Person>();

        /*
         * Counter helping us to print in which line error occurred (indexOf() has O(n) complexity), we could have used
         * ArrayList and use for loop with index and get() method, but that would require cast in line 24
         */
        int lineCounter = 1;

        for (String line : allLines) {
            /*
             * Trimming and splitting line of the input document around whitespace, and skipping it and writing error output
             * message if it has invalid format
             */
            String[] splitLine = line.trim().split("\\s");
            if (splitLine.length != 2) {
                System.out.println(line + ".. --> this line is invalid");
                continue;
            }

            /*
             * Creating two temporary Person objects from input document line values
             */
            Person child = new Person(splitLine[0]);
            Person parent = new Person(splitLine[1]);

            /*
             * Try to make connection between parent and child object using processPersons() method
             */
            try {
                processPersons(allPersons, child, parent);
            } catch (PersonException e) {
                System.out.println("Error occurred in line[" + (lineCounter) + "]: " + line + " --> " + e.getMessage());
            }

            lineCounter++;
        }


        /*
         * Printing descendants tree for each person which has no parents, which makes them root of their family tree
         */
        for (Person person : allPersons.values()) {
            if (!person.hasParents()) {
                person.printDescendantsTree();
            }
        }
    }


    /**
     * Method which processes objects made from two names from input document line. It checks whether objects with those identifiers (we use persons name)
     * already exist. If they do not, it makes connection between two objects adding one to another's parents list, and another to one's child list. If one
     * or both objects exist it gets them from hash map and updates their lists.
     *
     * @param allPersons HashMap of existing Person class objects
     * @param child      Person class object representing child
     * @param parent     Person class object representing parent
     * @throws PersonException If relationship is cyclic, child already has two parents
     */
    public static void processPersons(HashMap<String, Person> allPersons, Person child, Person parent) throws PersonException {
        if(allPersons == null)
            throw new IllegalArgumentException("HashMap<Strin, Person> allPersons is null!");
        if(child == null)
            throw new IllegalArgumentException("Person class object child is null!");
        if(parent == null)
            throw new IllegalArgumentException("Person class object parent is null!");
        if(parent.equals(child))
            throw new IllegalArgumentException("Person class objects child and parent are one the same!");

        /*
         * If neither person has come up before in input document we can just make connection between parent and child put them in HashMap
         */
        if (!allPersons.containsKey(child.getName()) && !allPersons.containsKey(parent.getName())) {
            parent.addChild(child);
            child.addParent(parent);

            allPersons.put(child.getName(), child);
            allPersons.put(parent.getName(), parent);
        }
        /*
         * If HashMap contains parent but not child, we get the object representing parent, add child to its list of children
         * and vice versa, add parent to child's list of parents. Finally we add child instance to HashMap
         */
        else if (allPersons.containsKey(parent.getName()) && !allPersons.containsKey(child.getName())) {
            parent = allPersons.get(parent.getName());

            parent.addChild(child);
            child.addParent(parent);

            allPersons.put(child.getName(), child);
        }
        /*
         * If HashMap contains child but not parent, we get the object representing child, add parent to its list of parents
         * and vice versa, add child to parents's list of children. Finally we add parent instance to HashMap
         */
        else if (!allPersons.containsKey(parent.getName()) && allPersons.containsKey(child.getName())) {
            child = allPersons.get(child.getName());

            parent.addChild(child);
            child.addParent(parent);

            allPersons.put(parent.getName(), parent);
        }
        /*
         * If HashMap contains both child and parent from given .txt file line, we get both objects and add each to another's list
         */
        else if (allPersons.containsKey(parent.getName()) && allPersons.containsKey(child.getName())) {
            parent = allPersons.get(parent.getName());
            child = allPersons.get(child.getName());

            parent.addChild(child);
            child.addParent(parent);
        }
    }
}
