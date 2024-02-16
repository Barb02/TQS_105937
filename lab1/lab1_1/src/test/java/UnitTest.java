import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pt.ua.TqsStack;

public class UnitTest {

    TqsStack<Integer> stack;
    
    @BeforeEach
    public void setUp() {
        stack = new TqsStack<Integer>();
    }

    @Test
    public void emptyStackConstruct(){
        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    public void sizeConstruct(){
        Assertions.assertEquals(0, stack.size());
    }
}
