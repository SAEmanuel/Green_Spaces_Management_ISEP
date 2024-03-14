# US008 - Overhauling of vehicles


## 1. Requirements Engineering

### 1.1. User Story Description

As a Fleet Manager I intend to list the vehicles that need overhauling.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	Each task is characterized by having a unique reference per organization, a designation, an informal and a technical description, an estimated duration and cost, as well as a task category. 

>	As long as it is not published, access to the task is exclusive to the employees of the respective organization. 

**From the client clarifications:**

> **Question:** 
>
> **Answer:** 

> **Question:** 
>
> **Answer:** 

### 1.3. Acceptance Criteria

* **AC1:** If FM does not have the necessary permissions, the system should prompt a warning message.
* **AC2:** If there are no vehicles that need overhauling, the system notifies the FM.
* **AC3:** -

### 1.4. Found out Dependencies

* There is a dependency on "US006 - Machinery registration" as there must be at least one vehicle in the system so that it can check the need for overhauling.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * selection of the option to see wich vehicles need overhauling
	
* Selected data:
    * vehicles category 

**Output Data:**

* List of vehicles that need overhauling
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us006-system-sequence-diagram-alternative-one.svg)



### 1.7 Other Relevant Remarks

* The created task stays in a "not published" state in order to distinguish from "published" tasks.