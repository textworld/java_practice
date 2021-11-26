package cn.textworld.java.jdk.proxy.demo.service.impl;

import cn.textworld.java.jdk.proxy.demo.service.SubjectService;

public class SubjectServiceImpl implements SubjectService {

    @Override
    public String SayHello(String name) {
        return "my name is " + name;
    }
}
