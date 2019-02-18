package com.wyk.sign;

public class SpringTest {

    public void myTest(Object o){
        System.out.println("my object!");
    }

    public void myTest(String s){
        System.out.println("my String!");
    }

    public static void main(String[] args) {
        SpringTest test = new SpringTest();
        test.myTest(null);
        System.out.println("|DF|A3".split("|").length);
    }

}
