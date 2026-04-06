# 📚 Enterprise Library Management Engine

A fully automated, state-managing backend API for a modern Library Management System built with Spring Boot. 

## 🚀 Key Features
* **Automated Waitlist System:** Users can queue for checked-out books. The system automatically intercepts returning books, places them on a "Hold Shelf", and emails the next user in line.
* **Cron Job Automation (`@Scheduled`):** Background workers run nightly to calculate overdue fines, lock accounts, and expire abandoned waitlist holds after 48 hours.
* **Transaction Safety (`@Transactional`):** Bulletproof state management ensuring physical inventory counts (`availableCopies`) never go out of sync with digital borrowing records.
* **Global Exception Handling:** A `@RestControllerAdvice` layer intercepts all runtime errors and returns clean, readable JSON standard HTTP responses instead of server crashes.
* **Secure Architecture:** Sensitive user data (like passwords) are hidden behind strict DTOs.

## 🛠️ Tech Stack
* **Java 17+**
* **Spring Boot 3** (Web, Data JPA, Mail)
* **MySQL** (Relational Database)
* **Hibernate** (ORM)

## 💡 Architecture Highlights
* Designed using strict **Separation of Concerns** (e.g., `BorrowingService` handles inventory, `WaitService` handles queues and emails).
* Implemented edge-case protections like the "Infinite Book Return Glitch" and "Spam Queue Glitch."
