import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { Container, Form, Row, Col, Button } from 'react-bootstrap';
import './ViewAttendance.css';

const localizer = momentLocalizer(moment);

const ViewStudentAttendance = () => {
    const [classes, setClasses] = useState([]);
    const [students, setStudents] = useState([]);
    const [selectedClassId, setSelectedClassId] = useState('');
    const [selectedStudentId, setSelectedStudentId] = useState('');
    const [events, setEvents] = useState([]);

    useEffect(() => {
        fetchClasses();
    }, []);

    useEffect(() => {
        if (selectedClassId) {
            fetchStudentsByClass(selectedClassId);
        }
    }, [selectedClassId]);

    useEffect(() => {
        if (selectedStudentId) {
            fetchAttendance(selectedStudentId);
        }
    }, [selectedStudentId]);

    const fetchClasses = async () => {
        try {
            const response = await axios.get('/class/all'); // Adjust endpoint as necessary
            setClasses(response.data || []);
        } catch (error) {
            console.error('Error fetching classes:', error);
        }
    };

    const fetchStudentsByClass = async (classId) => {
        try {
            const response = await axios.get(`/class/${classId}/students`); // Adjust endpoint as necessary
            setStudents(response.data || []);
        } catch (error) {
            console.error('Error fetching students:', error);
        }
    };

    const fetchAttendance = async (studentId) => {
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
        <Container className="mt-4">
            <h1 className="mb-4 text-center">View Student Attendance</h1>
            <Form>
                <Row className="mb-3">
                    <Col md={6}>
                        <Form.Group controlId="formClass">
                            <Form.Label>Select Class</Form.Label>
                            <Form.Control
                                as="select"
                                value={selectedClassId}
                                onChange={(e) => setSelectedClassId(e.target.value)}
                                required
                            >
                                <option value="">Choose...</option>
                                {classes.map((classItem) => (
                                    <option key={classItem.id} value={classItem.id}>
                                        {classItem.name}
                                    </option>
                                ))}
                            </Form.Control>
                        </Form.Group>
                    </Col>
                    <Col md={6}>
                        <Form.Group controlId="formStudent">
                            <Form.Label>Select Student</Form.Label>
                            <Form.Control
                                as="select"
                                value={selectedStudentId}
                                onChange={(e) => setSelectedStudentId(e.target.value)}
                                required
                                disabled={!selectedClassId}
                            >
                                <option value="">Choose...</option>
                                {students.map((student) => (
                                    <option key={student.id} value={student.id}>
                                        {student.firstName} {student.lastName}
                                    </option>
                                ))}
                            </Form.Control>
                        </Form.Group>
                    </Col>
                </Row>
            </Form>
            <Calendar
                localizer={localizer}
                events={events}
                startAccessor="start"
                endAccessor="end"
                style={{ height: 500 }}
                eventPropGetter={eventStyleGetter}
            />
        </Container>
    );
};

export default ViewStudentAttendance;
