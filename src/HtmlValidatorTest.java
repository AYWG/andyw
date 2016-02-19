import static org.junit.Assert.*;

import java.util.*;
import java.io.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HtmlValidatorTest {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(output));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
    }

    HtmlValidator testVal;
    HtmlValidator otherTestVal;
    Queue<HtmlTag> testQ;
    HtmlTag test_tag;
    HtmlTag test_tag2;
    HtmlTag test_tag3;
    HtmlTag test_tag4;
    HtmlTag test_tag5;
    HtmlTag test_tag6;

    @Test
    public void addTagTest() {

        testVal = new HtmlValidator();
        test_tag = new HtmlTag("table");
        test_tag2 = new HtmlTag("head");

        testVal.addTag(test_tag);
        assertEquals(testVal.getTags().peek(), test_tag);

        testVal.addTag(test_tag2);
        assertEquals(testVal.getTags().peek(), test_tag);

        testVal.getTags().remove();
        assertEquals(testVal.getTags().peek(), test_tag2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void addTagExceptionTest() {
        testVal = new HtmlValidator();
        test_tag = null;
        testVal.addTag(test_tag);
    }

    @Test
    public void getTagsTest() {

        testQ = new LinkedList<HtmlTag>();
        testVal = new HtmlValidator();
        assertEquals(testVal.getTags().equals(testQ), true);

        test_tag = new HtmlTag("table");
        test_tag2 = new HtmlTag("head");
        test_tag3 = new HtmlTag("head", false);
        test_tag4 = new HtmlTag("table", false);
        testVal.addTag(test_tag);
        testVal.addTag(test_tag2);
        testVal.addTag(test_tag3);
        testVal.addTag(test_tag4);

        testQ = testVal.getTags();
        otherTestVal = new HtmlValidator(testQ);

        assertEquals(testVal.getTags().equals(otherTestVal.getTags()), true);
        testVal.addTag(test_tag);
        assertEquals(testVal.getTags().equals(otherTestVal.getTags()), false);

    }

    @Test
    public void removeAllTest() {

        testQ = new LinkedList<HtmlTag>();
        testVal = new HtmlValidator();
        assertEquals(testVal.getTags().equals(testQ), true);

        test_tag = new HtmlTag("table");
        test_tag2 = new HtmlTag("head");
        test_tag3 = new HtmlTag("head", false);
        test_tag4 = new HtmlTag("table", false);
        testVal.addTag(test_tag);
        testVal.addTag(test_tag2);
        testVal.addTag(test_tag3);
        testVal.addTag(test_tag4);

        testQ = testVal.getTags();
        otherTestVal = new HtmlValidator(testQ);
        assertEquals(testVal.getTags().equals(otherTestVal.getTags()), true);

        testVal.removeAll("");
        assertEquals(testVal.getTags().equals(otherTestVal.getTags()), true);

        otherTestVal.removeAll("head");
        assertEquals(testVal.getTags().equals(otherTestVal.getTags()), false);

        testVal.addTag(test_tag3);
        testVal.removeAll("head");
        assertEquals(testVal.getTags().equals(otherTestVal.getTags()), true);

    }

    @Test(expected = IllegalArgumentException.class)
    public void removeAllExceptionTest() {
        testVal = new HtmlValidator();
        String testElement = null;
        testVal.removeAll(testElement);
    }

    @Test
    public void validateTest() {
        testVal = new HtmlValidator();

        test_tag = new HtmlTag("table");
        test_tag2 = new HtmlTag("head");
        test_tag3 = new HtmlTag("head", false);
        test_tag4 = new HtmlTag("table", false);
        testVal.addTag(test_tag);
        testVal.addTag(test_tag2);
        testVal.addTag(test_tag3);
        testVal.addTag(test_tag4);

        testVal.validate();
        String n = System.lineSeparator();
        String t = "    ";
        String s1 = "<table>" + n + t + "<head>" + n + t + "</head>" + n
                + "</table>" + n;
        assertEquals(s1, output.toString());
        
        output.reset();
        
        testVal.removeAll("table");
        testVal.removeAll("head");
        
        test_tag = new HtmlTag("html");
        test_tag2 = new HtmlTag("b");
        test_tag3 = new HtmlTag("i");
        test_tag4 = new HtmlTag("i", false);
        
        testVal.addTag(test_tag);
        testVal.addTag(test_tag2);
        testVal.addTag(test_tag3);
        testVal.addTag(test_tag4);
        testVal.validate();
        String s2 = "<html>" + n + t + "<b>" + n + t + t + "<i>" + n + t + t
                + "</i>" + n + "ERROR unclosed tag: <b>" + n
                + "ERROR unclosed tag: <html>" + n;
        assertEquals(s2, output.toString());
        
        output.reset();
        
        testVal.removeAll("html");
        testVal.removeAll("b");
        testVal.removeAll("i");
        
        test_tag5 = new HtmlTag("b", false);
        test_tag6 = new HtmlTag("html", false);
        
        testVal.addTag(test_tag2);
        testVal.addTag(test_tag3);
        testVal.addTag(test_tag5);
        testVal.addTag(test_tag4);
        testVal.addTag(test_tag5);
        testVal.addTag(test_tag6);
        
        testVal.validate();
        String s3 = "<b>" + n + t + "<i>" + n + "ERROR unexpected tag: </b>" + n + t + "</i>" + n + "</b>" + n + "ERROR unexpected tag: </html>" + n;
        assertEquals(s3, output.toString());
        
        output.reset();
        
        testVal.removeAll("html");
        testVal.removeAll("b");
        testVal.removeAll("i");
        
        testVal.validate();
        String s4 = "";
        assertEquals(s4, output.toString());

    }

}
