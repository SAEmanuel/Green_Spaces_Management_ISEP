# US022 - Add a new entry in the Agenda


## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to add a new entry in the Agenda.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document and client meetings:**

>	The system should prompt all the vehicles that need check-up. 

>	It is possible that no vehicles need overhauling.

>   The system takes in consideration the maintenance frequency (in kms) introduced by the HRM when the vehicle is registered and when the kms are 5% close to reach that limit it will be eligible for check-up.

**From forum:**

> **Question:** When a new entry is added to the Agenda, the status of that task will be, by default, set to "planned", right??
>
> **Answer:**  "Planned" as default for Agenda entries, sounds good.

> **Question:** Some tasks are meant to be reoccurring rather than occasional. Is this something that should be asked on creating the task in the to-do list? If so, what inputs should we expect from the user?
>
> **Answer:** For the current proof-of-concept there is no need to distinguish between recurring and occasional tasks.

> **Question:** When the GSM plans a task (that was previously in To-Do) into the Agenda, what aditional data/information does he need to input when planning?
>
> **Answer:** The starting date for the task. Later the GSM will be able to add the Team and vehicles (if required).

> **Question:** Some tasks are meant to be reoccurring rather than occasional. Is this something that should be asked on creating the task in the to-do list? If so, what inputs should we expect from the user?
>
> **Answer:** For the current proof-of-concept there is no need to distinguish between recurring and occasional tasks.



### 1.3. Acceptance Criteria 

* **AC1:** The new entry must be associated with a green space managed by the GSM.
* **AC2:** The new entry must exist in the To-Do list.


### 1.4. Found out Dependencies

* There is a dependency on "US020 - Register a Green Space" as there must exist green spaces in the system so that it can be associated with an entry in the Agenda.
* There is a dependency on "US021 - Add a new entry to the to-do list" as there must exist tasks in the to-do list so that it can be associated to the Agenda.

### 1.5 Input and Output Data

**Input Data:**

* The system automatically retrieves the maintenance frequency of each vehicle and calculates the 5% threshold limit before reaching it without requiring any manual input.

**Output Data:**

* List of vehicles that need a check-up
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us008-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)

