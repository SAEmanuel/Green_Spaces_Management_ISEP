# US003 - Register a collaborator with a job and fundamental characteristics


## 1. Requirements Engineering

### 1.1. User Story Description

As an Human Resources Manager, I want to register a collaborator with a job and fundamental characteristics.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	Each task is characterized by having a unique reference per organization, a designation, an informal and a technical description, an estimated duration and cost, as well as a task category. 

>	As long as it is not published, access to the task is exclusive to the employees of the respective organization.

>   The human resources manager will fill all the required fields: name, birthdate, admission date, residence, phone number, email address, taxpayer number and Identification document type. Should also load a image of the identification document. Then he can select a job from a list to register a collaborator.

**From the client clarifications:**

> **Question:** When creating a collaborator with an existing name ... What the system do?
>
> **Answer:** It's not common and most improbable to have different individual with same name in the same context, however it’s ID documentation number should be unique for sure.

> **Question:** Which information is mandatory to insert a collaborator in the program (fundamental characteristics)?
>
> **Answer:** - name, birthdate, admission date, address, contact info (mobile and email), taxpayer number, ID doc type and respective number 
>             - displaying or not, It's a matter of UX, the dev team should decide about it, but the valid jobs are the ones created within the US02.

### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** Name must be complete and should only have letters.
* **AC3:** Birthdate can only be chosen from the calendar given.
* **AC4:** Admission date can only be chosen from the calendar given.
* **AC5:** Residence must be written, this includes: door number, street, postal code, city, country. If there is anything more needed to be specified, should be written in the "More" box.
* **AC6:** Phone number can only have 9 numbers.
* **AC7:** Email address can only have characters and numbers.
* **AC8:** Taxpayer number can only have numbers.
* **AC9:** The user will select a identification document type from a list.
* **AC10:** An image of identification document should be loaded to the app.
* **AC11:** The user will select a job from a list of jobs provided by the app.

### 1.4. Found out Dependencies

* There is a dependency on "US002 - Profession Registration" as there must be at least one job to give a collaborator when registered.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * a name
    * the residence 
    * a phone number
    * an email
    * the taxpayer number
	
* Selected data:
    * a calendar for birthdate
    * a calendar for admission date
    * a list to select identification document type
    * a list to select a job

**Output Data:**

* List of existing task categories
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us006-system-sequence-diagram-alternative-one.svg)

#### Alternative Two

![System Sequence Diagram - Alternative Two](svg/us006-system-sequence-diagram-alternative-two.svg)

### 1.7 Other Relevant Remarks

* The created task stays in a "not published" state in order to distinguish from "published" tasks.