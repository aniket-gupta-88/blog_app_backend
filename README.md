# Blogger API

This is a RESTful API for a blogging platform, built using Spring Boot. It provides functionalities for users to create, read, update, and delete posts, as well as interact with other users' content through comments.

## Features

*   **User Management:**
    *   User registration and authentication (JWT).
    *   Profile management (view, update).

*   **Post Management:**
    *   Create, read, update, and delete posts.
    *   Pagination and sorting of posts.
    *   Search posts by keywords or title.

*   **Comment Management:**
    *   Add comments to posts.
    *   View comments on a post.
    *   Delete own comments.

*   **Role-Based Access Control:**
    *   Admin users have privileges to manage all users and posts.
    *   Regular users can manage their own posts and comments.

*   **Exception Handling:**
    *   Centralized exception handling for consistent error responses.

*   **Security:**
    *   JWT (JSON Web Token) based authentication and authorization.
    *   Protection against common web vulnerabilities.

## Technologies Used

*   **Spring Boot:** Core framework for building the API.
*   **Spring Security:** For authentication and authorization.
*   **Spring Data JPA:** For database interaction.
*   **Hibernate:** ORM (Object-Relational Mapping) for database persistence.
*   **JWT (JSON Web Token):** For secure authentication.
*   **Lombok:** For reducing boilerplate code.
*   **H2 Database (for development):** In-memory database for development and testing.  (You can replace this with a production database like MySQL or PostgreSQL).
*   **Maven/Gradle:** Build tool.
*   **(Add any other technologies you used)**

## Getting Started

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/](https://github.com/)<your-username>/<your-repository-name>.git
    ```

2.  **Build the project:**
    ```bash
    mvn clean install  // If using Maven
    ./gradlew build   // If using Gradle
    ```

3.  **Run the application:**
    ```bash
    mvn spring-boot:run // If using Maven
    ./gradlew bootRun  // If using Gradle
    ```

4.  **API Documentation:**
    *   (Ideally, include a link to your Swagger/OpenAPI documentation if you have it.  This makes it much easier for others to understand and use your API.)

## API Endpoints

(It's highly recommended to list some of your key API endpoints here, with a brief description of each.  For example:)

*   `POST /api/auth/login`: Authenticates a user and returns a JWT.
*   `POST /api/auth/register`: Registers a new user.
*   `GET /api/posts`: Retrieves all posts (paginated).
*   `POST /api/posts`: Creates a new post.
*   `DELETE /api/posts/{postId}`: Deletes a post.
*   `POST /api/posts/{postId}/comments`: Adds a comment to a post.
*   ... (and so on)

## Contributing

Contributions are welcome!  Please open an issue or submit a pull request.
