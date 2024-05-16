# US021 - Adding a To-Do List Entry for Green Space Management


## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to add a new entry to the To-Do List.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document and client meetings:**

>	The entries in this list describe the required task, the degree of urgency (High, Medium, and Low), and the approximate expected duration.

>   The To-Do List comprises all pending tasks for all parks.

**From forum:**

> **Question:** 
>
> **Answer:**  

> **Question:** 
>
> **Answer:** 


### 1.3. Acceptance Criteria 

* **AC1:** The new entry must be associated with a green space managed by the GSM.
* **AC2:**  The green space for the new entry should be chosen from a list presented to the GSM.
* **AC3:** The entry must include a task.
* **AC4:** The entry must specify the degree of urgency (High, Medium, Low).
* **AC5:** The entry must provide an approximate expected duration for the task.



### 1.4. Found out Dependencies

* There is a dependency on "US020 - Register a Green Space" as there must be at least one green space created.

### 1.5 Input and Output Data

**Input Data:**

* Green space ID
* Task
* Degree of urgency
* Duration for the task

**Output Data:**

* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us021-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)

