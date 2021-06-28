package demo.datajpamultipersist;

import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author : weizc
 * @since 2021/6/25
 */
@Component
public class MyGuard implements Guard<String, String> {
    @Override
    public boolean evaluate(StateContext<String, String> context) {
        Message<String> message = context.getMessage();
        ExtendedState extendedState = context.getExtendedState();
        Map<Object, Object> variables = extendedState.getVariables();
        return variables.containsKey("id");
    }
}
