import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ParentDashboard = ({ studentId }) => {
    const [attendance, setAttendance] = useState([]);
    const [grades, setGrades] = useState([]);

    useEffect(() => {
        const fetchAttendance = async () => {
            try {
                const response = await axios.get(`/child/${studentId}/attendance`);
                setAttendance(response.data);
            } catch (error) {
                console.error('Error fetching attendance:', error);
            }
        };

        const fetchGrades = async () => {
            try {
                const response = await axios.get(`/child/${studentId}/grades`);
                setGrades(response.data);
            } catch (error) {
                console.error('Error fetching grades:', error);
            }
        };

        fetchAttendance();
        fetchGrades();
    }, [studentId]);

    return (
        <div>
            <h2>Parent Dashboard</h2>
            <div>
                <h3>Attendance</h3>
                <ul>
                    {attendance.map((record) => (
                        <li key={record.id}>{record.date} - {record.status}</li>
                    ))}
                </ul>
            </div>
            <div>
                <h3>Grades</h3>
                <ul>
                    {grades.map((grade) => (
                        <li key={grade.id}>{grade.subject} - {grade.grade}</li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default ParentDashboard;
