package edu.njit.cs114.expressioneval;

/**
 * Author: Ravi Varadarajan
 * Date created: 10/16/2023
 */
public enum OperatorToken implements ExpressionToken {

        OPENPAR("(", 0), ADD("+",1), SUBTRACT("-",1), MULTIPLY("*",2), DIVIDE("/",2), MOD("%",2),
         EXP("**", 3), CLOSEDPAR(")", 4);

        public final String symbol;
        public final int precedence;

        OperatorToken(String symbol, int precedence) {
            this.symbol = symbol;
            this.precedence = precedence;
        }

        public static OperatorToken opType(String s) {
            for (OperatorToken type : OperatorToken.values()) {
                if (type.symbol.equals(s)) {
                    return type;
                }
            }
            return null;
        }

        /**
         * Use this to check operator precedence
         * @param other
         * @return
         */
        public boolean precedes(OperatorToken other) {
            return this.precedence > other.precedence;
        }

        public String toString() {
            return symbol;
        }

    }
