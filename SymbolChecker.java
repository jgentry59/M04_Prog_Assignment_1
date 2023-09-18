import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SymbolChecker {
    public static void main(String[] args) {
        if (args.length != 1) {      //Checks if user has input a one command line argument and prints error if not
            System.out.println("Usage: java SymbolChecker <source-code-file>");
            return;
        }

        String fileName = args[0];  //takes provided file name and stores it in fileName variable

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {  //begins a try block to use source code file
            String line;
            int lineNumber = 0;
            Stack<Character> symbolStack = new Stack<>();

            while ((line = reader.readLine()) != null) {
                lineNumber++;  //Keeps track of current line being processed

                for (char character : line.toCharArray()) {  //Nested for loop to iterate each character in current line
                    if (isOpenSymbol(character)) {
                        symbolStack.push(character);
                    } else if (isCloseSymbol(character)) {
                        if (symbolStack.isEmpty() || !areMatchingSymbols(symbolStack.peek(), character)) { //Checks if stack is empty, if not outputs error message
                            System.out.println("Error: Unmatched grouping symbol at line " + lineNumber);
                            return;
                        } else {
                            symbolStack.pop();
                        }
                    }
                }
            }

            if (!symbolStack.isEmpty()) {    //Prints end of file error message if stack is not empty
                System.out.println("Error: Unmatched grouping symbol(s) at the end of the file");
            } else {
                System.out.println("No errors. All grouping symbols are matched.");
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private static boolean isOpenSymbol(char c) {
        return c == '(' || c == '{' || c == '[';
    }

    private static boolean isCloseSymbol(char c) {
        return c == ')' || c == '}' || c == ']';
    }

    private static boolean areMatchingSymbols(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }
}
