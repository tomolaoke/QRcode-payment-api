# **QR Code Generation and Payment API**
A RESTful API for generating QR codes and processing payments built with Spring Boot and PostgreSQL.


## **Project Structure Overview**

```json
qr-payment-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── qrpayment/
│   │   │               ├── controller/
│   │   │               │   └── QRPaymentController.java
│   │   │               ├── dto/
│   │   │               │   ├── PaymentRequestDTO.java
│   │   │               │   ├── PaymentResponseDTO.java
│   │   │               │   ├── QRCodeRequestDTO.java
│   │   │               │   └── QRCodeResponseDTO.java
│   │   │               ├── entity/
│   │   │               │   ├── Merchant.java
│   │   │               │   ├── Transaction.java
│   │   │               │   └── User.java
│   │   │               ├── exception/
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               ├── repository/
│   │   │               │   ├── MerchantRepository.java
│   │   │               │   ├── TransactionRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               ├── service/
│   │   │               │   ├── PaymentService.java
│   │   │               │   └── QRCodeService.java
│   │   │               └── QrPaymentApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/ (add unit tests here)
├── pom.xml
├── README.md
└── api-tests.json
```


## **Prerequisites**

Java 17 or higher

Maven

PostgreSQL

## **Getting Started**

### **Database Setup**
1. Create a PostgreSQL database named qrpayment

2. Update application.properties with your database credentials
```json
spring.application.name=qr-payment-api

spring.datasource.url=jdbc:postgresql://localhost:5432/qrpayment
spring.datasource.username=postgres
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### **Running the Application**

mvn spring-boot:run

The application will start on http://localhost:8080


## **API Endpoints**

### ***Generate QR Code**

**POST**
http://localhost:8080/api/v1/generate-qr

Request body:
```json
{
  "amount": 50.0,
  "currency": "₦",
  "merchantId": 12345,
  "description": "Payment for services"
}
```

Response:
```json
{
  "qrCodeImage": "base64EncodedImage...",
  "transactionId": 1,
  "amount": 50.0,
  "currency": "₦",
  "merchantId": 12345,
  "description": "Payment for services"
}
```

### **Process Payment**

**POST** 
http://localhost:8080/api/v1/process-payment

Request body:
```json
{
  "transactionId": 1,
  "userId": 1
}
```

Response:
```json
{
  "transactionId": 1,
  "status": "SUCCESS",
  "amount": 50.0,
  "currency": "₦",
  "userId": 1,
  "userBalance": 950.0,
  "merchantId": 12345,
  "merchantBalance": 1050.0,
  "description": "Payment for services"
}
```

## **Testing**
```json
{
  "info": {
    "name": "QR Payment API Tests",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Generate QR Code",
      "request": {
        "method": "POST",
        "url": "http://localhost:8080/api/v1/generate-qr",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"amount\": 50.0,\n  \"currency\": \"₦\",\n  \"merchantId\": 12345,\n  \"description\": \"Payment for services\"\n}"
        }
      },
      "response": []
    },
    {
      "name": "Process Payment",
      "request": {
        "method": "POST",
        "url": "http://localhost:8080/api/v1/process-payment",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"transactionId\": 1,\n  \"userId\": 1\n}"
        }
      },
      "response": []
    }
  ]
}
```


## **Author**

Tomola OKe

[Linkedin](https://www.linkedin.com/in/tomolaoke)

[Email](tommola.oke@gmail.com)
