import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.pt.ua.TqsStack;

public class UnitTest {

    TqsStack<Integer> stack;
    
    @BeforeEach
    public void setUp() {
        stack = new TqsStack<Integer>();
    }

    @AfterEach
    public void tearDown() {
        stack = null;
    }

    @Test
    public void emptyStackConstruct(){
        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    public void sizeConstruct(){
        Assertions.assertEquals(0, stack.size());
    }

    @Test
    public void pushN(){
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Assertions.assertFalse(stack.isEmpty());
        Assertions.assertEquals(3, stack.size());
    }

    @Test
    public void popElement(){
        stack.push(3);
        stack.push(5);
        Assertions.assertEquals(5, stack.pop());
        Assertions.assertEquals(3, stack.pop());
    }

    @Test
    public void peekElement(){
        stack.push(4);
        Assertions.assertEquals(4, stack.peek());
        Assertions.assertEquals(1, stack.size());
    }

    @Test
    public void popN(){
        stack.push(1);
        stack.push(9);
        stack.push(7);
        int size = stack.size();
        for(int i = 0; i < size; i++){
            stack.pop();
        }
        Assertions.assertTrue(stack.isEmpty());
        Assertions.assertEquals(0, stack.size());
    }

    @Test
    public void popEmpty(){
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @Test
    public void peekEmpty(){
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        });
    }
    
}
