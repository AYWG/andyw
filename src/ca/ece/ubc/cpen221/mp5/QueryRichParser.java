// Generated from QueryRich.g4 by ANTLR 4.4

package ca.ece.ubc.cpen221.mp5;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryRichParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OR=1, AND=2, RANGE=3, LPAREN=4, RPAREN=5, STRING=6, QUOTE=7, DOTS=8, WHITESPACE=9;
	public static final String[] tokenNames = {
		"<INVALID>", "'||'", "'&&'", "RANGE", "'('", "')'", "STRING", "'\"'", 
		"'..'", "WHITESPACE"
	};
	public static final int
		RULE_root = 0, RULE_orexpr = 1, RULE_or = 2, RULE_andexpr = 3, RULE_and = 4, 
		RULE_atom = 5, RULE_operation = 6, RULE_order = 7, RULE_price = 8, RULE_low = 9, 
		RULE_high = 10, RULE_up = 11, RULE_terms = 12;
	public static final String[] ruleNames = {
		"root", "orexpr", "or", "andexpr", "and", "atom", "operation", "order", 
		"price", "low", "high", "up", "terms"
	};

	@Override
	public String getGrammarFileName() { return "QueryRich.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    // This method makes the lexer or parser stop running if it encounters
	    // invalid input and throw a RuntimeException.
	    public void reportErrorsAsExceptions() {
	        //removeErrorListeners();
	        
	        addErrorListener(new ExceptionThrowingErrorListener());
	    }
	    
	    private static class ExceptionThrowingErrorListener extends BaseErrorListener {
	        @Override
	        public void syntaxError(Recognizer<?, ?> recognizer,
	                Object offendingSymbol, int line, int charPositionInLine,
	                String msg, RecognitionException e) {
	            throw new RuntimeException(msg);
	        }
	    }

	public QueryRichParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RootContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(QueryRichParser.EOF, 0); }
		public OrexprContext orexpr() {
			return getRuleContext(OrexprContext.class,0);
		}
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterRoot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitRoot(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26); orexpr();
			setState(27); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrexprContext extends ParserRuleContext {
		public OrContext or(int i) {
			return getRuleContext(OrContext.class,i);
		}
		public AndexprContext andexpr(int i) {
			return getRuleContext(AndexprContext.class,i);
		}
		public List<OrContext> or() {
			return getRuleContexts(OrContext.class);
		}
		public List<AndexprContext> andexpr() {
			return getRuleContexts(AndexprContext.class);
		}
		public OrexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterOrexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitOrexpr(this);
		}
	}

	public final OrexprContext orexpr() throws RecognitionException {
		OrexprContext _localctx = new OrexprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_orexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29); andexpr();
			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(30); or();
				setState(31); andexpr();
				}
				}
				setState(37);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrContext extends ParserRuleContext {
		public TerminalNode OR() { return getToken(QueryRichParser.OR, 0); }
		public OrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_or; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitOr(this);
		}
	}

	public final OrContext or() throws RecognitionException {
		OrContext _localctx = new OrContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_or);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38); match(OR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndexprContext extends ParserRuleContext {
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AndContext and(int i) {
			return getRuleContext(AndContext.class,i);
		}
		public List<AndContext> and() {
			return getRuleContexts(AndContext.class);
		}
		public AndexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterAndexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitAndexpr(this);
		}
	}

	public final AndexprContext andexpr() throws RecognitionException {
		AndexprContext _localctx = new AndexprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_andexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40); atom();
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(41); and();
				setState(42); atom();
				}
				}
				setState(48);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(QueryRichParser.AND, 0); }
		public AndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitAnd(this);
		}
	}

	public final AndContext and() throws RecognitionException {
		AndContext _localctx = new AndContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_and);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49); match(AND);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(QueryRichParser.LPAREN, 0); }
		public OrderContext order() {
			return getRuleContext(OrderContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(QueryRichParser.RPAREN, 0); }
		public OperationContext operation() {
			return getRuleContext(OperationContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitAtom(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_atom);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51); operation();
			setState(52); match(LPAREN);
			setState(53); order();
			setState(54); match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperationContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(QueryRichParser.STRING, 0); }
		public OperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitOperation(this);
		}
	}

	public final OperationContext operation() throws RecognitionException {
		OperationContext _localctx = new OperationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_operation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56); match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrderContext extends ParserRuleContext {
		public TermsContext terms() {
			return getRuleContext(TermsContext.class,0);
		}
		public PriceContext price() {
			return getRuleContext(PriceContext.class,0);
		}
		public List<TerminalNode> QUOTE() { return getTokens(QueryRichParser.QUOTE); }
		public TerminalNode QUOTE(int i) {
			return getToken(QueryRichParser.QUOTE, i);
		}
		public OrderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_order; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterOrder(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitOrder(this);
		}
	}

	public final OrderContext order() throws RecognitionException {
		OrderContext _localctx = new OrderContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_order);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			switch (_input.LA(1)) {
			case QUOTE:
				{
				setState(58); match(QUOTE);
				setState(59); terms();
				setState(60); match(QUOTE);
				}
				break;
			case RANGE:
				{
				setState(62); price();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PriceContext extends ParserRuleContext {
		public LowContext low() {
			return getRuleContext(LowContext.class,0);
		}
		public HighContext high() {
			return getRuleContext(HighContext.class,0);
		}
		public PriceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_price; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterPrice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitPrice(this);
		}
	}

	public final PriceContext price() throws RecognitionException {
		PriceContext _localctx = new PriceContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_price);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65); low();
			setState(66); high();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LowContext extends ParserRuleContext {
		public TerminalNode RANGE() { return getToken(QueryRichParser.RANGE, 0); }
		public LowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_low; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterLow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitLow(this);
		}
	}

	public final LowContext low() throws RecognitionException {
		LowContext _localctx = new LowContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_low);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68); match(RANGE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HighContext extends ParserRuleContext {
		public TerminalNode DOTS() { return getToken(QueryRichParser.DOTS, 0); }
		public UpContext up() {
			return getRuleContext(UpContext.class,0);
		}
		public HighContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_high; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterHigh(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitHigh(this);
		}
	}

	public final HighContext high() throws RecognitionException {
		HighContext _localctx = new HighContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_high);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70); match(DOTS);
			setState(71); up();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UpContext extends ParserRuleContext {
		public TerminalNode RANGE() { return getToken(QueryRichParser.RANGE, 0); }
		public UpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_up; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterUp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitUp(this);
		}
	}

	public final UpContext up() throws RecognitionException {
		UpContext _localctx = new UpContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_up);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73); match(RANGE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermsContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(QueryRichParser.STRING, 0); }
		public TermsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terms; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).enterTerms(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryRichListener ) ((QueryRichListener)listener).exitTerms(this);
		}
	}

	public final TermsContext terms() throws RecognitionException {
		TermsContext _localctx = new TermsContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_terms);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75); match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\13P\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3$\n\3\f\3\16\3"+
		"\'\13\3\3\4\3\4\3\5\3\5\3\5\3\5\7\5/\n\5\f\5\16\5\62\13\5\3\6\3\6\3\7"+
		"\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5\tB\n\t\3\n\3\n\3\n\3\13"+
		"\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\2\2\17\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\2\2E\2\34\3\2\2\2\4\37\3\2\2\2\6(\3\2\2\2\b*\3\2\2\2\n\63\3"+
		"\2\2\2\f\65\3\2\2\2\16:\3\2\2\2\20A\3\2\2\2\22C\3\2\2\2\24F\3\2\2\2\26"+
		"H\3\2\2\2\30K\3\2\2\2\32M\3\2\2\2\34\35\5\4\3\2\35\36\7\2\2\3\36\3\3\2"+
		"\2\2\37%\5\b\5\2 !\5\6\4\2!\"\5\b\5\2\"$\3\2\2\2# \3\2\2\2$\'\3\2\2\2"+
		"%#\3\2\2\2%&\3\2\2\2&\5\3\2\2\2\'%\3\2\2\2()\7\3\2\2)\7\3\2\2\2*\60\5"+
		"\f\7\2+,\5\n\6\2,-\5\f\7\2-/\3\2\2\2.+\3\2\2\2/\62\3\2\2\2\60.\3\2\2\2"+
		"\60\61\3\2\2\2\61\t\3\2\2\2\62\60\3\2\2\2\63\64\7\4\2\2\64\13\3\2\2\2"+
		"\65\66\5\16\b\2\66\67\7\6\2\2\678\5\20\t\289\7\7\2\29\r\3\2\2\2:;\7\b"+
		"\2\2;\17\3\2\2\2<=\7\t\2\2=>\5\32\16\2>?\7\t\2\2?B\3\2\2\2@B\5\22\n\2"+
		"A<\3\2\2\2A@\3\2\2\2B\21\3\2\2\2CD\5\24\13\2DE\5\26\f\2E\23\3\2\2\2FG"+
		"\7\5\2\2G\25\3\2\2\2HI\7\n\2\2IJ\5\30\r\2J\27\3\2\2\2KL\7\5\2\2L\31\3"+
		"\2\2\2MN\7\b\2\2N\33\3\2\2\2\5%\60A";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}