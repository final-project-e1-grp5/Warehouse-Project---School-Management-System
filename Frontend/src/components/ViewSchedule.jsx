import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import './ViewSchedule.css';

const ViewSchedule = () => {
    const [events, setEvents] = useState([]);
    const [date, setDate] = useState(new Date());

    useEffect(() => {
        fetchSchedule();
    }, []);

    const fetchSchedule = async () => {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                throw new Error("No token found");
            }

            const response = await axios.get('/schedule', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setEvents(response.data);
        } catch (error) {
            console.error('Error fetching schedule:', error);
        }
    };

    const tileContent = ({ date, view }) => {
        if (view === 'month') {
            const dayEvents = events.filter(event => new Date(event.date).toDateString() === date.toDateString());
            return (
                <ul className="events">
                    {dayEvents.map((event, index) => (
                        <li key={index}>{event.classEntity.name} ({event.startTime} - {event.endTime})</li>
                    ))}
                </ul>
            );
        }
    };

    return (
        <div className="calendar-container">
            <Calendar
                value={date}
                onChange={setDate}
                tileContent={tileContent}
            />
        </div>
    );
};

export default ViewSchedule;
