import java.util.*;
import java.util.regex.*;
import java.math.BigDecimal;

public class ConstantFoldingOptimizer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an arithmetic expression: ");
        String expression = scanner.nextLine();
        scanner.close();

        String optimizedExpression = optimizeExpression(expression);
        System.out.println("Optimized Expression: " + optimizedExpression);
    }

    public static String optimizeExpression(String expr) {
        expr = expr.replaceAll("\\s+", ""); // Remove spaces
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?[*/+-]\\d+(\\.\\d+)?"); // Match constant operations
        Matcher matcher = pattern.matcher(expr);

        while (matcher.find()) {
            String subExpr = matcher.group();
            String result = evaluate(subExpr);
            expr = expr.replace(subExpr, result);
            matcher = pattern.matcher(expr); // Re-check with updated expression
        }

        return expr;
    }

    public static String evaluate(String expr) {
        char operator = ' ';
        for (char op : new char[]{'+', '-', '*', '/'}) {
            if (expr.contains(String.valueOf(op))) {
                operator = op;
                break;
            }
        }

        if (operator == ' ') return expr; // No valid operator found

        String[] parts = expr.split("\\" + operator);
        BigDecimal num1 = new BigDecimal(parts[0]);
        BigDecimal num2 = new BigDecimal(parts[1]);

        BigDecimal result;
        switch (operator) {
            case '+': result = num1.add(num2); break;
            case '-': result = num1.subtract(num2); break;
            case '*': result = num1.multiply(num2); break;
            case '/': result = num1.divide(num2, 10, BigDecimal.ROUND_HALF_UP); break;
            default: return expr;
        }
        return result.stripTrailingZeros().toPlainString();
    }
}
