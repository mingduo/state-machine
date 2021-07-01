package complier.mock;

/**
 * Mock一个普通的JavaBean
 */
public class MockJavaClazz {

    public int calPrice(int num) {
        System.out.println("total price: " + 30 * num);
        return 30 * num;
    }

    public double sqrt(double a) {
        return Math.sqrt(a);
    }

}
