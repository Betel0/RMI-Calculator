# 🖥️ RMI Calculator

A **Modern Remote Calculator** implemented in **Java** using **RMI (Remote Method Invocation)**.  
This project allows multiple clients to connect to a remote calculator server and perform arithmetic and advanced operations remotely.

---

## ✨ Features

- **Basic Arithmetic:** `+`, `-`, `×`, `÷`  
- **Advanced Operations:** power, square root, percentage, reciprocal, sign change  
- **Memory Functions:** `M+`, `M-`, `MR`, `MC`  
- **Remote Execution:** Perform calculations via RMI  
- **Interactive GUI:** Clean, responsive, modern design  
- **Configurable Host & Port:** Easily change via `.env`  

---

## 🗂️ Project Structure

```bat

RMI-Calculator/
├─ src/
│ ├─ client/
│ │ └─ CalculatorClient.java
│ ├─ gui/
│ │ └─ CalculatorGUI.java
│ ├─ remote/
│ │ ├─ CalculatorInterface.java
│ │ └─ CalculatorImpl.java
│ └─ server/
│ └─ CalculatorServer.java
├─ README.md # Project documentation
├─ .env # Environment variables (optional)
└─ screenshots/ # Optional GUI screenshots

```

---

## ⚙️ Requirements

- **Java 8** or higher  
- No external dependencies required  

---

## 🖼️ Screenshots

### Summation of two numbers (12 + 15)

![RMI Calculator Screenshot 1](https://github.com/Betel0/RMI-Calculator/blob/66362872a0c33b89b2d9aa0b330b9a168be5645d/Photo/RMI1.png)

![RMI Calculator Screenshot 2](https://github.com/Betel0/RMI-Calculator/blob/66362872a0c33b89b2d9aa0b330b9a168be5645d/Photo/RMI2.png)

---

## 🚀 How to Run

1. **Set the port in `.env`** (optional):
   ```env
