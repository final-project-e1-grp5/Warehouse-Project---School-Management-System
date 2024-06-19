import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, ListGroup, InputGroup } from 'react-bootstrap';
import './AddClassForm.css';

const AddClassForm = () => {
    const [className, setClassName] = useState('');
    const [teacherId, setTeacherId] = useState('');
    const [teachers, setTeachers] = useState([]);
    const [students, setStudents] = useState([]);
    const [filteredStudents, setFilteredStudents] = useState([]);
    const [selectedStudents, setSelectedStudents] = useState(new Set());
    const [searchQuery, setSearchQuery] = useState('');
    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);
    const [flashyStudent, setFlashyStudent] = useState(null);

    useEffect(() => {
        fetchTeachers();
        fetchStudents();
    }, []);

    const fetchTeachers = async () => {
        try {
            const response = await axios.get('/teacher/all');
            setTeachers(response.data || []);  // Ensure that teachers is an array
        } catch (error) {
            console.error('Error fetching teachers:', error);
        }
    };

    const fetchStudents = async () => {
        try {
            const response = await axios.get('/student/all');
            setStudents(response.data || []);  // Ensure that students is an array
            setFilteredStudents(response.data || []);
        } catch (error) {
            console.error('Error fetching students:', error);
        }
    };

    const handleSearchChange = (e) => {
        const query = e.target.value;
        setSearchQuery(query);
        const filtered = students.filter(student =>
            (student.firstName.toLowerCase().includes(query.toLowerCase()) ||
                student.lastName.toLowerCase().includes(query.toLowerCase()) ||
                student.email.toLowerCase().includes(query.toLowerCase()) ||
                student.username.toLowerCase().includes(query.toLowerCase())) &&
            !selectedStudents.has(student.id)
        );
        setFilteredStudents(filtered);
    };

    const handleSelectStudent = (studentId) => {
        const newSelectedStudents = new Set(selectedStudents);
        if (newSelectedStudents.has(studentId)) {
            newSelectedStudents.delete(studentId);
        } else {
            newSelectedStudents.add(studentId);
        }
        setSelectedStudents(newSelectedStudents);
        setFilteredStudents(students.filter(student => !newSelectedStudents.has(student.id) && (student.firstName.toLowerCase().includes(searchQuery.toLowerCase()) || student.lastName.toLowerCase().includes(searchQuery.toLowerCase()) || student.email.toLowerCase().includes(searchQuery.toLowerCase()) || student.username.toLowerCase().includes(searchQuery.toLowerCase()))));

        // Set flashyStudent for visual feedback
        setFlashyStudent(studentId);
        setTimeout(() => setFlashyStudent(null), 500); // Remove flashyStudent after 500ms
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === 'className') {
            setClassName(value);
        } else if (name === 'teacherId') {
            setTeacherId(value);
        }
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

                const response = await axios.post('/class/add', {
                    name: className,
                    teacher: { id: teacherId },
                    studentIds: Array.from(selectedStudents)
                }, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'X-XSRF-TOKEN': csrfToken,
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Class added successfully:', response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000);

                // Reset form
                setClassName('');
                setTeacherId('');
                setSelectedStudents(new Set());
                setSearchQuery('');
                setFilteredStudents(students.filter(student => !selectedStudents.has(student.id)));
                setValidated(false);
            } catch (error) {
                console.error('Error adding class:', error);
            }
        }
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Add Class</h1>
            {showSuccess && <Alert variant="success">Class added successfully!</Alert>}
            <Row className="justify-content-center">
                <Col md={8} lg={6}>
                    <Form noValidate validated={validated} onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="formClassName">
                            <Form.Label>Class Name</Form.Label>
                            <Form.Control
                                type="text"
                                name="className"
                                value={className}
                                onChange={handleChange}
                                required
                                maxLength="100"
                            />
                            <Form.Control.Feedback type="invalid">
                                Please provide a valid class name.
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="mb3" controlId="formTeacher">
                            <Form.Label>Teacher</Form.Label>
                            <Form.Control
                                as="select"
                                name="teacherId"
                                value={teacherId}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select Teacher</option>
                                {Array.isArray(teachers) && teachers.map(teacher => (
                                    <option key={teacher.id} value={teacher.id}>
                                        {teacher.firstName} {teacher.lastName}
                                    </option>
                                ))}
                            </Form.Control>
                            <Form.Control.Feedback type="invalid">
                                Please select a teacher.
                            </Form.Control.Feedback>
                        </Form.Group>

                        <h5 className="mb-3">Add Students</h5>
                        <InputGroup className="mb-3">
                            <Form.Control
                                type="text"
                                placeholder="Search by name, email or username"
                                value={searchQuery}
                                onChange={handleSearchChange}
                            />
                        </InputGroup>
                        <ListGroup>
                            {searchQuery !== '' && filteredStudents.map(student => (
                                <ListGroup.Item
                                    key={student.id}
                                    className={`d-flex justify-content-between align-items-center stretched-link ${flashyStudent === student.id ? 'flashy' : ''}`}
                                    onClick={() => handleSelectStudent(student.id)}
                                >
                                    <div>
                                        <strong>{student.firstName} {student.lastName}</strong> ({student.username})<br />
                                        {student.email}
                                    </div>
                                </ListGroup.Item>
                            ))}
                        </ListGroup>

                        <h5 className="mt-4 mb-3">Selected Students</h5>
                        <ListGroup className="mb-3">
                            {Array.from(selectedStudents).map(studentId => {
                                const student = students.find(s => s.id === studentId);
                                return (
                                    <ListGroup.Item
                                        key={student.id}
                                        className={`d-flex justify-content-between align-items-center bg-primary text-white stretched-link ${flashyStudent === student.id ? 'flashy' : ''}`}
                                        onClick={() => handleSelectStudent(student.id)}
                                    >
                                        <div>
                                            <strong>{student.firstName} {student.lastName}</strong> ({student.username})<br />
                                            {student.email}
                                        </div>
                                    </ListGroup.Item>
                                );
                            })}
                        </ListGroup>

                        <Button variant="success" type="submit" className="w-100 mt-3 mb-4">
                            Add Class
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default AddClassForm;
