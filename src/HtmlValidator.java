import java.util.*;

/**
 * This class is used to determine if an HTML file represents a valid sequence
 * of tags.
 */
public class HtmlValidator {

    // declare queue to be used by HtmlValidator for storing HTML tags
    private Queue<HtmlTag> queueHtmlTag;

    /**
     * Constructs an empty queue for storing HtmlTag objects
     */
    public HtmlValidator() {
        queueHtmlTag = new LinkedList<HtmlTag>();
    }

    /**
     * Constructs an entirely separate copy of <b>tags</b>
     * 
     * @param tags
     *            a queue containing 0 or more HtmlTag objects.
     * @throws IllegalArgumentException
     *             if tags is null.
     */
    public HtmlValidator(Queue<HtmlTag> tags) {

        if (tags == null) {
            throw new IllegalArgumentException();
        }

        queueHtmlTag = new LinkedList<HtmlTag>();

        for (HtmlTag tag : tags) {
            queueHtmlTag.add(tag.clone());
        }

    }

    /**
     * Adds the given <b>tag</b> to the end of the HtmlValidator queue of HTML
     * tags.
     * 
     * @param tag
     *            the HtmlTag object to be added to the end of the validator's
     *            queue of HTML tags.
     * @throws IllegalArgumentExcetion
     *             if <b>tag</b> is null
     */
    public void addTag(HtmlTag tag) {

        if (tag == null) {
            throw new IllegalArgumentException();
        }

        queueHtmlTag.add(tag);

    }

    /**
     * Returns HtmlValidator's queue of HTML tags. All tags that were passed to
     * the constructor (if any) are in their proper order. Any changes made to
     * the queue (adding tags, removing tags) are reflected in the queue.
     * 
     * @return HtmlValidator's queue of HtmlTag objects.
     * 
     */
    public Queue<HtmlTag> getTags() {

        return queueHtmlTag;
    }

    /**
     * Removes from the validator's queue any tags that match the given element.
     * This includes both opening and closing tags. If element does not exactly
     * match any tags, the queue should not be modified.
     * 
     * @param element
     *            of the tags that are to be removed
     * @throws IllegalArgumentException
     *             if <b>element</b> is null
     */
    public void removeAll(String element) {

        if (element == null) {
            throw new IllegalArgumentException();
        }

        // Iterate through each tag in the queue and check if the tag's element
        // matches the given element. If so, remove it from the queue
        Iterator<HtmlTag> iter = queueHtmlTag.iterator();
        while (iter.hasNext()) {
            HtmlTag tag = iter.next();
            if (tag.getElement().equals(element)) {
                iter.remove();
            }
        }

    }

    /**
     * Prints an indented text representation of the HTML tags in the
     * validator's queue. Each tag is displayed on its own line. Every opening
     * tag that requires a closing tag increases the level of indentation of
     * following tags by four spaces until its closing tag is reached (which
     * then decreases the indentation by four spaces).
     * 
     * Prints an error message next to a closing tag if it does not match the
     * most recently opened tag (or if there are currently no open tags).
     * 
     * Prints an error message next to any tags that are still open after the
     * end of the HTML is reached.
     */

    public void validate() {

        // Stack for organizing the indentation algorithm
        MyStack stackHtmlTag = new MyStack();

        // four empty spaces for each indent
        String indent = "    ";

        int countIndent = 0;

        for (HtmlTag tag : queueHtmlTag) {

            // Check if tag is opening and not self-closing
            if (tag.isOpenTag() == true && tag.isSelfClosing() == false) {

                // Add tag to stack, print the current indentation level, print
                // the tag, go to next line and increase indent

                stackHtmlTag.push(tag);

                for (int i = 0; i < countIndent; i++) {
                    System.out.print(indent);
                }

                System.out.println(tag.toString());
                countIndent++;

                // Check if tag is closing
            } else if (tag.isOpenTag() == false) {

                // If stack is empty, then there is currently no unclosed tag
                // that matches the closing tag; print error
                if (stackHtmlTag.isEmpty()) {
                    System.out.println("ERROR unexpected tag: " + tag.toString());
                    continue;
                }

                // If the tag on top of the stack (most recently opened tag)
                // does not match the closing tag, then print error.
                else if (tag.matches(stackHtmlTag.peek()) == false) {
                    System.out.println("ERROR unexpected tag: " + tag.toString());
                    continue;

                } else {

                    // Remove tag from stack, decrease the indent, print the
                    // indent, print the tag, go to next line.

                    stackHtmlTag.pop();
                    countIndent--;

                    for (int i = 0; i < countIndent; i++) {
                        System.out.print(indent);
                    }

                    System.out.println(tag.toString());
                }
                // when tag is self-closing
            } else {

                for (int i = 0; i < countIndent; i++) {
                    System.out.print(indent);
                }
                System.out.println(tag.toString());
            }

        }

        // Print error message for each tag that is unclosed;
        // each unclosed tag should still be on the stack.
        while (stackHtmlTag.isEmpty() == false) {
            System.out.println("ERROR unclosed tag: " + stackHtmlTag.pop().toString());
        }

    }
}
