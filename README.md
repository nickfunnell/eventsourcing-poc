An Event Sourcing proof-of-concept application, built as a learning exercise in Java, and based largely on [Greg Young's 
EventSourcing and CQRS material](http://codebetter.com/gregyoung/2010/02/20/why-use-event-sourcing/). The application takes the form a simple Virtual Instance management system - it can create VirtualInstances of a given type, stop, start and destroy them. While there are no external dependencies, one would anticipate coupling this to a Cloud VM provider (Azure, AWS, etc.). This application's intent is to maintain the state of the Virtual Instances and allow queries on this information. The focus here is purely on the Event Sourcing architecture, and not on the management of Cloud instances (in fact, one could replace the VirtualInstance class with any 'thing' that can be created, started, stopped and destroyed). The application only employs a single Aggregate, and is probably overly-complex for the simple use case presented.

Key Points
----------

* Event Sourcing and CQRS encourages a 'rich domain model'. Any change of state results in an 'event' being generated,
which is persisted in an 'EventStore'. Current state can be ascertained at any time by simply summing all historical 
events. Thus, it discourages the use of a traditional RDBS for the persistence of state.
* If no RDBS is used, there is no requirement for an ORM layer, and thus we avoid any consequent [Impedence Mismatch](http://www.agiledata.org/essays/impedanceMismatch.html).
This means that we can avoid an [Anemic Domain Model](http://www.martinfowler.com/bliki/AnemicDomainModel.html) and 
focus on building a behaviourally-rich model.
* By convention, all Events are named in the past tense (because an event has happened, and can't 'un-happen' - a 
'reversal' event would be employed to achieve the same result.)
* Command-side (Read) and Query-side (Write) are completely isolated from one another. Any methods modifying state 
should not return any values, and any methods returning values should not modify state (no 'side-effects'). Thus, in a
'normal' application, any request to modify state (change email address, for instance), would not return any values - 
a further call to the 'read' side would be employed to fetch the updated object.
* The Read side consists of a number of read-optimised views (one or more of which may even use a RDBS). The current
state is maintained in these views on an 'eventually consistent' basis, by subscribing to the events generated by
the Write side. A Thin Read Layer (TRL) is employed, which simply consists of DTOs which can be passed directly to the
client (and may, indeed, look very like an anaemic domain model).
* Further Read views can be developed at any point in the application's lifecycle - the complete record of events can be
interrogated using a ['Projection'](http://codebetter.com/gregyoung/2013/02/13/projections-1-the-theory/). Thus, a 
report could be created that would not only run from the moment it's deployed to Production, but could also be run on
any historical data.

ToDo
----

* Refactor to fully decouple Command and Query sides using Messaging implementation (EventStore, Kafka, etc.) or brevity, read side subscribes to a shared 'pseudo-bus' which the Command side writes to)
* Provide a REST API to allow a separate Client process to call the application.
* Genericise InstanceRepository to allow Events to be persisted for any given aggregate.
* Clean up the VirtualInstance aggregate, and remove 'instance of'.
* Add 'started' and 'stopped' timestamp records to VirtualInstances, then write an example projection that can calculate total uptime for a VirtualInstance using a 'left fold' algorithm against historical events.
* Rewrite in Golang (or similar). CQRS encourages a more 'functional' style, and the domain-driven design becomes more focused on modelling 'behaviours' than objects themselves.