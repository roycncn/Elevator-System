# Intelligent Elevator System #

This README would normally document whatever steps are necessary to get the Intelligent Elevator System (IES) up and running.

### What is this repository for? ###

This is a smart lift control system in client-server model. It was developed in Java using JetBrains Intellij v2016.3. In this repository there is a subdirectory named:

* ElevatorBackend -- The directory for classes run in back-end server

The other classes in `project_root` are used in control panel GUI

### Prerequisite ###
Here is the required software to run the system

* Java JDK8 or higher version
* Apache ANT v.1.6 or above

### Installation ###

To build the project, just run `ant` in the project root directory. There is a build file in the `project_root`.

To run the system, please run `java -jar elevator.jar`. Remarks that the directory `project_root/JSON` must be placed in the same level with the `elevator.jar`

 * Deployment instructions
	There are three types of classes in the system :
	1. Infrastructure class -- Class mapped to the component and hardware of the system
		* ElevatorBackend.Kiosk
		* ElevatorBackend.Elevator
	2. Configuration class -- Class for CRUD of configuration
		* ElevatorBackend.AccessConfiguration
		* ElevatorBackend.ElevatorConfiguration
		* ElevatorBackend.Configuration
		* ElevatorBackend.AccessRule
		* ElevatorBackend.Role
		* ElevatorBackend.ElevatorFactorySetting
	3. Simulation class -- Class for demo
		* ElevatorBackend.Building
		* ElevatorBackend.Floor
		* ElevatorBackend.Simulator
	4. Communication class -- Class for transmission of data
		* ElevatorBackend.AppThread
		* ElevatorBackend.Message
		* ElevatorBackend.MessageBox
		* ElevatorBackend.Ticker
	5. Front-end class -- Class for the GUI
		* Classes which are not in above classes.
		
	To deploy the IES to real environment, the _infrastructure class_ should go to the driver of the hardware. The _configuration class_ should go to back-end server. The _communication class_ should go to all the components of the IES excepting the __Ticker__ class which is stored in the back-end server for synchronization. The _Front-end class_ should go to front-end server.

