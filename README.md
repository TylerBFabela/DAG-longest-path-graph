# 🏗️ DAG Graph – Java Dependency & Scheduling Engine

A lightweight, extensible Java framework for modeling directed dependencies and calculating task schedules.

---

### 🔹 Overview

This project implements a Directed Acyclic Graph (DAG) where each vertex represents a task with a weight (execution time). It automatically computes:

- Earliest start times for all tasks
- Total completion time for the entire graph
- Cycle detection (returns -1 if dependencies are cyclic)
- Designed to be generic and extensible, allowing easy customization of vertex types.

---

### 🔹 Key Features

- Generic Graph<V extends Vertex> implementation
- Nested Vertex class supporting dependencies (requires)
- Longest path calculation using relaxation-based algorithm
- Topological sorting: BFS & DFS
- Automatic detection of cycles in the graph
- Tested with JUnit 4
- Maven build system for easy compilation and testing

---

### 🔹 Tech Stack

- Language: Java
- Build & Test: Maven, JUnit 4
- Version Control: Git
- IDE Friendly: IntelliJ, Eclipse

---

### 🔹 Project Structure
```bash
dag-graph/  
├── pom.xml                              # Maven configuration  
├── README.md                            # Project overview & instructions  
├── src/  
│   ├── main/java/graph/Graph.java  
│   └── test/java/graph/GraphTest.java
```

---

### 🔹 Example Usage
```bash
Graph<Graph.Vertex> graph = new Graph<>();

Graph.Vertex a = graph.insert(3);
Graph.Vertex b = graph.insert(5);

a.requires(b); // a depends on b

System.out.println("Earliest start of a: " + graph.get(0).start());
System.out.println("Total completion time: " + graph.finish());
```

---

### 🔹 Running Tests

Run all tests using Maven:
```bash
mvn clean test
```

Expected output:
```text
BUILD SUCCESS
```

All edge cases, cycles, and scheduling scenarios are covered in GraphTest.java.

---

### 🔹 What This Demonstrates

- Object-Oriented Design: clean separation between graph and vertex logic

- Generics: Graph<V extends Vertex> allows flexible vertex types

- Algorithmic Thinking: topological sorting & longest path in DAG

- Unit Testing: JUnit tests for correctness and edge cases

- Build Automation: Maven for compilation, testing, and dependency management

--- 
Author:

Tyler Fabela – showcasing practical Java and algorithmic skills