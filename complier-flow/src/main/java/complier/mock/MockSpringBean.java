package complier.mock;

import org.springframework.stereotype.Component;

/**
 * Mock一个SpringBean
 */
@Component("mockSpringBean")
public class MockSpringBean {

    public void sing(String name) {
        System.out.println(name + " is singing");
    }

    public void payMoney(int price) {
        System.out.println("actually paid money: " + price);
    }
}
