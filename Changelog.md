[Initial Project Setup with Postgresql, JPA Entities, and Catalog CRUD API with Auditing](https://github.com/Saidasaradharam/Library-Management/commit/85adee02ef7c05dccc082c8bef57d34f27212210)
- Initialized Spring Boot project via Initializr with core dependencies (Web, JPA, Security, PostgreSQL, Lombok).
- Configured PostgreSQL connectivity in application.properties.
- Defined core JPA entities: Author, Book, and BookCopy.
- Implemented Auditable class for tracking created/updated timestamps and users.

[Added controller, service and request/response for Author and Book entities](https://github.com/Saidasaradharam/Library-Management/commit/70fda54e119828675979a0c0812f84b07a8c13bd#diff-82ffadafffd49fdd91ec66d1e3109700761156be307678f0562d02d527debab0)
- Created necessary DTOs for Author and Book entities (Request/Response) for a clean API contract.
- Implemented Service and Controller layers for both Author and Book entities.
- Implemented full REST APIs for the Author and Book.

[Added User Entity and Implemented Registration via REST API](https://github.com/Saidasaradharam/Library-Management/commit/8428d83c04db3e04e1ae596022ff466003584451)
- Created User entity with fields: username, password, roles(Enum)
- Implemented UserRepository for CRUD operations on User entity.
- Developed AuthService to handle user registration logic, and included password encoding **(BCrypt NFR)**.

[Added Category Entity and Implemented Category CRUD APIs + Book Search Enhancements](https://github.com/Saidasaradharam/Library-Management/commit/5eea6da88d79a28b63b8ef797a45c0fb22a5fa1b)
- Implemented Category entity and established One-to-Many relationship with Book entity.
- Implemented CategoryService and CategoryController for CRUD operations on categories.
- Enhanced BookRepository to support searching books by title, author name.
- Implemented the logic for handling BookCopy operations.

[Added Loan Entity to Manage the Book Status](https://github.com/Saidasaradharam/Library-Management/commit/fdf40e6079974ecbb2f65049c9c50b24c1b86969)
- Created Loan entity to track book loans with fields: member, bookCopy, loanDate, dueDate, returnDate, status(Enum).
- Implemented LoanService and LoanController to handle loaning and returning of books with fine calculation.

[Added Docker configuration](https://github.com/Saidasaradharam/Library-Management/commit/3c95a7f7658d25460637dc2322bfdd5d6125d50b#diff-e45e45baeda1c1e73482975a664062aa56f20c03dd9d64a827aba57775bed0d3)
- Created Dockerfile to containerize the Spring Boot application.
- Added docker-compose.yml to orchestrate the application and PostgreSQL database.

[Configured to view the Open API documentation using Swagger UI](https://github.com/Saidasaradharam/Library-Management/commit/6c166f240b21861cfecc23e5f0b24573a33681cf)
- Added springdoc dependency to the project.

[Using JSON Structure for logging](https://github.com/Saidasaradharam/Library-Management/commit/aa34ac063a954fe60b88319c871e51e052aac799)
- Using logstash.logback-encoder dependency to log in JSON format.

[Implemented Refresh token logic](https://github.com/Saidasaradharam/Library-Management/commit/4d95fa077a0e6ea9de1fc518404517f9cbc25ef1)
- Created a TokenResponse record which has both AccessToken and RefreshToken with individual expiry dates.
- Every time user fetches access token with his refresh token, a new refresh token is generated and stored in the database.