## Spring Boot ChatRoom Application with Basic Authentication and WebSocket

## This Spring Boot application demonstrates a simple server chat application with basic authentication for user login and WebSocket protocol for real-time communication. The application also includes CRUD operations for sending, receiving and deleting chats using RESTful API endpoints. In this repository  you will also find commented out code highlighted with TODO commnets for a feature that would implement basic authentication through the websocket.

## Technologies Used
    Java
    Spring Boot
    Spring Security
    Spring Data JPA
    WebSocket
    Maven

## Getting Started
Clone the repository:
git clone https://gitlab.com/gugsy/chatroom.git
Navigate to the project directory:

## Configure Database:
Configure the database settings in src/main/resources/application.properties.

## Run the Application:
Open your browser and go to http://localhost:8080 to access the application.
Use the to test the CRUD endpoints api-documents/chatroom-endpoints.postman_collection.json file to import the Postman collection and test the API 

## Real-Time Communication with WebSocket
The WebSocket endpoint is ws://localhost:8080/websocket-chatroom
Connect to the WebSocket endpoint using a WebSocket client via POSTMAN to send and receive real-time messages.
