package com.tn.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public class LoggingProxy {

    public static void main(String[] args) {
        TestProxyService helloService = createMethodLoggingProxy(TestProxyService.class);
        helloService.print("hello world"); // logs nothing to the console
        helloService.printWithLogging("hello world"); // logs method invocation to the console
    }

    /**
     * Creates a proxy of the provided class that logs its method invocations. If a method that
     * is marked with {@link LogInvocation} annotation is invoked, it prints to the console the following statement:
     * "[PROXY: Calling method '%s' of the class '%s']%n", where the params are method and class names accordingly.
     *
     * @param targetClass a class that is extended with proxy
     * @param <T>         target class type parameter
     * @return an instance of a proxy class
     */
    @SuppressWarnings("unchecked")
    public static <T> T createMethodLoggingProxy(Class<T> targetClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback((MethodInterceptor) (object, method, objects, methodProxy) -> {
            if (method.isAnnotationPresent(LogInvocation.class)) {
                System.out.printf("[PROXY: Calling method '%s' of the class '%s']%n", method.getName(),
                                  targetClass.getSimpleName());
            }
            return methodProxy.invokeSuper(object, objects);
        });
        return (T) enhancer.create();
    }
}
