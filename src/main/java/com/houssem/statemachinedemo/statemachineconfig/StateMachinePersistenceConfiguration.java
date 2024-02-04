package com.houssem.statemachinedemo.statemachineconfig;

import com.houssem.statemachinedemo.enums.Events;
import com.houssem.statemachinedemo.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
public class StateMachinePersistenceConfiguration {

    @Bean
    public StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister(
            final JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }
}

