##Java8 Streams API

函数式编程和LINQ的关系
IBM[http://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/]

-----
串行Stream
sequential


    package base;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 今天我才体会到一点函数式编程的好处
 * 允许我们去表达我们想要完成什么而不是要怎样做
 * Created by kanbujian on 2016/8/18.
 */
public class StreamTest {

    public static void main(String[] args) {
        List<Apple> appList = new ArrayList<Apple>();
        Apple app1 = new Apple("Tiny", Color.green, 50.0);
        Apple app2 = new Apple("Tiny", Color.red, 75.0);
        appList.add(app1); //  Tiny是青苹果
        appList.add(app2); //
        appList.add(app2);   //  Tiny长熟啦
        appList.add(new Apple("Milana", Color.yellow, 50.0));  // Milana
        // 得到最重的苹果
        Apple maxApp = appList.stream().max((o1, o2) -> o1.getPounds().compareTo(o2.getPounds())).get();
        System.out.println("最重的苹果 : " + maxApp);
        // 去重 distinct 只能调用Object的equals()方法，重写给定对象的equals方法，不会被调用
        System.out.println(app1.equals(app2));
        long trueSize = appList.stream().distinct().count();
        System.out.println("去重后真正的个数 : " + trueSize);
        // 找到所有的红苹果的名字
        appList.stream()
            .filter(o -> o.getColor() == Color.red)
            .map(o -> o.getNickName())
            .forEach(str -> System.out.println(str + " is a red apple "));
        // 是否全部是苹果
        System.out.println("是否全部是苹果: " + appList.stream().allMatch(o -> o instanceof Apple));
        // 是否存在发黄的苹果
        System.out.println("是否存在发黄的苹果: " + appList.stream().anyMatch(o -> o.color == Color.yellow));
        // 不存在 紫苹果
        System.out.println("不存在 紫苹果: " + appList.stream().noneMatch(o -> o.color == Color.purple));

        // 懒加载
        appList.stream().sorted();// 此时并不会报错， base.StreamTest$Apple cannot be cast to java.lang.Comparable
        // appList.stream().sorted().forEach(str->System.out.println(str )); 当需要值的时候就会抛异常
        appList.stream().
            sorted((o1, o2) -> {
                if (o1.getPounds().compareTo(o2.getPounds()) == 0) {
                    return -o1.getNickName().compareTo(o2.getNickName());
                } else {
                    return -o1.getPounds().compareTo(o2.getPounds());
                }
            }).forEach(str -> System.out.println("先按重量，再按名称排序-- " + str));


        appList.stream().limit(1).forEach(str -> System.out.println("limit >> " + str));

        // peek 类似于forEach(终端操作) ,只不过是中间操作，会返回一个新流
        appList.stream().peek(str -> System.out.println("now you see me " + str)).collect(Collectors.toList());
        // flatMap
        appList.stream()
            .flatMap((o) -> Stream.of(o.getNickName()))
            .collect(Collectors.toList())
            .forEach(str -> System.out.println(" is " + str.getClass()));


    }

    static class Apple {
        private String nickName;
        private Color color;
        private Double pounds;

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setPounds(Double pounds) {
            this.pounds = pounds;
        }

        public String getNickName() {
            return nickName;
        }

        public Color getColor() {
            return color;
        }

        public Double getPounds() {
            return pounds;
        }

        public Apple(String nickName, Color color, Double pounds) {
            this.nickName = nickName;
            this.color = color;
            this.pounds = pounds;
        }

        @Override
        public String toString() {
            return "Apple{" +
                "nickName='" + nickName + '\'' +
                ", color=" + color +
                ", pounds=" + pounds +
                '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Apple apple = (Apple) o;
            if (nickName.equals(apple.nickName)) {
                // 只要名字相等，我就认为相等了
                return true;
            }

            if (color != apple.color) return false;
            return pounds != null ? pounds.equals(apple.pounds) : apple.pounds == null;

        }

        @Override
        public int hashCode() {
            int result = color != null ? color.hashCode() : 0;
            result = 31 * result + (pounds != null ? pounds.hashCode() : 0);
            return result;
        }
    }

    enum Color {
        red,
        green,
        yellow,
        purple

    }
}
