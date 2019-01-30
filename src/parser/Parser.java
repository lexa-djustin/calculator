package parser;

import parser.ast.*;

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

    public List<Statement> parse() {
        List<Statement> result = new ArrayList<>();

        while (!this.match(TokenType.EOF)) {
            result.add(this.statement());
        }

        return result;
    }

    private Statement statement() {
        if (this.match(TokenType.PRINT)){
            return new PrintStatement(this.expression());
        }

        return this.assignmentStatement();
    }

    private Statement assignmentStatement() {
        Token current = this.get(0);

        if (this.match(TokenType.WORD) && get(0).getType() == TokenType.EQ) {
            String variable = current.getText();
            this.consume(TokenType.EQ);

            return new AssignmentStatement(variable, this.expression());
        }

        throw new RuntimeException("Unknown operator");
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

    private Expression power() {
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

        if (this.match(TokenType.WORD)) {
            return new VariableExpression(current.getText());
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

    private Token consume(TokenType type) {
        Token current = this.get(0);

        if (current.getType() != type) {
            throw new RuntimeException("Token " + current + " does'n match " + type);
        }

        this.position++;

        return current;
    }

    private Token get(int relativePosition) {
        int position = this.position + relativePosition;

        if (position >= this.size) {
            return EOF;
        }

        return tokens.get(position);
    }
}
