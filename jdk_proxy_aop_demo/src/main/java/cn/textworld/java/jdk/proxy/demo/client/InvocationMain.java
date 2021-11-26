package cn.textworld.java.jdk.proxy.demo.client;

import cn.textworld.java.jdk.proxy.demo.handler.InvocationHandlerImpl;
import cn.textworld.java.jdk.proxy.demo.service.SubjectService;
import cn.textworld.java.jdk.proxy.demo.service.impl.SubjectServiceImpl;

import java.lang.reflect.Proxy;

public class InvocationMain {
    public static void main(String[] args) {

        SubjectService subjectService = new SubjectServiceImpl();

        InvocationHandlerImpl handler = new InvocationHandlerImpl();
        handler.setSubjectService(subjectService);

        SubjectService proxy = (SubjectService)Proxy.newProxyInstance(subjectService.getClass().getClassLoader(), subjectService.getClass().getInterfaces(), handler);
        String myname = proxy.SayHello("ss");
        System.out.println(myname);

    }
}
