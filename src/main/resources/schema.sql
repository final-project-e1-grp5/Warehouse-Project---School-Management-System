DROP DATABASE school;
CREATE DATABASE school;


-- create USER TABLE for users like admin, teacher, student, parent
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(50) UNIQUE NOT NULL, -- UUID actual length 32 chars, but I gave a bit more space
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255), -- can be NULL, because we maybe need to store users from somewhere else, like google or so (maybe later on "feature")
    role VARCHAR(50) NOT NULL
);

-- create ATTENDANCE TABLE
CREATE TABLE attendance (
    id SERIAL PRIMARY KEY,
    date DATE NOT NULL, -- date of the attendance
    student_id VARCHAR(50) NOT NULL, -- ↳ "all three for searching purpose e.g."
    teacher_id VARCHAR(50) NOT NULL, --  ⇨ "user_id"
    course_id VARCHAR(50) NOT NULL,  -- course id ( self explained =) )
    status VARCHAR(20) NOT NULL, -- e.g. "present", "absent", "late"
    reason VARCHAR(100), -- reason for the absence like "illness, "appointment", "family emergency", "personal reasons", "other"
    attendance_time TIME, -- time at which the presence was recorded
    course VARCHAR(50), -- e.g. "MATHEMATICS", "IT", "..."
    notes TEXT -- self explained
);



