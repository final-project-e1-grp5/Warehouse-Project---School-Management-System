-- Create 'users' database
CREATE DATABASE IF NOT EXISTS `user`;

-- Use 'users' database
USE `user`;

-- Add 'uuid_ossp' extension to generate uuids
    CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- Users Table
CREATE TABLE users
(

    user_id  UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    username VARCHAR(50)  NOT NULL,
    email    VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled  BOOLEAN      NOT NULL,
    role     VARCHAR(20)  NOT NULL CHECK (role IN ('admin', 'teacher', 'student', 'parent'))
);

-- Create 'student' database
CREATE DATABASE IF NOT EXISTS `student`;

-- Use 'student' database
USE `student`;

-- Students Table
CREATE TABLE students
(
    user_id       VARCHAR(100) PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    date_of_birth DATE        NOT NULL,
    class_id      BIGINT,
    parent_id     VARCHAR(100),
    phone_number  BIGINT,
    address       VARCHAR(100)
);

-- Create 'teacher' database
CREATE DATABASE IF NOT EXISTS `teacher`;

-- Use 'teacher' database
USE `teacher`;

-- Teachers Table
CREATE TABLE teachers
(
    user_id       VARCHAR PRIMARY KEY NOT NULL,
    first_name    VARCHAR(50)         NOT NULL,
    last_name     VARCHAR(50)         NOT NULL,
    date_of_birth DATE,
    phone_number  BIGINT,
    address       VARCHAR(150),
    subject       VARCHAR(100)        NOT NULL
);

-- Teacher Class Table
CREATE TABLE teacher_classes
(
    id SERIAL PRIMARY KEY,
    teacher_id VARCHAR(255) NOT NULL,
    class_id   BIGINT       NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teachers (user_id)
);


-- Create 'admin' database
CREATE DATABASE IF NOT EXISTS `admin`;

-- Use 'admin' database
USE `admin`;

-- Admins Table
CREATE TABLE admins
(
    user_id      VARCHAR(100)  PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    date_of_birth DATE   NOT NULL,
    phone_number  BIGINT,
    address       VARCHAR(100)
);

-- Create 'parent' database
CREATE DATABASE IF NOT EXISTS `parent`;

-- Use 'parent' database
USE `parent`;

-- parents Table

CREATE TABLE parents (
    user_id VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number BIGINT NOT NULL
);


-- children Table

CREATE TABLE children (
    id SERIAL PRIMARY KEY,
    parent_id VARCHAR(255) NOT NULL,
    child_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES parents(user_id)
);


-- Create 'class' database
CREATE DATABASE IF NOT EXISTS `class`;

-- Use 'class' database
USE `class`;

-- Classes Table
CREATE TABLE classes
(
    class_id   INT PRIMARY KEY,
    class_name VARCHAR(100) NOT NULL,
    class_year INT
);

-- Create 'schedule' database
CREATE DATABASE IF NOT EXISTS `schedule`;

-- Use 'schedule' database
USE `schedule`;

-- Schedules Table
CREATE TABLE schedule
(
    id          BIGSERIAL PRIMARY KEY,
    day_of_week VARCHAR(10)  NOT NULL CHECK (day_of_week IN
                                             ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY',
                                              'SUNDAY')),
    start_time  TIME         NOT NULL,
    end_time    TIME         NOT NULL,
    class_id    BIGINT       NOT NULL,
    teacher_id  VARCHAR(100) NOT NULL
);


-- Create 'attendance' database
CREATE DATABASE IF NOT EXISTS `attendance`;

-- Use 'attendance' database
USE `Attendance`;

-- student_attendance Table
CREATE TABLE student_attendance
(
    attendance_id   SERIAL PRIMARY KEY,
    student_id      VARCHAR(255) NOT NULL,
    class_id        BIGINT       NOT NULL,
    attendance_date DATE         NOT NULL,
    is_present      BOOLEAN      NOT NULL DEFAULT FALSE,
    early_leave     TIME,
    late_arrive     TIME,
    note            VARCHAR(150)
);

-- teacher_attendance Table

CREATE TABLE teacher_attendance
(
    attendance_id   SERIAL PRIMARY KEY,
    teacher_id      VARCHAR(255) NOT NULL,
    attendance_date DATE         NOT NULL,
    is_present      BOOLEAN      NOT NULL DEFAULT FALSE,
    early_leave     TIME,
    late_arrive     TIME,
    note            VARCHAR(150)
);


-- Create 'grade' database
CREATE
DATABASE IF NOT EXISTS `grade`;

-- Use 'grade' database
USE
`grade`;

-- Grades Table
CREATE TABLE grades
(
    grade_id   SERIAL PRIMARY KEY,
    student_id VARCHAR(100),
    class_id   BIGINT,
    assessment VARCHAR(100)  NOT NULL,
    grade      NUMERIC(5, 2) NOT NULL
);

-- Create 'messages' database
CREATE
DATABASE IF NOT EXISTS `message`;

-- Use 'message' database
USE
`message`;

-- Messages Table
CREATE TABLE messages
(
    message_id     SERIAL PRIMARY KEY,
    sender_id      INT,
    receiver_id    INT,
    message        TEXT        NOT NULL,
    send_date_time TIMESTAMPTZ NOT NULL
);

