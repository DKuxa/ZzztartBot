# ZzztartBot

ZzztartBot is a Java-based Telegram bot application, leveraging Spring Boot and PostgreSQL.

## Prerequisites

- Docker
- Docker Compose

## Quick Start

Follow these steps to get the application running on your local machine:

### Step 1: Clone the Repository

```bash
git clone https://github.com/DKuxa/ZzztartBot.git
cd ZzztartBot
```
### Step 2: Run with Docker Compose
Build the Docker image and start the application along with the PostgreSQL database:

```bash
docker-compose up --build
```
The application will be accessible at http://localhost:8080 (adjust this if your configuration differs).

### Step 3: Stopping the Application
To stop the application and remove containers:

```bash
docker-compose down
```
### Configuration
Environment variables are used to configure the database connection in docker-compose.yml. Modify these as needed:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

### Contributing
Feel free to fork the project and submit pull requests.

### Contact
Email: d.kuxa88@gmail.com

Project Link: https://github.com/DKuxa/ZzztartBot