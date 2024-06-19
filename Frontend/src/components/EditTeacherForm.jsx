import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, InputGroup, ListGroup, Modal } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const EditTeacherForm = () => {
    const [searchQuery, setSearchQuery] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [selectedTeacherId, setSelectedTeacherId] = useState(null);
    const [teacherData, setTeacherData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        password: '********', // Placeholder for password
        birthday: null,
        phone: '',
        address: '',
        gender: '',
        role: 'TEACHER' // Assuming the role is always TEACHER for this form
    });
    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);
    const [showDeleteSuccess, setShowDeleteSuccess] = useState(false);
    const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);

    useEffect(() => {
        if (selectedTeacherId) {
            fetchTeacherData(selectedTeacherId);
        }
    }, [selectedTeacherId]);

    const fetchTeacherData = async (id) => {
        try {
            const response = await axios.get(`/teacher/${id}`);
            const teacher = response.data;
            setTeacherData({
                firstName: teacher.firstName,
                lastName: teacher.lastName,
                email: teacher.email,
                username: teacher.username,
                password: '********', // Placeholder for password
                birthday: new Date(teacher.birthday),
                phone: teacher.phone,
                address: teacher.address,
                gender: teacher.gender,
                role: teacher.role // Assuming the role is available from the backend
            });
        } catch (error) {
            console.error('Error fetching teacher data:', error);
        }
    };

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
    };

    const handleSearch = async () => {
        try {
            const response = await axios.get(`/teacher?search=${searchQuery}`);
            if (Array.isArray(response.data)) {
                setSearchResults(response.data);
            } else {
                setSearchResults([]);
            }
        } catch (error) {
            console.error('Error searching for teacher:', error);
            setSearchResults([]);
        }
    };

    const handleViewAll = async () => {
        try {
            const response = await axios.get(`/teacher`);
            if (Array.isArray(response.data)) {
                setSearchResults(response.data);
            } else {
                setSearchResults([]);
            }
        } catch (error) {
            console.error('Error fetching all teachers:', error);
            setSearchResults([]);
        }
    };

    const handleTeacherSelect = (teacher) => {
        setSelectedTeacherId(teacher.id);
        setTeacherData({
            firstName: teacher.firstName,
            lastName: teacher.lastName,
            email: teacher.email,
            username: teacher.username,
            password: '********', // Placeholder for password
            birthday: new Date(teacher.birthday),
            phone: teacher.phone,
            address: teacher.address,
            gender: teacher.gender,
            role: teacher.role // Assuming the role is available from the backend
        });
        setSearchResults([]);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        const updatedTeacherData = { ...teacherData, [name]: value };

        if (name === 'firstName' || name === 'lastName' || name === 'birthday') {
            updatedTeacherData.username = generateUsername(updatedTeacherData);
        }

        setTeacherData(updatedTeacherData);
    };

    const handleDateChange = (date) => {
        const updatedTeacherData = { ...teacherData, birthday: date };
        updatedTeacherData.username = generateUsername(updatedTeacherData);
        setTeacherData(updatedTeacherData);
    };

    const generateUsername = ({ firstName, lastName, birthday }) => {
        if (!firstName || !lastName || !birthday) {
            return '';
        }
        const rolePrefix = 'ST';
        const initials = firstName.charAt(0) + lastName.charAt(0);
        const datePart = birthday.toISOString().split('T')[0].replace(/-/g, '');
        return `${rolePrefix}${datePart}${initials.toUpperCase()}`;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const form = e.currentTarget;
        if (form.checkValidity() === false) {
            e.stopPropagation();
            setValidated(true);
        } else {
            try {
                const token = localStorage.getItem('token'); // Ensure the token is retrieved correctly
                if (!token) {
                    throw new Error("No token found");
                }

                const csrfToken = document.cookie.split('; ').find(row => row.startsWith('XSRF-TOKEN')).split('=')[1]; // Get CSRF token from cookie

                // Create a copy of teacherData without the placeholder password
                const updatedTeacherData = { ...teacherData };
                if (updatedTeacherData.password === '********') {
                    delete updatedTeacherData.password;
                }

                const response = await axios.put(`/teacher/${selectedTeacherId}`, updatedTeacherData, {
                    headers: {
                        'Authorization': `Bearer ${token}`, // Add Authorization header
                        'X-XSRF-TOKEN': csrfToken, // Add CSRF token header
                        'Content-Type': 'application/json'
                    }
                });
                console.log(`Teacher updated successfully:`, response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000); // Hide success message after 3 seconds
            } catch (error) {
                console.error(`Error updating teacher:`, error);
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
            const token = localStorage.getItem('token'); // Ensure the token is retrieved correctly
            if (!token) {
                throw new Error("No token found");
            }

            const csrfToken = document.cookie.split('; ').find(row => row.startsWith('XSRF-TOKEN')).split('=')[1]; // Get CSRF token from cookie

            const response = await axios.delete(`/teacher/${selectedTeacherId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`, // Add Authorization header
                    'X-XSRF-TOKEN': csrfToken, // Add CSRF token header
                    'Content-Type': 'application/json'
                }
            });
            console.log(`Teacher deleted successfully:`, response.data);

            setSelectedTeacherId(null);
            setTeacherData({
                firstName: '',
                lastName: '',
                email: '',
                username: '',
                password: '',
                birthday: null,
                phone: '',
                address: '',
                gender: '',
                role: 'TEACHER'
            });

            setShowDeleteSuccess(true);
            setTimeout(() => setShowDeleteSuccess(false), 3000);
            setShowDeleteConfirm(false);
        } catch (error) {
            console.error(`Error deleting teacher:`, error);
        }
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Edit Teacher</h1>
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
                    {Array.isArray(searchResults) && searchResults.length > 0 && (
                        <ListGroup className="mt-2">
                            {searchResults.map(teacher => (
                                <ListGroup.Item
                                    key={teacher.id}
                                    action
                                    onClick={() => handleTeacherSelect(teacher)}
                                    active={teacher.id === selectedTeacherId}
                                    className={teacher.id === selectedTeacherId ? 'bg-primary text-white' : 'bg-light'}
                                >
                                    <div><strong>Name:</strong> {teacher.firstName} {teacher.lastName}</div>
                                    <div><strong>Username:</strong> {teacher.username}</div>
                                    <div><strong>Email:</strong> {teacher.email}</div>
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    )}
                </Col>
            </Row>
            {selectedTeacherId && (
                <Row className="justify-content-center">
                    <Col md={8} lg={6}>
                        {showSuccess && <Alert variant="success">Teacher updated successfully!</Alert>}
                        {showDeleteSuccess && <Alert variant="danger">Teacher deleted successfully!</Alert>}
                        <Form noValidate validated={validated} onSubmit={handleSubmit}>
                            <Row>
                                <Col md={6}>
                                    <Form.Group className="mb-3" controlId="formFirstName">
                                        <Form.Label>First Name</Form.Label>
                                        <Form.Control
                                            type="text"
                                            name="firstName"
                                            value={teacherData.firstName}
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
                                            value={teacherData.lastName}
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
                                            selected={teacherData.birthday}
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
                                            value={teacherData.gender}
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
                                            value={teacherData.address}
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
                                            value={teacherData.phone}
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
                                            value={teacherData.email}
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
                                            value={teacherData.username}
                                            readOnly
                                            maxLength="50"
                                            style={{ backgroundColor: '#e9ecef' }} // Grey out the username field
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
                <Modal.Body>Are you sure you want to delete this teacher?</Modal.Body>
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

export default EditTeacherForm;
