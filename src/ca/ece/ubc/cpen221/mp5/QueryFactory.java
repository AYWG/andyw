package ca.ece.ubc.cpen221.mp5;

import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.sun.xml.internal.txw2.Document;

import ca.ece.ubc.cpen221.mp5.QueryParser.OperationContext;
import ca.ece.ubc.cpen221.mp5.QueryParser.OrderContext;

public class QueryFactory {

    /*
     * @requires: a string with query
     * 
     * @returns: a String array where the index 0 is the
     * command and index 1 is the criteria
     */

    public static String[] query(String query) {
        String[] stringArray = new String[2];
        CharStream stream = new ANTLRInputStream(query);
        QueryLexer lexer = new QueryLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);
        QueryParser parser = new QueryParser(tokens);
        parser.reportErrorsAsExceptions();
        ParseTree tree = parser.root();

        System.err.println(tree.toStringTree(parser));
        ((RuleContext) tree).inspect(parser);

        ParseTreeWalker walker = new ParseTreeWalker();
        QueryListener listener = new QueryListenerDocumentCreator();
        walker.walk(listener, tree);

        stringArray[0] = QueryListenerDocumentCreator.getOperation();
        stringArray[1] = QueryListenerDocumentCreator.getOrder();

        return stringArray;

    }

    private static class QueryListenerDocumentCreator extends QueryBaseListener {
        public static String order;
        public static String operation;

        public void exitOrder(OrderContext ctx) {
            TerminalNode token = ctx.STRING();
            String text = token.getText();
            order = text;
        }

        public void exitOperation(OperationContext ctx) {
            TerminalNode token = ctx.STRING();
            String text = token.getText();
            operation = text;

        }

        public static String getOperation() {
            if (isLegalOperation(operation))
                return operation;
            else
                return null;
        }

        public static String getOrder() {
            return order;
        }

        /*
         * @requires: operation String parsed from quary
         * 
         * @returns: if it is one of the commands given returns false if is
         * not a valid operation
         * 
         */
        public static boolean isLegalOperation(String operation) {
            if (operation.equals("randomReview")) {
                return true;
            }

            if (operation.equals("getRestaurant")) {
                return true;
            }

            if (operation.equals("addRestaurant")) {
                return true;
            }

            if (operation.equals("addUser")) {
                return true;
            }

            if (operation.equals("addReview")) {
                return true;
            }

            else
                return false;
        }
    }
}
