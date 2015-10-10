
/*
 * Implementation of a simple stack for HtmlTags.
 * You should implement this class.
 */

import java.util.ArrayList;

public class MyStack {
    // An ArrayList to hold HtmlTag objects.
    // Use this to implement StackMP2.
    private ArrayList<HtmlTag> stack_internal;

    /**
     * Create an empty stack.
     */
    public MyStack() {
        this.stack_internal = new ArrayList<HtmlTag>();
    }

    /**
     * Push a tag onto the top of the stack.
     * 
     * @param tag
     *            an HtmlTag object to be pushed on top of the stack
     * 
     */
    public void push(HtmlTag tag) {
        stack_internal.add(0, tag);
    }

    /**
     * Removes the tag at the top of the stack. Should throw an exception if the
     * stack is empty. @throws an IllegalStateException if stack is empty
     */
    public HtmlTag pop() {
        
        if (stack_internal.isEmpty()){
            throw new IllegalStateException();
        }
        return stack_internal.remove(0);
    }

    /**
     * Looks at the object at the top of the stack but does not actually remove
     * the object. Should throw an exception if the stack is empty.
     */
    public HtmlTag peek() {

        if (stack_internal.isEmpty()) {
            throw new IllegalStateException();
        }

        return stack_internal.get(0);
    }

    /*
     * Tests if the stack is empty. Returns true if the stack is empty; false
     * otherwise.
     */
    public boolean isEmpty() {
        return stack_internal.isEmpty();

    }
}
