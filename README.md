# PCI DSS Demo

**PCI DSS Demo** is a sample payment card application created as part of the *Coding the Standards* Medium article series. The project demonstrates how a payment system can be designed with **PCI DSS (Payment Card Industry Data Security Standard)** considerations in mind.

## About the Project

This demo showcases a simplified **secure payment flow** using modern technologies on both backend and frontend. It is intended for educational purposes, focusing on how PCI DSS principles can be applied in real-world applications.

## Technologies Used

- **Backend:** Java, Spring Boot  
- **Frontend:** React, TypeScript  
- **Security:** Spring Security  
- **Containerization:** Docker  

## Purpose

The main goal of this project is to provide developers with a practical example of implementing **PCI DSS–aware architecture and security practices** in a payment card application.

## How to run

- Start docker daemon in your local.
- Follow commands below:

```
    cd docker
    docker-compose up
```

- For stopping services:

```
CTRL + C (stop run of docker-compose)
```

- For removing containers:

```
docker-compose down
```

- For removing everything (including volumes):

```
docker-compose down -v
```
