
/**
 * Implementation of a simple stack for HtmlTags.
 */

import java.util.ArrayList;
import java.util.EmptyStackException;

public class MyStack {
    
    // An ArrayList to hold HtmlTag objects.
    // This will be used to implement the stack.
    private ArrayList<HtmlTag> stack_internal;

    /**
     * Create an empty stack.
     */
    public MyStack() {
        this.stack_internal = new ArrayList<HtmlTag>();
    }

    /**
     * Push a tag on to the top of the stack.
     * 
     * @param tag
     *            an HtmlTag object to be pushed on top of the stack.
     * 
     */
    public void push(HtmlTag tag) {
        stack_internal.add(0, tag);
    }

    /**
     * Removes the tag at the top of the stack.
     * 
     * @return a reference to the HtmlTag object that was at the top of the
     *         stack before the method was called.
     * @throws EmptyStackException
     *             if stack is empty.
     */
    public HtmlTag pop() {

        if (stack_internal.isEmpty()) {
            throw new EmptyStackException();
        }
        return stack_internal.remove(0);
    }

    /**
     * Looks at the HtmlTag at the top of the stack but does not actually remove
     * it from the stack.
     * 
     * @return a reference to the HtmlTag object at the top of the stack.
     * @throws EmptyStackException
     *             if stack is empty.
     * 
     */
    public HtmlTag peek() {

        if (stack_internal.isEmpty()) {
            throw new EmptyStackException();
        }

        return stack_internal.get(0);
    }

    /**
     * Tests if the stack is empty. Returns true if the stack is empty and false
     * otherwise.
     * @return true if stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return stack_internal.isEmpty();

    }
}
