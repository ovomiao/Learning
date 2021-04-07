package p2_lambda;

/**
 * 在 Java 中将方法作为参数传递给方法方案：
 *      * 将接口把方法包装，再将接口传递给目标的方法
 */
public interface Function {
    // 一个稍后会被调用的方法，即回调方法
    void function();
}

class Main {

    // 间接实现接收方法作为形参的方法
    public static void test(Function function) {
        if (function != null) {
            function.function();
        }
    }

    public static void main(String[] args) {
        test(new Function() {
            @Override
            public void function() {
                System.out.printf("拉拉");
            }
        });
    }
}

