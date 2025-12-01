<div align="center">

  # 🌳 Green Spaces Management System
  ### MusgoSublime

  <p align="center">
    A comprehensive Java solution for managing urban green spaces, multidisciplinary teams, vehicles, and infrastructure planning using Graph Algorithms.
  </p>

  <p align="center">
    <a href="https://skillicons.dev">
      <img src="https://skillicons.dev/icons?i=java,maven,idea,git,python,linux&theme=dark" />
    </a>
  </p>

  ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
  ![JavaFX](https://img.shields.io/badge/JavaFX-007396?style=for-the-badge&logo=java&logoColor=white)
  ![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
  ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

</div>

---

## 📘 Project Overview

Developed within the scope of the **Integrative Project (2nd Semester - LEI/ISEP)**, this application supports **MusgoSublime**, an organization responsible for planning, constructing, and maintaining green spaces for collective use.

The solution is an integrated system that combines **Resource Management** (Human Resources, Fleet) with **Advanced Algorithmic Planning** (Irrigation optimization and Emergency routes) using **Graph Theory** and **Data Analysis**.

---

## ✨ Key Features

### 🛠️ Resource & Task Management
* **Human Resources:** Management of collaborators, jobs, and skills.
* **Team Generation:** Automatic proposal of multidisciplinary teams based on required skills and team size constraints.
* **Fleet Management:** Registration of vehicles/machines and maintenance scheduling (preventive check-ups).
* **Task Agenda:** Management of the "To-Do List" and daily Agenda, assigning teams and vehicles to specific tasks in green spaces.

### 🧠 Algorithms & Optimization (Graph Theory)
* **💧 Irrigation System Planning:**
    * Uses **Minimum Spanning Tree (MST)** algorithms (Kruskal/Prim) to design irrigation networks with minimum accumulated cost, ensuring all water points are connected.
* **🚨 Emergency Evacuation Routes:**
    * Uses **Shortest Path** algorithms (Dijkstra) to determine the fastest evacuation routes from signage points to assembly points.

### 📊 Data Analysis & AI
* **Statistical Analysis:** Integration with **Python/Jupyter Notebooks** to analyze KPIs such as water consumption costs, equipment usage, and user demographics.
* **Predictive Models:** Application of **Linear and Polynomial Regression** to predict monthly water costs based on park size.

---

## 🏗️ Architecture & Design

* **Layered Architecture:** Follows strict Object-Oriented principles and Separation of Concerns.
* **Design Patterns:** Applied **SOLID**, **GRASP**, and GoF patterns (e.g., DTO, Repository, Controller).
* **Testing:** Developed using **TDD** (Test-Driven Development) with JUnit 5 and JaCoCo for coverage.
* **Persistence:** Object serialization for data preservation.

---

## 🚀 Build & Run

This project uses **Maven** for build automation. Ensure you have JDK 11+ installed.

### 📦 Core Commands

| Action | Command |
| :--- | :--- |
| **Run Tests** | `mvn clean test` |
| **Build JAR** | `mvn package` |
| **Run Application** | `java -jar target/project-template-1.0-SNAPSHOT-jar-with-dependencies.jar` |

### 🔍 Quality & Documentation

<details>
<summary>Click to expand advanced Maven goals</summary>

#### Generate Documentation (Javadoc)
```bash
mvn javadoc:javadoc
mvn javadoc:test-javadoc
