# Distributed System Simulation  
This project simulates a distributed system with a Master program, multiple Slave programs, and Client programs. 
It demonstrates how jobs are submitted by clients, assigned by the master to the appropriate slave based on load and efficiency, executed with simulated timing, and reported back to clients. 
The system uses sockets for communication and multiple threads in the master to manage concurrent job processing safely.
 
## Features
Client-Server Communication: clients submit jobs to the master via sockets.
Master Scheduling: assigns jobs to slaves based on type and efficiency using a load-balancing algorithm.
Slave Execution: simulates job processing with different durations depending on type and slave specialization.
Thread-Safe Communication: uses shared memory and locks for safe concurrent updates.
Real-Time Console Output: shows job submission, assignment, execution, completion, and client notifications.

## Program Flow
Clients submit jobs to the master, which assigns them to the appropriate slave. 
Slaves execute jobs according to their specialization and simulated timing, notifying the master when finished. 
The master then notifies the respective client, with the console displaying every step from submission to completion, illustrating a fully simulated distributed processing system.
