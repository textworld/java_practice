package cn.textworld.java.jdk.proxy.demo.handler;

import cn.textworld.java.jdk.proxy.demo.service.SubjectService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerImpl implements InvocationHandler {

    private SubjectService subjectService;

    public SubjectService getSubjectService() {
        return subjectService;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before method");
        Object result = method.invoke(subjectService, args);
        System.out.println("after method");
        return result;
    }
}
