# EduCenter

A Java-based education center management system designed to streamline the administration of students, courses, and instructors. Built with clean object-oriented principles and persistent data storage using Java serialization.

## Features

- **Student Management** — Register, update, and track student records
- **Course Management** — Create and manage courses with assigned instructors
- **Instructor Management** — Handle instructor profiles and course assignments
- **Enrollment Tracking** — Manage student-course relationships
- **Data Persistence** — All records are saved and restored via Java serialization

## Tech Stack

- **Language:** Java
- **Build Tool:** Maven
- **Storage:** Java Object Serialization (`.ser`)
- **Architecture:** Object-Oriented Programming (OOP)

## Getting Started

### Requirements

- Java 11+
- Maven 3.6+

### Run

```bash
git clone https://github.com/en3sctl/EduCenter.git
cd EduCenter
mvn compile
mvn exec:java -Dexec.mainClass="mas.educenter.Main"
```

## Project Structure

```
src/
└── main/
    └── java/
        └── mas/
            └── educenter/
                ├── models/        # Entity classes
                ├── services/      # Business logic
                └── Main.java      # Entry point
extents.ser                        # Serialized data store
```
