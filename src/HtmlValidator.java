import java.util.*;

/*
 * This is the HtmlValidator class. You should implement this class.
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
     * M
     * 
     * @param tags
     */
    // should initialize your validator with an entirely separate copy of the
    // queue that was passed in. If the queue passed is null, you should throw
    // an IllegalArgumentException. An empty queue (size 0) is allowed.
    public HtmlValidator(Queue<HtmlTag> tags) {

        if (tags == null) {
            throw new IllegalArgumentException();
        }

        queueHtmlTag = new LinkedList<HtmlTag>();

        for (HtmlTag tag : tags) {
            queueHtmlTag.add(tag.clone());
        }

        // Queue<HtmlTag> queueHtmlTag = new LinkedList<HtmlTag>(tags);
        // new LinkedList(tags);
        // Queue <HtmlTag> tags = new LinkedList <HtmlTag>();
    }

    // In this method you should add the given tag to the end of your
    // validator's queue. If the tag passed is null, you should throw an
    // IllegalArgumentException.
    public void addTag(HtmlTag tag) {

        if (tag == null) {
            throw new IllegalArgumentException();
        }

        queueHtmlTag.add(tag);

    }

    /**
     * should return your validator's queue of HTML tags. The queue contains all
     * tags that were passed to the constructor (if any) in their proper order;
     * it should also reflect any changes made such as adding tags with addTag
     * or removing tags with removeAll.
     * 
     * @return
     * 
     */
    public Queue<HtmlTag> getTags() {

        return queueHtmlTag;
    }

    /**
     * In this method you should remove from your validator's queue any tags
     * that match the given element. Opening and closing tags removed. If
     * element does not exactly match any tags, queue should not be modified
     * 
     * @param element
     * @throws IllegalArgumentException
     */
    public void removeAll(String element) {

        if (element == null) {
            throw new IllegalArgumentException();
        }

        // for each tag in the queue, check if its element is equal to the given
        // element. If so, remove it from the queue
        Iterator<HtmlTag> iter = queueHtmlTag.iterator();
        while (iter.hasNext()) {
            HtmlTag tag = iter.next();
            if (tag.getElement().equals(element)) {
                iter.remove();
            }
        }

    }

    /**
     * Prints an indented text representation should print an indented text
     * representation of the HTML tags in your queue. Display each tag on its
     * own line. Every opening tag that requires a closing tag increases the
     * level of indentation of following tags by four spaces until its closing
     * tag is reached.
     */

    public void validate() {
        MyStack stackHtmlTag = new MyStack();

        int countIndent = 0;
        // four empty spaces for each indent
        String indent = "    ";

        for (HtmlTag tag : queueHtmlTag) {

            // Opening tag that's not self-closing
            if (tag.isOpenTag() == true && tag.isSelfClosing() == false) {
                // add to stack, print the indent, print the tag, go to next
                // line and increment indent
                stackHtmlTag.push(tag);

                for (int i = 0; i < countIndent; i++) {
                    System.out.print(indent);
                }

                System.out.println(tag.toString());
                countIndent++;

            } else if (tag.isOpenTag() == false) {

                // Error message when closing tag does not match most recently
                // opened tag or if there are no open tags at that point
                if (stackHtmlTag.isEmpty() == false && tag.matches(stackHtmlTag.peek()) == false) {
                    System.out.println("ERROR unexpected tag: " + tag.toString());
                    continue;
                }
                // Testcase4 is not successful due to unexpected closing tag + empty stack
                // remove tag from stack, decrease the indent, print the indent,
                // print the tag, go to next line,
                stackHtmlTag.pop();
                countIndent--;

                for (int i = 0; i < countIndent; i++) {
                    System.out.print(indent);
                }

                System.out.println(tag.toString());
            } else {
                // when tag is self-closing
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
