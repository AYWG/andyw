// Generated from QueryRich.g4 by ANTLR 4.4

package ca.ece.ubc.cpen221.mp5;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QueryRichParser}.
 */
public interface QueryRichListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#or}.
	 * @param ctx the parse tree
	 */
	void enterOr(@NotNull QueryRichParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#or}.
	 * @param ctx the parse tree
	 */
	void exitOr(@NotNull QueryRichParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#andexpr}.
	 * @param ctx the parse tree
	 */
	void enterAndexpr(@NotNull QueryRichParser.AndexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#andexpr}.
	 * @param ctx the parse tree
	 */
	void exitAndexpr(@NotNull QueryRichParser.AndexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#high}.
	 * @param ctx the parse tree
	 */
	void enterHigh(@NotNull QueryRichParser.HighContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#high}.
	 * @param ctx the parse tree
	 */
	void exitHigh(@NotNull QueryRichParser.HighContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#low}.
	 * @param ctx the parse tree
	 */
	void enterLow(@NotNull QueryRichParser.LowContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#low}.
	 * @param ctx the parse tree
	 */
	void exitLow(@NotNull QueryRichParser.LowContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#terms}.
	 * @param ctx the parse tree
	 */
	void enterTerms(@NotNull QueryRichParser.TermsContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#terms}.
	 * @param ctx the parse tree
	 */
	void exitTerms(@NotNull QueryRichParser.TermsContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#and}.
	 * @param ctx the parse tree
	 */
	void enterAnd(@NotNull QueryRichParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#and}.
	 * @param ctx the parse tree
	 */
	void exitAnd(@NotNull QueryRichParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#price}.
	 * @param ctx the parse tree
	 */
	void enterPrice(@NotNull QueryRichParser.PriceContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#price}.
	 * @param ctx the parse tree
	 */
	void exitPrice(@NotNull QueryRichParser.PriceContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(@NotNull QueryRichParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(@NotNull QueryRichParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#orexpr}.
	 * @param ctx the parse tree
	 */
	void enterOrexpr(@NotNull QueryRichParser.OrexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#orexpr}.
	 * @param ctx the parse tree
	 */
	void exitOrexpr(@NotNull QueryRichParser.OrexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#up}.
	 * @param ctx the parse tree
	 */
	void enterUp(@NotNull QueryRichParser.UpContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#up}.
	 * @param ctx the parse tree
	 */
	void exitUp(@NotNull QueryRichParser.UpContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(@NotNull QueryRichParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(@NotNull QueryRichParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterOperation(@NotNull QueryRichParser.OperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitOperation(@NotNull QueryRichParser.OperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryRichParser#order}.
	 * @param ctx the parse tree
	 */
	void enterOrder(@NotNull QueryRichParser.OrderContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryRichParser#order}.
	 * @param ctx the parse tree
	 */
	void exitOrder(@NotNull QueryRichParser.OrderContext ctx);
}