import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col } from 'react-bootstrap';

const AddGradeForm = () => {
    const [gradeData, setGradeData] = useState({
        studentId: '',
        classId: '',
        grade: '',
        date: ''
    });
    const [students, setStudents] = useState([]);
    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);

    useEffect(() => {
        fetchStudents();
    }, []);

    const fetchStudents = async () => {
        try {
            const response = await axios.get('/student/all');
            setStudents(response.data || []);
        } catch (error) {
            console.error('Error fetching students:', error);
        }
    };

    const handleStudentChange = async (e) => {
        const studentId = e.target.value;
        setGradeData({ ...gradeData, studentId });

        if (studentId) {
            try {
                const response = await axios.get(`/student/${studentId}`);
                const student = response.data;
                setGradeData({
                    ...gradeData,
                    studentId,
                    classId: student.classEntity.id
                });
            } catch (error) {
                console.error('Error fetching student data:', error);
            }
        } else {
            setGradeData({
                ...gradeData,
                classId: ''
            });
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setGradeData({ ...gradeData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const form = e.currentTarget;
        if (form.checkValidity() === false) {
            e.stopPropagation();
            setValidated(true);
        } else {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    throw new Error("No token found");
                }

                const csrfToken = document.cookie.split('; ').find(row => row.startsWith('XSRF-TOKEN')).split('=')[1];

                const response = await axios.post('/grades/assign', gradeData, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'X-XSRF-TOKEN': csrfToken,
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Grade added successfully:', response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000);

                // Reset form
                setGradeData({
                    studentId: '',
                    classId: '',
                    grade: '',
                    date: ''
                });
                setValidated(false);
            } catch (error) {
                console.error('Error adding grade:', error);
            }
        }
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Add Grade</h1>
            {showSuccess && <Alert variant="success">Grade added successfully!</Alert>}
            <Row className="justify-content-center">
                <Col md={8} lg={6}>
                    <Form noValidate validated={validated} onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="formStudent">
                            <Form.Label>Student</Form.Label>
                            <Form.Control
                                as="select"
                                name="studentId"
                                value={gradeData.studentId}
                                onChange={handleStudentChange}
                                required
                            >
                                <option value="">Select Student</option>
                                {Array.isArray(students) && students.map(student => (
                                    <option key={student.id} value={student.id}>
                                        {student.firstName} {student.lastName}
                                    </option>
                                ))}
                            </Form.Control>
                            <Form.Control.Feedback type="invalid">
                                Please select a student.
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formClass">
                            <Form.Label>Class</Form.Label>
                            <Form.Control
                                type="text"
                                name="classId"
                                value={gradeData.classId}
                                readOnly
                                className="bg-light"
                            />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formGrade">
                            <Form.Label>Grade</Form.Label>
                            <Form.Control
                                type="text"
                                name="grade"
                                value={gradeData.grade}
                                onChange={handleChange}
                                required
                                maxLength="10"
                            />
                            <Form.Control.Feedback type="invalid">
                                Please provide a valid grade.
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formDate">
                            <Form.Label>Date</Form.Label>
                            <Form.Control
                                type="date"
                                name="date"
                                value={gradeData.date}
                                onChange={handleChange}
                                required
                            />
                            <Form.Control.Feedback type="invalid">
                                Please provide a valid date.
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Button variant="success" type="submit" className="w-100 mt-3 mb-4">
                            Add Grade
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default AddGradeForm;
