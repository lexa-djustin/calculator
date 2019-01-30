package parser.ast;

import lib.Variables;

public class VariableExpression implements Expression {

    private String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public double eval() {
        return Variables.get(this.name);
    }

    @Override
    public String toString(){
        return String.format("%s", this.name);
    }
}
