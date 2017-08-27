## Papers Used
* [Context-Aware Elevator Scheduling](https://www.semanticscholar.org/paper/Context-Aware-Elevator-Scheduling-Strang-Bauer/6af00d65db3fe72658d041829ddfc682c0a12e46)


## Elevator Model

A building is defined as consisting of N floors (excluding the lobby) and L elevator cars, where these cars with index {0, 1, ... , L − 1} are either idle and standing on one of the floors {0, 1, ... , N} or moving with direction UP or DOWN.

**Floor calls** are calls initiated by passengers waiting on a floor by pressing the buttons UP or DOWN.

**Floor calls** are registered with the **Group Elevator Controller**.

**Group Elevator Controller** assigns the calls to one of the cars based on the result of a car selection algorithm. Assignment may be delayed for as long as no elevator with sufficient capacity is available.

**Car calls** are calls initiated by passengers who boarded an elevator and press one of the floor buttons inside the elevator car, where the call floor corresponds to the passengers’ desired exit floor

**Car calls** are immediately assigned to the **Elevator Controller**.

**Elevator Controller** is responsible for sorting calls assigned by the **Group Elevator Controller** into the elevator’s internal sequence list.

---

If at least one call is assigned, the elevator moves into the target’s floor direction, that is, the level of the first call of the sequence list.

Once the target floor is reached, the following operations are performed:

1. If the elevator’s current floor is equal to the exit floor of a boarded passenger, the passenger leaves the elevator
2. Passengers waiting on the current floor are boarding the elevator. Of course, passengers can only enter an elevator if the maximal passenger capacity has not already been reached. Otherwise, they initiate a new floor call
and wait for the next elevator.
3. Calls which refer to the current floor (floor and/or **Car call**) are removed from the sequence and a new target floor is set.

## Traffic Pattens

* Up-peak
	* In the morning, with people entering the building in the lobby and travelling upwards to their offices.
* Down-peak
	* In the late afternoon, with workers leaving their offices and heading downwards to the lobby.
* Mixed peak
	* At lunchtime with people simultaneously leaving and entering the building.

## Scheduling Algorithms

Elevator scheduling is performed on two levels, **Group Elevator Controller** and **Elevator Controller**. The **Group Elevator Controller** is in charge of all elevator banks and performs the global planning. Once a call is assigned to an elevator, the appropriate **Elevator Controller**. performs local planning in the scope of the single elevator.

Scheduling on the **Elevator Controller** level (local planning) is usually performed in a one-way fashion: The elevator answers all calls along the current movement direction until the last floor with calls - the reversal floor - is reached, where direction is reversed and all calls along the new direction are answered until the next reversal floor.

* Closest-Call-First
* Round-Robin Group Elevator Scheduling
	* First-come-first-served approach
	* The goal is to achieve an equal load for all cars
	* Calls are assigned in the order they arrive in a sequential way to single elevators. Call 0 is assigned to car
0, call 1 to car 1, . . . call L to car L, call L + 1 to car 0, and so on
* Up-Peak Group Elevator Scheduling
	* Special variant of Round-Robin
	* For use in up-peak traffic situations
	* Special parking policy:
		* In case an elevator is idle, the **Group Elevator Controller** creates a floor call with level 0 in order to relocate the car to the lobby. The idea is to reduce the waiting time for future passengers arriving at the lobby.
*  Down-Peak Group Elevator Scheduling
	* Same as Up-Peak Group Elevator Scheduling, but
	* The **Group Elevator Controller** creates a floor call with level N in order to relocate the car to the highest floor. The idea is to reduce the waiting time for future passengers going down to the lobby.
	* Or position the elevators at N/L distance from each other
* Zoning Group Elevator Scheduling
	* Splits a (usually high-rise) building into several adjacent zones
	* Every elevator only serving floor calls that occur in the zone assigned to the respective car
	* The primary objective is to reduce the number of car stops and therefore the total journey time
	* A building served by `m` cars can be split into up to `m` zones, where these `m` zones are either disjoint or not
	* Cars in idle state are repositioned to the **zone’s center level**, therefore minimizing waiting time for passengers on adjacent floors
	* Zoning may be either static, where zones are assigned permanently to a group
of elevators, or 
	* Dynamic, where zones are assigned temporarily and time-scheduled
* Three Passage Group Elevator Scheduling
	* Special variant of the Estimated Time of Arrival (ETA) Based Elevator Group Control Algorithm
	* Used to determine the service order of the floor calls:
		* **P1 Passage One** calls can be served by the elevator along the **current** travel direction
		* **P2 Passage Two** calls can be served after **reversing** the direction **once**
		* **P3 Passage Three** calls **require reversing** the direction **twice**
	* Floor calls with direction UP are only served by an elevator when travelling upwards, calls with direction DOWN only when travelling downwards

	![Three Passage Group Elevator Scheduling Diagram](https://github.com/00111000/Elevator-Scheduling/blob/master/Three-Passage-Group-Elevator-Scheduling.png)

	* The calls from floors 5 and 7 with direction UP can be served along the elevator’s current movement direction and are therefore P1 calls. Level 7, being the highest floor with the last calls to serve, is the **reversal floor**, as the elevator will have to reverse its travel direction at this position in order to serve the remaining calls
	* The calls from level 6 and 2, both with direction DOWN, are therefore P2 calls, as direction is reversed once prior to serving
	* Starting from level 2 the elevator moves downward to the next reversal floor, the lobby, where direction is reversed for the second time before answering the calls from the lobby and level 1, therefore making these remaining calls P3 calls.

	* Car calls are always either P1 or P2 calls, as they require none or one reversal at most.

	* Prior to assigning a call to a car, the **Group Elevator Controller** submits the call to every **Elevator Controller**, which perform a cost analysis. This analysis estimates the costs that would result from assigning the new call to the elevator, as the necessary amount of time needed for serving all calls could increase, because of an additional stop and a longer passage
	* Stop costs are static and include the period of time necessary for opening the door, unloading and loading each one passenger and closing the door
	* The call is assigned to the elevator with the lowest costs
	* In case every elevator already reached 80% load (number of calls > 80% car capacity), calls will not get assigned until at least one elevator falls below this mark

## Context-Aware Elevator Systems

*









