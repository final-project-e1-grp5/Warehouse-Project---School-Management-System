import React, { useState, useEffect } from 'react';
import { Form, Button, Container, Row, Col, InputGroup, ListGroup, ToggleButton, ToggleButtonGroup } from 'react-bootstrap';
import axios from 'axios';

const TakeAttendance = () => {
    const [classes, setClasses] = useState([]);
    const [schedules, setSchedules] = useState([]);
    const [students, setStudents] = useState([]);
    const [selectedClassId, setSelectedClassId] = useState('');
    const [selectedScheduleId, setSelectedScheduleId] = useState('');
    const [attendanceData, setAttendanceData] = useState({});

    useEffect(() => {
        fetchClasses();
    }, []);

    const fetchClasses = async () => {
        try {
            const response = await axios.get('/class/all'); // Adjust endpoint as necessary
            setClasses(response.data);
        } catch (error) {
            console.error('Error fetching classes:', error);
        }
    };

    const fetchSchedules = async (classId) => {
        try {
            const response = await axios.get(`/schedule/class/${classId}`); // Adjust endpoint as necessary
            setSchedules(response.data);
        } catch (error) {
            console.error('Error fetching schedules:', error);
        }
    };

    const fetchStudents = async (classId) => {
        try {
            const response = await axios.get(`/student/class/${classId}`); // Adjust endpoint as necessary
            setStudents(response.data);
        } catch (error) {
            console.error('Error fetching students:', error);
        }
    };

    useEffect(() => {
        if (selectedClassId) {
            fetchSchedules(selectedClassId);
            fetchStudents(selectedClassId);
        }
    }, [selectedClassId]);

    const handleAttendanceChange = (studentId, status) => {
        setAttendanceData((prevState) => ({
            ...prevState,
            [studentId]: status,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const attendancePayload = {
                scheduleId: selectedScheduleId,
                attendanceData,
            };
            const response = await axios.post('/attendance/mark', attendancePayload); // Adjust endpoint as necessary
            console.log('Attendance marked successfully:', response.data);
        } catch (error) {
            console.error('Error marking attendance:', error);
        }
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Take Attendance</h1>
            <Form onSubmit={handleSubmit}>
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
                                {classes.map((classEntity) => (
                                    <option key={classEntity.id} value={classEntity.id}>
                                        {classEntity.name}
                                    </option>
                                ))}
                            </Form.Control>
                        </Form.Group>
                    </Col>
                    <Col md={6}>
                        <Form.Group controlId="formSchedule">
                            <Form.Label>Select Schedule</Form.Label>
                            <Form.Control
                                as="select"
                                value={selectedScheduleId}
                                onChange={(e) => setSelectedScheduleId(e.target.value)}
                                required
                                disabled={!selectedClassId}
                            >
                                <option value="">Choose...</option>
                                {schedules.map((schedule) => (
                                    <option key={schedule.id} value={schedule.id}>
                                        {schedule.dayOfWeek} {schedule.startTime} - {schedule.endTime}
                                    </option>
                                ))}
                            </Form.Control>
                        </Form.Group>
                    </Col>
                </Row>
                {students.length > 0 && (
                    <>
                        <h3 className="mb-3">Mark Attendance</h3>
                        {students.map((student) => (
                            <Row key={student.id} className="mb-2 align-items-center">
                                <Col md={4}>{student.firstName} {student.lastName}</Col>
                                <Col md={8}>
                                    <ToggleButtonGroup
                                        type="radio"
                                        name={`attendance-${student.id}`}
                                        value={attendanceData[student.id]}
                                        onChange={(val) => handleAttendanceChange(student.id, val)}
                                    >
                                        <ToggleButton id={`present-${student.id}`} value="PRESENT" variant="outline-success">
                                            Present
                                        </ToggleButton>
                                        <ToggleButton id={`absent-${student.id}`} value="ABSENT" variant="outline-danger">
                                            Absent
                                        </ToggleButton>
                                        <ToggleButton id={`late-${student.id}`} value="LATE" variant="outline-warning">
                                            Late
                                        </ToggleButton>
                                        <ToggleButton id={`excused-${student.id}`} value="EXCUSED" variant="outline-primary">
                                            Excused
                                        </ToggleButton>
                                    </ToggleButtonGroup>
                                </Col>
                            </Row>
                        ))}
                        <Button variant="primary" type="submit">
                            Submit Attendance
                        </Button>
                    </>
                )}
            </Form>
        </Container>
    );
};

export default TakeAttendance;
