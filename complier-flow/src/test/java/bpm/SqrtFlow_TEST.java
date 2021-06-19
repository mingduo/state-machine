package bpm;

import com.alibaba.compileflow.engine.ProcessEngine;
import com.alibaba.compileflow.engine.ProcessEngineFactory;
import com.alibaba.compileflow.engine.definition.tbbpm.TbbpmModel;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SqrtFlow_TEST extends TestCase {


    @Test
    public void testProcess() throws Exception {
        String code = "bpm.sqrt";
        ProcessEngine<TbbpmModel> engine = ProcessEngineFactory.getProcessEngine();
        System.out.println(engine.getJavaCode(code));
        Map<String, Object> context = new HashMap<>();
        context.put("num", 12);
        try {
            System.out.println(engine.start(code, context));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

}