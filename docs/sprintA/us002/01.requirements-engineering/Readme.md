# US002 - Profession Registration


## 1. Requirements Engineering

### 1.1. User Story Description

As a Human Resources Manager I want to register a job.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	Each job is characterized by having a unique name. 

>	There can not be duplicated professions.

**From the client clarifications:**

> **Question:** 
>
> **Answer:** 

> **Question:** 
>
> **Answer:** 

### 1.3. Acceptance Criteria

* **AC1:** If HRM does not have necessary permissions, the system should prompt a warning message.
* **AC2:** If HRM puts an invalid username or password, the system should prompt an error message.
* **AC3:** If the profession is already registered in the system, the system should notify the manager and suggest choosing another one or the existing one.

### 1.4. Found out Dependencies

* There is a dependency on "US003 - Register an Employee" as there must be at least one employee resgistered to add/change his job.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * a job
    * a designation 
    * an informal description
    * a technical description
    * ??? an estimated base salary ???
	
* Selected data:
    * a jobs category

**Output Data:**

* List of professions added to the system
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us002-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)


### 1.7 Other Relevant Remarks

* Only new professions can be added, if an existing one is inserted by the manager the system issues a warning.
