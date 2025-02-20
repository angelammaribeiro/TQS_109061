import org.example.TqsStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TqsStackTest {
    TqsStack<Integer> tqsStack;

    @BeforeEach
    void setup() {
        tqsStack = new TqsStack<>();
    }

    @Test
    public void isEmptyOnConstructionTest(){
        Assertions.assertTrue(tqsStack.isEmpty());
    }

    @Test
    public void sizeZeroOnConstructionTest(){
        Assertions.assertEquals(0, tqsStack.size());
    }

    @Test
    public void populateStackTest(){
        tqsStack.push(1);
        tqsStack.push(2);
        tqsStack.push(3);

        Assertions.assertFalse(tqsStack.isEmpty());
        Assertions.assertEquals(3, tqsStack.size());
    }

    @Test
    public void pushAndPopTest(){
        tqsStack.push(1);
        tqsStack.push(2);
        tqsStack.pop();

        Assertions.assertEquals(1, tqsStack.peek());

    }

    @Test
    public void peekTest(){
        tqsStack.push(1);
        tqsStack.push(2);

        Assertions.assertEquals(2, tqsStack.peek());
        Assertions.assertEquals(2, tqsStack.size());
    }

    @Test
    public void sizeAfterNPopTest(){
        tqsStack.push(1);
        tqsStack.push(2);
        tqsStack.push(3);

        tqsStack.pop();
        tqsStack.pop();
        tqsStack.pop();

        Assertions.assertTrue(tqsStack.isEmpty());
        Assertions.assertEquals(0, tqsStack.size());
    }

    @Test
    public void popEmptyStackTest(){
        Assertions.assertThrows(java.util.NoSuchElementException.class, tqsStack::pop);
    }

    @Test
    public void peekEmptyStackTest(){
        Assertions.assertThrows(java.util.NoSuchElementException.class, tqsStack::peek);
    }

    @Test
    public void boundedStacksTest(){
        TqsStack<Integer> new_tqsStack = new TqsStack<>(4);
        if (tqsStack.isBounded()){
            for (int i = 0; i <= tqsStack.getBound(); i++){
                tqsStack.push(i);
            }

            Assertions.assertThrows(IllegalStateException.class, () -> {tqsStack.push(1);});
        }
    }

    @Test
    public void PopTopNTest(){
        tqsStack.push(1);
        tqsStack.push(2);
        tqsStack.push(3);
        tqsStack.push(4);

        Assertions.assertEquals(2,tqsStack.popTopN(3));
    }
}
