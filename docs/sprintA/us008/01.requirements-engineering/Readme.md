# US008 - Overhauling of vehicles


## 1. Requirements Engineering

### 1.1. User Story Description

As a Fleet Manager I intend to list the vehicles that need overhauling.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	The system should prompt all the vehicles that need overhauling and the user must choose the one(s) he wants to select. 

>	It is possible that no vehicles need overhauling.

>	The user must inform the system every few kilometers that he wants to have his vehicles checked.
 
>	The user must inform the system of how many kilometers before reaching the limit for review he wants to be informed of.

**From the client clarifications:**

> **Question:** Can the vehicles get placed automatically on a list or the one listing has to be the FM?
>
> **Answer:**  The list of vehicles is automatically created but the creation is triggered by the FM.

> **Question:** What information will appear on the final list regarding the vehicle, besides the needing for check-up?
>
> **Answer:** Data that allow to identify the vehicle like Plate, brand and modle, as well as, the data that allowed to select/insert te vehicle in the list, number of kms, frequecny of checkup and the last checkup.



### 1.3. Acceptance Criteria 

* **AC1:** If FM does not have the necessary permissions, the system should prompt a warning message.
* **AC2:** If there are no vehicles that need overhauling, the system notifies the FM.


### 1.4. Found out Dependencies

* There is a dependency on "US006 - Machinery registration" as there must be at least one vehicle in the system so that it can check the need for a check-up.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * range of km for overhauling
    * number of km before reaching the limit for review that FM wants to be informed 
	
* Selected data:
    * vehicles category 

**Output Data:**

* List of vehicles that need a check-up
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us008-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)

#### Alternative Two

![System Sequence Diagram - Alternative One](svg/us008-system-sequence-diagram-alternative-two-System_Sequence_Diagram__SSD____Alternative_Two.svg)