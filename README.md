# Keisatsu-cho
Software Engineering Course Team Project - ```Keisatsu-cho Team```

## Team
### Members
<ul>
    <li><a href="https://github.com/EmanuelPutura">Emanuel-Vasile Puțura</a></li>
    <li><a href="https://github.com/HoriaRaduRusu">Horia-Radu Rusu</a></li>
    <li><a href="https://github.com/nacho-vlad">Florin-Vlad Sabău</a></li>
    <li><a href="https://github.com/916-Pop-Felix">Felix-Cristian Pop</a></li>
    <li><a href="https://github.com/PredaBoss">Andrei-Constantin Preda</a></li>
</ul>

### Front-end Devs
- technology: ```React``` + ```Typescript```
- devs: Radu

### Backend Devs
- technology: ```Kotlin``` + ```Spring Boot```
- devs: Emanuel, Felix

### Full Stack Devs
- devs: Florin, Andrei

### Database Devs
- technology: ```PostgreSQL``` + ```Ktorm (ORM)?```
- devs: Andrei, Florin

<hr/>

## 1. Introduction
The process of managing information required for organising a ```conference``` is a time-consuming activity. In order to produce and manage this information, using a dedicated software application is by far the best solution.

## 2. Proposed system
### 2.1 Overview
The application will support three types of ```users (i.e., chair, reviewer and author).``` It will follow a server-client arhitecture, providing a database management system for data persistency.

### 2.2 Functional requirements
#### 2.2.1 General Requirements
- sign up/sign in
- upload personal information
- assign papers to reviewers automatically

#### 2.2.2 Chair
- submit basic conference information, such as conference name, URL, subtitles, main organiser's contact information
- set and modify the deadline for paper submission, paper review, acceptance notification, and the uploading of accepted paper (camera ready-copies)
- set up topics of interest
- make the final decision on accepting or rejecting a specific paper
- assign an accepted paper to a specific conference session

#### 2.2.3 Reviewer
- specify the topics that falls into the area of expertise of the reviewer
- bid for papers that they are interested in reviewing
- indicate any conflict of interest
- submit an evaluation of a paper that was assigned to the reviewer
- submit a special comment that can be read by other reviewers
 
#### 2.2.4 Author
- submit an abstract of a paper in addition to the paper title, authors, their emails, addresses, keywords, and the topic of interest that applies to their paper
- upload the full paper in a specific format (e.g. PDF file or Word file)
- upload the camera-ready copy of an accepted paper

### 2.3 Nonfunctional requirements
#### 2.3.1 Performance
- all performed actions and information retrieval operations should take less than 5 seconds
- all documents should take less than 20 seconds to be generated

#### 2.3.2 Security
- user's personal information should be ```confidential```
- user's personal & authentification information cannot be modified by any other person
- passwords should be ```securely stored```

#### 2.3.3 Maintainability
- the system should be able to ```restart``` after a failure
- the system should be able to ```back-up``` data and recover it using the backups
- the system should generate ```fault reports & various logs``` (both for user behaviour & error logging)

### 2.4 Constraints
- emails should be validated to exist and be unique
- usernames should be validated to be unique
