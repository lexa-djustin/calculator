package parser;

import lib.Variables;
import parser.ast.Expression;
import parser.ast.Statement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = new String(Files.readAllBytes(Paths.get("program.txt")), "UTF-8");

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token.getType());
        }

        Parser parser = new Parser(tokens);
        List<Statement> statements = parser.parse();

        System.out.println(statements.size());

        for (Statement statement : statements) {
            System.out.println(statement);
        }

        for (Statement statement : statements) {
            statement.execute();
        }
    }
}
