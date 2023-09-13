# Wex Purchase Transaction API with PostgreSQL

This repository contains a Spring Boot application that provides APIs for store and retrieve purchase transactions. It is configured to run with a PostgreSQL database using Docker Compose. You can explore and test these APIs interactively using Swagger.

## Prerequisites

Before you begin, make sure you have the following installed:

- [Docker](https://www.docker.com/)

## Getting Started

1. Clone this repository to your local machine:

   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```
2. Start the Docker containers:

    ```bash
   docker-compose up
    ```
3. Access the application

    You can make sure the application is up and running by accessing the Spring Boot application through healthcheck endpoint: http://localhost:8080/wex-purchase-transaction/

4. Stop and remove the containers

    To stop the containers and remove them, press Ctrl+C in the terminal where docker-compose is running.

## Usage
   By default, PostgreSQL database will not contain records.
   - First you need to store a purchase transaction, its ID is an integer auto-incremented by 1.
   - Now you are able to retrieve a purchase transaction successfully.
   - If you want to retrieve another transaction, just remember to insert another transaction and increment its last used ID.

## API Endpoints
   Here are the main API endpoints provided by this application:

   - **POST** /api/v1/purchase-transactions/store: Store a purchase transaction.

   - **GET** /api/v1/purchase-transactions/retrieve/{id}/{country}: Retrieve a purchase transaction by its ID and a country.

## Swagger Documentation
   For more detailed information about these endpoints and interactive testing, you can explore the Swagger documentation by running the application and accessing the following URL:
   
   Swagger UI - http://localhost:8080/wex-purchase-transaction/swagger-ui.html