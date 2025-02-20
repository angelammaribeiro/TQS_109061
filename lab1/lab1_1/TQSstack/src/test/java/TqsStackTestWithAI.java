
import org.example.TqsStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TqsStackTestWithAI {

    private TqsStack<Integer> stack;
    private TqsStack<Integer> boundedStack;

    @BeforeEach
    void setUp() {
        stack = new TqsStack<>(); // Unbounded stack
        boundedStack = new TqsStack<>(2); // Bounded stack with capacity 2
    }

    @Test
    void testStackIsEmptyOnCreation() {
        assertTrue(stack.isEmpty(), "Stack should be empty on creation");
        assertEquals(0, stack.size(), "Stack size should be 0");
    }

    @Test
    void testPushIncreasesSize() {
        stack.push(1);
        assertFalse(stack.isEmpty(), "Stack should not be empty after push");
        assertEquals(1, stack.size(), "Stack size should be 1 after push");
    }

    @Test
    void testPushAndPopReturnsCorrectValues() {
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.pop(), "Popped value should be the last pushed value");
        assertEquals(10, stack.pop(), "Next pop should return the previous value");
    }

    @Test
    void testPeekDoesNotRemoveElement() {
        stack.push(5);
        int top = stack.peek();
        assertEquals(5, top, "Peek should return the top element");
        assertEquals(1, stack.size(), "Size should remain the same after peek");
    }

    @Test
    void testPopOnEmptyStackThrowsException() {
        assertThrows(NoSuchElementException.class, stack::pop, "Pop on empty stack should throw exception");
    }

    @Test
    void testPeekOnEmptyStackThrowsException() {
        assertThrows(NoSuchElementException.class, stack::peek, "Peek on empty stack should throw exception");
    }

    @Test
    void testBoundedStackRespectsCapacity() {
        boundedStack.push(1);
        boundedStack.push(2);
        assertEquals(2, boundedStack.size(), "Bounded stack should contain 2 elements");

        assertThrows(NoSuchElementException.class, () -> boundedStack.push(3), "Pushing beyond limit should throw exception");
    }

    @Test
    void testBoundedStackPopsCorrectly() {
        boundedStack.push(100);
        boundedStack.push(200);
        assertEquals(200, boundedStack.pop(), "First pop should return last pushed value");
        assertEquals(100, boundedStack.pop(), "Next pop should return the previous value");
        assertTrue(boundedStack.isEmpty(), "Stack should be empty after popping all elements");
    }
}
