package JavaLambda.Interfaces;

/**
 * Created by Илоныч on 16.08.2016.
 */
public class ClassToImplDefault implements DefaultInterface {
    @Override
    public String notDefaultMethod() {
        return "Sorry, im not default (Q_Q)";
    }

    public static void main(String[] args) {
        ClassToImplDefault test = new ClassToImplDefault();
        System.out.println(test.getDefaultString());
        System.out.println(test.notDefaultMethod());
    }
}
