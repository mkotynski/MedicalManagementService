# MedicalManagementService
Medical facilites management system

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Roles](#roles)
* [Functionalities](#functionalities)

## General info
This application supports the management of medical facilities. It supports the process of registering for an appointment and making the appointment.

## Technologies
- Java 11
- Spring Boot 2.5.4
- PostgreSQL 11
- Keycloak 15.0.1

## Roles
There are three roles in the system:
- Patient
- Doctor
- Administrator

## Functionalities
Each system role has access to different functionalities.

Patient
- register for the appointment (choosing avaiable date, time, doctor)
- appointment history
- receipt, recipe and referal registry
- payments

Doctor
- making appointment (fill a appointment form)
- access to medical documentation
- setting the availability of visits
- prescribe a prescription
- prescribe a referal
- billing for medical appointment
- appointment history

Administrator
- appointments management
- users management
- doctor availability management

