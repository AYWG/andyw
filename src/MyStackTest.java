import static org.junit.Assert.*;

import org.junit.Test;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class MyStackTest {

    MyStack test_stack;
    HtmlTag test_tag;
    HtmlTag test_tag2;

    @Test
    public void pushTest() {

        test_stack = new MyStack();
        test_tag = new HtmlTag("html");
        test_tag2 = new HtmlTag("title");
        
        test_stack.push(test_tag);
        assertEquals(test_stack.peek(), test_tag);
        
        test_stack.push(test_tag2);
        assertEquals(test_stack.peek(), test_tag2);
        
    }
    
    @Test
    public void popTest() {
        
        test_stack = new MyStack();
        test_tag = new HtmlTag("html");
        test_tag2 = new HtmlTag("title");
        
        test_stack.push(test_tag2);
        test_stack.push(test_tag);
        
        test_stack.pop();
        assertEquals(test_stack.peek(), test_tag2);
        
        
    }
    
    @Test(expected = EmptyStackException.class)
    public void popExceptionTest() {
        test_stack = new MyStack();
        test_stack.pop();
    }
    
    @Test
    public void peekTest() {
        
        test_stack = new MyStack();
        test_tag = new HtmlTag("html");
        test_tag2 = new HtmlTag("title");
        
        test_stack.push(test_tag);
        assertEquals(test_stack.peek(), test_tag);
        
        test_stack.push(test_tag2);
        assertEquals(test_stack.peek(), test_tag2);
        
        
    }
    
    @Test(expected = EmptyStackException.class)
    public void peekExceptionTest() {
        test_stack = new MyStack();
        test_stack.peek();
    }
    
    @Test
    public void isEmptyTest() {
        
        test_stack = new MyStack();
        assertEquals(test_stack.isEmpty(), true);
        
        test_tag = new HtmlTag("html");
        test_stack.push(test_tag);
        assertEquals(test_stack.isEmpty(), false);
        
    }

}
