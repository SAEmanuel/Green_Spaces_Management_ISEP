# US005 - Generate a team proposal automatically


## 1. Requirements Engineering

### 1.1. User Story Description

As a HRM, I want to generate a team proposal automatically

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>   The team proposal generated his focus on facilitating and speeding up the process of creating a team. The Human Resources Manager (HRM) has the possibility of generating a team based on a set of previously selected skills and team size. Engage in the task of team building and come automate and recommend a team, having a great weight in managing and choosing the right team for a designated task.

>	The system provides a user interface to select various skills and team size. When HRM uses this generation, it will add all the skills you want the team to have for this task and the number of elements. Furthermore, HRM can view the list of selected information and confirm team generation based on this information.

**From the client clarifications:**

> **Question:** What is the information necessary for a team proposal?
>
> **Answer:** The customer provide the max size of the team and a set of skills

> **Question:** By having more team combinations for the same information. Should the system also generate them?
>
> **Answer:** .

> **Question:** If it doesn't have enough collaborators, or they don't have the requests skills. Should the system send a message?
>
> **Answer:** Yes, the system should provide information why it can't generate a team.
### 1.3. Acceptance Criteria

* **AC1:** Need to have at least two collaborators to generate a team
* **AC2:** Need to have as many collaborators with the desired skills as the number of them requested
* **AC3:** Need to have as many collaborators requests of the skills selected
* **AC4:** Need to have at least one skill registed to start a team generation 
*
*
*
*
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

* There is a dependency on "US01 - Register skills that a collaborator may have"
* There is a dependency on "US04 - Assign one or more skills to a collaborator"

### 1.5 Input and Output Data

**Input Data:**
  * Selected data:
    * skills
    * team size

**Output Data:**
  * team proposal/s
  * Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

![System Sequence Diagram - Alternative One](svg/us005-system-sequence-diagram-alternative-one.svg)

### 1.7 Other Relevant Remarks

* The created task stays in a "not published" state in order to distinguish from "published" tasks.
