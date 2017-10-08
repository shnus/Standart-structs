package seminar1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 1. пустая строка — правильная скобочная последовательность;
 * 2. правильная скобочная последовательность,
 *      взятая в скобки одного типа — правильная скобочная последовательность;
 * 3. правильная скобочная последовательность,
 *      к которой приписана слева или справа правильная скобочная последовательность
 *      — тоже правильная скобочная последовательность.
 */
public class ParenthesesSequenceExt {

    private static final String QUIT = "q";

    private static final char LEFT_PAREN     = '(';
    private static final char RIGHT_PAREN    = ')';
    private static final char LEFT_BRACE     = '{';
    private static final char RIGHT_BRACE    = '}';
    private static final char LEFT_BRACKET   = '[';
    private static final char RIGHT_BRACKET  = ']';

    // sequence = "()()" | "(({}[]))[[[" | "{}" |

    static Set<String> openBrackets = new HashSet(){{
        add("{");
        add("[");
        add("(");
    }};
    static Set<String> closeBrackets = new HashSet(){{
        add("}");
        add("]");
        add(")");
    }};

    private static boolean isBalanced(String line) {
        Stack<String> brackets = new Stack();
        Stack<Integer> bracketsNums = new Stack();
        int i;
        for (i = 0; i < line.length(); i++) {
            String bracket = Character.toString(line.charAt(i));
            if (openBrackets.contains(bracket)) {
                brackets.push(bracket);
                bracketsNums.push(i);
            } else if (closeBrackets.contains(bracket)) {
                if (brackets.empty()) {
                    System.out.println(i + 1);
                    return false;
                }
                String topBracket = brackets.pop();
                bracketsNums.pop();
                if ((topBracket.equals("[") && !bracket.equals("]"))
                        || (topBracket.equals("{") && !bracket.equals("}"))
                        || (topBracket.equals("(") && !bracket.equals(")"))) {
                    System.out.println(i + 1);
                    return false;
                }
            }
        }
        if (!brackets.empty()) {
            System.out.println(bracketsNums.pop() + 1);
            return false;
        } else return true;

    }

    public static void main(String[] args) {
        try (BufferedReader lineReader = new BufferedReader(new InputStreamReader(System.in))) {
            String sequence;
            while (!QUIT.equals(sequence = lineReader.readLine())) {
                System.out.println(isBalanced(sequence) ? "YES" : "NO");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
