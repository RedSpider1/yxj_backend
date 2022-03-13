package com.redspider.pss.service.db.impl;

import com.google.common.collect.Lists;
import com.redspider.pss.constant.enums.Group.GroupConfirm;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.service.db.spi.ApiCodeService;
import com.redspider.pss.utils.annotation.EnumDescription;
import com.redspider.pss.vo.team.CustomerExceptionVO;
import com.redspider.pss.vo.team.EnumDescriptionListVO;
import com.redspider.pss.vo.team.EnumDescriptionVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;

@Service
@Slf4j
public class ApiCodeServiceImpl implements ApiCodeService {

    @Override
    public List<CustomerExceptionVO> getAll() {
        List<CustomerExceptionVO> res = Lists.newArrayList();
        ResponseCode[] responseCodes = ResponseCode.values();
        Arrays.stream(responseCodes).forEach(apiCode->{
            res.add(new CustomerExceptionVO(apiCode.getCode(), apiCode.getMessage()));
        });
        return res;
    }

    @Override
    public List<EnumDescriptionListVO> getAllEnums() {
        //反射获取所有枚举类
        List<EnumDescriptionListVO> enumDescriptionListVOS = new ArrayList<>();
        for (Class enumName : getEnumsByReflect()) {
            EnumDescriptionListVO descriptionListVO = new EnumDescriptionListVO();
            List<EnumDescriptionVO> descriptionVOS = new ArrayList<>();
            String enumAlias = "";
            try {
                Class<Enum> clazz = (Class<Enum>) enumName;
                if (clazz.isAnnotationPresent(EnumDescription.class)) {
                    enumAlias = clazz.getAnnotation(EnumDescription.class).value();
                }else {
                    enumAlias = enumName.getName().substring(enumName.getName().lastIndexOf(".") + 1);
                }
                descriptionListVO.setEnumAlias(enumAlias);
                descriptionListVO.setEnumDescriptionVOS(descriptionVOS);
                //反射获取该枚举所有实例
                Enum[] enumConstants = clazz.getEnumConstants();
                Field code = clazz.getDeclaredField("code");
                Field dec = clazz.getDeclaredField("description");
//                Method getCode = clazz.getDeclaredMethod("getCode");
//                Method getDec = clazz.getDeclaredMethod("getDescription");
                code.setAccessible(true);
                dec.setAccessible(true);
                for (Enum  e: enumConstants) {
                    EnumDescriptionVO enumDecVO = new EnumDescriptionVO();
                    Object codeValue = code.get(e);
                    Object decValue;
                    Field enumFiled = clazz.getDeclaredField(e.name());
                    //获取枚举实例上的注解
                    if (enumFiled.isAnnotationPresent(EnumDescription.class)) {
                        decValue = enumFiled.getAnnotation(EnumDescription.class).value();
                    }else {
                        decValue = dec.get(e);
                    }
                    enumDecVO.setCode((Integer) codeValue);
                    enumDecVO.setDescription((String) decValue);
                    descriptionVOS.add(enumDecVO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            enumDescriptionListVOS.add(descriptionListVO);
        }

        return enumDescriptionListVOS;
    }

    /**
     * 反射获取所有枚举类全类名
     * @return
     */
    private Set<Class<?>> getEnumsByReflect() {
        Set<Class<?>> clzFromPkg = getClzFromPkg("com/redspider/pss/constant/enums");
        return clzFromPkg;
    }
    
    
    
    /**
     * 从包package中获取所有的Class
     *
     * @param pkg 包名
     */
    public static Set<Class<?>> getClzFromPkg(String pkg) {
        //第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<>();
        // 获取包的名字 并进行替换
        String pkgDirName = pkg.replace('.', '/');
        try {
            Enumeration<URL> urls = ApiCodeService.class.getClassLoader().getResources(pkgDirName);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上（项目中的包）
                if ("file".equals(protocol)) {
                    // 获取
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findClassesByFile2(pkg, filePath, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 获取jar
                    java.util.jar.JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    //扫描jar包文件 并添加到集合中
                    findClassesByJar(pkg, jar, classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return classes;
    }
    
    /**
     * 获取jar包中指定包下的class
     *
     * @param pkgName 包名
     * @param jar     JarFile
     * @param classes
     * @return
     */
    private static void findClassesByJar(String pkgName, java.util.jar.JarFile jar, Set<Class<?>> classes) {
        String pkgDir = pkgName.replace(".", "/");
        // 从此jar包 得到一个枚举类
        Enumeration<JarEntry> entry = jar.entries();
        
        JarEntry jarEntry;
        String name, className;
        Class<?> claze;
        // 同样的进行循环迭代
        while (entry.hasMoreElements()) {
            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文
            jarEntry = entry.nextElement();
            
            name = jarEntry.getName();
            // 如果是以/开头的
            if (name.charAt(0) == '/') {
                // 获取后面的字符串
                name = name.substring(1);
            }
            
            if (jarEntry.isDirectory() || !name.startsWith(pkgDir) || !name.endsWith(".class")) {
                continue;
            }
            //如果是一个.class文件 而且不是目录
            // 去掉后面的".class" 获取真正的类名
            className = name.substring(0, name.length() - 6);
            //加载类
            claze = loadClass(className.replace("/", "."));
            // 添加到集合中去
            if (claze != null) {
                classes.add(claze);
            }
        }
    }
    
    /**
     * 获取项目中包下的class 使用FileUtils.listFile()方法，需要依赖commons-io
     * <dependency>
     *     <groupId>commons-io</groupId>
     *     <artifactId>commons-io</artifactId>
     *     <version>2.6</version>
     * </dependency>
     *
     * @param pkgName 包名
     * @param pkgPath 包的绝对路径
     * @param classes
     * @return
     */
    private static void findClassesByFile2(String pkgName, String pkgPath, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(pkgPath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        
        // 如果存在 就获取包下的所有class
        //FileUtils.listFiles()
        Collection<File> files = FileUtils.listFiles(dir, new String[]{"class"}, true);
        
        files.forEach(file -> {
            //加载类
            Class clz = loadClass(pkgName + "." + file.getName().split("\\.")[0]);
            // 添加到集合中去
            if (clz != null) {
                classes.add(clz);
            }
        });
    }
    
    /**
     * 获取项目中包下的class 递归遍历子包
     * 不想引入common-io包的可以用这个方法
     *
     * @param pkgName 包名
     * @param pkgPath 包的绝对路径
     * @param classes
     * @return
     */
    private static void findClassesByFile(String pkgName, String pkgPath, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(pkgPath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith("class"));
        
        if (dirfiles == null || dirfiles.length == 0) {
            return;
        }
        
        String className;
        Class clz;
        // 循环所有文件
        for (File f : dirfiles) {
            // 如果是目录 则继续扫描
            if (f.isDirectory()) {
                findClassesByFile(pkgName + "." + f.getName(), pkgPath + "/" + f.getName(), classes);
                continue;
            }
            // 如果是java类文件 去掉后面的.class 只留下类名
            className = f.getName();
            className = className.substring(0, className.length() - 6);
            
            //加载类
            clz = loadClass(pkgName + "." + className);
            // 添加到集合中去
            if (clz != null) {
                classes.add(clz);
            }
        }
    }
    
    /**
     * 加载类
     *
     * @param fullClzName 类全名
     * @return
     */
    private static Class<?> loadClass(String fullClzName) {
        fullClzName = fullClzName.replaceAll("/", ".");
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(fullClzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
