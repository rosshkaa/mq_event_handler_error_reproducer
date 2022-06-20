### Description
This code is reproducing the issue described in https://github.com/eclipse-ee4j/openmq/issues/1502
Main method runs the set of operations over com.sun.messaging.jms.Connection in parallel to provoke
ConcurrentModificationException during iterating over EventHandler's consumerEventListeners.

The operation set is demonstrative in nature and has not any sacral sense.

### Build
`
mvn clean install
`

### Run
`
java -jar openmq_eventhandler_error_demo.jar
`
