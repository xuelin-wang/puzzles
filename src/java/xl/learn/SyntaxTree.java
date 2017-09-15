package xl.learn;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Created by xuelin on 9/14/17.
 */
public class SyntaxTree {
// evalexpr(-4 - 3 * 2 / 2 + 4) -> result (float or double)
//
// [Token(NUM, -4.), Token(SUB), Token(NUM, 3), Token(MUL)…]
//
// input: an array/list of Tokens representing a VALID arithmetic expression
// output: the result as a float or double
//
// Token:
// type: one of NUM, ADD, SUB, MUL, DIV
// value: float or double it's only defined/relevant when the token as type NUM
//
// Todo:
// 1. implement the Token class/struct
// 2. implement evalexpr

    enum TokenType {
        NUM, ADD, SUB, MUL, DIV
    }

    public static class Token {
        TokenType type;
        double val;
        int priorityLevel;

        public Token(TokenType type, double val) {
            assert(type == TokenType.NUM);

            this.type = type;
            this.val = val;
        }

        public Token(TokenType type) {
            assert(type != null && type != TokenType.NUM);

            this.type = type;
            if (type == TokenType.ADD || type == TokenType.SUB)
                this.priorityLevel = 1;
            else
                this.priorityLevel = 2;
        }
    }

    public static double eval(Token token, double d1, double d2) {
        if (token.type == TokenType.ADD) {
            return d1 + d2;
        }
        else if (token.type == TokenType.SUB) {
            return d1 - d2;
        }
        else if (token.type == TokenType.MUL) {
            return d1 * d2;
        }
        else if (token.type == TokenType.DIV) {
            return d1 / d2;
        }
        else
            throw new RuntimeException("Should never be here");
    }

    public static class Node {
        Node left;
        Node right;
        Token token;
        public Node(Node left, Node right, Token token) {
            this.left = left;
            this.right = right;
            this.token = token;
        }
    }

    public static double evalexpr(Token[] tokens) {
        assert(tokens.length > 0 && tokens.length % 2 == 1);

        if (tokens.length == 1)
            return tokens[0].val;

        Token pendingOp = null;
        Double t_2Val = null;
        double t_1Val = tokens[0].val;

        for (int i = 1; i < tokens.length; ) {
            //three possible operations:
            //1. do an op in pending
            //2. push this op into pending and do next op
            //3. do this op
            //invariant: at most one pending op
            Token op = tokens[i];

            if (pendingOp != null && pendingOp.priorityLevel >= op.priorityLevel) {
                t_1Val = eval(pendingOp, t_2Val, t_1Val);
                t_2Val = null;
                pendingOp = null;
            }

            Token nextOp = i + 2 >= tokens.length ? null : tokens[i + 2];
            double tVal = tokens[i + 1].val;
            if (nextOp == null || op.priorityLevel >= nextOp.priorityLevel) {
                //do this op
                t_1Val = eval(op, t_1Val, tVal);
            }
            else {
                pendingOp = op;
                t_2Val = t_1Val;
                t_1Val = tVal;
            }

            i = i + 2;
        }

        if (pendingOp != null)
            t_1Val = eval(pendingOp, t_2Val, t_1Val);

        return t_1Val;
    }

    public static void main(String[] args) {
        Token[] tokens = new Token[]{
                new Token(TokenType.NUM, -4), new Token(TokenType.SUB), new Token(TokenType.NUM, 3), new Token(TokenType.MUL),
                new Token(TokenType.NUM, 2), new Token(TokenType.DIV), new Token(TokenType.NUM, 2), new Token(TokenType.ADD),
                new Token(TokenType.NUM, 4)
        };

        double val = evalexpr(tokens);

        System.out.println(val);
    }
}
