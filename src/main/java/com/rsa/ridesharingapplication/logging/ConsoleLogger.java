package com.rsa.ridesharingapplication.logging;

public class ConsoleLogger implements Logger{
    @Override
    public void log(Object obj) {
        System.out.println("-----------------------------");
        System.out.println(obj);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }
}
