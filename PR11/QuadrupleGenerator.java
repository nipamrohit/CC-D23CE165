import java.util.*;

class Quadruple {
    String op, arg1, arg2, result;

    public Quadruple(String op, String arg1, String arg2, String result) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
    }
}

public class QuadrupleGenerator {
    private static int tempCount = 1;
    private static List<Quadruple> quadruples = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an arithmetic expression: ");
        String expression = scanner.nextLine().replaceAll("\s+", "");
        scanner.close();
        
        String result = parseExpression(expression);
        printQuadruples();
    }
    
    private static String parseExpression(String expr) {
        Stack<String> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        
        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            
            if (Character.isDigit(ch)) {
                StringBuilder num = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    num.append(expr.charAt(i));
                    i++;
                }
                i--;
                operands.push(num.toString());
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    processOperator(operators.pop(), operands);
                }
                operators.pop(); // Remove '('
            } else if (isOperator(ch)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    processOperator(operators.pop(), operands);
                }
                operators.push(ch);
            }
        }
        
        while (!operators.isEmpty()) {
            processOperator(operators.pop(), operands);
        }
        
        return operands.pop();
    }
    
    private static void processOperator(char op, Stack<String> operands) {
        String operand2 = operands.pop();
        String operand1 = operands.pop();
        String temp = "t" + (tempCount++);
        quadruples.add(new Quadruple(String.valueOf(op), operand1, operand2, temp));
        operands.push(temp);
    }
    
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }
    
    private static int precedence(char op) {
        switch (op) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            default: return 0;
        }
    }
    
    private static void printQuadruples() {
        System.out.println("\nQuadruple Table:");
        System.out.println("Operator\tOperand1\tOperand2\tResult");
        for (Quadruple q : quadruples) {
            System.out.println(q.op + "\t\t" + q.arg1 + "\t\t" + q.arg2 + "\t\t" + q.result);
        }
    }
}