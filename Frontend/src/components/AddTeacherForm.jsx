// Version 1.7
import React, {useState} from 'react';
import axios from 'axios';
import {Form, Button, Container, Alert, Row, Col, InputGroup} from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faEye, faEyeSlash} from '@fortawesome/free-solid-svg-icons';

const AddTeacherForm = () => {
    const [teacherData, setTeacherData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        dateOfBirth: null,
        phoneNumber: '',
        address: '',
        role: 'teacher',
        enabled: true,
        username: '',
        subject: ''
    });

    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);
    const [showPassword, setShowPassword] = useState(false);

    const handleChange = (e) => {
        const {name, value} = e.target;
        setTeacherData({...teacherData, [name]: value});
    };

    const handleDateChange = (date) => {
        setTeacherData({...teacherData, dateOfBirth: date});
    };

    const generateUsername = () => {
        const role = "TE"; // Hardcoded role for teacher
        const dob = teacherData.dateOfBirth ? teacherData.dateOfBirth.toISOString().split('T')[0].replace(/-/g, '') : '';
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
                const token = localStorage.getItem('token');
                if (!token) {
                    throw new Error("No token found");
                }

                const username = generateUsername();
                console.log('Generated Username:', username);

                const response = await axios.post('/admin/teacher/add', {
                    ...teacherData,
                    username
                }, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Teacher added successfully:', response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000);

                // Reset form
                setTeacherData({
                    firstName: '',
                    lastName: '',
                    email: '',
                    password: '',
                    dateOfBirth: null,
                    phoneNumber: '',
                    address: '',
                    role: 'teacher',
                    enabled: true,
                    username: '',
                    subject: ''
                });
                setValidated(false);
            } catch (error) {
                console.error('Error adding teacher:', error);
                if (error.response) {
                    console.error('Error response data:', error.response.data);
                }
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
                                <Form.Group className="mb-3" controlId="formDateOfBirth">
                                    <Form.Label>Date of Birth</Form.Label>
                                    <div className="custom-datepicker">
                                        <DatePicker
                                            selected={teacherData.dateOfBirth}
                                            onChange={handleDateChange}
                                            dateFormat="yyyy-MM-dd"
                                            className="form-control"
                                            name="dateOfBirth"
                                            required
                                        />
                                    </div>
                                    <div className="invalid-feedback">
                                        Please provide a valid date of birth.
                                    </div>
                                </Form.Group>
                            </Col>
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
                        </Row>
                        <Row>
                            <Col md={6}>
                                <Form.Group className="mb-3" controlId="formPhoneNumber">
                                    <Form.Label>Phone Number</Form.Label>
                                    <Form.Control
                                        type="tel"
                                        name="phoneNumber"
                                        value={teacherData.phoneNumber}
                                        onChange={handleChange}
                                        required
                                        pattern="^\d*$"
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Please provide a valid phone number.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </Col>
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
                        </Row>
                        <Row>
                            <Col md={6}>
                                <Form.Group className="mb-3" controlId="formSubject">
                                    <Form.Label>Subject</Form.Label>
                                    <Form.Control
                                        as="select"
                                        name="subject"
                                        value={teacherData.subject}
                                        onChange={handleChange}
                                        required
                                    >
                                        <option value="">Select Subject</option>
                                        <option value="Math">Math</option>
                                        <option value="Science">Science</option>
                                        <option value="English">English</option>
                                        <option value="History">History</option>
                                        <option value="Geography">Geography</option>
                                        <option value="Physics">Physics</option>
                                        <option value="Chemistry">Chemistry</option>
                                        <option value="Biology">Biology</option>
                                        <option value="Physical Education">Physical Education</option>
                                        <option value="Art">Art</option>
                                    </Form.Control>
                                    <Form.Control.Feedback type="invalid">
                                        Please select a subject.
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
                                            <FontAwesomeIcon icon={showPassword ? faEyeSlash : faEye}/>
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
