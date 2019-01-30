package parser.ast;

public class PrintStatement implements Statement {

    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        System.out.println(this.expression.eval());
    }

    @Override
    public String toString() {
        return "print " + this.expression;
    }
}
