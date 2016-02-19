grammar QueryRich;

// This puts "package formula;" at the top of the output Java files.
@header {
package ca.ece.ubc.cpen221.mp5;
}

// This adds code to the generated lexer and parser. Do not change these lines.
@members {
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
}

/*
 * These are the lexical rules. They define the tokens used by the lexer.
 *   *** Antlr requires tokens to be CAPITALIZED, like START_ITALIC, END_ITALIC, and TEXT.
 */

 
OR : '||';
AND : '&&';
RANGE : [1-5];
LPAREN : '(';
RPAREN : ')';
STRING : ~[()|&\".<>]+;
QUOTE : '\"';
DOTS: '..';
WHITESPACE : [ \t\r\n]+ -> skip ;

/*
 * These are the parser rules. They define the structures used by the parser.
 *    *** Antlr requires grammar nonterminals to be lowercase, like html, normal, and italic.
 */
root : orexpr EOF ;
orexpr : andexpr(OR andexpr)*;
andexpr: atom(AND atom)*;
atom: operation LPAREN order RPAREN;
operation: STRING;
order: (QUOTE terms QUOTE |price);
price: RANGE DOTS RANGE;
terms: STRING;





