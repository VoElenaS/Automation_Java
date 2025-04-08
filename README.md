# 🤖 Stomanager Project Automation

> **Note:** This automation framework is **not standalone**. 

---

## 📦 Project Overview

The **Stomanager Project** is a comprehensive warehouse and order management system. 
This automation suite was built **from scratch** to validate the system's functionality at both API and UI levels, applying industry-standard design and testing practices.

The framework emphasizes **modularity**, **readability**, and **maintainability**, making it scalable for long-term usage and easy onboarding.

---

## 🧪 Testing Methodologies Used

To ensure high-quality coverage, the following test design techniques were applied:

- **Boundary Value Analysis**
- **Equivalence Partitioning**
- **Decision Table Testing**
- **State Transition Testing**
- **Error Guessing / Exploratory Testing**

---

## 🛠️ Key Technologies & Patterns

| Technology          | Purpose                                         |
|-------------------- |-------------------------------------------------|
| **JUnit 5**         | Test execution and structure                    |
| **RestAssured**     | REST API testing                                |
| **Selenium**        | End-to-end UI testing                           |
| **Lombok**          | Reducing boilerplate (getters/setters)          |
| **Builder Pattern** | Simplified creation of complex test objects     |
| **TestProperties**  | Centralized test configuration                  |
| **UserQueries**     | Test data handling and verification             |

---


## 🧱 Framework Structure

The framework is organized to ensure each API service is easy to manage and extend.

### ✅ Service Structure

- **Each API service has its own class** for better separation and readability.
- **Custom data generators** provide flexible test inputs per service.
- **Shared base classes** encapsulate reusable logic like authentication, setup, and common utilities.

---

## ⚙️ Design Highlights

- Clean separation of concerns across test layers.
- Avoids duplication with a shared utility and base class system.
- Modular design makes it easy to extend and maintain.
- Flexible data handling supports both positive and negative test cases.

---

## 🚀 Getting Started

Clone this automation framework:
   ```bash
   git clone https://github.com/your-username/stomanager-automation.git
   cd stomanager-automation
