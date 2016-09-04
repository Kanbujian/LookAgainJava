package Annotation;


import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;
/**
 * 魔术的背后的秘密
 * Annotation Handler
 * Created by kanbujian on 2016/9/1.
 */
public class MagicHandler {
    public static void registerHandler() throws Exception{
        // 通过Reflections框架简化反射操作
        Reflections reflections=new Reflections("Anno");
        Set<Class<?>> targetList=reflections.getTypesAnnotatedWith(Unit.class);
        targetList.forEach(target->{
            try {
                // 注册handler
                addHandlerForTarget(target);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 注释handler
     * @param target
     * @throws Exception
     */
    private static void addHandlerForTarget(Class<?> target) throws Exception {
        if(target.isAnnotationPresent(Unit.class)){

        }
        // 得到实例
        Object instance=target.newInstance();
        // 得到所有的方法
        Method[] methods=target.getMethods();

        for(Method method:methods){
            if(method.isAnnotationPresent(Magic.class)){
                Magic magic=method.getAnnotation(Magic.class);
                Magician magician=magic.magician();
                String magicName=magic.magicName();
                //根据魔术师的不同，展现不同的动作
                if(Magician.Angie.equals(magician)){
                    System.out.print("1. [ I'm "+Magician.Angie+" , using  "+ magicName);
                    System.out.println(" create me kill me ]");
                    // 通过反射调用其它方法，配合接口定义
                    target.getMethod("normalAction").invoke(instance);
                }else if(Magician.Borden.equals(magician)){
                    System.out.println("1. [ I'm "+Magician.Borden+" ,  using  "+ magicName);
                    System.out.println("kill me heal me ]");
                    target.getMethod("normalAction").invoke(instance);
                }
            }
        }
    }
}
