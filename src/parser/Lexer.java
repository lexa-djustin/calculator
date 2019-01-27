package parser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private static final String OPERATOR_CHARS = "+-*/()^";

    private static final TokenType[] OPERATOR_TOKENS = {
            TokenType.PLUS, TokenType.MINUS,
            TokenType.STAR, TokenType.SLASH,
            TokenType.LPAREN, TokenType.RPAREN,
            TokenType.POW,
    };

    private String input;

    private List<Token> tokens;

    private int position, length;

    public Lexer(String input) {
        this.input = input;
        this.length = input.length();
        this.tokens = new ArrayList<>();
    }

    public List<Token> tokenize() {
        while (this.position < this.length) {
            char current = this.peek(0);

            if (Character.isDigit(current)) {
                this.tokenizeNumber();
            } else if (OPERATOR_CHARS.indexOf(current) != -1) {
                this.tokenizeOperator();
            } else {
                this.next();
            }
        }

        return this.tokens;
    }

    private void tokenizeNumber() {
        char current = this.peek(0);
        StringBuilder buffer = new StringBuilder();

        while (Character.isDigit(current)) {
            buffer.append(current);
            current = this.next();
        }

        this.addToken(TokenType.NUMBER, buffer.toString());
    }

    private void tokenizeOperator() {
        int position = OPERATOR_CHARS.indexOf(this.peek(0));

        this.addToken(OPERATOR_TOKENS[position]);
        this.next();
    }

    private void addToken(TokenType type) {
        this.addToken(type, "");
    }

    private void addToken(TokenType type, String text) {
        this.tokens.add(new Token(type, text));
    }

    private char peek(int relativePosition) {
        int position = this.position + relativePosition;

        if (position >= this.length) {
            return '\0';
        }

        return this.input.charAt(position);
    }

    private char next() {
        this.position++;
        return this.peek(0);
    }
}
