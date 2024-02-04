package com.houssem.statemachinedemo.service.impl;

import com.houssem.statemachinedemo.enums.Events;
import com.houssem.statemachinedemo.enums.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class StateMachineSimpleUseCase implements CommandLineRunner {

    @Autowired
    private StateMachineService<States, Events> stateMachineService;

    /**
     * Instantiate a state machine
     * then pass a variable through the context
     * then force reset at a certain state
     * then resume by sending the corresponding event
     * the statemachine won't allow resume anymore because it's at the last state (end state)
     */
    private void stateMachineUseCase() {

        StateMachine<States, Events> stateMachine = stateMachineService.acquireStateMachine(UUID.randomUUID().toString(), true);

        String machineIdentifier = stateMachine.getId();
        Map<String, Object> variables = new HashMap<>();
        variables.put("machineIdentifier", machineIdentifier);

        // Retrieve from the context when needed - example when defining an action bean for a transition
        stateMachine.getExtendedState().getVariables().putAll(variables);

        stateMachine.getStateMachineAccessor().doWithAllRegions(stateMachineAccess -> {
            stateMachineAccess.resetStateMachineReactively(
                    new DefaultStateMachineContext<>(States.THIRD, null, null, null, null, machineIdentifier)
            ).subscribe();
        });

        stateMachine.sendEvent(Mono.just(MessageBuilder
                        .withPayload(Events.END)
                        .build()))
                .subscribe();
    }

    @Override
    public void run(String... args) throws Exception {
        this.stateMachineUseCase();
    }
}
