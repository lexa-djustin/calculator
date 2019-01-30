package parser.ast;

import lib.Variables;

public class AssignmentStatement implements Statement {

    private String variable;

    private Expression expr;

    public AssignmentStatement(String variable, Expression expr) {
        this.variable = variable;
        this.expr = expr;
    }

    @Override
    public void execute() {
        double result = this.expr.eval();
        Variables.set(this.variable, result);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", this.variable, this.expr);
    }
}
