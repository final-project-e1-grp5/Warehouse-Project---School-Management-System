import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import './ViewAttendance.css';

const localizer = momentLocalizer(moment);

const ViewAttendance = ({ studentId }) => {
    const [events, setEvents] = useState([]);

    useEffect(() => {
        const fetchAttendance = async () => {
            try {
                const response = await axios.get(`/attendance/${studentId}`);
                const attendanceData = response.data.map((attendance) => ({
                    title: attendance.status,
                    start: new Date(attendance.date),
                    end: new Date(attendance.date),
                    allDay: true,
                    resource: attendance.status,
                }));
                setEvents(attendanceData);
            } catch (error) {
                console.error('Error fetching attendance data', error);
            }
        };

        fetchAttendance();
    }, [studentId]);

    const eventStyleGetter = (event) => {
        let backgroundColor;
        switch (event.resource) {
            case 'PRESENT':
                backgroundColor = 'green';
                break;
            case 'ABSENT':
                backgroundColor = 'red';
                break;
            case 'LATE':
                backgroundColor = 'yellow';
                break;
            case 'EXCUSED':
                backgroundColor = 'blue';
                break;
            default:
                backgroundColor = 'gray';
        }

        return {
            style: {
                backgroundColor,
                borderRadius: '0px',
                opacity: 0.8,
                color: 'black',
                border: '0px',
                display: 'block',
            },
        };
    };

    return (
        <div className="view-attendance">
            <h1 className="mb-4 text-center">View Attendance</h1>
            <Calendar
                localizer={localizer}
                events={events}
                startAccessor="start"
                endAccessor="end"
                style={{ height: 500 }}
                eventPropGetter={eventStyleGetter}
            />
        </div>
    );
};

export default ViewAttendance;
