package pt.ua.deti.tqs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TqsStackTest {

    private TqsStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new TqsStack<Integer>();
    }

    @DisplayName("Test a) - isEmpty()")
    @Test
    void isEmpty() {
        assertEquals(true, stack.isEmpty());
    }

    @DisplayName("Test b) - sizeZero()")
    @Test
    void sizeZero() {
        assertEquals(0, stack.size());
    }

    @DisplayName("Test c) - push()")
    @Test
    void push() {

        int n = 5;
        for (int i = 0; i < n; i++) {
            stack.push(1);
        }

        assertEquals(false, stack.isEmpty());
        assertEquals(n, stack.size());
    }

    @DisplayName("Test d) - pop()")
    @Test
    void pop() {

        int x = 1;
        stack.push(1);

        assertEquals(x, stack.pop());
    }

    @DisplayName("Test e) - peek()")
    @Test
    void peek() {

        int x = 1;
        stack.push(1);

        assertEquals(x, stack.peek());
        assertEquals(1, stack.size());
    }

    @DisplayName("Test f) - size()")
    @Test
    void size() {

        int size = stack.size();
        for (int i = 0; i < size; i++) {
            stack.pop();
        }

        assertEquals(0, stack.size());
    }

    @DisplayName("Test g) - popEmptyStack()")
    @Test
    void popEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @DisplayName("Test h) - peekEmptyStack()")
    @Test
    void peekEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    @DisplayName("Test i) - pushFullStack()")
    @Test
    void pushFullStack() {
        stack.setLimit(0);
        assertThrows(IllegalStateException.class, () -> stack.push(1));
    }
}