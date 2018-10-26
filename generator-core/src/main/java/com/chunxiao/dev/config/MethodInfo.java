package com.chunxiao.dev.config;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chunxiaoli on 1/5/17.
 */
public class MethodInfo {
    private String name;
    private String returnTypeFullClassName;

    private Class returnType;

    private Type  genericReturnType;

    private List<ParameterInfo> parameterInfoList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnTypeFullClassName() {
        return returnTypeFullClassName;
    }

    public void setReturnTypeFullClassName(String returnTypeFullClassName) {
        this.returnTypeFullClassName = returnTypeFullClassName;
    }

    public List<ParameterInfo> getParameterInfoList() {
        return parameterInfoList;
    }

    public void setParameterInfoList(List<ParameterInfo> parameterInfoList) {
        this.parameterInfoList = parameterInfoList;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public Type getGenericReturnType() {
        return genericReturnType;
    }

    public void setGenericReturnType(Type genericReturnType) {
        this.genericReturnType = genericReturnType;
    }
}
