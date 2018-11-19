
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

public class PersonTest {
    private  static Person grandparent1;
    private  static Person grandparent2;
    private  static Person grandparent3;
    private  static Person grandparent4;
    private  static Person parent1;
    private  static Person parent2;
    private  static Person child;

    @BeforeClass
    public static void initializeTestSubjects() throws PersonException {
        grandparent1 = new Person("Grandparent1");
        grandparent2 = new Person("Grandparent2");
        grandparent3 = new Person("Grandparent3");
        grandparent4 = new Person("Grandparent4");
        parent1 = new Person("Parent1");
        parent2 = new Person("Parent2");
        child = new Person("Child");

        grandparent1.addChild(parent1);
        grandparent2.addChild(parent1);
        grandparent3.addChild(parent2);
        grandparent4.addChild(parent2);
        parent1.addParent(grandparent1);
        parent1.addParent(grandparent2);
        parent2.addParent(grandparent3);
        parent2.addParent(grandparent4);

        parent1.addChild(child);
        parent2.addChild(child);
        child.addParent(parent1);
        child.addParent(parent2);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void nullConstructorArgumentTest() {
        exception.expect(IllegalArgumentException.class);
        Person toBeTested = new Person(null);
    }

    @Test
    public void addChildCyclicRelationshipTest1() throws PersonException {
        exception.expect(PersonException.class);
        child.addChild(grandparent1);
    }

    @Test
    public void addChildCyclicRelationshipTest2() throws PersonException {
        exception.expect(PersonException.class);
        child.addChild(grandparent2);
    }

    @Test
    public void addChildCyclicRelationshipTest3() throws PersonException {
        exception.expect(PersonException.class);
        child.addChild(grandparent3);
    }

    @Test
    public void addChildCyclicRelationshipTest4() throws PersonException {
        exception.expect(PersonException.class);
        child.addChild(grandparent4);
    }

    @Test
    public void addChildCyclicRelationshipTest5() throws PersonException {
        exception.expect(PersonException.class);
        child.addChild(parent1);
    }

    @Test
    public void addChildCyclicRelationshipTest6() throws PersonException {
        exception.expect(PersonException.class);
        child.addChild(parent2);
    }

    @Test
    public void addParentCyclicRelationshipTest1() throws PersonException {
        exception.expect(PersonException.class);
        grandparent1.addParent(child);
    }

    @Test
    public void addParentCyclicRelationshipTest2() throws PersonException {
        exception.expect(PersonException.class);
        grandparent2.addParent(child);
    }

    @Test
    public void addParentCyclicRelationshipTest3() throws PersonException {
        exception.expect(PersonException.class);
        grandparent3.addParent(child);
    }

    @Test
    public void addParentCyclicRelationshipTest4() throws PersonException {
        exception.expect(PersonException.class);
        grandparent4.addParent(child);
    }

    @Test
    public void addParentCyclicRelationshipTest5() throws PersonException {
        exception.expect(PersonException.class);
        parent1.addParent(child);
    }

    @Test
    public void addParentCyclicRelationshipTest6() throws PersonException {
        exception.expect(PersonException.class);
        parent2.addParent(child);
    }

    @Test
    public void addExistingDescendantTest() throws PersonException {
        exception.expect(PersonException.class);
        grandparent1.addChild(child);
    }

    @Test
    public void addExistingAncestorTest() throws PersonException {
        exception.expect(PersonException.class);
        child.addParent(parent1);
    }

    @Test
    public void addChildNullTest() throws PersonException {
        exception.expect(PersonException.class);
        child.addChild(null);
    }

    @Test
    public void addParentNullTest() throws PersonException {
        exception.expect(PersonException.class);
        child.addParent(null);
    }

    @Test
    public void addThirdParentTest() throws PersonException {
        exception.expect(PersonException.class);
        parent1.addParent(new Person("TestName"));
    }

    @Test
    public void addChildSelfTest() throws PersonException {
        exception.expect(PersonException.class);
        child.addChild(child);
    }

    @Test
    public void addParentSelfTest() throws PersonException {
        exception.expect(PersonException.class);
        child.addParent(child);
    }
}
