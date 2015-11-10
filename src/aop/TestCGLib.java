package aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by yanbin.yin on 2015/11/10.
 */
public class TestCGLib {
    public static void main(String[] args) throws ClassNotFoundException {
        HelloWorld hw= (HelloWorld) ProxyFactory.getProxyObject(HelloWorld.class.getName());
        hw.sayHello();
    }
}

class HelloWorld{
    public void sayHello(){
        System.out.println("hello world 1527");
    }

}

class MyMethodInterceptor implements MethodInterceptor{

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("--------before---------");
        //method.invoke(o, objects);
        methodProxy.invokeSuper(o, objects);
        System.out.println("----------after----------");
        return null;
    }
}

class ProxyFactory{
    public static Object getProxyObject(String cname) throws ClassNotFoundException {
        Class<?> superClass= Class.forName(cname);
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(superClass);
        enhancer.setCallback(new MyMethodInterceptor());
        return enhancer.create();
    }
}
