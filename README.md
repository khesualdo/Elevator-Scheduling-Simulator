## Data Structures Used

* [CopyOnWriteArrayList - 3d Party Source](http://www.baeldung.com/java-copy-on-write-arraylist)
* [CopyOnWriteArrayList - Javadoc](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CopyOnWriteArrayList.html)
* [PriorityBlockingQueue](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/PriorityBlockingQueue.html)

## Group Scheduling Algorithms Implemented

* Round-Robin
    * The implementation is found in `RoundRobin.java`
    * Is the default group scheduling algorithm

* Up-Peak
    * Uses Round-Robin
    * If an elevator is idle for ~7 seconds, then the elevator generates a call to itself to go down to the lobby
    * This reduces the waiting time for future passengers arriving at the lobby

## Threads

### `Building.java` Thread

* The main thread of the program
* Randomly chooses a floor from the `floors` array and calls the `generatePassenger()` method
* Sleep time is ~ 20 seconds

### `GroupElevatorController.java` Thread

* The thread calls the `scheduler()` method with an interval of ~ 2 seconds
* The `scheduler()` method scans the `floors` array and looks for passengers
* If a passenger is found, then a job is assign to one of the elevators from the `elevatorGroup` array
* The target elevator is chosen by group scheduling algorithms

### `Elevator.java` Thread I

* The `performJobThread` calls the `performJob()` method with an interval of ~ 2 seconds
* The `performJob()` method:
    * Takes a call from `sequence` queue
    * Simulates the elevator going up or down

### `Elevator.java` Thread II

* The `elevatorControllerThread` calls the `elevatorController()` method with an interval of ~ 2 seconds
* The `elevatorController()` method:
    * Gets a `floorCall` from `floorCalls` array
    * Assigns it a passage
    * Adds it to the `sequence` queue

### `Elevator.java` Thread III

* The `upPeakThread`

## Information Sources

* [Object level locking vs. Class level locking](https://stackoverflow.com/questions/3718148/java-class-level-lock-vs-object-level-lock)
* [Intrinsic Locks and Synchronization](https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html)
