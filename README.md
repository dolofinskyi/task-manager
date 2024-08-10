# Task Manager

Task Manager is a simple web application that allows users to add and edit notes. This is an ideal project for learning the basics of backend development and data management.

## Features

- **Add Notes**: Users can add new notes to the list.
- **Edit Notes**: Users can edit existing notes.

## How to Use

1. **Clone the Repository**

   ```bash
   git clone https://github.com/dolofinskyi/task-manager.git

2. **Launch**

   You have 2 choises: dev or prod prifiles.

   ## Dev profile
   This profile named ```application-dev.yml``` and connected to H2 database, which saves the ```.mv.db``` file to following path: ```./db/files/<dbname>.mv.db```. Also you must fill your ```username``` and  ```password``` variables.

   ## Prod profile
   This profile named ```application-prod.yml``` and connected to PostgreSQL database. You must fill your ```database-url```, ```username``` and  ```password``` variables.

 Each profile have default settings for Jwt token. You can configure ```secret-key``` and ```expiration-time``` for each of them.  ```secret-key``` must be 256 bits HEX string. 
