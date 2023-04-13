# Varna Buslines
The following project was my Semester 3 assignment - to build a full stack application. 
In other words, a replica of the existing system here: [https://varnatraffic.com](https://varnatraffic.com/en).
You can learn more about it from the [documentation available](./project/doc/).
The current state of the project is unfinished, however its aim was for me to learn many software engineering concepts, such as:
- Java
- Spring framework
- Maven/Gradle
- Hibernate (ORM)
- Javascript
- React.js
- UI/UX development
- Docker
- CI/CD
- GitLab workflow
- Agile concepts
- C4 diagram
- Research using the DOT framework
- SonarQube quality assurance
- JWT Authentication & Authorization

Some information might mismatch with what is available. Here are the differences:
- The project was done using GitLab, but is distributed using GitHub.
- There is no active CI/CD, although the script is available.
- The original git history is not available.
- There is no source for the documents (doc/src).
- No UX Report & Feedback (privacy reasons)

You can run the project by **just** using Docker. Build instructions can be found down below.
If you have any questions about the project, feel free to ask!

# Progress
* Backend - ![](https://progress-bar.dev/80)
* Frontend - ![](https://progress-bar.dev/80)
* Unit tests - ![](https://progress-bar.dev/46)

# What is what in the project
* [/frontend/](./frontend/) - The source code for the React web application
    * [/frontend/src/](./frontend/src/) - Runtime source code 
    * [/frontend/cypress/](./frontend/cypress/) - Test soure code
* [/backend/](./backend/) - The source code for the Java backend application
    * [/backend/src/main/java/](./backend/src/main/java) - Runtime source code
    * [/backend/src/test/java/](./backend/src/test/java) - Test source code
* [/doc/](./doc/) - All documents exported to PDF format
    * [/doc/src/](./doc/src/) - All documents in raw format
    * [/doc/src/c4/](./doc/src/c4/) - The source code for the C4 diagram generator
* [/scripts/](./scripts/) - Any scripts referenced in the project
* [/docker-compose*.yml](./docker-compose.yml) - Docker specific files found in the root folder
* [/.gitlab-ci-yml](./.gitlab-ci.yml) - CI/CD pipeline script

You can find further information about the fundamentals with the provided documentation.

# Documentation
* [Project plan](./doc/Project%20plan.pdf)
* [Design document](./doc/Design%20document.pdf)
* [C4 diagram](https://structurizr.com/share/77232/diagrams)
* [UML diagram](./doc/UML.pdf)
* [Pipeline diagram](./doc/Pipeline%20diagram.png)
* [Research paper on SQL vs NoSQL regarding the project](./doc/Applied%20research.pdf)
* [Test plan](./doc/Test%20plan.pdf)
* [Test report](./doc/Test%20report.pdf)
* [UX Feedback & Report](./doc/UX%20Feedback%20%26%20Report.pdf)
* [Performance report as of 11/01/2023](./doc/Performance%20report.pdf)
* [Research paper for justifying technology stack used](./doc/Research%20paper%20for%20justifying%20technology%20stack%20used.pdf)
* [OWASP Security report](./doc/OWASP%20Security%20report.pdf)
* [API documentation with Swagger](localhost:8080/swagger-ui/) 
    * Start the app and go to [localhost:8080/swagger-ui/](localhost:8080/swagger-ui/)


# Prominent features
- Docker integration
- Token-based authentication & authorization
- WebSockets support
- A real world map overview using Leaflet + OpenStreetMap
- Excellent performance, according to the [Google Lighthouse report](./doc/Performance%20report.pdf)
- [CI/CD pipeline](./doc/Pipeline%20diagram.png) with SonarQube quality assurance
- [Password hashing using Argon2](https://www.password-hashing.net/)
- Database persistence using an ORM (MySQL + Hibernate)
- Frontend and backend testing

# Build instructions
1. Clone the repository with git:
```
git clone https://github.com/jookzie/varnabuslines --depth=1
cd ./varnabuslines/
```
2. Ensure you have Docker installed: [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)
4. Open a terminal and execute:
```
docker compose up
```
Alternatively, you can run the production build
```
docker compose -f ./docker-compose.prod.yml up
```
5. Visit [http://localhost:80](http://localhost:80) in your browser.

You can use phpMyAdmin on [http://localhost:8081](http://localhost:8081) to view the database.

# Mock data
## Why?
Having data ready to test features on is a part of the presentation process.
Best practice for this is to have a public database, which any client can use.
Due to the project's goal being for educational purposes, hosting a database on a public domain is out of scope, primarily due to funding limitation.
[Fontys' free database hosting](https://selfservice.app.fhict.nl/) has been considered, but has failed to comply with the project's demands, detailed here:
- Risk locking out of the database by having too many connections open
- Limited privileges to increase `max_user_connections` (currently being 10) OR increase `wait_timeout` of the connection pool.
- Unneccessary hassle of connecting to a VDI manually, with no provided support for app integration
## How?
A current workaround is to load mock data *once* by manually executing an SQL script on the database.
1. Start the project using the build instructions provided
2. Connect to the database using the provided [phpMyAdmin](localhost:8081).
3. Copy over and execute the script under [scripts/mock-data.sql](./scripts/mock-data.sql)
4. Done!
If any of the script commands fail, that would mean that the data is already there.

# Neccessary credentials
There are two roles in the application: users and administrators (also known as admins).
To access CRUD operations, you need an admin account.
By design, administrators are created by other administrators, which means that the first administrator account 
should be hard-coded into the database.
If you have loaded the mock data presented above, you will be given an administrator accoount.
- Email: `admin@mock.com`
- Password: `password`

Else, you can follow these instructions:
1. Create a regular user account
2. Open the [phpMyAdmin client](localhost:8081)
3. Go to the `user` table
4. Change the `role` value of your account from `0` to `1`
5. Done! Your account has become an administrator one.
