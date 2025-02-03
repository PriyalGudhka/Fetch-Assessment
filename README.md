# Receipt Processing System

## Overview

The Receipt Processing System is designed to store receipts and calculate reward points based on various conditions. The system accepts receipts, processes the details of the items on the receipt, and rewards points based on certain criteria such as:

- The length of the retailer's name
- Whether the total has cents
- The number of items purchased
- Whether the purchase date is odd
- The time of purchase

## Features

- **Store Receipts**: Allows storing receipts with associated items.
- **Calculate Points**: Calculates reward points based on receipt details.
- **Date and Time-based Calculation**: Points are awarded based on the purchase time and date.
- **Item-based Calculation**: Points are awarded based on item details such as description length and price.

## Technologies Used

- **Java**: Programming language used for developing the application.
- **Spring Boot**: For building the REST API and handling dependency injection.
- **ModelMapper**: Used to map data between request DTOs and entity objects.
- **Docker**: To containerize the application and make it easier to deploy and run.

## Project Structure

- **Controller**: The controller (`ProcessReceiptsController`) handles incoming requests for processing receipts and calculating points.
- **Service**: The service layer (`ProcessReceiptsService`) contains the logic for processing receipts and calculating loyalty points.
- **DTOs**: Data transfer objects are used to define the structure of the data for requests and responses.
- **Entities**: The `Receipts` and `Items` entities represent the database structure for storing receipt and item data.
- **Repository**: The repository interfaces (`ReceiptsRepo`, `ItemsRepo`) interact with the database for storing and retrieving receipt and item data.

## Setup and Installation

## Docker Setup

To containerize the application and run it inside a Docker container, follow the steps below:

### Build the Docker Image

1. Clone the repository:
   ```bash
   git clone https://github.com/PriyalGudhka/Fetch-Assessment.git
   ```

2. Navigate into the project directory:
   ```bash
   cd Fetch-Assessment
   ```
   
3. Run the following command to build the Docker image:

   ```bash
   docker build -t spring-docker .
   ```

4. This will create a Docker image named `spring-docker`.

### Run the Docker Container

1. After the image is built, run the container with the following command:

   ```bash
   docker run -p 8080:8080 spring-docker
   ```

2. This will start the application inside the Docker container and expose the application on `http://localhost:8080`.

### Accessing the H2 Database Console

If you'd like to view and interact with the H2 in-memory database that is used by the application, you can access the H2 console by navigating to:

- **URL**: `http://localhost:8080/h2-console`

In the H2 console and click on Connect:

- **JDBC URL**: `jdbc:h2:mem:receiptsDB`
- **Username**: `root`
- **Password**: `root`