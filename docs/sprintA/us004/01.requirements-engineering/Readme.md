# US004 -  Assigning skills to a collaborator 


## 1. Requirements Engineering

### 1.1. User Story Description

As an HRM, I want to assign one or more skills to a collaborator.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	Each task is characterized by having a unique reference per organization, a designation, an informal and a technical description, an estimated duration and cost, as well as a task category. #NOT OFFICIAL

>	As long as it is not published, access to the task is exclusive to the employees of the respective organization. #NOT OFFICIAL

**From the client clarifications:**

> **Question:** Is there a minimum and maximum number of skills?
>
> **Answer:** No.

> **Question:** Is there any special characteristic that the employee needs to have in order for these skills to be added?
>
> **Answer:** No.

### 1.3. Acceptance Criteria

* **AC1:** The employee must be registered.
* **AC2:** All required fields must be filled in.
* **AC3:** The system prevents duplicate competency assignments.


### 1.4. Found out Dependencies

* There is a dependency on "US001 - Registering Skills for Collaborators" as there must be at least one skill created.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * none.
	
* Selected data:
    * one or more skills.

**Output Data:**

* Display a message with all the information that will be changed.
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us006-system-sequence-diagram-alternative-one.svg)

#### Alternative Two

![System Sequence Diagram - Alternative Two](svg/us006-system-sequence-diagram-alternative-two.svg)

### 1.7 Other Relevant Remarks

* The created task stays in a "not published" state in order to distinguish from "published" tasks.