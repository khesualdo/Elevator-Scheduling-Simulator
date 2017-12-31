# Elevator Scheduling Simulator :office: :bank: :hotel: :department_store:
---

## Description

A simulation environment, which mimics the scheduling of multiple elevators within a building of any size. The multithreaded approach helps to simulate elevators running concurrently. The simulation also makes use of several group scheduling algorithms, which help to distribute the workload between elevator-cars as well as make the elevators adaptable to various situations (eg. more people in the morning - Up-Peak, everyone is going to the main floor - Zoning).

## How to Run

```sh
> cd src/
> javac *.java
> java Building <number of floors> <number of elevators> <building population>
```

## Data Structures Used

* [CopyOnWriteArrayList - Source I](http://www.baeldung.com/java-copy-on-write-arraylist), [CopyOnWriteArrayList - Source II](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CopyOnWriteArrayList.html)
    * Makes it possible to iterate over a list in a safe way, even when concurrent modification is happening
        * Creates a snapshot of the array, every time a mutation operation is invoked, hence, the iteration would always take place on the previous version of the array
    * The data structure was used to hold `floorCalls` and `carCalls` in the Elevator class
* [PriorityBlockingQueue](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/PriorityBlockingQueue.html)
    * Supplies blocking retrieval operations
    * Allows for custom element ordering
    * The data structure was used to implement `sequence`, in which the calls had to be sorted based on their passage

## Group Scheduling Algorithms Implemented

* Round-Robin
    * Is the default group scheduling algorithm
    * Calls are assigned in the order they arrive in a sequential way to single elevators

* Up-Peak
    * A variation of Round-Robin
    * If an elevator is idle for ~7 seconds, then a call is generated to take the elevator to the lobby
    * This reduces the waiting time for future passengers arriving at the lobby

* Zoning
    * Splits a building into several adjacent zones
    * Every elevator only serving floor calls that occur in the zone assigned to the respective car
    * Implemented Static Zoning, zones are assigned permanently

* Three-Passage
    * Estimates the costs that would result from assigning a new call to the elevator
    * The call is assigned to the elevator with the lowest costs
    * In case every elevator already reached 80% load (number of calls > 80% elevator's capacity), calls will not get assigned until at least one elevator falls below this mark

## Threads

### `Building.java` Thread

* The main thread
* Randomly chooses a floor from the `floors` array and calls the `generatePassenger()` method
* Sleep time is ~ 20 seconds

### `GroupElevatorController.java` Thread

* The thread calls the `scheduler()` method with an interval of ~ 2 seconds
* The `scheduler()` method scans the `floors` array and looks for passengers
* If a passenger is found, then a job is assign to one of the elevators from the `elevatorGroup` array
* The target elevator is chosen by group scheduling algorithms

### `Elevator.java` Thread I

* The `elevatorControllerThread` calls the `elevatorController()` method with an interval of ~ 2 seconds
* The `elevatorController()` method:
    * Gets a `floorCall` from `floorCalls` array
    * Assigns it a passage
    * Adds it to the `sequence` queue

### `Elevator.java` Thread II

* The `performJobThread` calls the `performJob()` method with an interval of ~ 2 seconds
* The `performJob()` method:
    * Takes a call from `sequence` queue
    * Simulates the elevator going up or down

### `Elevator.java` Thread III

* The `upPeakThread` relocates elevators in idle state to the lobby.

### `Elevator.java` Thread IV

* The `zoningThread` relocates elevators in idle state to go to a floor within their assigned zone.

## Information Sources

* [Object level locking vs. Class level locking](https://stackoverflow.com/questions/3718148/java-class-level-lock-vs-object-level-lock)
* [Intrinsic Locks and Synchronization](https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html)
* [PriorityBlockingQueue Javadoc](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/PriorityBlockingQueue.html)
* [CopyOnWriteArrayList Javadoc](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CopyOnWriteArrayList.html)
