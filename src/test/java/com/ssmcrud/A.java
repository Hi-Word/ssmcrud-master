package com.ssmcrud;

import com.d1.P;

public class A  extends P{
    public void m(){

        System.out.println(this.name);
    }

    public static void main(String[] args) {
        A a=new A();
        a.m();

    }
}
