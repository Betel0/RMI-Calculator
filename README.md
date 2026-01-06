##RMI Calculator

A Modern Remote Calculator implemented in Java using RMI (Remote Method Invocation).
This project allows multiple clients to connect to a remote calculator server and perform arithmetic and advanced operations remotely.

##Features

Basic arithmetic: +, -, ×, ÷

Advanced operations: power, square root, percentage, reciprocal, sign change

Memory functions: M+, M-, MR, MC

Remote execution via RMI

Interactive GUI with clean, responsive design

Configurable server host and port via .env

##Project Structure

RMI-Calculator/
├─ src/
│  ├─ client/
│  │   └─ CalculatorClient.java
│  ├─ gui/
│  │   └─ CalculatorGUI.java
│  ├─ remote/
│  │   ├─ CalculatorInterface.java
│  │   └─ CalculatorImpl.java
│  └─ server/
│      └─ CalculatorServer.java
└─ README.md

##Requirements

Java 8 or higher

No external dependencies required

##Screenshot

![image alt](https://github.com/Betel0/RMI-Calculator/blob/66362872a0c33b89b2d9aa0b330b9a168be5645d/Photo/RMI1.png)
![image alt](https://github.com/Betel0/RMI-Calculator/blob/66362872a0c33b89b2d9aa0b330b9a168be5645d/Photo/RMI2.png)
