public class PrimeNumberList {
    public static void getPrimeNumberList(int count){
        System.out.println(2);
        for (int i = 3 ; i < count; i++){
            int flag = 0;
            for(int j = 2 ; j < (i/2); j++){
                if(i%j == 0){
                    flag++;
                    break;
                }
            }
            if(flag == 0){
                System.out.println(i);
            }
        }
    }
    public static void main(String[] args) {
        getPrimeNumberList(100);
    }
}
