# US003 - Register a collaborator 


## 1. Requirements Engineering

### 1.1. User Story Description

As a Human Resources Manager, I want to register a collaborator with a job and fundamental characteristics.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document and client meetings:**

>   The human resources manager will fill all the required fields: name, birthdate, admission date, address, phone number, email address, taxpayer number and Identification document type. Should also load an image of the identification document. Then he can select a job from a list to register a collaborator.

**From forum:**

> **Question:** When creating a collaborator with an existing name ... What the system do?
>
> **Answer:** It's not common and most improbable to have different individual with same name in the same context, however it’s ID documentation number should be unique for sure.

> **Question:** Which information is mandatory to insert a collaborator in the program (fundamental characteristics)?
>
> **Answer:** - name, birthdate, admission date, address, contact info (mobile and email), taxpayer number, ID doc type and respective number 
>             - displaying or not, It's a matter of UX, the dev team should decide about it, but the valid jobs are the ones created within the US02.

> **Question:** Should the system able the HRM to insert multiple collaborators in one interaction before saving them.
> 
> **Answer:** It's not required to do so.

> **Question:** Is there any limitation regarding the length of the name of the collaborator?
> 
> **Answer:** According to the Portuguese law a name should contain at maximum six words

> **Question:** Should we consider valid only the birthdate in which the collaborator has more than 18 years?
> 
> **Answer:** Yes

> **Question:** What should be the format for the phone number? 9 numbers?
> 
> **Answer:** validating 9 digits will be acceptable; validating with international format would be excellent.

> **Question:** What is the format for the numbers from the id doc types?
> 
> **Answer:** each doc type has specific formats like taxpayer number, Citizen Card ou passport.

> **Question:** What is needed for the address?  Street, zipcode and a city?
> 
> **Answer:** That would be enough.

> **Question:** What should be the accepted format for the emails? Should only specific email services be accepted?
> 
> **Answer:** A valid email address consists of an email prefix and an email domain, both in acceptable formats.
  The prefix appears to the left of the @ symbol. The domain appears to the right of the @ symbol.
  For example, in the address example@mail.com, "example" is the email prefix, and "mail.com" is the email domain.


### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** Name should only have letters.
* **AC3:** Admission date can only be valid if the collaborator is over 18 years old.
* **AC4:** Phone number can only have 9 numbers.
* **AC5:** Phone number can only be from the specified domains for phone numbers
* **AC6:** Email address should have a prefix (before "@") and "." after "@".
* **AC7:** Taxpayer number can only have 9 numbers.
* **AC8:** The user will select a job from a list of jobs provided by the application.
* **AC9:** An collaborator can only be added if it was not in the list previously (global validation).
* **AC10:** Every acceptance criteria must be met, in order for the collaborator to be created successfully (local validation).

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
    * a calendar for birthdate
    * a calendar for admission date
    * a list to select identification document type

* Selected data:
    * a list to select a job

**Output Data:**

* List of existing jobs
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us003-system-sequence-diagram-alternative-one.svg)

### 1.7 Other Relevant Remarks

* None.