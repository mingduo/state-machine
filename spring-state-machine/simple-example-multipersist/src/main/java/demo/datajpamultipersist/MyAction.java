package demo.datajpamultipersist;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * @author : weizc
 * @since 2021/6/25
 */
@Slf4j
@Component
public class MyAction implements Action<String, String> {
    @Override
    public void execute(StateContext<String, String> context) {
        log.info("Stage:{} ,Message:{} ,StateMachine:{} source:{} target:{}",
                context.getStage(), context.getMessage(),
                context.getStateMachine(),
                context.getSource(),
                context.getTarget()
        );
    }
}
