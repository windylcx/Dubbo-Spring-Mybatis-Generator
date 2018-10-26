package com.chunxiao.dev.codegen;

import com.chunxiao.dev.util.FileUtil;
import com.chunxiao.dev.util.SourceCodeUtil;
import com.chunxiao.dev.util.StringUtil;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chunxiaoli on 12/30/16.
 */
public class ClassLoaderUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassLoaderUtil.class);

    public final static String tmp = "/var/tmp/project-generator";

    private final static String classReg   = "(class|interface)\\s+[a-zA-Z0-9]+\\s+";
    private final static String packageReg = "package\\s+[a-zA-Z0-9.]+;";

    private final static Pattern pacakgePattern = Pattern.compile(packageReg);

    private final static Pattern classPattern = Pattern.compile(classReg);

    private final static Map<String, Class> cache = new ConcurrentHashMap<>();

    private final static JavaCompiler                        compiler    = ToolProvider
            .getSystemJavaCompiler();

    private final static List<JavaFileObject> compilationUnits = new ArrayList<>();

    private static final  List<String>         clsList          = new ArrayList<>();

    

    private final static FastClasspathScanner fastClasspathScanner = new FastClasspathScanner();

    private final static List<File> classPathList = fastClasspathScanner.getUniqueClasspathElements();

    public static String resolvePackage(String sourceCode) {
        Matcher m = pacakgePattern.matcher(sourceCode);
        if (m.find()) {
            String s = m.group(0);
            return s.split("\\s+")[1].split(";")[0];
        }
        return null;
    }

    public static String resolveFullName(String sourceCode) {
        return resolvePackage(sourceCode) + "." + resolveClassName(sourceCode);
    }

    public static String resolveClassName(String sourceCode) {
        Matcher m = classPattern.matcher(sourceCode);
        if (m.find()) {
            String s = m.group(0);
            return s.split("\\s+")[1];
        }
        return null;
    }

    public static void main(String args[]) {
        String source = "package test; public class Test { static { System.out.println(\"hello\"); } public Test() { System.out.println(\"world\"); } }";
        Class cls = loadFromSource(source);
        String packageName = resolvePackage(source);
        String className = resolveClassName(source);
        System.out.println(packageName);
        System.out.println(className);
        System.out.println(cls);

    }

    public static Class loadFromFile(String file) {
        Class ret = null;
        try {
            ret = loadFromSource(FileUtil.readFromFile(file));
            if (ret == null) {
                logger.error("Class not found try to compiler and reload");
                loadAllClass(file.replace(".java", ""));
                ret = loadFromSource(FileUtil.readFromFile(file));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Class loadFromSource(String sourceCode) {

        // Save source in .java file.
        File root = new File(tmp);
        String packageName = resolvePackage(sourceCode);
        String className = resolveClassName(sourceCode);

        if (StringUtil.isEmpty(packageName) || StringUtil.isEmpty(className)) {
            return null;
        }

        String tmpClass =
                SourceCodeUtil.convertPackage2Dir(packageName) + File.separator + className;
        File sourceFile = new File(root, tmpClass + ".java");
        sourceFile.getParentFile().mkdirs();
        try {
            Files.write(sourceFile.toPath(), sourceCode.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());

        // Load and instantiate compiled class.
        URLClassLoader classLoader = null;
        try {
            classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Class<?> cls = null;
        String fullName = packageName + "." + className;
        try {
            cls = Class.forName(fullName, true, classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        boolean ret = FileUtil.delete(root.getPath());

        System.out.println("delete " + root + " : " + ret);

        if (cls != null) {
            cache.put(fullName, cls);
        }

        return cls;
    }

    /**
     * @param inputDir
     * @param outDir
     * @param libDir   依赖的jar包 classpath
     * @return
     */
    public static String compilerFiles(String inputDir, String outDir, String libDir) {
        if (!StringUtil.isEmpty(inputDir)) {
            List<File> files = FileUtil.getAllFilesByFilter(inputDir, new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return FileUtil.isJavaFile(pathname);
                }
            }, true);

            if (files != null && files.size() > 0) {
                if (!new File(outDir).exists()) {
                    FileUtil.createDir(outDir);
                }

                return compile(files, inputDir, outDir, libDir);

            }
        }
        return null;
    }
    

    private static void reset(){
        compilationUnits.clear();
        clsList.clear();
    }

    private synchronized static String compile(List<File> files, String inputDir, String outDir, String libDir) {

        //logger.info("classPathList:{}", classPathList);
        reset();
        for (File f : files) {
            String source = FileUtil.readFromFile(f.getPath());
            JavaFileObject file = new JavaSourceFromFile(f.toURI().toString(), source);
            compilationUnits.add(file);
            clsList.add(resolveFullName(source));
        }

        if (compilationUnits.size() > 0) {
            // Compiler options
            List<String> options = new ArrayList<>();
            //options.add("-verbose");
            //options.add("-parameters");

            final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

            StandardJavaFileManager sjfm = compiler
                    .getStandardFileManager(diagnostics, null, null);
            try {
                sjfm.setLocation(StandardLocation.CLASS_OUTPUT,
                        Collections.singleton(new File(outDir)));
                sjfm.setLocation(StandardLocation.CLASS_PATH, classPathList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            JavaCompiler.CompilationTask task = compiler.getTask(null, sjfm, diagnostics,
                    options, null, compilationUnits);

            boolean success = false;
            try {
                success = task.call();
            } catch (Throwable e) {
                e.printStackTrace();
                System.out.println(
                        "load class from " + inputDir + " cls" + compilationUnits
                                + " error " + e);
            }

            System.out.println("Success: " + success);

            if (success) {
                return outDir;
            } else {
                //throw new RuntimeException("compile java  error");
                logDebug(diagnostics);
                
                logger.error("compile java  error ");
            }
        }
        return null;
    }

    private static void logDebug(DiagnosticCollector<JavaFileObject> diagnostics) {
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            System.out.println(diagnostic.getCode());
            System.out.println(diagnostic.getKind());
            System.out.println(diagnostic.getPosition());
            System.out.println(diagnostic.getStartPosition());
            System.out.println(diagnostic.getEndPosition());
            System.out.println(diagnostic.getSource());
            System.out.println(diagnostic.getMessage(null));

        }
    }

    public static String buildClassPath(String dir) {
        StringBuilder sb = new StringBuilder();
        int j = 0;

        List<File> files = FileUtil.getAllFilesByFilter(dir,
                pathname -> pathname.getAbsolutePath().endsWith(".jar"), true);
        int i = 0;
        for (File f : files) {
            sb.append(f.getAbsolutePath());
            sb.append(i++ == files.size() - 1 ? "" : ",");
        }
        return sb.toString();
    }

    public static Class loadClassByFile(String file, ClassLoader classLoader) {

        String source = FileUtil.readFromFile(file);
        if (!StringUtil.isEmpty(source)) {
            try {
                String fullName = resolveFullName(source);

                Class ret = loadFromCache(fullName);

                return ret != null ? ret : Class.forName(fullName, true, classLoader);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Class load(String classFullName, ClassLoader classLoader) {
        Class ret = null;
        if (!StringUtil.isEmpty(classFullName)) {
            ret = loadFromCache(classFullName);
            if (ret == null) {
                try {
                    return Class.forName(classFullName, true, classLoader);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public static ClassLoader loadAllClass(String classDir) {
        return loadAllClass(new String[] { classDir });
    }

    public static ClassLoader loadAllClass(String... classDir) {
        return loadAllClass(null, classDir);
    }

    public static ClassLoader loadAllClass(String libDir, String... classDir) {
        ClassLoader classLoader = null;
        for (String dir : classDir) {
            ClassLoaderUtil.compilerFiles(dir, ClassLoaderUtil.tmp, libDir);
        }
        try {
            classLoader = URLClassLoader
                    .newInstance(new URL[] { new File(ClassLoaderUtil.tmp).toURI().toURL() },
                            ClassLoaderUtil.class.getClassLoader());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //return new JoinClassLoader(ClassLoaderUtil.class.getClassLoader(),classLoader);
        return classLoader;
    }

    private static Class loadFromCache(String fullName) {
        return cache.get(fullName);
    }
}

class CompileSourceInMemory {
    public static void main(String args[]) throws IOException {

    }
}

