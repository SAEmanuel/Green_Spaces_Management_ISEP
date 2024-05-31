# US0023 -  Assign a Team to an entry in the Agenda


## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to assign a Team to an entry in the Agenda
### 1.2. Customer Specifications and Clarifications 

**From the specifications document and client meetings:**

> The Agenda is made
up of entries that relate to a task (which was previously in the To-Do List),
the team that will carry out the task, the vehicles/equipment assigned to
the task, expected duration, and the status (Planned, Postponed, Canceled,
Done).
 

**From forum:**

> **Question:** Can the vehicles get placed automatically on a list or the one listing has to be the FM?
>
> **Answer:**  The list of vehicles is automatically created but the creation is triggered by the FM.

### 1.3. Acceptance Criteria 

* **AC1** A message must be sent to all team members informing them about the assignment.
* **AC2** Different email services can send the message. These services must be defined through a configuration file to allow the use of different platforms (e.g. Gmail, DEI’s email service, etc.).


### 1.4. Found out Dependencies

* There is a dependency on "US005 - Generate Team" as there must be at least one team in the system so that it can associate it to an agenda entry.
* There is a dependency on "US022 - Add a new entry in the Agenda" as there must be at least one entry in agenda, in the system so that it can associate a team to it.

### 1.5 Input and Output Data

**Input Data:**

* Team number
* Agenda entry
* Email Service

**Output Data:**

* Email to all team members
* Success of the operation

### 1.6. System Sequence Diagram (SSD)

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us023-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)

