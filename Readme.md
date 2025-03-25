# HTTP Methods API Documentation

This project demonstrates the use of various HTTP methods in an API, including standard methods (`GET`, `HEAD`, `POST`, `PUT`, `PATCH`, `DELETE`, `OPTIONS`) and two custom methods (`TRACK` and `SYNC`). Each method serves a specific purpose in API communication.

**Disclaimer:** Please note that this implementation intentionally deviates from standard HTTP protocols (refer to RFC 9110 for HTTP/1.1 semantics and RFC 9112 for HTTP/1.1 message syntax and routing). This design is strictly intended for controlled internal use and is not suitable for external client-facing applications, as it may violate expected protocol behavior and interoperability standards.

---

## Table of Contents

- [HTTP Methods API Documentation](#http-methods-api-documentation)
  - [Table of Contents](#table-of-contents)
  - [HTTP Methods Overview](#http-methods-overview)
    - [1. **GET**](#1-get)
    - [2. **HEAD**](#2-head)
    - [3. **POST**](#3-post)
    - [4. **PUT**](#4-put)
    - [5. **PATCH**](#5-patch)
    - [6. **DELETE**](#6-delete)
    - [7. **OPTIONS**](#7-options)
    - [8. **TRACE**](#8-trace)
    - [9. **SYNC**](#9-sync)
    - [10. **TRACK**](#10-track)
  - [Summary Table](#summary-table)

## HTTP Methods Overview

### 1. **GET**

- **Description**: Retrieves data from the server without modifying it.
- **Idempotent**: Yes

**Before**:  
No data is fetched.

**Request**:

```http
GET /api/products HTTP/1.1
Host: example.com
```

**After**:
The list of products is retrieved.

```json
[
    { "id": 1, "name": "Product A", "price": 25.00 },
    { "id": 2, "name": "Product B", "price": 15.50 }
]
```

### 2. **HEAD**

- **Description**: Similar to GET but only retrieves headers, not the body.
- **Idempotent**: Yes

**Before**:
No metadata is retrieved.

**Request**:

```http
HEAD /api/files/report.pdf HTTP/1.1
Host: example.com
```

**After**:
Headers like file size and type are retrieved (no body).

```http
Content-Type: application/pdf
Content-Length: 1048576
```

### 3. **POST**

- **Description**: Sends data to the server to create or process a resource.
- **Idempotent**: No

**Before**:
No user record exists.

**Request**:

```http
POST /api/register HTTP/1.1
Content-Type: application/json
```

```json
{
    "username": "user123",
    "password": "securepassword"
}
```

**After**:
A new user record is created.

```json
{
    "id": 101,
    "username": "user123"
}
```

### 4. **PUT**

- **Description**: Replaces an existing resource or creates a new one if it doesn't exist.
- **Idempotent**: Yes

**Before**:
Product does not exist.

**Request**:

```http
PUT /api/products/123 HTTP/1.1
Content-Type: application/json
```

```json
{
    "name": "New Product",
    "price": 19.99
}
```

**After**:
The product is created or replaced.

```json
{
    "id": 123,
    "name": "New Product",
    "price": 19.99
}
```

### 5. **PATCH**

- **Description**: Partially updates an existing resource.
- **Idempotent**: No

**Before**:

```json
{
    "id": 123,
    "name": "New Product",
    "price": 19.99
}
```

**Request**:

```http
PATCH /api/products/123 HTTP/1.1
Content-Type: application/json
```

```json
{
    "price": 15.99
}
```

**After**:

```json
{
    "id": 123,
    "name": "New Product",
    "price": 15.99
}
```

### 6. **DELETE**

- **Description**: Removes a resource from the server.
- **Idempotent**: Yes

**Before**:
The user exists.

```json
{
    "id": 123,
    "username": "user123"
}
```

**Request**:

```http
DELETE /api/users/123 HTTP/1.1
```

**After**:
The user is deleted (no longer exists).

### 7. **OPTIONS**

- **Description**: Fetches the communication options available for a resource.
- **Idempotent**: Yes

**Before**:
No knowledge of supported methods.

**Request**:

```http
OPTIONS /api/products HTTP/1.1
```

**After**:
Supported methods are retrieved.

```http
Allow: GET, POST, PUT, DELETE, OPTIONS
```

### 8. **TRACE**

- **Description**: Debugging method that echoes back the received request.
- **Idempotent**: Yes

**Before**:
Request content is unknown.

**Request**:

```http
TRACE /api/debug HTTP/1.1
Host: example.com
```

**After**:
The exact request is echoed back for debugging.

```http
TRACE /api/debug HTTP/1.1
Host: example.com
```

### 9. **SYNC**

- **Description**: Triggers synchronization of resources between two systems.
- **Idempotent**: Depends on implementation.

**Before**:
API B's collection is outdated or inconsistent.

```json
{
    "products": [
        { "id": 1, "name": "Product A", "price": 20.00 },
        { "id": 2, "name": "Product B", "price": 15.50 }
    ]
}
```

**Request**:

```http
SYNC /api/products/sync HTTP/1.1
Host: api-b.example.com
Content-Type: application/json
```

```json
{
    "source": "https://api-a.example.com/products"
}
```

**After**:
API B's collection is updated to match API A.

```json
{
    "products": [
        { "id": 1, "name": "Product A", "price": 25.00 },
        { "id": 2, "name": "Product B", "price": 15.50 }
    ]
}
```

### 10. **TRACK**

- **Description**: Traces the execution flow of a request for debugging purposes (e.g., across controller, service, or repository layers).
- **Idempotent**: Yes (retrieves logs without modifying state).

**Before**:  An error occurs in the API, but the failure location is unknown.

**Request**:

```http
TRACK /api/orders/123/logs HTTP/1.1
Host: api.example.com
Accept: application/json
```

**After**:
The error is traced to the repository layer (Connection timeout). Debugging can now focus on database connectivity.

```json
{
    "requestId": "req-123456",
    "status": "failed",
    "executionPath": [
        {
            "layer": "controller",
            "action": "processOrder",
            "status": "success",
            "timestamp": "2025-03-25T10:00:00Z"
        },
        {
            "layer": "service",
            "action": "validateAndProcess",
            "status": "success",
            "timestamp": "2025-03-25T10:00:01Z"
        },
        {
            "layer": "repository",
            "action": "saveToDatabase",
            "status": "failed",
            "error": "Connection timeout",
            "timestamp": "2025-03-25T10:00:02Z"
        }
    ]
}
```

## Summary Table

| Method  | Description                                         | Idempotent | Example Request                                                                                                              | Example Response                                                                                                                                                                                                                                     |
|---------|-----------------------------------------------------|------------|------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET     | Retrieves data from the server without modifying it | Yes        | `GET /api/products HTTP/1.1`                                                                                                 | `[{"id": 1, "name": "Product A", "price": 25.00}, {"id": 2, "name": "Product B", "price": 15.50}]`                                                                                                                                                   |
| HEAD    | Retrieves headers, not the body                     | Yes        | `HEAD /api/files/report.pdf HTTP/1.1`                                                                                        | `Content-Type: application/pdf`<br>`Content-Length: 1048576`                                                                                                                                                                                         |
| POST    | Sends data to create or process a resource          | No         | `POST /api/register HTTP/1.1`<br>`Content-Type: application/json`<br>`{"username": "user123", "password": "securepassword"}` | `{"id": 101, "username": "user123"}`                                                                                                                                                                                                                 |
| PUT     | Replaces or creates a resource                      | Yes        | `PUT /api/products/123 HTTP/1.1`<br>`Content-Type: application/json`<br>`{"name": "New Product", "price": 19.99}`            | `{"id": 123, "name": "New Product", "price": 19.99}`                                                                                                                                                                                                 |
| PATCH   | Partially updates an existing resource              | No         | `PATCH /api/products/123 HTTP/1.1`<br>`Content-Type: application/json`<br>`{"price": 15.99}`                                 | `{"id": 123, "name": "New Product", "price": 15.99}`                                                                                                                                                                                                 |
| DELETE  | Removes a resource from the server                  | Yes        | `DELETE /api/users/123 HTTP/1.1`                                                                                             | Resource is deleted                                                                                                                                                                                                                                  |
| OPTIONS | Fetches communication options for a resource        | Yes        | `OPTIONS /api/products HTTP/1.1`                                                                                             | `Allow: GET, POST, PUT, DELETE, OPTIONS`                                                                                                                                                                                                             |
| TRACE   | Echoes back the received request for debugging      | Yes        | `TRACE /api/debug HTTP/1.1`                                                                                                  | `TRACE /api/debug HTTP/1.1`                                                                                                                                                                                                                          |
| SYNC    | Triggers synchronization between two systems        | Depends    | `SYNC /api/products/sync HTTP/1.1`<br>`Content-Type: application/json`<br>`{"source": "https://api-a.example.com/products"}` | `{"products": [{"id": 1, "name": "Product A", "price": 25.00}, {"id": 2, "name": "Product B", "price": 15.50}]}`                                                                                                                                     |
| TRACK   | Traces request execution flow for debugging         | Yes        | `TRACK /api/orders/123/logs HTTP/1.1`<br>`Accept: application/json`                                                          | `{"requestId": "req-123456", "status": "failed", "executionPath": [{"layer": "controller", "action": "processOrder", "status": "success"}, {"layer": "repository", "action": "saveToDatabase", "status": "failed", "error": "Connection timeout"}]}` |
