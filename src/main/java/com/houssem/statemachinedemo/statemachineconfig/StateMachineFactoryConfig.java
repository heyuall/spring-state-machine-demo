package com.houssem.statemachinedemo.statemachineconfig;

import com.houssem.statemachinedemo.enums.Events;
import com.houssem.statemachinedemo.enums.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.*;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

@Configuration
@EnableStateMachineFactory()
public class StateMachineFactoryConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    private static final Logger logger = LoggerFactory.getLogger(StateMachineConfig.class);

    @Autowired
    private StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .listener(listener())
                .and()
                .withPersistence()
                .runtimePersister(stateMachineRuntimePersister);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.INITIAL)
                .state(States.FIRST)
                .state(States.SECOND)
                .state(States.THIRD)
                .end(States.LAST);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.INITIAL).target(States.FIRST).event(Events.START).action(stateContext -> {
                    logger.info("Action 1 is LOGGED");
                })
                .and()
                .withExternal()
                .source(States.FIRST).target(States.SECOND).event(Events.GOSECOND)
                .and()
                .withExternal()
                .source(States.SECOND).target(States.THIRD).event(Events.GOTHIRD)
                .and()
                .withExternal()
                .source(States.THIRD).target(States.LAST).event(Events.END);
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                logger.info("Transitioned from {} to {}", from == null ? "none" : from.getId(), to.getId());
            }

            @Override
            public void stateEntered(State<States, Events> state) {
                logger.info("Entered state: {}", state.getId());
            }

            @Override
            public void stateExited(State<States, Events> state) {
                logger.info("Exited state: {}", state.getId());
            }

            @Override
            public void eventNotAccepted(Message<Events> event) {
                logger.info("Event not accepted: {}", event.getPayload());
            }

            @Override
            public void transition(Transition<States, Events> transition) {
                logger.info("Transition: {} -> {}",
                        transition.getSource() != null ? transition.getSource().getId() : transition.getSource(),
                        transition.getTarget().getId());
            }

            @Override
            public void stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {
                logger.info("stateMachineError: ", stateMachine.getId(), exception);
            }

            @Override
            public void stateContext(StateContext<States, Events> stateContext) {
            }
        };
    }

}
