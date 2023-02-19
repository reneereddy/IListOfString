package student;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IListOfStringTest {
    EmptyListOfString emptyList = new EmptyListOfString();
    NonEmptyListOfString list1 = new NonEmptyListOfString("one", emptyList);
    NonEmptyListOfString list2 = new NonEmptyListOfString("two", list1);
    NonEmptyListOfString list3 = new NonEmptyListOfString("three", list2);
    NonEmptyListOfString list4 = new NonEmptyListOfString("north", list1);
    NonEmptyListOfString list5 = new NonEmptyListOfString("month", list4);
    NonEmptyListOfString list6 = new NonEmptyListOfString("puddle", list5);
    NonEmptyListOfString list7 = new NonEmptyListOfString("c", emptyList);
    NonEmptyListOfString list8 = new NonEmptyListOfString("b", list7);
    NonEmptyListOfString list9 = new NonEmptyListOfString("a", list8);
    NonEmptyListOfString list10 = new NonEmptyListOfString("ñ", emptyList);
    NonEmptyListOfString list11 = new NonEmptyListOfString("é", list10);
    NonEmptyListOfString list12 = new NonEmptyListOfString("a", list10);
    NonEmptyListOfString list13 = new NonEmptyListOfString("one", list1);
    NonEmptyListOfString list14 = new NonEmptyListOfString(null, emptyList);
    NonEmptyListOfString list15 = new NonEmptyListOfString("", emptyList);
    NonEmptyListOfString list16 = new NonEmptyListOfString(" ", emptyList);
    NonEmptyListOfString list17 = new NonEmptyListOfString("One", emptyList);
    NonEmptyListOfString list18 = new NonEmptyListOfString("one", list17);
    NonEmptyListOfString list19 = new NonEmptyListOfString("And", list17);
    NonEmptyListOfString list20 = new NonEmptyListOfString("And", list18);
    NonEmptyListOfString list21 = new NonEmptyListOfString(null, list1);
    NonEmptyListOfString list22 = new NonEmptyListOfString("", list1);
    NonEmptyListOfString list23 = new NonEmptyListOfString(" ", list1);
    NonEmptyListOfString list24 = new NonEmptyListOfString("north", list21);

    @Test
    public void sizeOfEmptyListIsZero() {
        assertEquals(0, emptyList.size());
    }

    @Test
    public void sizeOfNonEmptyListIsRight() {
        assertEquals(1, list1.size());
        assertEquals(2, list2.size());
        assertEquals(3, list3.size());
    }

    @Test
    public void getRejectsNegativeIndex() {
        assertThrows(IllegalArgumentException.class,
                () -> emptyList.get(-1));
        assertThrows(IllegalArgumentException.class,
                () -> list1.get(-5));
    }

    @Test
    public void getRejectsOutOfBoundsIndex() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> emptyList.get(0));
        assertThrows(IndexOutOfBoundsException.class,
                () -> list1.get(1));
        assertThrows(IndexOutOfBoundsException.class,
                () -> list3.get(10));
    }

    @Test
    public void getReturnsRightString() {
        assertEquals("one", list1.get(0));
        assertEquals("two", list2.get(0));
        assertEquals("one", list3.get(2));
        assertEquals("two", list3.get(1));
        assertEquals("three", list3.get(0));
    }

    @Test
    public void combineEmptyList() {
        assertEquals("", emptyList.combine());
    }

    @Test
    public void combineNonEmptyList() {
        assertEquals("one", list1.combine());
        assertEquals("twoone", list2.combine());
        assertEquals("threetwoone", list3.combine());
    }

    //TODO: write tests
    @Test
    public void isSortedEmptyList(){
        assertEquals(true, emptyList.isSorted());
    }

    @Test
    public void isSortedNonEmptyList(){
        assertEquals(true, list1.isSorted());
        assertEquals(false, list2.isSorted());
        assertEquals(false, list3.isSorted());
        assertEquals(true, list9.isSorted());
        assertEquals(true, list13.isSorted());
        assertEquals(true, list17.isSorted());
        assertEquals(true, list18.isSorted());
        assertEquals(true, list19.isSorted());
        assertEquals(true, list20.isSorted());
        assertEquals(false, list21.isSorted());
        assertEquals(true, list22.isSorted());
        assertEquals(true, list23.isSorted());
        assertEquals(false, list24.isSorted());
    }

    @Test
    public void interleaveEmptyList() {
        assertEquals(list1, emptyList.interleave(list1));
        assertEquals(emptyList, emptyList.interleave(emptyList));
    }

    @Test
    public void interleaveNonEmptyList() {
        assertEquals(new NonEmptyListOfString("one",
                new EmptyListOfString()), list1.interleave(emptyList));
        assertEquals(new NonEmptyListOfString("one",
                new NonEmptyListOfString("two",
                        new NonEmptyListOfString("one",
                                new EmptyListOfString()))), list1.interleave(list2));
        assertEquals(new NonEmptyListOfString("one",
                new NonEmptyListOfString("three",
                        new NonEmptyListOfString("two",
                                new NonEmptyListOfString("one",
                                        new EmptyListOfString())))), list1.interleave(list3));
        assertEquals(new NonEmptyListOfString("two",
                new NonEmptyListOfString("one",
                        new NonEmptyListOfString("one",
                                new EmptyListOfString()))), list2.interleave(list1));
        assertEquals(new NonEmptyListOfString("two",
                new NonEmptyListOfString("three",
                        new NonEmptyListOfString("one",
                                new NonEmptyListOfString("two",
                                        new NonEmptyListOfString("one",
                                                new EmptyListOfString()))))), list2.interleave(list3));
        assertEquals(new NonEmptyListOfString("three",
                new NonEmptyListOfString("one",
                        new NonEmptyListOfString("two",
                                new NonEmptyListOfString("one",
                                        new EmptyListOfString())))), list3.interleave(list1));
        assertEquals(new NonEmptyListOfString("three",
                new NonEmptyListOfString("two",
                        new NonEmptyListOfString("two",
                                new NonEmptyListOfString("one",
                                        new NonEmptyListOfString("one",
                                                new EmptyListOfString()))))), list3.interleave(list2));
        assertEquals(new NonEmptyListOfString("three",
                new NonEmptyListOfString("month",
                        new NonEmptyListOfString("two",
                                new NonEmptyListOfString("north",
                                        new NonEmptyListOfString("one",
                                                new NonEmptyListOfString("one",
                                                        new EmptyListOfString())))))), list3.interleave(list5));
        assertEquals(new NonEmptyListOfString("month",
                new NonEmptyListOfString("three",
                        new NonEmptyListOfString("north",
                                new NonEmptyListOfString("two",
                                        new NonEmptyListOfString("one",
                                                new NonEmptyListOfString("one",
                                                        new EmptyListOfString())))))), list5.interleave(list3));
    }

    @Test
    public void isAlphabeticallyConsecutiveEmptyList() {
        assertEquals(true, emptyList.isAlphabeticallyConsecutive());
    }

    //edge cases: emptylist, done: two elements are the same, done: nonemptylist with an empty string
    @Test
    public void isAlphabeticallyConsecutiveNonEmptyList() {
        assertEquals(false, list14.isAlphabeticallyConsecutive()); //null
        assertEquals(true, list1.isAlphabeticallyConsecutive());
        assertEquals(false, list2.isAlphabeticallyConsecutive());
        assertEquals(false, list3.isAlphabeticallyConsecutive());
        assertEquals(true, list5.isAlphabeticallyConsecutive());
        assertEquals(false, list6.isAlphabeticallyConsecutive());
        assertEquals(false, list11.isAlphabeticallyConsecutive());
        assertEquals(false, list12.isAlphabeticallyConsecutive());
        assertEquals(false, list15.isAlphabeticallyConsecutive()); // ""
        assertEquals(false, list16.isAlphabeticallyConsecutive()); // " "
        assertEquals(false, list13.isAlphabeticallyConsecutive()); // same word
        assertEquals(true, list17.isAlphabeticallyConsecutive()); //Capital
    }

    @Test
    public void equalsNonEmptyList(){
        assertEquals(false,list1.equals(emptyList));
        assertEquals(false, list1 == list2);
        assertEquals(false, list1.equals("one"));
    }
}