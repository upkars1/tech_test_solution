Stars Group Tech Test Solution - Notes

The solution offered is for demonstration purposes only, as the functionality is only currently demonstrable via Unit/Integration and System Tests.

Setup: 

- Software:
    - JDK 8u212-b04
    - Apache Maven 3.6.1
    - Intellij Idea 2019 Community Edition (to view the code)

- Enable the mock server to generate the mock data
    - Check out the feedme-tech-test repository at https://github.com/thestarsgroup/feedme-tech-test.
    - Execute on command line: 'docker-compose up' having followed the 'Getting Started' instructions on the feedme repository.

- Start Kafka
    - Download and extract Kafka with resource at https://www-us.apache.org/dist/kafka/2.3.1/kafka_2.12-2.3.1.tgz
    - Navigate to inside the expanded kafka directory so that the bin and config directories are available.
    - Start zookeeper: bin/zookeeper-server-start.sh config/zookeeper.properties   
    - Start kafka: bin/kafka-server-start.sh config/server.properties

- Create a Kafka topic
    - Execute: bin/kafka-topics.sh --create --topic tech_test --replication-factor 1 --partitions 1 --zookeeper localhost:2181
        
Running the tests:

- Unit Tests: mvn clean install -Punit-tests
- Integration Tests: mvn clean install -Pintegration-tests
- System Tests: mvn clean install -Psystem-tests

Before running system tests ensure that the following command has been executed to populate the Kafka topic with mock data:
-  nc localhost 8282 | bin/kafka-console-producer.sh --broker-list localhost:9092 --topic tech_test

Key Technology Choices

- Kafka
    - Kafka has been chosen to act as the 'Producer' of data into a topic by reading from the feed. It allows the data to be taken from the feed with the following advantages:
        - Immediate persistence of the data with minimal processing. Taking the raw values and storing immediately protects against possible data loss from any 'catastrophic' system event.
        - Kafka allows for manual 'commits' - the ability to mark the log as read and processed up to a certain point. This is possible using other mechanisms, eg. storing the raw values to a conventional database table, however this would most likely require bespoke code. 
        
- Parser implemented using TDD.
    - The parsing of the event data required bespoke logic, which was best handled by writing tests first against smaller, simpler data structures in order to implement the logic incrementally.
    - The decision was made to parse to simple POJOs, before using a standard JSON (Jackson) library for formatting the POJO. 
        - It is a lot cleaner and clearer in terms of the code, to parse into POJOs first. If there are performance implications by parsing to these intermediate objects then these would need to be considered.       

Assumptions / Clarifications / Improvements

- The data gives 0 and 1 values for the display and suspended values. It has been assumed that this follows IT convention of 0 meaning false and 1 meaning true. This needs to be clarified with the feed providers.
- It has been assumed that data will be present for every field. If any of the fields are not mandatory, then we need to adjust the parser to cope with this (delimiters next to each other with no data).
- A possible improvement would be to transform the POJOs into other objects that would represent an internal definition of an Event/Market/Outcome. This would isolate any future changes in the feed to just this point in the code. It would also allow for a number of different feeds to be represented in the same internal Stars Group backend systems. 






