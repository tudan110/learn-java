package indi.tudan.learn.jvm;

/**
 * 学习类加载机制
 *
 * @author wangtan
 * @date 2020-02-14 21:13:29
 */
public class ClassLoaderTest {

    public static void main(String[] args) {

        // BootStrapClassLoader
        // 加载基本类 Class Object rt.jar
        System.out.println(System.getProperty("sun.boot.class.path"));

        // ExtClassLoader
        // 加载扩展类 ext路径中
        System.out.println(System.getProperty("java.ext.dirs"));

        // AppClassLoader
        // 加载工程路径中的 Class
        System.out.println(System.getProperty("java.class.path"));

        ClassLoader c = ClassLoaderTest.class.getClassLoader();
        while (c != null) {
            System.out.println(c);
            c = c.getParent();
        }

    }

}
