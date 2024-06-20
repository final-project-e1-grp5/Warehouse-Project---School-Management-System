import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = ({ userRole }) => {
    return (
        <nav>
            <ul>
                <li><Link to="/">Home</Link></li>
                {userRole === 'admin' && (
                    <>
                        <li><Link to="/admin/dashboard">Admin Dashboard</Link></li>
                        <li><Link to="/admin/add">Add Admin</Link></li>
                        <li><Link to="/student/add">Add Student</Link></li>
                        <li><Link to="/teacher/add">Add Teacher</Link></li>
                        <li><Link to="/class/add">Add Class</Link></li>
                        <li><Link to="/schedule/add">Add Schedule</Link></li>
                    </>
                )}
                {userRole === 'student' && (
                    <>
                        <li><Link to="/student/dashboard">Student Dashboard</Link></li>
                        <li><Link to="/attendance/view">View Attendance</Link></li>
                        <li><Link to="/grades/view">View Grades</Link></li>
                    </>
                )}
                {userRole === 'teacher' && (
                    <>
                        <li><Link to="/teacher/dashboard">Teacher Dashboard</Link></li>
                    </>
                )}
            </ul>
        </nav>
    );
};

export default Navbar;
