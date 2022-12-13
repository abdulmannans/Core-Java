import java.sql.Array;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

class Arithmetic{
    static final char[] operators = {'/', '*', '+', '-'};
    static final String regex = "\\d.[0-9*/+\\-].*\\d";
    static final String regexForSmallEquation = "\\d";
    private static final DecimalFormat dfZero = new DecimalFormat("0.00");
    private static boolean isOperatorsPresent(String expression){
        for (char opr : operators){
            if (expression.contains(String.valueOf(opr))){
                return true;
            }
        }
        return false;
    }
    private static boolean isNumberAtEndAndBegin(String expression){

        if(expression.length() == 3){
            if(Pattern.matches(regexForSmallEquation, String.valueOf(expression.charAt(0)))){
                return Pattern.matches(regexForSmallEquation, String.valueOf(expression.charAt(expression.length()-1)));
            }else{
                return false;
            }
        }
        return Pattern.matches(regex,expression);
    }
    private static boolean isValidArithmetic(String expression){
        char[] equationArray = expression.toCharArray();
        HashSet<Integer> operatorIndex = getOperatorsIndex(equationArray);
        if(isOperatorIndexValid(operatorIndex)){
            return true;
        }
        return false;
    }
    private static HashSet<Integer> getOperatorsIndex(char[] expressionArray){
        HashSet<Integer> operatorIndex = new HashSet<Integer>();
        for (int i = 0 ; i < expressionArray.length ; i++){
            if(new String(operators).contains(String.valueOf(expressionArray[i]))){
                operatorIndex.add(i);
            }
        }
        return operatorIndex;
    }
    private static boolean isOperatorIndexValid(HashSet<Integer> operatorIndex){
        if(operatorIndex.size() > 1){
            Object[] operatorsIndexArray = operatorIndex.toArray();
            int arrayLength = operatorsIndexArray.length;
            for (int i = 0 ; i < arrayLength; i++){
                int incrementedValue = i+1;
                if ((incrementedValue) < arrayLength){
                    int diff = Integer.parseInt(operatorsIndexArray[incrementedValue].toString()) - Integer.parseInt(operatorsIndexArray[i].toString());
                    if(diff == 1){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private static boolean isExpressionValid(String expression){
        if(isNumberAtEndAndBegin(expression)){
            if(isOperatorsPresent(expression)){
                return isValidArithmetic(expression);
            }else {
                return false;
            }
        }
        return false;
    }

    private static HashMap<String, HashMap<Integer, String>> getNumberArray(char[] expressionArray){
        HashMap<String, HashMap<Integer, String>> responseArray = new HashMap<String, HashMap<Integer, String>>();
        HashMap<Integer, String> numberArray = new HashMap<Integer, String>();
        HashMap<Integer, String> operatorsArray = new HashMap<Integer, String>();
        int lastPosition = 0;
        int numberPosition = 0;
        StringBuilder tempValue = new StringBuilder();
        for (int i = 0; i < expressionArray.length ; i++){
            String currentValue = String.valueOf(expressionArray[i]);
            if(String.valueOf(operators).contains(currentValue)){
                if(i > 0){
                    if(lastPosition > 0){
                        lastPosition+=1;
                    }
                    for (int j = lastPosition ; j < i ; j ++){
                        tempValue.append(expressionArray[j]);
                    }
                    lastPosition = i;

                    numberArray.put(numberPosition ,String.valueOf(tempValue));
                    operatorsArray.put(numberPosition+1, String.valueOf(expressionArray[lastPosition]));
                    tempValue.delete(0 , tempValue.length());
                    numberPosition+=2;
                }
            }else if (i == (expressionArray.length-1)){
                if(lastPosition > 0){
                    lastPosition+=1;
                }
                for (int j = lastPosition ; j < expressionArray.length; j++){
                    tempValue.append(expressionArray[j]);
                }
                numberArray.put(numberPosition,String.valueOf(tempValue));
                tempValue.delete(0 , tempValue.length());
            }
        }
        responseArray.put("numbers", numberArray);
        responseArray.put("operators", operatorsArray);
        return responseArray;
    }
    private static String solveExpression(String expression){
        char[] expressionArray = expression.toCharArray();
        HashMap<String, HashMap<Integer, String>> response = getNumberArray(expressionArray);
        HashMap<Integer, String> numberArray = response.get("numbers");
        HashMap<Integer, String> operatorsArray = response.get("operators");
        for (char opr : operators){
            operatorsArray.forEach((key, value) ->{
                if(Objects.equals(value, String.valueOf(opr))){
                    if (key > 0){
                        if(numberArray.size() > 1){
                            double numOne = 0, numTwo = 0;
                            int keyOne = 0 , keyTwo = 0;
                            if(numberArray.containsKey(key-1) && numberArray.containsKey(key+1)){
                                numOne = Double.parseDouble(numberArray.get(key-1));
                                keyOne = key - 1;
                                numTwo = Double.parseDouble(numberArray.get(key+1));
                                keyTwo = key + 2;
                            }else if(numberArray.containsKey(key -1) || numberArray.containsKey(key+1)){
                                if(numberArray.containsKey(key-1)){
                                    numOne = Double.parseDouble(numberArray.get(key-1));
                                    keyOne = key - 1;
                                    int lastEntry = numberArray.keySet().stream().reduce((one , two) -> two).get();
                                    for (int i = key; i <= lastEntry ; i++){
                                        if(numberArray.containsKey(i)){
                                            numTwo = Double.parseDouble(numberArray.get(i));
                                            keyTwo = i;
                                            break;
                                        }
                                    }
                                }else if(numberArray.containsKey(key+1)){
                                    numTwo = Double.parseDouble(numberArray.get(key+1));
                                    keyTwo = key + 1;
                                    for (int i = key ; i > 0 ; i--){
                                        if (numberArray.containsKey(i)){
                                            numOne =Double.parseDouble(numberArray.get(i));
                                            keyOne = i;
                                            break;
                                        }
                                    }
                                }
                            }

                            double updatedNumber = 0;
                            switch (opr) {
                                case '/' -> {
                                    updatedNumber = numOne / numTwo;
                                    numberArray.remove(keyOne);
                                    numberArray.remove(keyTwo);
                                    numberArray.put(key + 1, String.valueOf(dfZero.format(updatedNumber)));
                                }
                                case '*' -> {
                                    updatedNumber = numOne * numTwo;
                                    numberArray.remove(keyOne);
                                    numberArray.remove(keyTwo);
                                    numberArray.put(key + 1, String.valueOf(dfZero.format(updatedNumber)));
                                }
                                case '+' -> {
                                    updatedNumber = numOne + numTwo;
                                    numberArray.remove(keyOne);
                                    numberArray.remove(keyTwo);
                                    numberArray.put(key + 1, String.valueOf(dfZero.format(updatedNumber)));
                                }
                                case '-' -> {
                                    updatedNumber = numOne - numTwo;
                                    numberArray.remove(keyOne);
                                    numberArray.remove(keyTwo);
                                    numberArray.put(key + 1, String.valueOf(dfZero.format(updatedNumber)));
                                }
                            }
                        }
                    }
                }
            });
        }
        return String.valueOf(numberArray.values());
    }
    public static String getSolution(String expression){
        if(isExpressionValid(expression)){
            return String.valueOf(solveExpression(expression));
        }
        return "Invalid Expression";
    }
}

public class Calculator extends Arithmetic {
    public static void main(String[] args){
        String s1 = "99+98+9/2/3/3";
        System.out.println(getSolution(s1));
    }
}
