package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by yanbin.yin on 2015/11/10.
 */
public class JDKProxyFactory implements InvocationHandler {
    private Object targetObject;

    public Object createProxyInstance(Object targetObject){
        this.targetObject=targetObject;
        return Proxy.newProxyInstance(this.targetObject.getClass().getClassLoader(), this.targetObject.getClass().getInterfaces(), this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PersonServiceBean bean=(PersonServiceBean)this.targetObject;
        Object result=null;
        if(bean.getUser("")!=null){
            args=new String[]{"nihao"};
            result=method.invoke(targetObject,args);
            result=result+" ----xxxx";
        }
        return result;
    }
    public static void main(String[] args){
        PersonServiceBean bean=new PersonServiceBean();
        JDKProxyFactory myFactory=new JDKProxyFactory();
        IMybean proxied=(IMybean)myFactory.createProxyInstance(bean);
        System.out.println(proxied.getUser("22222"));
    }
}

interface IMybean{
    public String getPwd(String user);

    Object getUser(String s);
};

class PersonServiceBean implements IMybean{

    public Object getUser(String s) {
        return "hello";
    }
    public String getPwd(String user){
        System.out.println(user+" world");
        return user+" world";
    }
}
