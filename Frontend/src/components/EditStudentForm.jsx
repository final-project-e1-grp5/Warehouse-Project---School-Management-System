import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, InputGroup, ListGroup, Modal } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const EditStudentForm = () => {
    const [searchQuery, setSearchQuery] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [selectedStudentId, setSelectedStudentId] = useState(null);
    const [studentData, setStudentData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        password: '********', // Placeholder for password
        birthday: null,
        phone: '',
        address: '',
        gender: '',
        role: 'STUDENT'
    });
    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);
    const [showDeleteSuccess, setShowDeleteSuccess] = useState(false);
    const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);

    useEffect(() => {
        if (selectedStudentId) {
            fetchStudentData(selectedStudentId);
        }
    }, [selectedStudentId]);

    const fetchStudentData = async (id) => {
        try {
            const response = await axios.get(`/student/${id}`);
            const student = response.data;
            setStudentData({
                firstName: student.firstName,
                lastName: student.lastName,
                email: student.email,
                username: student.username,
                password: '********',
                birthday: new Date(student.birthday),
                phone: student.phone,
                address: student.address,
                gender: student.gender,
                role: student.role
            });
        } catch (error) {
            console.error('Error fetching student data:', error);
        }
    };

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
    };

    const handleSearch = async () => {
        try {
            const response = await axios.get(`/student?search=${searchQuery}`);
            setSearchResults(response.data);
        } catch (error) {
            console.error('Error searching for student:', error);
        }
    };

    const handleViewAll = async () => {
        try {
            const response = await axios.get(`/student`);
            setSearchResults(response.data);
        } catch (error) {
            console.error('Error fetching all students:', error);
        }
    };

    const handleStudentSelect = (student) => {
        setSelectedStudentId(student.id);
        setStudentData({
            firstName: student.firstName,
            lastName: student.lastName,
            email: student.email,
            username: student.username,
            password: '********',
            birthday: new Date(student.birthday),
            phone: student.phone,
            address: student.address,
            gender: student.gender,
            role: student.role
        });
        setSearchResults([]);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setStudentData({ ...studentData, [name]: value });
    };

    const handleDateChange = (date) => {
        setStudentData({ ...studentData, birthday: date });
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

                const updatedStudentData = { ...studentData };
                if (updatedStudentData.password === '********') {
                    delete updatedStudentData.password;
                }

                console.log('Updating student with data:', updatedStudentData);

                const response = await axios.put(`/student/${selectedStudentId}`, updatedStudentData, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'X-XSRF-TOKEN': csrfToken,
                        'Content-Type': 'application/json'
                    }
                });
                console.log(`Student updated successfully:`, response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000);
            } catch (error) {
                console.error(`Error updating student:`, error);
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

            const response = await axios.delete(`/student/${selectedStudentId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'X-XSRF-TOKEN': csrfToken,
                    'Content-Type': 'application/json'
                }
            });
            console.log(`Student deleted successfully:`, response.data);

            setSelectedStudentId(null);
            setStudentData({
                firstName: '',
                lastName: '',
                email: '',
                username: '',
                password: '',
                birthday: null,
                phone: '',
                address: '',
                gender: '',
                role: 'STUDENT'
            });

            setShowDeleteSuccess(true);
            setTimeout(() => setShowDeleteSuccess(false), 3000);
            setShowDeleteConfirm(false);
        } catch (error) {
            console.error(`Error deleting student:`, error);
        }
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Edit Student</h1>
            <Row className="justify-content-center mb-4">
                <Col md={8} lg={6}>
                    <InputGroup>
                        <Form.Control
                            type="text"
                            placeholder="Search by name, username or email"
                            value={searchQuery}
                            onChange={handleSearchChange}
                        />
                        <Button variant="primary" onClick={handleSearch}>
                            Search
                        </Button>
                        <Button variant="secondary" onClick={handleViewAll}>
                            View All
                        </Button>
                    </InputGroup>
                    {searchResults.length > 0 && (
                        <ListGroup className="mt-2">
                            {searchResults.map(student => (
                                <ListGroup.Item
                                    key={student.id}
                                    action
                                    onClick={() => handleStudentSelect(student)}
                                    active={student.id === selectedStudentId}
                                    className={student.id === selectedStudentId ? 'bg-primary text-white' : 'bg-light'}
                                >
                                    <div><strong>Name:</strong> {student.firstName} {student.lastName}</div>
                                    <div><strong>Username:</strong> {student.username}</div>
                                    <div><strong>Email:</strong> {student.email}</div>
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    )}
                </Col>
            </Row>
            {selectedStudentId && (
                <Row className="justify-content-center">
                    <Col md={8} lg={6}>
                        {showSuccess && <Alert variant="success">Student updated successfully!</Alert>}
                        {showDeleteSuccess && <Alert variant="danger">Student deleted successfully!</Alert>}
                        <Form noValidate validated={validated} onSubmit={handleSubmit}>
                            <Row>
                                <Col md={6}>
                                    <Form.Group className="mb-3" controlId="formFirstName">
                                        <Form.Label>First Name</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name="firstName"
                                            value={studentData.firstName}
                                            onChange={handleChange}
                                            required
                                            maxLength="50"
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            Please provide a valid first name.
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                </Col>
                                <Col md={6}>
                                    <Form.Group className="mb-3" controlId="formLastName">
                                        <Form.Label>Last Name</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name="lastName"
                                            value={studentData.lastName}
                                            onChange={handleChange}
                                            required
                                            maxLength="50"
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            Please provide a valid last name.
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                </Col>
                            </Row>
                            <Row>
                                <Col md={6}>
                                    <Form.Group className="mb-3" controlId="formBirthday">
                                        <Form.Label>Birthday</Form.Label>
                                        <DatePicker
                                            selected={studentData.birthday}
                                            onChange={handleDateChange}
                                            dateFormat="yyyy-MM-dd"
                                            className="form-control"
                                            maxDate={new Date()}
                                            showYearDropdown
                                            showMonthDropdown
                                            dropdownMode="select"
                                            required
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            Please provide a valid birthday.
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                </Col>
                                <Col md={6}>
                                    <Form.Group className="mb-3" controlId="formGender">
                                        <Form.Label>Gender</Form.Label>
                                        <Form.Control
                                            as="select"
                                            name="gender"
                                            value={studentData.gender}
                                            onChange={handleChange}
                                            required
                                        >
                                            <option value="">Select Gender</option>
                                            <option value="MALE">Male</option>
                                            <option value="FEMALE">Female</option>
                                            <option value="DIVERSE">Diverse</option>
                                        </Form.Control>
                                        <Form.Control.Feedback type="invalid">
                                            Please select a gender.
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                </Col>
                            </Row>
                            <Row>
                                <Col md={6}>
                                    <Form.Group className="mb-3" controlId="formAddress">
                                        <Form.Label>Address</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name="address"
                                            value={studentData.address}
                                            onChange={handleChange}
                                            required
                                            maxLength="100"
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            Please provide a valid address.
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                </Col>
                                <Col md={6}>
                                    <Form.Group className="mb-3" controlId="formPhone">
                                        <Form.Label>Phone</Form.Label>
                                        <Form.Control
                                            type="tel"
                                            name="phone"
                                            value={studentData.phone}
                                            onChange={handleChange}
                                            required
                                            pattern="^\d*$"
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            Please provide a valid phone number.
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                </Col>
                            </Row>
                            <Row>
                                <Col md={6}>
                                    <Form.Group className="mb-3" controlId="formEmail">
                                        <Form.Label>Email</Form.Label>
                                        <Form.Control
                                            type="email"
                                            name="email"
                                            value={studentData.email}
                                            onChange={handleChange}
                                            required
                                            maxLength="100"
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            Please provide a valid email.
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                </Col>
                                <Col md={6}>
                                    <Form.Group className="mb-3" controlId="formUsername">
                                        <Form.Label>Username</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name="username"
                                            value={studentData.username}
                                            readOnly
                                            maxLength="50"
                                            style={{ backgroundColor: '#e9ecef' }}
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            Please provide a valid username.
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                </Col>
                            </Row>
                            <Button variant="primary" type="submit" className="me-2 mb-4">
                                Update
                            </Button>
                            <Button variant="danger" onClick={handleDeleteConfirm} className="me-2 mb-4">
                                Delete
                            </Button>
                        </Form>
                    </Col>
                </Row>
            )}
            <Modal show={showDeleteConfirm} onHide={handleDeleteCancel}>
                <Modal.Header closeButton>
                    <Modal.Title>Confirm Delete</Modal.Title>
                </Modal.Header>
                <Modal.Body>Are you sure you want to delete this student?</Modal.Body>
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

export default EditStudentForm;
