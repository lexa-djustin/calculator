package parser;

import parser.ast.BinaryExpression;
import parser.ast.Expression;
import parser.ast.NumberExpression;
import parser.ast.UnaryExpression;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final Token EOF = new Token(TokenType.EOF, "");

    private List<Token> tokens;

    private int position, size;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.size = tokens.size();
    }

    public List<Expression> parse() {
        List<Expression> result = new ArrayList<>();

        while (!this.match(TokenType.EOF)) {
            result.add(this.expression());
        }

        return result;
    }

    private Expression expression() {
        return this.additive();
    }

    private Expression additive() {
        Expression result = this.multiplicative();

        while (true) {
            if (this.match(TokenType.PLUS)) {
                result = new BinaryExpression('+', result, this.multiplicative());
                continue;
            }
            if (this.match(TokenType.MINUS)) {
                result = new BinaryExpression('-', result, this.multiplicative());
                continue;
            }

            break;
        }

        return result;
    }

    private Expression multiplicative() {
        Expression result = this.power();

        while (true) {
            if (this.match(TokenType.STAR)) {
                result = new BinaryExpression('*', result, this.power());
                continue;
            }
            if (this.match(TokenType.SLASH)) {
                result = new BinaryExpression('/', result, this.power());
                continue;
            }

            break;
        }

        return result;
    }

    private Expression power()
    {
        Expression result = this.unary();

        while (true) {
            if (this.match(TokenType.POW)) {
                result = new BinaryExpression('^', result, this.unary());
                continue;
            }

            break;
        }

        return result;
    }

    private Expression unary() {
        if (this.match(TokenType.MINUS)) {
            return new UnaryExpression('-', this.primary());
        }

        return this.primary();
    }

    private Expression primary() {
        Token current = this.get(0);

        if (this.match(TokenType.NUMBER)) {
            return new NumberExpression(Double.parseDouble(current.getText()));
        }

        if (this.match(TokenType.LPAREN)) {
            Expression result = this.expression();
            this.match(TokenType.RPAREN);

            return result;
        }

        throw new RuntimeException("Unknown expression");
    }

    private boolean match(TokenType type) {
        final Token current = this.get(0);

        if (type != current.getType()) {
            return false;
        }

        this.position++;
        return true;

    }

    private Token get(int relativePosition) {
        int position = this.position + relativePosition;

        if (position >= this.size) {
            return EOF;
        }

        return tokens.get(position);
    }
}
