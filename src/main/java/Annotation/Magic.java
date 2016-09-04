package Annotation;

import java.lang.annotation.*;

/**
 * magic
 * Created by kanbujian on 2016/9/1.
 */
@Target(ElementType.METHOD)
@Documented
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
