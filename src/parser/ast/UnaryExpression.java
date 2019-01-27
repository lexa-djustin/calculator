package parser.ast;

public class UnaryExpression implements Expression {

    private char operation;

    private Expression expr;

    public UnaryExpression(char operation, Expression expr) {
        this.operation = operation;
        this.expr = expr;
    }

    @Override
    public double eval() {
        switch (this.operation) {
            case '-':
                return -this.expr.eval();
            case '+':
            default:
                return this.expr.eval();
        }
    }

    @Override
    public String toString() {
        return String.format("%c %s", this.operation, this.expr);
    }
}
