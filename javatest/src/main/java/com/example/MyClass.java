package com.example;

import java.util.Random;


public class MyClass {
    public static void main(String[] args) throws Exception {
        method(10);
    }

    public static void method(int n) {
        int[] nums = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int result = random.nextInt(9) + 1;
            print(result);
            nums[i] = result;
        }
        int n1 = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        for (int x = 0; x < nums.length; x++) {
            switch (nums[x]) {
                case 1:
                    n1++;
                    break;
                case 2:
                    n2++;
                    break;
                case 3:
                    n3++;
                    break;
                case 4:
                    n4++;
                    break;
                case 5:
                    n5++;
                    break;
                case 6:
                    n6++;
                    break;
                case 7:
                    n7++;
                    break;
                case 8:
                    n8++;
                    break;
                case 9:
                    n9++;
                    break;
            }
        }
        print("1出现的次数：" + n1);
        print("2出现的次数：" + n2);
        print("3出现的次数：" + n3);
        print("4出现的次数：" + n4);
        print("5出现的次数：" + n5);
        print("6出现的次数：" + n6);
        print("7出现的次数：" + n7);
        print("8出现的次数：" + n8);
        print("9出现的次数：" + n9);


    }

    private static void print(Object s) {
        System.out.println(s);
    }
}
