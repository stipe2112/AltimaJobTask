import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a person for the given task, name of the person is unique identifier, contains list of all the children and the list of persons parents
 *
 * @author Stipan Petrovic
 */
public class Person {

    /**
     * String representing the persons name, also unique identifier
     */
    private String name;
    /**
     * List of Person objects representing persons children
     */
    private List<Person> children;
    /**
     * List of Person objects representing persons parents
     */
    private List<Person> parents;

    /**
     * Constructor for the class Person
     *
     * @param name String representing the name of the person
     */
    public Person(String name){
        if(name == null) throw new IllegalArgumentException("Argument given to Person class constructor is invalid");

        this.name = name;
        children = new LinkedList<Person>();
        parents = new LinkedList<Person>();
    }

    /**
     * Adds a Person object to a children list of object upon it was called upon
     *
     * @param child Person object representing child
     * @throws PersonException If the relationship is cyclic or the given argument is null
     */
    public void addChild(Person child) throws PersonException {
        if (child == null) {
            throw new PersonException("Given argument, where instance of Person class was expected, is null!");
        }

        if(this.equals(child)) {
            throw new PersonException("Person you're trying to add as a child is the person itself!");
        }

        if (isMyAncestor(child)) {
            throw new PersonException("Cyclic relationships are not valid!");
        }

        if(isMyDescendant(child)) {
            throw new PersonException("Person you're trying to add as a child is already a descendant!");
        }

        this.children.add(child);
    }

    /**
     * Adds a Person object to a parents list of object upon which it was called upon
     *
     * @param parent Person object representing an objects parent
     * @throws PersonException If the relationship is cyclic, the object upon which it was called upon already has two
     *                          parents or the given argument is null
     */
    public void addParent(Person parent) throws PersonException {
        if (parent == null) {
            throw new PersonException("Given argument, where instance of Person class was expected, is null!");
        }

        if(this.equals(parent)) {
            throw new PersonException("The person you're trying to add as a parent is the person itself!");
        }

        if (isMyDescendant(parent)) {
            throw new PersonException("Cyclic relationships are not valid!");
        }

        if (isMyAncestor(parent)) {
            throw new PersonException("Person you're trying to add as a child is already an ancestor!");
        }

        if (parents.size() >= 2) {
            throw new PersonException("Person cannot have more than two parents!");
        }

        this.parents.add(parent);
    }

    /**
     * Method which recursively iterates through all persons ancestors, checking whether given object is one
     *
     * @param person Potential ancestor
     * @return True if given argument is objects ancestor or the object itself, false otherwise
     */
    private boolean isMyAncestor(Person person) {
        if (this.equals(person)) return true;

        for (Person parent : parents) {
            if (parent.isMyAncestor(person)) return true;
        }

        return false;
    }

    /**
     * Method which recursively iterates through all persons descendants, checking whether given Person object is one
     *
     * @param person Potential descendant
     * @return True if given argument is objects descendant or object itself, false otherwise
     */
    private boolean isMyDescendant(Person person) {
        if (this.equals(person)) return true;

        for (Person child : children) {
            if (child.isMyDescendant(person)) return true;
        }

        return false;
    }

    /**
     * Method which checks whether Person object has any parents
     *
     * @return False if persons parents list size is different than 0, true otherwise
     */
    public boolean hasParents() {
        return this.parents.size() != 0;
    }

    /**
     * Getter for persons name variable
     *
     * @return String value of persons name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for persons children list
     *
     * @return List<Person> of persons
     */
    public List<Person> getChildren() {
        return children;
    }

    /**
     * Getter for persons parents list
     *
     * @return List<Person> of persons
     */
    public List<Person> getParents() {
        return parents;
    }

    /**
     * Auto-generated hashCode() method which generates hash-code from persons name
     *
     * @return int hash-code value from persons name
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * Auto-generated equals() method which compares Person objects by their name
     *
     * @return True if two objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * Method that prints to standard output all the descendants from Person object upon which it was called upon
     */
    public void printDescendantsTree() {
        /*
         * Calls upon overloaded method sending 0 as an argument used for indentation of the output.
         * Overloaded method is private and unaccessible to the class user. Made this way to simplify the usage for the end-user.
         */
        printDescendantsTree(0);
    }

    /*
     * Method that recursively prints and indents the descendants of the object. Takes int level as an argument, which it
     * increments each turn to set the proper indentation for each new generation of the descendants
     */
    private void printDescendantsTree(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("-----");
        }

        System.out.print((level == 0 ? "" : "|") + this.name + "\n");

        for (Person child : children) {
            child.printDescendantsTree(level + 1);
        }

    }
}
