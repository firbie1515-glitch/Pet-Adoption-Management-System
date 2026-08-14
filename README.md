# Pet Adoption Management System

A Java Swing desktop application for managing pet adoption-related information. The project integrates a graphical user interface with a MySQL relational database through JDBC.

## Features
- Member registration and login
- Member information management
- Pet search with multiple conditions
- Adoption record management and lookup
- Volunteer activity information
- Donation information and statistics
- Relational data queries and database integration

## Tech Stack
Java · Java Swing · MySQL · JDBC · SQL

## Main Database Tables
The project includes relational data for members, stray animals, adoption records, intake records, donations, volunteer activities, and volunteer participation.

## Project Structure
```text
java code/    # Java Swing application source
table_data/   # Sample/exported table data
資料庫關聯綱目的描述檔.sql  # Database schema/data dump
專題報告書.docx            # Project report
```

## Database Configuration
Real database credentials are not stored in source code. Configure these environment variables before running:

```text
DB_URL=jdbc:mysql://127.0.0.1:3306/dbproject
DB_USER=root
DB_PASSWORD=your_password
```

`DB_URL` and `DB_USER` have local-development defaults. `DB_PASSWORD` must be supplied through the environment.

> `.env.example` is only a safe configuration reference. This plain Java project does not automatically load `.env` files.

## How to Run
1. Install Java, MySQL, and MySQL Connector/J.
2. Create the database first:
   ```sql
   CREATE DATABASE dbproject CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
3. Select `dbproject` and import `資料庫關聯綱目的描述檔.sql`.
4. Add the MySQL Connector/J JDBC driver to the project classpath.
5. Configure `DB_URL`, `DB_USER`, and `DB_PASSWORD` in your environment.
6. Compile the Java files in `java code/` and run `PetAdoptionSystem.java`.

## My Contribution
I served as a primary developer and participated in system feature planning, GUI design, database design, core Java implementation, SQL development, system integration, testing, debugging, and project presentation.

The project demonstrates practical experience connecting Java applications to a relational database and implementing CRUD operations, prepared statements, joins, aggregation, and multi-table data workflows.

## Security
Database passwords and other secrets should never be committed to Git. Compiled Java files and local development settings are excluded through `.gitignore`.
