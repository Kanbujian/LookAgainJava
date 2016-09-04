package Annotation;

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
