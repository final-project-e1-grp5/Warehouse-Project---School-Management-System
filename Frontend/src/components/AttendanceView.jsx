import React, { useEffect, useState } from 'react';
import axios from 'axios';

const AttendanceView = () => {
    const [attendance, setAttendance] = useState([]);

    useEffect(() => {
        const fetchAttendance = async () => {
            try {
                const response = await axios.get('/attendance/student');
                setAttendance(response.data);
            } catch (error) {
                console.error('Error fetching attendance:', error);
            }
        };

        fetchAttendance();
    }, []);

    return (
        <div>
            <h2>Attendance</h2>
            <ul>
                {attendance.map((record) => (
                    <li key={record.id}>{record.date} - {record.status}</li>
                ))}
            </ul>
        </div>
    );
};

export default AttendanceView;
