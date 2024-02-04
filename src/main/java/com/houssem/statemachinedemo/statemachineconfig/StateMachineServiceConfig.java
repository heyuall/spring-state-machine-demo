package com.houssem.statemachinedemo.statemachineconfig;

import com.houssem.statemachinedemo.enums.Events;
import com.houssem.statemachinedemo.enums.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

@Configuration
public class StateMachineServiceConfig {

    @Autowired
    StateMachineFactory<States, Events> stateMachineFactory;

    @Autowired
    StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister;

    @Bean
    public StateMachineService<States, Events> stateMachineService() {
        return new DefaultStateMachineService<>(stateMachineFactory, stateMachineRuntimePersister);
    }
}
