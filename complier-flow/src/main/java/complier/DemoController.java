package complier;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xuan
 * @since 2020/8/16
 */
@RestController
public class DemoController {

    @Resource
    private RunCase runCase;


    @RequestMapping("/run")
    public String run() {
        runCase.run();
        return "Run ok.";
    }

    @RequestMapping("/run2")
    public String run2() {
        runCase.run2();
        return "Run ok.";
    }

}
