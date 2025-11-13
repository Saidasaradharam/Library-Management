[Initial Project Setup with Postgresql, JPA Entities, and Catalog CRUD API with Auditing](https://github.com/Saidasaradharam/Library-Management/commit/85adee02ef7c05dccc082c8bef57d34f27212210)
- Initialized Spring Boot project via Initializr with core dependencies (Web, JPA, Security, PostgreSQL, Lombok).
- Configured PostgreSQL connectivity in application.properties.
- Defined core JPA entities: Author, Book, and BookCopy.
- Implemented Auditable class for tracking created/updated timestamps and users.

[Added controller, service and request/response for Author and Book entities](https://github.com/Saidasaradharam/Library-Management/commit/70fda54e119828675979a0c0812f84b07a8c13bd#diff-82ffadafffd49fdd91ec66d1e3109700761156be307678f0562d02d527debab0)
- Created necessary DTOs for Author and Book entities (Request/Response) for a clean API contract.
- Implemented Service and Controller layers for both Author and Book entities.
- Implemented full REST APIs for the Author and Book.