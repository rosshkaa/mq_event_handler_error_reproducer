### Description
This code is reproducing the issue described in https://github.com/eclipse-ee4j/openmq/issues/1502
Main method runs the set of operations over com.sun.messaging.jms.Connection in parallel to provoke
ConcurrentModificationException during iterating over EventHandler's consumerEventListeners.

### Build
`
mvn clean install
`

### Run
Run [openmq](https://github.com/eclipse-ee4j/openmq) broker on default configuration localhost:7676.

Then run this application with: 

`
java -jar target/openmq_eventhandler_error_demo.jar
`
