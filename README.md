Stars Group Tech Test Solution

Setup: 

- Software:
    - JDK 8u212-b04
    - Apache Maven 3.6.1
    - Intellij Idea 2019 Community Edition (to view the code)

- Install the database
    - CouchDB 2.3.1 at https://couchdb.apache.org/.
    - Test the install by executing this command from the command line: curl http://127.0.0.1:5984/, the response should be similar to the following:
      {"couchdb":"Welcome","uuid":"f33e87d034bb8c1227f866445a977555","version":"1.6.1","vendor":{"version":"16.04","name":"Ubuntu"}}
    - Create a database called "stars_group", via the UI at http://127.0.0.1:5984/_utils/ (you'll need to have logged in first)
    
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
        
Observing the System Behaviour
        
No actual artifact has been produced, rather the behaviour of the developed software can be observed via tests. Three different categories of tests have been written:
- Unit Tests - execute via command line 'mvn clean install -Punit-tests'. These tests have no external dependency outside of the JVM. They are the fastest to execute and ensure each class aka unit executes in the correct logical manner.
- Integration Tests - execute via command line 'mvn clean install -Pintegration-tests'. These tests interact with one or more, but not all, external dependencies outside of the JVM (eg database, file system, network location), or prove that a combination of classes interact correctly to provide the required functionality.
- System Tests - execute via command line 'mvn clean install -Psystem-tests'. These tests interact with all necessary external dependencies in order to provide increased confidence that any actual deployed software will behave correctly with real external dependencies.  

To run the System Tests please take the following pre-requisite steps:
- ensure you have taken the previous mentioned steps to install the software, database, mock server, kafka, and kafka topic.
- execute this command from the kafka installation folder to write data from the mock feed into a kafka topic: "nc localhost 8282 | bin/kafka-console-producer.sh --broker-list localhost:9092 --topic tech_test"

There are no checks that the pre-requisite steps have been taken, in the code. 
The full package of the System Test is: com.starsgroup.techtest.feed.kafka_streams_impl.FeedConsumerSystemTest
The default period of the test execution is 60 seconds. This can be changed and executed as necessary in the code itself.


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
- For the purposes of the demo, the 'Update' packets have been written straight to the database. The idea is that, to avoid slow down of the processing of the feed data, updates could be applied either Just-In-Time when the data is read from the database, or via a separate parallel job. Ideally there would be a different 'Updates' database that would store these.





