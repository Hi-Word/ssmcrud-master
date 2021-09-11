package com.ssmcrud;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import static java.math.BigInteger.valueOf;

public  class Test {
    static BigInteger n;
    static int sum=0;
    static int[] arr;
    static int[] path;
    static boolean[] st;
    static BigInteger m;
    //不能添加重复值添加
    static Set<String> set = new TreeSet<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextBigInteger();
        arr = parseArrFromInt(n);
        m = sc.nextBigInteger();
        path=new int[arr.length];

        st=new boolean[arr.length];
        dfs(0);
        for (String str : set) {

           System.out.println(str);


        }
        //System.out.println(sum);
    }

    private static void dfs(int u) {
        if (u == arr.length) {
            StringBuffer sb=new StringBuffer();
            for (int i = 0; i < arr.length; i++) {
                if(path[0]==0){
                    return;
                }
                sb.append(path[i]);
            }
            String s= String.valueOf(sb);

            set.add(s);

            return;
        } else {
            for (int i = 0; i < arr.length; i++) {
                if (!st[i] ) {
                    path[u] = arr[i];
                    st[i] = true;
                    dfs(u + 1);
                    st[i] = false;
                }
            }
        }
    }

    //将字符串单个分解成数组元素返回
    private static int[] parseArrFromInt(BigInteger input) {
        String s = String.valueOf(input);
        int[] arr = new int[s.length()];
        for (int i = 0, len = arr.length; i < len; ++i) {
            arr[i] = s.charAt(i) - '0';
        }
        return arr;
    }
}