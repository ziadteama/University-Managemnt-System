-- Create Departments Table
CREATE TABLE Departments (
    department_id INT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(100) NOT NULL
);

-- Create Majors Table
CREATE TABLE Majors (
    major_id INT PRIMARY KEY AUTO_INCREMENT,
    major_name VARCHAR(100) NOT NULL,
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES Departments(department_id)
);

-- Create Users Table
CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    role ENUM('student', 'advisor', 'dr', 'ta') NOT NULL,
    department_id INT,
    major_id INT,
    FOREIGN KEY (department_id) REFERENCES Departments(department_id),
    FOREIGN KEY (major_id) REFERENCES Majors(major_id)
);

-- Create Courses Table
CREATE TABLE Courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    department_id INT,
    credit_hours INT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES Departments(department_id)
);

-- Create Course Prerequisites Table
CREATE TABLE CoursePrerequisites (
    course_id INT,
    prerequisite_course_id INT,
    PRIMARY KEY (course_id, prerequisite_course_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id),
    FOREIGN KEY (prerequisite_course_id) REFERENCES Courses(course_id)
);

-- Create Sections Table
CREATE TABLE Sections (
    section_id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT,
    semester VARCHAR(50),
    year INT,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id),
);

-- Create Tutorials Table
CREATE TABLE Tutorials (
    tutorial_id INT PRIMARY KEY AUTO_INCREMENT,
    section_id INT,
    ta_id INT,
    FOREIGN KEY (section_id) REFERENCES Sections(section_id),
    FOREIGN KEY (ta_id) REFERENCES Users(user_id)
);

-- Create Enrollments Table
CREATE TABLE Enrollments (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    section_id INT,
    enrollment_date DATE,
    grade ENUM('A+', 'A', 'B', 'C', 'D', 'F', 'W') DEFAULT 'W',
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (section_id) REFERENCES Sections(section_id)
);

-- Create Schedules Table
CREATE TABLE Schedules (
    schedule_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    section_id INT,
    day_of_week ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday') NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (section_id) REFERENCES Sections(section_id)
);

-- Create Marks Table (For storing the marks for assignments, 7th, 12th, CW, Final)
CREATE TABLE Marks (
    mark_id INT PRIMARY KEY AUTO_INCREMENT,
    enrollment_id INT,
    seventh_exam DECIMAL(5, 2),
    twelfth_exam DECIMAL(5, 2),
    cw DECIMAL(5, 2),
    final_exam DECIMAL(5, 2),
    FOREIGN KEY (enrollment_id) REFERENCES Enrollments(enrollment_id)
);

-- Create StudentGPAs Table (For storing each semester GPA of students)
CREATE TABLE StudentGPAs (
    gpa_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    semester VARCHAR(50),
    gpa DECIMAL(5, 2),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Create GradePoints Table (For storing grade points for GPA calculation)
CREATE TABLE GradePoints (
    grade ENUM('A+', 'A', 'B', 'C', 'D', 'F', 'W') PRIMARY KEY,
    grade_point DECIMAL(5, 2)
);

-- Create Withdrawals Table (Optional, for recording student withdrawals)
CREATE TABLE Withdrawals (
    withdrawal_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    course_id INT,
    withdrawal_date DATE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

-- Lectures Table
CREATE TABLE Lectures (
    lecture_id INT PRIMARY KEY AUTO_INCREMENT,
    section_id INT,
    dr_id INT,
    FOREIGN KEY (section_id) REFERENCES Sections(section_id),
    FOREIGN KEY (dr_id) REFERENCES Users(user_id)
);


