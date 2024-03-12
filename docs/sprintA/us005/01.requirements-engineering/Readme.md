# US005 - Generate a team proposal automatically


## 1. Requirements Engineering

### 1.1. User Story Description

As a HRM, I want to generate a team proposal automatically

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	Each task is characterized by having a unique reference per organization, a designation, an informal and a technical description, an estimated duration and cost, as well as a task category. 

>	As long as it is not published, access to the task is exclusive to the employees of the respective organization. 

**From the client clarifications:**

> **Question:** Which is the unit of measurement used to estimate duration?
>
> **Answer:** Duration is estimated in days.

> **Question:** Monetary data is expressed in any particular currency?
>
> **Answer:** Monetary data (e.g. estimated cost of a task) is indicated in POT (virtual currency internal to the platform).

### 1.3. Acceptance Criteria

* **AC1:** The system should allow the HRM to input project details such as project name, task details, objectives.
* **AC2:** The HRM should be able to specify the number of team members needed for the project.
* **AC3:** The system should provide options to select team members based on their skills, experience, and availability.
* **AC4:** It should automatically generate a team proposal based on project relevant skills, experience and urgency, using collaborators availability.
* **AC5:** The HRM should have the ability to review and edit the generated team proposal before finalizing it.
* **AC6:** The generated team proposal should use a database to save past team history, as future reference.
* **AC7:** The generator should consider the availability of vehicles, machines, and equipment required for the tasks when assigning team members to projects.
* **AC8:** It should offer the capability to generate multiple team proposals simultaneously for better assistance.
* **AC?:** The system should prioritize team member selection based on their proximity to the project location to minimize transportation time and costs.

### 1.4. Found out Dependencies

* Must have existing collaborators in both the filter and registration to generate a team
* Must have enough collaborators in both the filter and registration to generate a team with a certain number of members

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
  * name
  * task details
  * objectives

* Selected data:
  * skills
  * experience
  * urgency
  * vehicles, machines and equipment

**Output Data:**

* team proposal/s
* Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us006-system-sequence-diagram-alternative-one.svg)

#### Alternative Two

![System Sequence Diagram - Alternative Two](svg/us006-system-sequence-diagram-alternative-two.svg)

### 1.7 Other Relevant Remarks

* The created task stays in a "not published" state in order to distinguish from "published" tasks.
