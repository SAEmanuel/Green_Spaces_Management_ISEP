# US027 - Show an organized list of all green spaces managed by manager


## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I need to list all green spaces managed by me.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document and client meetings:**

> The list of green spaces must be sorted by size in descending
order (area in hectares should be used)

**From forum:**

> **Question:** Can the vehicles get placed automatically on a list or the one listing has to be the FM?
>
> **Answer:**  The list of vehicles is automatically created but the creation is triggered by the FM.


### 1.3. Acceptance Criteria 

* **AC1:** The list of green spaces must be sorted by size in descending
  order (area in hectares should be used). The sorting algorithm to
  be used by the application must be defined through a configuration
  file. At least two sorting algorithms should be available.


### 1.4. Found out Dependencies

* There is a dependency on " US020 - Register a Green Space" as there must be at least one green space in the system so that it can show an organized list.

### 1.5 Input and Output Data

**Input Data:**

* Sorting Method

**Output Data:**

* Organized list of Green Spaces
* Success of the operation

### 1.6. System Sequence Diagram (SSD)

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us027-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)

