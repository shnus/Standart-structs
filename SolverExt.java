package com.company.nusrat.structures.technopolis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) ) = 101
 * ( 1 + ( 5 * ( 4 * 5 ) ) )
 * ( 1 + ( 5 * 20 ) ) = 101
 * ( 1 + 100 ) = 101
 *
 * 1 + ( 2 + 3 ) * 4 * 5 = 101
 * 1 + 5 * 4 * 5 = 101
 * 1 + 5 * 20 = 101
 * 1 + 100 = 101
 * 20 / 4 = 5
 * ( 101 - 1 ) / 5 = 20
 *
 * Считаем, что операции деления на ноль отсутствуют
 */
public class SolverExt {

    private static final String QUIT = "q";

    private static final String LEFT_PAREN   = "(";
    private static final String RIGHT_PAREN  = ")";
    private static final String PLUS         = "+";
    private static final String MINUS        = "-";
    private static final String TIMES        = "*";
    private static final String DIVISION     = "/";

    private static boolean isNumber(String s){
        try
        {
            double d = Double.parseDouble(s);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    private static int isOperator(String s){
        switch (s){
        case PLUS:
            return 1;
        case MINUS:
            return 1;
        case TIMES:
            return 2;
        case DIVISION:
            return 2;
        default: return 0;

        }
    }

    private static double evaluate(String[] values) throws Exception {
        Queue<String> out = new LinkedList<>();
        Stack<String> operators = new Stack<>();
        for(String token: values){
            if(isNumber(token)){
                out.add(token);
                continue;
            }

            int prior1;
            int prior2;
            if((prior1 = isOperator(token))>0){
                while (!operators.isEmpty() && (prior2=isOperator(operators.peek()))>0 && prior1<=prior2){
                    out.add(operators.pop());
                }
                operators.push(token);
                continue;
            }

            if(token.equals(LEFT_PAREN)){
                operators.push(token);
                continue;
            }

            boolean isOpenBracket = false;
            if(token.equals(RIGHT_PAREN)){
                while (!operators.isEmpty()){
                    if(isOperator(operators.peek())>0){
                        out.add(operators.pop());
                    } else {
                        operators.pop();
                        isOpenBracket = true;
                        break;
                    }
                }
                if(!isOpenBracket)
                    throw new Exception("Wrong input");
            }
        }
        while (!operators.isEmpty()){
            if(operators.peek().equals(LEFT_PAREN)){
                throw new Exception("Non closed bracket");
            } else {
                out.add(operators.pop());
            }
        }

        System.out.println("Polish notation: "+out);

        Stack<Double> solver = new Stack<>();
        while(!out.isEmpty()){
            String token = out.remove();
            if(isNumber(token)){
                solver.push(Double.valueOf(token));
                continue;
            }

            if(isOperator(token)>0){
                double operand2 = Double.valueOf(solver.pop());
                double operand1 = Double.valueOf(solver.pop());
                switch (token) {
                case PLUS:
                    solver.push(operand1 + operand2);
                    break;
                case MINUS:
                    solver.push(operand1 - operand2);
                    break;
                case TIMES:
                    solver.push(operand1 * operand2);
                    break;
                case DIVISION:
                    solver.push(operand1 / operand2);
                    break;
                }
            }
        }

        return solver.pop();
    }

    public static void main(String[] args) throws Exception {
        try (BufferedReader lineReader = new BufferedReader(new InputStreamReader(System.in))) {
            String sequence;
            while (!QUIT.equals(sequence = lineReader.readLine())) {
                System.out.println(evaluate(sequence.split(" ")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
