package com.wzz.ownv.aspect;/**
 * Created by Enzo Cotter on 2020/9/22.
 */

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @program: ownv
 * @description: 切面
 * @author: wzz
 * @create: 2020-09-22 10:35
 */
@Aspect
@Component
@Data
@Slf4j
public class OperationLogAop {
    @Value("${wzz.scan-path}")
    private String scanPath;


    @Pointcut("@annotation(com.wzz.ownv.annotation.OperationLog)")
    public void controllerMethod() {
    }

    @AfterReturning(value = "controllerMethod()", returning = "keys")
    public void logAround(JoinPoint joinPoint, Object keys) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        System.out.println(method.getName());
        Map<String, String> rtnMap = converMap(request.getParameterMap());
        // 将参数所在的数组转换成json
        String params = JSON.toJSONString(rtnMap);
        System.out.println(params);
    }

    public static List<Class<?>> getClasses(String packageName, boolean scanSubPackage) {
        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<>();
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        } catch (IOException e) {
            throw new IllegalArgumentException("packageName is illegal.", e);
        }
        //循环迭代下去
        while (dirs.hasMoreElements()) {
            //获取下一个元素
            URL url = dirs.nextElement();
            //得到协议的名称
            String protocol = url.getProtocol();
            //如果是以文件的形式保存在服务器上
            if ("file".equals(protocol)) {
                //获取包的物理路径
                String filePath = null;
                try {
                    filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //以文件的方式扫描整个包下的文件 并添加到集合中
                findAndAddClassesInPackageByFile(packageName, filePath, scanSubPackage, classes);
            } else if ("jar".equals(protocol)) {
                //如果是jar包文件
                //定义一个JarFile
                JarFile jar = null;
                try {
                    //获取jar
                    jar = ((JarURLConnection) url.openConnection()).getJarFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //从此jar包 得到一个枚举类
                Enumeration<JarEntry> entries = jar.entries();
                //同样的进行循环迭代
                while (entries.hasMoreElements()) {
                    //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    //如果是以/开头的
                    if (name.charAt(0) == '/') {
                        //获取后面的字符串
                        name = name.substring(1);
                    }
                    //如果前半部分和定义的包名相同
                    if (name.startsWith(packageDirName)) {
                        int idx = name.lastIndexOf('/');
                        //如果以"/"结尾 是一个包
                        if (idx != -1) {
                            //获取包名 把"/"替换成"."
                            packageName = name.substring(0, idx).replace('/', '.');
                        }
                        //如果可以迭代下去 并且是一个包
                        if ((idx != -1) || scanSubPackage) {
                            //如果是一个.class文件 而且不是目录
                            if (name.endsWith(".class") && !entry.isDirectory()) {
                                //去掉后面的".class" 获取真正的类名
                                String className = name.substring(packageName.length() + 1, name.length() - 6);
                                try {
                                    //添加到classes
                                    classes.add(Class.forName(packageName + '.' + className));
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return classes;
    }




    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                try {
                    findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                            file.getCanonicalPath(),
                            recursive,
                            classes);
                } catch (IOException e) {
                    log.warn("get folder error");
                }
            }
            else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
          * 转换request 请求参数
          *
         * @param paramMap request获取的参数数组
     */
     public Map<String, String> converMap(Map<String, String[]> paramMap) {
                 Map<String, String> rtnMap = new HashMap<String, String>();
                for (String key : paramMap.keySet()) {
                    rtnMap.put(key, paramMap.get(key)[0]);
                     }
                return rtnMap;
            }
}
