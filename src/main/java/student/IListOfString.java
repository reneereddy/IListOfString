// This is the version that should be given to students.

package student;

import java.util.Objects;

import static java.lang.Math.min;

/**
 * An immutable list of zero or more strings.
 */
interface IListOfString {

    /**
     * Returns the number of strings in this list.
     *
     * @return the number of strings in this list
     */
    int size();

    /**
     * Returns the string at the specified index in this list.
     *
     * @param index the index of the string
     * @return the string at the specified position
     * @throws IllegalArgumentException  if index is less than zero
     * @throws IndexOutOfBoundsException if index is greater than or equal to
     *                                   the length of the list
     */
    String get(int index);

    /**
     * Concatenates all the strings in the list in order.
     *
     * @return the concatenation of all the strings, or the empty string if the list is empty
     */
    String combine();

    /**
     * Determines whether this list is sorted. The method
     * {@link String#compareToIgnoreCase(String)} is used to determine ordering.
     *
     * @return true if this list is sorted, false otherwise
     */
    boolean isSorted();

    /**
     * Constructs a new list by interleaving the elements of this list and
     * the passed list, starting with this list. Any leftover elements
     * (when one list is longer than the others) appear at the end of
     * the new list.
     *
     * @param other the passed list
     * @return a new list consisting of interleaved elements from the two lists
     */
    IListOfString interleave(IListOfString other);

    /**
     * Checks whether the strings in this list start with consecutive letters
     * of the English alphabet. For example, the list with elements "Hello",
     * "I'm", "joyful" qualifies, while "amazing", "zebras" does not. If any
     * of the strings do not start with English letters, this should return
     * false.
     *
     * @return true if the strings start with consecutive letters of the English
     * alphabet, false otherwise.
     */
    boolean isAlphabeticallyConsecutive();
}

/**
 * An immutable empty list of Strings.
 */
class EmptyListOfString implements IListOfString {
    /**
     * Constructs an empty list of strings.
     */
    public EmptyListOfString() {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String get(int index) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (index < 0) {
            throw new IllegalArgumentException("Negative index to get() not permitted");
        } else {
            throw new IndexOutOfBoundsException(String.format("Index %1s is out of bounds", index));
        }
    }

    @Override
    public String combine() {
        return "";
    }

    @Override
    public boolean isSorted() {
        return true;
    }

    @Override
    public IListOfString interleave(IListOfString other) {
        return other;
    }

    @Override
    public boolean isAlphabeticallyConsecutive() {
        return true;
    }
}

/**
 * An immutable non-empty list of Strings.
 */
class NonEmptyListOfString implements IListOfString {
    private final String first;
    private final IListOfString rest;

    public NonEmptyListOfString(String first, IListOfString rest) {
        this.first = first;
        this.rest = rest;
    }

    // Space: O(n) because the stack will get n calls deep
    // Time: O(n) because there will be n method calls, each of which is O(1)
    @Override
    public int size() {
        return 1 + rest.size();
    }

    // Space: O(n) because the stack will get at most n calls deep
    // Time: O(n) because there will be up to n method calls, each of which is O(1)
    @Override
    public String get(int index) {
        if (index == 0) {
            return first;
        } else {
            return rest.get(index - 1);
        }
    }

    // Space: O(n) because the stack will get n calls deep
    // Time: O(n) because there will be n method calls, each of which is O(1)
    @Override
    public String combine() {
        return this.first + this.rest.combine();
    }

    // Space: O(n) because the stack will get n calls deep
    // Time: O(n) because there is a rest.get(i) that loops through the entire size at least once
    @Override
    public boolean isSorted() {
        if (first == null) {
            return false;
        }
        for (int i = 0; i < rest.size(); i++) {
            if (rest.get(i) == null) {
                return false;
            }
        }
        if (rest instanceof EmptyListOfString) {
            return true;
        }
        NonEmptyListOfString x = (NonEmptyListOfString) rest;
        if (first.compareToIgnoreCase(x.first) <= 0) {
            return rest.isSorted();
        }
        return false;
    }

    // Space: O(n) because the stack will get n calls deep
    // Time: O(n) because there is a for loop that loops through the entire size
    @Override
    public IListOfString interleave(IListOfString other) {
        if (other instanceof EmptyListOfString) {
            return this;
        }
        IListOfString interleft = new EmptyListOfString();
        int length = min(this.size(), other.size());
        if (this.size() > other.size()) {
            for (int i = this.size() - 1; i >= other.size(); i--) {
                interleft = new NonEmptyListOfString(this.get(i), interleft);
            }
        } else if (other.size() > this.size()) {
            for (int i = other.size() - 1; i >= this.size(); i--) {
                interleft = new NonEmptyListOfString(other.get(i), interleft);
            }
        }
        for (int i = length - 1; i >= 0; i--) {
            interleft = new NonEmptyListOfString(other.get(i), interleft);
            interleft = new NonEmptyListOfString(this.get(i), interleft);
        }
        return interleft;
    }

    private boolean isEngLowerCase(char c) {
        return (c >= 'a' && c <= 'z');
    }

    // Space: O(n) because the stack will get n calls deep
    // Time: O(n) because there is a for loop that loops through the entire size
    @Override
    public boolean isAlphabeticallyConsecutive() {
        if (first == null || rest == null) {
            return false;
        }
        if (first.isEmpty()) {
            return false;
        }
        if (first.equals(" ")) {
            return false;
        }
        if (!isEngLowerCase(first.toLowerCase().charAt(0))) {
            return false;
        }
        if (rest instanceof EmptyListOfString) {
            return true;
        }
        for (int i = 0; i < size() - 1; i++) {
            if (!isEngLowerCase(get(i + 1).toLowerCase().charAt(0))
                    || !isEngLowerCase(get(i).toLowerCase().charAt(0))) {
                return false;
            }
            if (get(i).toLowerCase().charAt(0) - get(i + 1).toLowerCase().charAt(0) != -1) {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean equals(Object other) {
        if (other instanceof IListOfString) {
            if (other instanceof EmptyListOfString || this.size() != ((IListOfString) other).size()) {
                return false;
            }
            return Objects.equals(this.combine(), ((IListOfString) other).combine());
        }
        return false;
    }
}
