// Version 1.0
import React, { useState } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, InputGroup } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';

const AddTeacherForm = () => {
    const [teacherData, setTeacherData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        birthday: null,
        phone: '',
        address: '',
        gender: ''
    });

    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);
    const [showPassword, setShowPassword] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setTeacherData({ ...teacherData, [name]: value });
    };

    const handleDateChange = (date) => {
        setTeacherData({ ...teacherData, birthday: date });
    };

    const generateUsername = () => {
        const role = "TE"; // Hardcoded role for teacher
        const dob = teacherData.birthday ? teacherData.birthday.toISOString().split('T')[0].replace(/-/g, '') : '';
        const initials = (teacherData.firstName[0] || '') + (teacherData.lastName[0] || '');
        return `${role}${dob}${initials}`.toUpperCase();
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

                const username = generateUsername();
                console.log('Generated Username:', username);

                const response = await axios.post('/teacher/add', { ...teacherData, username }, {
                    headers: {
                        'Authorization': `Bearer ${token}`, // Add Authorization header
                        'X-XSRF-TOKEN': csrfToken, // Add CSRF token header
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Teacher added successfully:', response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000); // Hide success message after 3 seconds

                // Reset form
                setTeacherData({
                    firstName: '',
                    lastName: '',
                    email: '',
                    password: '',
                    birthday: null,
                    phone: '',
                    address: '',
                    gender: ''
                });
                setValidated(false);
            } catch (error) {
                console.error('Error adding teacher:', error);
            }
        }
    };

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Add Teacher</h1>
            {showSuccess && <Alert variant="success">Teacher added successfully!</Alert>}
            <Row className="justify-content-center">
                <Col md={8} lg={6}>
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
                                    <div className="custom-datepicker">
                                        <DatePicker
                                            selected={teacherData.birthday}
                                            onChange={handleDateChange}
                                            dateFormat="yyyy-MM-dd"
                                            className="form-control"
                                            name="birthday"
                                            required
                                        />
                                    </div>
                                    <div className="invalid-feedback">
                                        Please provide a valid birthday.
                                    </div>
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
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Please provide a valid email address.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </Col>
                            <Col md={6}>
                                <Form.Group className="mb-3" controlId="formPassword">
                                    <Form.Label>Password</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type={showPassword ? "text" : "password"}
                                            name="password"
                                            value={teacherData.password}
                                            onChange={handleChange}
                                            required
                                            minLength="6"
                                        />
                                        <Button variant="outline-secondary" onClick={togglePasswordVisibility}>
                                            <FontAwesomeIcon icon={showPassword ? faEyeSlash : faEye} />
                                        </Button>
                                        <Form.Control.Feedback type="invalid">
                                            Please provide a password with at least 6 characters.
                                        </Form.Control.Feedback>
                                    </InputGroup>
                                </Form.Group>
                            </Col>
                        </Row>
                        <Button variant="success" type="submit" className="mb-4">
                            Add Teacher
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default AddTeacherForm;
