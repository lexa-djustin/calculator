package parser;

import parser.ast.Expression;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String input = "1 + 2 + 3";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token.getType());
        }

        Parser parser = new Parser(tokens);
        List<Expression> expressions = parser.parse();

        for(Expression expression : expressions){
            System.out.println(expression + " " + expression.eval());
        }
    }
}
