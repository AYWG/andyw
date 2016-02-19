// Generated from Query.g4 by ANTLR 4.4

package ca.ece.ubc.cpen221.mp5;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QueryParser}.
 */
public interface QueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull QueryParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull QueryParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(@NotNull QueryParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(@NotNull QueryParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterOperation(@NotNull QueryParser.OperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitOperation(@NotNull QueryParser.OperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#order}.
	 * @param ctx the parse tree
	 */
	void enterOrder(@NotNull QueryParser.OrderContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#order}.
	 * @param ctx the parse tree
	 */
	void exitOrder(@NotNull QueryParser.OrderContext ctx);
}