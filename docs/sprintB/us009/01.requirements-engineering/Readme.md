# US008 - Overhauling of vehicles


## 1. Requirements Engineering

### 1.1. User Story Description

As a Fleet Manager I intend to list the vehicles that need overhauling.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document and client meetings:**

>	The system should prompt all the vehicles that need check-up. 

>	It is possible that no vehicles need overhauling.

>   The system takes in consideration the maintenance frequency (in kms) introduced by the HRM when the vehicle is registered and when the kms are 5% close to reach that limit it will be eligible for check-up.

**From forum:**

> **Question:** Can the vehicles get placed automatically on a list or the one listing has to be the FM?
>
> **Answer:**  The list of vehicles is automatically created but the creation is triggered by the FM.

> **Question:** What information will appear on the final list regarding the vehicle, besides the needing for check-up?
>
> **Answer:** Data that allow to identify the vehicle like Plate, brand and model, as well as, the data that allowed to select/insert te vehicle in the list, number of kms, frequency of checkup and the last checkup.

> **Question:** I'm not sure if I understood everything about US08. In this US we should only list the vehicles that need a check up (surpass the check-up frequency in KM) or that will shortly need a check-up (5% to the check-up frequency *already said in another post*), correct? If so, the information about the checkup frequency for each vehicle should be asked in US07?
>
> **Answer:** No; the Maintenance/Check-up Frequency (in km) is registered in US06.

> **Question:** What are the requests/ input data to list the vehicles needing the check-up? Type of vehicle, Current Km and Maintenance/Check-up Frequency (in Kms) are sufficient?
>
> **Answer:** Current Km and Maintenance/Check-up Frequency (in Kms) are sufficient, yes;
The list must contain all vehicles that have already exceeded the number of km required for the inspection or those that are close to it.
For example: a vehicle that made the checkup at 23500 and has a checkup frequency of 10000km.
a) If it currently has 33600 (exceeded) or
b) 33480 (there is a difference minor than 5% of the number of kms of the checkup frequency).
The list must clearly identify the vehicles through: plate number, brand, model and the that justified the checkup need.


### 1.3. Acceptance Criteria 

* **AC1:** If there are no vehicles that need overhauling, the system notifies the FM.
* **AC2:** The list of vehicles needing overhauling should contain all the vehicles' info.
* **AC2:** The list should include vehicles that have either exceeded the maintenance frequency limit or are within 5% of reaching it.


### 1.4. Found out Dependencies

* There is a dependency on "US006 - Machinery registration" as there must be at least one vehicle in the system so that it can check the need for a check-up.

### 1.5 Input and Output Data

**Input Data:**

* The system automatically retrieves the maintenance frequency of each vehicle and calculates the 5% threshold limit before reaching it without requiring any manual input.

**Output Data:**

* List of vehicles that need a check-up
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us008-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)

