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

/*
*被代理的类
 */
class HelloWorld{
    public void sayHello(){
        System.out.println("hello world 1527");
    }

}

/*
*方法拦截器
 */
class MyMethodInterceptor implements MethodInterceptor{

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("--------before---------");
        //method.invoke(o, objects);
        methodProxy.invokeSuper(o, objects);    //方法代理调用父类的方法
        System.out.println("----------after----------");
        return null;
    }
}

class ProxyFactory{

    /*
    *产生代理类
     */
    public static Object getProxyObject(String cname) throws ClassNotFoundException {
        Class<?> superClass= Class.forName(cname);
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(superClass);
        enhancer.setCallback(new MyMethodInterceptor());
        return enhancer.create();
    }
}
