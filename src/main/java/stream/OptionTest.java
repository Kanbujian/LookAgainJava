package stream;

import java.util.Optional;

/**
 * Optional 避免java常见nullPointerException
 * Created by kanbujian on 2016/8/24 0024.
 */
public class OptionTest {
    public static void main(String[] args) throws Throwable {
        // Optional 是一个对象容器，可以包括null 非null的对象


        // 将一个非null对象转化为Optional
        Optional<String> op1 = Optional.of("nikan");
        // 将一个对象(可以为null)转化为Optional
        Optional<String> op2 = Optional.ofNullable(null);
        // 如果存在返回，否则抛出NoSuchElementException异常
        System.out.println(op1.get());
        // 如果存在返回,否则返回给定值
        System.out.println(op1.orElse("op1 default value"));
        // 如果存在则返回，否则返回Supplier得到的结果
        System.out.println(op1.orElseGet(() -> {
            return "op1 get from net";
        }));
        // 如果存在则返回，否则返回Supplier抛出的异常
        try {
            System.out.println(op1.orElseThrow(() -> {

                throw new RuntimeException("op1 has null value");

            }));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // map
        System.out.println(op1.map(str->str+" 通过map方法").get());

        //flatMap
        System.out.println(op1.flatMap(str->Optional.of("通过flatmap方法 "+str)).get());

        // filter
        System.out.println(op1.filter(str->str.contains("ni")).get());



        System.out.println("-------我是分隔线------");
        //System.out.println(op2.get());
        System.out.println(op2.orElse("op2 default value"));
        System.out.println(op2.orElseGet(() -> {
            return "op2 get from net";
        }));
        try {
            System.out.println(op2.orElseThrow(() -> {
                throw new RuntimeException("op2 has null value");
            }));
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }
}
