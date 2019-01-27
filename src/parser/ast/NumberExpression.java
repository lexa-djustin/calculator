package parser.ast;

public class NumberExpression implements Expression {

    private double value;

    public NumberExpression(double value) {
        this.value = value;
    }

    @Override
    public double eval() {
        return this.value;
    }

    @Override
    public String toString() {
        return Double.toString(this.value);
    }
}
