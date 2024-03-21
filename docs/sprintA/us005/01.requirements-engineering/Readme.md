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

### 1.4. Found out Dependencies

* No dependencies found.

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
