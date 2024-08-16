# Library Provider: Backend

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Hexagonal Architecture](https://img.shields.io/badge/Hexagonal%20Architecture-000000?style=for-the-badge&logo=hexagonal)
![Event Sourcing](https://img.shields.io/badge/Event%20Sourcing-000000?style=for-the-badge&logo=eventbrite)
![DDD](https://img.shields.io/badge/DDD-000000?style=for-the-badge&logo=domain-driven-design)

## About

This project is developed in **Java** using **Spring Boot** for the backend and **MySQL** as the relational database.

Library Provider is an application designed to manage the operations of a library, including managing users and texts. The backend part of the application handles the **CRUD** (Create, Read, Update, Delete) operations for managing books and user information. The architecture has been enhanced with **Hexagonal Architecture**, **Event Sourcing**, and **Domain-Driven Design (DDD)** principles.

## Features

1. User management (create, read, update, delete).
2. Book management (create, read, update, delete).
3. Integration with MySQL database.
4. RESTful API implementation using Spring Boot.
5. Secure handling of user data.
6. Hexagonal Architecture for better separation of concerns.
7. Event Sourcing for maintaining a history of changes.
8. Domain-Driven Design for a robust domain model.

## Installation

To set up the project locally, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/camilo6castell/library-provider-backend.git
   cd library-provider-backend
   ```

2. **Set up the database:**

   - Make sure you have MySQL installed and running.
   - Create a new database for the project.
   - Update the `application.properties` file with your database connection details.

3. **Build the project:**

   ```bash
   ./gradlew build
   ```

4. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```

## Testing with Postman

To test the API endpoints using Postman, you can import the provided Postman collection and execute the requests directly. Follow these steps:

1. **Import the Postman collection:**

   - Download the [Postman collection](https://drive.google.com/file/d/1-7eZIgGvCbVpv7aqJV1679djr9F5GMnn/view?usp=sharing).
   - Open Postman, go to "File" -> "Import", and select the downloaded JSON file.

2. **Available Requests:**

   - **Create User:**

     - **Method:** POST
     - **Endpoint:** `http://127.0.0.1:8080/api/v1/createUser`
     - **Body (raw JSON):**
       ```json
       {
         "email": "test@test.com",
         "password": "zzzzDzddddd2%",
         "entryDate": "2000-01-01"
       }
       ```

   - **Save and Quote Text:**
     - **Method:** POST
     - **Endpoint:** `http://127.0.0.1:8080/api/v1/saveAndQuoteText`
     - **Body (raw JSON):**
       ```json
       {
         "userID": "",
         "title": "test",
         "type": "BOOK",
         "initialPrice": 100.0
       }
       ```

3. **Execute the Requests:**

   - Ensure your backend service is running.
   - Use the imported requests in Postman to interact with the API endpoints.
   - Check the responses and verify the operations.

## Contact

[GitHub](https://github.com/camilo6castell?tab=repositories)

[LinkedIn](https://www.linkedin.com/in/camilocastell/)
