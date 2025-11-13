[Initial Project Setup with Postgresql, JPA Entities, and Catalog CRUD API with Auditing]
- Initialized Spring Boot project via Initializr with core dependencies (Web, JPA, Security, PostgreSQL, Lombok).
- Configured PostgreSQL connectivity in application.properties.
- Defined core JPA entities: Author, Book, and BookCopy.
- Implemented Auditable class for tracking created/updated timestamps and users.

[Added controller, service and request/response for Author and Book entities]
- Created necessary DTOs for Author and Book entities (Request/Response) for a clean API contract.
- Implemented Service and Controller layers for both Author and Book entities.
- Implemented full REST APIs for the Author and Book.