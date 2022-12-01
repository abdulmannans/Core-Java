public class FibonacciRecursion {
    static int firstNumber = 0 , secondNumber = 1 , temp =0;
    private static void fibonacciSeries(int count){
        if(count > 0){
            temp = firstNumber + secondNumber;
            System.out.println(temp);
            firstNumber = secondNumber;
            secondNumber = temp;
            fibonacciSeries(count-1);
        }
    }
    public static void main(String[] args) {
        int count = 10;
        System.out.println(firstNumber);
        System.out.println(secondNumber);
        fibonacciSeries(count);
    }
}
