import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProgramTest {
    private  static Person grandparent1;
    private  static Person grandparent2;
    private  static Person grandparent3;
    private  static Person grandparent4;
    private  static Person parent1;
    private  static Person parent2;
    private  static Person child;

    private static HashMap<String, Person> allPersons;

    @Before
    public void initializeTestSubjects() throws PersonException {
        grandparent1 = new Person("Grandparent1");
        grandparent2 = new Person("Grandparent2");
        grandparent3 = new Person("Grandparent3");
        grandparent4 = new Person("Grandparent4");
        parent1 = new Person("Parent1");
        parent2 = new Person("Parent2");
        child = new Person("Child");

        allPersons = new HashMap<String, Person>();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void processPersonsNullArgumentsTest1() throws PersonException {
        exception.expect(IllegalArgumentException.class);
        Program.processPersons(null, child, parent1);
    }

    @Test
    public void processPersonsNullArgumentsTest2() throws PersonException {
        exception.expect(IllegalArgumentException.class);
        Program.processPersons(allPersons, null, parent1);
    }

    @Test
    public void processPersonsNullArgumentsTest3() throws PersonException {
        exception.expect(IllegalArgumentException.class);
        Program.processPersons(allPersons, child, null);
    }

    @Test
    public void processPersonsSamePersonObject() throws PersonException {
        exception.expect(IllegalArgumentException.class);
        Program.processPersons(allPersons, child, child);
    }

    @Test
    public void processPersonsHashMapContainsNeitherTest() throws PersonException {
        assertTrue(allPersons.size() == 0);
        Program.processPersons(allPersons, child, parent1);
        assertTrue(allPersons.size() == 2);

        assertTrue(allPersons.containsKey(child.getName()));
        assertTrue(allPersons.containsKey(parent1.getName()));
    }

    @Test
    public void processPersonsHashMapContainsBothTest() throws PersonException {
        assertTrue(allPersons.size() == 0);

        grandparent1.addChild(parent1);
        parent1.addParent(grandparent1);
        allPersons.put(grandparent1.getName(), grandparent1);
        allPersons.put(parent1.getName(), parent1);

        assertTrue(allPersons.size() == 2);

        parent2.addChild(child);
        child.addParent(parent2);
        allPersons.put(parent2.getName(), parent2);
        allPersons.put(child.getName(), child);

        assertTrue(allPersons.size() == 4);


        Program.processPersons(allPersons, child, parent1);
        assertTrue(allPersons.size() == 4);
    }

    @Test
    public void processPersonsHashMapContainsChildTest() throws PersonException {
        assertTrue(allPersons.size() == 0);

        child.addParent(parent1);
        parent1.addChild(child);
        allPersons.put(parent1.getName(), parent1);
        allPersons.put(child.getName(), child);

        assertTrue(allPersons.size() == 2);

        assertTrue(allPersons.containsKey(child.getName()));
        assertFalse(allPersons.containsKey(parent2.getName()));
        Program.processPersons(allPersons, child, parent2);

        assertTrue(allPersons.size() == 3);
        assertTrue(allPersons.containsKey(parent2.getName()));
    }

    @Test
    public void processPersonsHashMapContainsParentTest() throws PersonException {
        assertTrue(allPersons.size() == 0);

        grandparent1.addChild(parent1);
        parent1.addParent(grandparent1);
        allPersons.put(grandparent1.getName(), grandparent1);
        allPersons.put(parent1.getName(), parent1);

        assertTrue(allPersons.size() == 2);

        assertTrue(allPersons.containsKey(parent1.getName()));
        assertFalse(allPersons.containsKey(child.getName()));
        Program.processPersons(allPersons, child, parent1);

        assertTrue(allPersons.size() == 3);
        assertTrue(allPersons.containsKey(child.getName()));
    }
}
