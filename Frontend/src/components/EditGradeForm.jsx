import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, Modal } from 'react-bootstrap';

const EditGradeForm = ({ gradeId }) => {
    const [gradeData, setGradeData] = useState({
        studentId: '',
        classId: '',
        grade: '',
        date: ''
    });
    const [students, setStudents] = useState([]);
    const [classes, setClasses] = useState([]);
    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);
    const [showDeleteSuccess, setShowDeleteSuccess] = useState(false);
    const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);

    useEffect(() => {
        fetchStudents();
        fetchClasses();
        if (gradeId) {
            fetchGradeData(gradeId);
        }
    }, [gradeId]);

    const fetchStudents = async () => {
        try {
            const response = await axios.get('/student/all');
            setStudents(response.data || []);
        } catch (error) {
            console.error('Error fetching students:', error);
        }
    };

    const fetchClasses = async () => {
        try {
            const response = await axios.get('/class/all');
            setClasses(response.data || []);
        } catch (error) {
            console.error('Error fetching classes:', error);
        }
    };

    const fetchGradeData = async (id) => {
        try {
            const response = await axios.get(`/grades/${id}`);
            const grade = response.data;
            setGradeData({
                studentId: grade.student.id,
                classId: grade.classEntity.id,
                grade: grade.grade,
                date: grade.date
            });
        } catch (error) {
            console.error('Error fetching grade data:', error);
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

                const response = await axios.put(`/grades/${gradeId}`, gradeData, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'X-XSRF-TOKEN': csrfToken,
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Grade updated successfully:', response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000);

                setValidated(false);
            } catch (error) {
                console.error('Error updating grade:', error);
            }
        }
    };

    const handleDeleteConfirm = () => {
        setShowDeleteConfirm(true);
    };

    const handleDeleteCancel = () => {
        setShowDeleteConfirm(false);
    };

    const handleDelete = async () => {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                throw new Error("No token found");
            }

            const csrfToken = document.cookie.split('; ').find(row => row.startsWith('XSRF-TOKEN')).split('=')[1];

            const response = await axios.delete(`/grades/${gradeId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'X-XSRF-TOKEN': csrfToken,
                    'Content-Type': 'application/json'
                }
            });
            console.log(`Grade deleted successfully:`, response.data);

            setShowDeleteSuccess(true);
            setTimeout(() => setShowDeleteSuccess(false), 3000);
            setShowDeleteConfirm(false);

            setGradeData({
                studentId: '',
                classId: '',
                grade: '',
                date: ''
            });
        } catch (error) {
            console.error(`Error deleting grade:`, error);
        }
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Edit Grade</h1>
            {showSuccess && <Alert variant="success">Grade updated successfully!</Alert>}
            {showDeleteSuccess && <Alert variant="danger">Grade deleted successfully!</Alert>}
            <Row className="justify-content-center">
                <Col md={8} lg={6}>
                    <Form noValidate validated={validated} onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="formStudent">
                            <Form.Label>Student</Form.Label>
                            <Form.Control
                                as="select"
                                name="studentId"
                                value={gradeData.studentId}
                                onChange={handleChange}
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
                                as="select"
                                name="classId"
                                value={gradeData.classId}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select Class</option>
                                {Array.isArray(classes) && classes.map(classEntity => (
                                    <option key={classEntity.id} value={classEntity.id}>
                                        {classEntity.name}
                                    </option>
                                ))}
                            </Form.Control>
                            <Form.Control.Feedback type="invalid">
                                Please select a class.
                            </Form.Control.Feedback>
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
                        <Button variant="success" type="submit" className="me-2 mb-4">
                            Update Grade
                        </Button>
                        <Button variant="danger" onClick={handleDeleteConfirm} className="mb-4">
                            Delete Grade
                        </Button>
                    </Form>
                </Col>
            </Row>
            <Modal show={showDeleteConfirm} onHide={handleDeleteCancel}>
                <Modal.Header closeButton>
                    <Modal.Title>Confirm Delete</Modal.Title>
                </Modal.Header>
                <Modal.Body>Are you sure you want to delete this grade?</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleDeleteCancel}>
                        Cancel
                    </Button>
                    <Button variant="danger" onClick={handleDelete}>
                        Delete
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
};

export default EditGradeForm;
