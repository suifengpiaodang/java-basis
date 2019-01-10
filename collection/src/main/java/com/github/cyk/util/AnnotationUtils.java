package com.github.cyk.util;

import com.google.common.collect.Lists;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

import java.util.List;

/**
 * 注解属性 设置获取
 */
public class AnnotationUtils {


    private static class SingletonHolder {
        private static final AnnotationUtils INSTANCE = new AnnotationUtils();
    }

    private AnnotationUtils (){}
    public static final AnnotationUtils get() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 修改注解上的属性值
     *
     * @param className  当前类名
     * @param methodName 当前方法名
     * @param annoName   方法上的注解名
     * @param fieldName  注解中的属性名
     * @param fieldValue 注解中的属性值
     * @throws NotFoundException
     */
    public void setAnnotatioinFieldValue(String className, String methodName, String annoName, String fieldName, String fieldValue) throws NotFoundException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ct = classPool.get(className);
        CtMethod ctMethod = ct.getDeclaredMethod(methodName);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        ConstPool constPool = methodInfo.getConstPool();
        AnnotationsAttribute attr = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
        Annotation annotation = attr.getAnnotation(annoName);
        if (annotation != null) {
            annotation.addMemberValue(fieldName, new StringMemberValue(fieldValue, constPool));
            attr.setAnnotation(annotation);
            methodInfo.addAttribute(attr);
        }
    }

    /**
     * 获取注解中的属性值
     *
     * @param className  当前类名
     * @param methodName 当前方法名
     * @param annoName   方法上的注解名
     * @param fieldName  注解中的属性名
     * @return
     * @throws NotFoundException
     *发现javassist包出现这个奇怪的错误。通过分析，发现javassist 是一个动态生成字节码的开源库，由于我使用的是jdk1.8.
     * 原因：javassist 3.18以下的版本不支持在JDK1.8下运行，详情点击.
     */

    public String getAnnotatioinFieldValue(String className, String methodName, String annoName, String fieldName) throws NotFoundException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ct = classPool.get(className);
        CtMethod ctMethod = ct.getDeclaredMethod(methodName);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        AnnotationsAttribute attr = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
        String value = "";
        if (attr != null) {
            Annotation an = attr.getAnnotation(annoName);
            if (an != null) {
                value = ((StringMemberValue) an.getMemberValue(fieldName)).getValue();
            }
        }
        return value;
    }


}
