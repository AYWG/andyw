package ca.ece.ubc.cpen221.mp5;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
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

import ca.ece.ubc.cpen221.mp5.QueryParser.OrderContext;
import ca.ece.ubc.cpen221.mp5.QueryRichParser.AndContext;
import ca.ece.ubc.cpen221.mp5.QueryRichParser.AndexprContext;
import ca.ece.ubc.cpen221.mp5.QueryRichParser.LowContext;
import ca.ece.ubc.cpen221.mp5.QueryRichParser.OperationContext;
import ca.ece.ubc.cpen221.mp5.QueryRichParser.OrContext;
import ca.ece.ubc.cpen221.mp5.QueryRichParser.OrexprContext;
import ca.ece.ubc.cpen221.mp5.QueryRichParser.PriceContext;
import ca.ece.ubc.cpen221.mp5.QueryRichParser.TermsContext;
import ca.ece.ubc.cpen221.mp5.QueryRichParser.UpContext;
import javafx.scene.text.Text;

public class QueryRichFactory {
    /*
     * @requires: a string with query
     * 
     * @returns: a String queue that has separated as the operation, and the the
     * criteria of the operation for rich queries.
     */
    public static Queue<String> richQuery(String string) {
        CharStream stream = new ANTLRInputStream(string);
        QueryRichLexer lexer = new QueryRichLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);
        QueryRichParser parser = new QueryRichParser(tokens);
        parser.reportErrorsAsExceptions();
        ParseTree tree = parser.root();

        System.err.println(tree.toStringTree(parser));
        ((RuleContext) tree).inspect(parser);

        ParseTreeWalker walker = new ParseTreeWalker();
        QueryRichListener listener = new QueryRichListenerDocumentCreator();
        walker.walk(listener, tree);
        return QueryRichListenerDocumentCreator.queue;
    }

    private static class QueryRichListenerDocumentCreator extends QueryRichBaseListener {
        public static Queue<String> queue = new LinkedList<String>();

        public void exitOperation(OperationContext ctx) {
            TerminalNode token = ctx.STRING();
            String text = token.getText();
            queue.add(text);
        }

        public void exitTerms(TermsContext ctx) {
            TerminalNode token = ctx.STRING();
            String text = token.getText();
            queue.add(text);
        }

        public void exitAnd(AndContext ctx) {
            TerminalNode token = ctx.AND();
            String text = token.getText();
            queue.add(text);
        }

        public void exitLow(LowContext ctx) {
            TerminalNode token = ctx.RANGE();
            String text = token.getText();
            queue.add(text);
        }

        public void exitUp(UpContext ctx) {
            TerminalNode token = ctx.RANGE();
            String text = token.getText();
            queue.add(text);
        }

        public void exitOr(OrContext ctx) {
            TerminalNode token = (TerminalNode) ctx.OR();
            String text = token.getText();
            queue.add(text);
        }
    }
}
