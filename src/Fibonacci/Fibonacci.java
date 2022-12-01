package Fibonacci;

public class Fibonacci {
    public static void main(String[] args){
        int firstNumber = 0;
        int secondNumber = 1;
        int temp = 0;
        int i = 0;
        System.out.println(firstNumber); // printing first number
        System.out.println(secondNumber); // printing second number
        while (i < 10){
            temp = firstNumber + secondNumber;
            System.out.println(temp);
            firstNumber = secondNumber;
            secondNumber = temp;
            i++;
        }
    }
}
