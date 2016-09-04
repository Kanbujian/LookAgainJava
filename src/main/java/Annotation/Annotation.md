## Annotation并没有什么神奇之处

----

Java开发一直以来因为繁杂的配置文件，被很多人诟病为笨重 低效 。 

现在越来越多的框架摒弃传统的xml配置方式，转而使用和代码一致性更强的注解进行配置。

在使用框架时，我们往往会惊叹，就加一个@和几个字符串就可以完成bean的托管和entity的映射了？

其实这世界上并没有什么捷径，Annotation的精髓 annotation handler 框架已经帮我实现了，而我们只需要在需要用的地方加注解就可以，当然方便了不少！
当我们真正开始封装自己的注解简化我们的开发流程，才算是真是的使用注解了！




---

1. 定义注解


    元注解：
java.lang.annotation提供了四种元注解，专门注解其他的注解：

   @Documented –注解是否将包含在JavaDoc中
   @Retention –什么时候使用该注解
   @Target –注解用于什么地方
   @Inherited – 是否允许子类继承该注解

  1.）@Retention– 定义该注解的生命周期
  RetentionPolicy.SOURCE : 在编译阶段丢弃。这些注解在编译结束之后就不再有任何意义，所以它们不会写入字节码。@Override, @SuppressWarnings都属于这类注解。
  RetentionPolicy.CLASS : 在类加载的时候丢弃。在字节码文件的处理中有用。注解默认使用这种方式
  RetentionPolicy.RUNTIME : 始终不会丢弃，运行期也保留该注解，因此可以使用反射机制读取该注解的信息。我们自定义的注解通常使用这种方式。
  举例：bufferKnife 8.0 中@BindView 生命周期为CLASS

@Retention(CLASS) @Target(FIELD)
public @interface BindView {
  /** View ID to which the field will be bound. */
  @IdRes int value();
}
  2.）Target – 表示该注解用于什么地方。默认值为任何元素，表示该注解用于什么地方。可用的ElementType参数包括
ElementType.CONSTRUCTOR:用于描述构造器
ElementType.FIELD:成员变量、对象、属性（包括enum实例）
ElementType.LOCAL_VARIABLE:用于描述局部变量
ElementType.METHOD:用于描述方法
ElementType.PACKAGE:用于描述包
ElementType.PARAMETER:用于描述参数
ElementType.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 举例Retrofit 2 中@Field 作用域为参数

复制代码
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Field {
  String value();

  /** Specifies whether the {@linkplain #value() name} and value are already URL encoded. */
  boolean encoded() default false;
}
复制代码
 3.)@Documented–一个简单的Annotations标记注解，表示是否将注解信息添加在java文档中。
 4.)@Inherited – 定义该注释和子类的关系
     @Inherited 元注解是一个标记注解，@Inherited阐述了某个被标注的类型是被继承的。如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。

   
   
    package Anno;

    import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by kanbujian on 2016/9/1.
 */

/**
 * 类层级的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unit {
}


@Retention(RetentionPolicy.RUNTIME)
public @interface Magic {
    /**
     * 那个魔术师
     * @return
     */
    Magician magician() default Magician.Borden;

    /**
     * 魔术的名称
     * @return
     */
    String magicName() default "";

}


 依赖类
 package Anno;

/**
 * 魔术师 (刚看完致命魔术)
 * Created by kanbujian on 2016/9/1.
 */
public enum Magician {
    Angie,
    Borden
}




2. Annotation Handler

    package Anno;


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


3. 测试类


    package Anno;

/**
 * Created by kanbujian on 2016/9/1.
 */
@Unit
public class AnnotationTest {

    public static void main(String[] args) throws Exception {

        MagicHandler.registerHandler();
        simpleAction();

    }
    @Magic(magicName = "devil tech 移行换影",magician = Magician.Angie)
    public static void simpleAction() {

        System.out.println("3. I do a simple action ,before annotation ");
    }


    public static void normalAction(){
        System.out.println("2. be invoked by annotation handler ");
    }
}


