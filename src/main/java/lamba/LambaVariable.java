package lamba;

import java.util.ArrayList;
import java.util.List;

/**
 * Lambda表达式引用外部变量
 * Created by kanbujian on 2016/8/24 0024.
 */
public class LambaVariable {
    public static void main(String[] args) {
        // 值 从Lambda获得数据，需要final修饰
        final String[] consumer = {"$$__$$"};
        // 值 供Lambda表达式使用，不用final修饰
        String producer = "__%%__";
        List<String> strList = new ArrayList<String>();
        strList.add("mike");
        strList.add("nike");
        strList.add("Joe");
        strList.forEach(str -> {
            // 可以转化为一个只有一个值的final数组
            consumer[0] +=str+"__";
            str+=producer;
        });

        System.out.println("消费Lamba表达式中的数据 : "+consumer[0]);
        System.out.println("数据供Lamba表达式使用 : "+producer);
        strList.forEach(s->System.out.println(s));




    }
}
