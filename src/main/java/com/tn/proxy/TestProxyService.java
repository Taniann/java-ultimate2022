package com.tn.proxy;

public class TestProxyService {
    public void print(String value) {
        System.out.println(value);
    }

    @LogInvocation
    public void printWithLogging(String value) {
        System.out.println(value);
    }
}
