package parser.ast;

import static java.lang.Math.pow;

public class BinaryExpression implements Expression {

    private char operation;

    private Expression expr1, expr2;

    public BinaryExpression(char operation, Expression expr1, Expression expr2) {
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
        int i = 0;

    }

    @Override
    public double eval() {
        switch (this.operation) {
            case '-':
                return this.expr1.eval() - this.expr2.eval();
            case '*':
                return this.expr1.eval() * this.expr2.eval();
            case '/':
                return this.expr1.eval() / this.expr2.eval();
            case '^':
                return pow(this.expr1.eval(), this.expr2.eval());
            case '+':
            default:
                return this.expr1.eval() + this.expr2.eval();
        }
    }

    @Override
    public String toString() {
        return String.format("[%s %c %s]", this.expr1, this.operation, this.expr2);
    }
}
