# Task Manager

Task Manager is a simple web application that allows users to add and edit notes. This is an ideal project for learning the basics of backend development and data management.

## Features

- **Add Notes**: Users can add new notes to the list.
- **Edit Notes**: Users can edit existing notes.

## How to Use

1. **Clone the Repository**
   
   ```bash
   git clone https://github.com/dolofinskyi/task-manager.git

# Profiles

> [!WARNING]
> You must choose your profile by setting ```spring.profiles.active``` variable ```dev``` or ```prod``` values respectively.

## Dev profile

> [!IMPORTANT]
> This profile named ```application-dev.yml``` and connected to H2 database, which saves the ```.mv.db``` file to following path: ```./db/files/<dbname>.mv.db```.  
> You should set your ```username``` and  ```password``` variables accordingly to your H2 database settings.

## Prod profile

> [!IMPORTANT]
> This profile named ```application-prod.yml``` and connected to PostgreSQL database. 
> You must set your ```database-url```, ```username``` and  ```password``` variables.

##
Each profile have default settings for Jwt token. You can configure ```secret-key``` and ```expiration-time``` for each of them.  ```secret-key``` must be 256 bits HEX string. 
