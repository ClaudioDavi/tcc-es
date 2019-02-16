# Role Identification Platform

> **This was my very first complete machine learning product, also the first software that I built alone, back in 2016. Today there is a lot of things that I would change to make my life easier, but with time comes experience. This project made me fall in love with Machine Learning and Software Engeneering and I'm releasing it now because I believe it's a very important piece of my past and that all knowledge should be available to everyone.**

To get an overview, please check the Presentation.pdf file in the root folder.

To read the full text, access the PDF ClaudioSouza.pdf

If you want to try it for yourself, follow the instructions below:

# Install the app

This section describes the steps for configuring the RIP platform to fully operate on your SO.

-> Requirements: Text Editor, PostgreSQL 9.3, Java 1.8, Maven, Jira Server connected to PostgreSQL (seek for instructions on the Atlassian oficial website).

## Setting Up JIRA

After setting up JIRA following their normal instructions you have to configure the RIP platform to operate and read it's database.
In order to do that, you have to open the project on your Text Editor and search for "persistance" package and edit the connection data for JIRA and your database for the application on Class ConnectionManager.java.

---

After editing the data in ConnectionManager.java you have to go to postgreSQL and run the following scripts:

```
CREATE DATABASE "roleApp"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'pt_BR.UTF-8'
       CONNECTION LIMIT = -1;
```

---

After succesfully creating the RoleApp database, you should run these scripts to create the tables that the app will use.

```
CREATE TABLE "Features"
(
  id integer NOT NULL,
  name character varying[],
  CONSTRAINT id PRIMARY KEY (id)
);

CREATE TABLE "Issues"
(
  issuenum integer NOT NULL,
  description character varying,
  reporter character varying,
  assignee character varying,
  project integer,
  summary character varying,
  CONSTRAINT "Issues_pkey" PRIMARY KEY (issuenum)
);
CREATE TABLE "Projects"
(
  id integer NOT NULL,
  name character varying,
  CONSTRAINT "Projects_pkey" PRIMARY KEY (id)
);

CREATE TABLE "Reports"
(
  filename character varying NOT NULL,
  CONSTRAINT "Reports_pkey" PRIMARY KEY (filename)
);

CREATE TABLE "TeamMembers"
(
  username character varying NOT NULL,
  activities character varying[],
  myissues integer[],
  state integer[],
  CONSTRAINT teammembers_pkey PRIMARY KEY (username)
);
```

---

To check if everything is working right, go to your JIRA instance and create a new project, compile the RIP Source Code using Maven and click on 'Options' -> 'Load JIRA Data'. If your new project appears on the Projects list you have everything set up and ready to go.
