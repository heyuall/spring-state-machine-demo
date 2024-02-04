# spring-state-machine-demo
Spring State Machine Example Project
## Overview

This project serves as an example of using Spring State Machine to implement a simple use case. The use case involves creating a state machine, passing variables through the context, resetting the state machine to a specific state, and sending events.

The project is built with Java 17 and uses Spring State Machine version 4.0.0.

## Getting Started

### Prerequisites

- Java 17
- Maven
- Spring State Machine 4.0.0

### Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/heyuall/spring-state-machine-demo.git
   ```
2. **Build the project:**

   ```bash
   mvn clean install
   ```
### Configuration

1. **State Machine Config (`StateMachineConfig.java`):**
   This configuration class uses `StateMachineFactory` to configure the state machine. This approach is suitable when dealing with multiple state machines, allowing for effective lifecycle management.
  

2. **State Machine Persistence Config (`StateMachinePersistenceConfiguration.java`):**
   This configuration class focuses on state machine persistence. It utilizes `JpaPersistingStateMachineInterceptor` to persist the state machine context, storing it in the automatically created "state_machine" table.
  

3. **State Machine Service Config (`StateMachineServiceConfig.java`):**
   This configuration class is responsible for setting up the state machine service. It wires up the `StateMachineFactory` and `StateMachineRuntimePersister` to create a `DefaultStateMachineService` that manages state machines.

### State Machine Use Case Service

This service, implemented in `StateMachineUseCaseExampleService`, demonstrates a simple use case using Spring State Machine. The steps are as follows:

1. **Instantiate State Machine:**
   - Acquires a new state machine instance from the `StateMachineService` using `acquireStateMachine`.
   - Generates a unique identifier for the state machine.

2. **Pass Variables to Context:**
   - Creates a map of variables, e.g., `machineIdentifier`, to be passed through the state machine context.
   - Sets these variables in the state machine's extended state for potential retrieval during transitions.

3. **Force Reset to a Specific State:**
   - Utilizes `resetStateMachineReactively` to forcefully reset the state machine to a designated state (e.g., `States.THIRD`).
   - This action effectively places the state machine in a predetermined state, ready for further processing.

4. **Resume by Sending an Event:**
   - Sends an event (e.g., `Events.END`) to the state machine using `sendEvent`.
   - This step demonstrates a transition to the end state.

5. **Note on Resuming:**
   - A comment indicates that attempting to resume the state machine after reaching the end state may not be allowed, as it's already at the last state.

6. **Run the Use Case:**
   - The use case is initiated on application start by implementing `CommandLineRunner` and calling `stateMachineUseCase`.

These steps illustrate a basic flow in working with Spring State Machine, involving instantiation, context manipulation, state resetting, and event triggering.
  

### Usage
The state machine is created, and the use case is executed when the application starts.  
The `CommandLineRunner` implementation in `StateMachineUseCaseExampleService` initiates the use case.

The state machine context is automatically persisted in the **state_machine** table, which is created by Spring State Machine.


### Run the Application
To run the application, execute:
    
   ```bash
   java -jar target/stateMachineDemo0.0.1-SNAPSHOT.jar
   ``` 
