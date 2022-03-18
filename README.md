# Keisatsu-cho

## Introduction
The process of managing information required for organising a conference is a time-consuming activity. In order to produce and manage this information, using a dedicated software application is by far the best solution.

## Proposed system
### Overview
The application will support three types of users (i.e., chair, reviewer and author). It will follow a server-client arhitecture, providing a DBMS for data persistency.

### Functional requirements
#### General Requirements:
- sign up/sign in 🔴
- upload personal information 🔴
- assign papers to reviewers automatically

#### Chair:
- submit basic conference information, such as conference name, URL, subtitles, main organiser's contact information
- set and modify the deadline for paper submission, paper review, acceptance notification, and the uploading of accepted paper (camera ready-copies)
- set up topics of interest
- make the final decision on accepting or rejecting a specific paper
- assign an accepted paper to a specific conference session

#### Reviewer:
- specify the topics that falls into the area of expertise of the reviewer
- bid for papers that they are interested in reviewing
- indicate any conflict of interest
- submit an evaluation of a paper that was assigned to the reviewer
- submit a special comment that can be read by other reviewers
- 
 
#### Author:
- submit an abstract of a paper in addition to the paper title, authors, their emails, addresses, keywords, and the topic of interest that applies to their paper
- upload the full paper in a specific format (e.g. PDF file or Word file)
- upload the camera-ready copy of an accepted paper

### Nonfunctional requirements
- all performed actions and information retrieval operations should take less than 5 seconds
- all documents should take less than 20 seconds to be generated
- user's personal information should be confidential
- user's personal & authentification information cannot be modified by any other person
- the system should be able to restart after a failure
- the system should be able to back-up data and recover it using the backups
- the system should generate fault reports & various logs (both for user behaviour & error logging)

### Constraints
- emails should be validated to exist and be unique
- usernames should be validated to be unique
- passwords should be securely stored
