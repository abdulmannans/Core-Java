public class PrimeNumber {
    public static void primeNumberChecker(int number){
        int flag = 0;
        if(number > 1){
            for (int i = 2; i < (number/2); i++){
                if(number%i == 0){
                    System.out.println(number + " is not prime number.");
                    flag++;
                    break;
                }
            }
            if(flag == 0){
                System.out.println(number + " is a prime number.");
            }
        }else{
            System.out.println(number + " is not prime number.");
        }
    }
    public static void main(String[] args) {
        primeNumberChecker(0);
        primeNumberChecker(1);
        primeNumberChecker(2);
        primeNumberChecker(3);
        primeNumberChecker(100);
        primeNumberChecker(5);
        primeNumberChecker(39);

    }
}
