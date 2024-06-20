import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const AssignGrades = () => {
    const [students, setStudents] = useState([]);
    const [classes, setClasses] = useState([]);
    const [selectedStudentId, setSelectedStudentId] = useState('');
    const [selectedClassId, setSelectedClassId] = useState('');
    const [grade, setGrade] = useState(5.0);
    const [date, setDate] = useState(new Date());

    useEffect(() => {
        fetchClasses();
    }, []);

    useEffect(() => {
        if (selectedClassId) {
            fetchStudentsByClass(selectedClassId);
        }
    }, [selectedClassId]);

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

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const gradePayload = {
                studentId: selectedStudentId,
                classId: selectedClassId,
                grade,
                date
            };
            const response = await axios.post('/grades/assign', gradePayload); // Adjust endpoint as necessary
            console.log('Grade assigned successfully:', response.data);
        } catch (error) {
            console.error('Error assigning grade:', error);
        }
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Assign Grades</h1>
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
                                {classes.map((classItem) => (
                                    <option key={classItem.id} value={classItem.id}>
                                        {classItem.name}
                                    </option>
                                ))}
                            </Form.Control>
                        </Form.Group>
                    </Col>
                </Row>
                <Row className="mb-3">
                    <Col md={6}>
                        <Form.Group controlId="formStudent">
                            <Form.Label>Select Student</Form.Label>
                            <Form.Control
                                as="select"
                                value={selectedStudentId}
                                onChange={(e) => setSelectedStudentId(e.target.value)}
                                required
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
                <Row className="mb-3">
                    <Col md={6}>
                        <Form.Group controlId="formDate">
                            <Form.Label>Select Date</Form.Label>
                            <DatePicker
                                selected={date}
                                onChange={(d) => setDate(d)}
                                dateFormat="yyyy-MM-dd"
                                className="form-control"
                                required
                            />
                        </Form.Group>
                    </Col>
                </Row>
                <Row className="mb-3">
                    <Col md={12}>
                        <Form.Group controlId="formGrade">
                            <Form.Label>Assign Grade: {grade.toFixed(1)}</Form.Label>
                            <Form.Control
                                type="range"
                                min="1"
                                max="5"
                                step="0.1"
                                value={grade}
                                onChange={(e) => setGrade(parseFloat(e.target.value))}
                            />
                        </Form.Group>
                    </Col>
                </Row>
                <Button variant="primary" type="submit">
                    Submit Grade
                </Button>
            </Form>
        </Container>
    );
};

export default AssignGrades;
