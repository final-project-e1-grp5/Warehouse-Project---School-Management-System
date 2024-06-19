import React, {useState} from 'react';
import axios from 'axios';
import {Form, Button, Container, Alert, Row, Col, InputGroup} from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faEye, faEyeSlash} from '@fortawesome/free-solid-svg-icons';

const AddAdminForm = () => {
    const [adminData, setAdminData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        dateOfBirth: null,
        phoneNumber: '',
        address: '',
        role: 'admin',
        enabled: false,
        username: ''
    });

    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);
    const [showPassword, setShowPassword] = useState(false);

    const handleChange = (e) => {
        const {name, value} = e.target;
        setAdminData({...adminData, [name]: value});
    };

    const handleDateChange = (date) => {
        setAdminData({...adminData, dateOfBirth: date});
    };

    const generateUsername = () => {
        const role = "AD"; // Hardcoded role for admin
        const dob = adminData.dateOfBirth ? adminData.dateOfBirth.toISOString().split('T')[0].replace(/-/g, '') : '';
        const initials = (adminData.firstName[0] || '') + (adminData.lastName[0] || '');
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

                const csrfToken = document.cookie.split('; ').find(row => row.startsWith('XSRF-TOKEN')).split('=')[1]; // Get CSRF token from cookie

                const username = generateUsername();
                console.log('Generated Username:', username);

                const response = await axios.post('http://localhost:7777/admin', {
                    ...adminData,
                    username
                }, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'X-XSRF-TOKEN': csrfToken,
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Admin added successfully:', response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000);

                // Reset form
                setAdminData({
                    firstName: '',
                    lastName: '',
                    email: '',
                    password: '',
                    dateOfBirth: null,
                    phoneNumber: '',
                    address: '',
                    role: 'admin',
                    enabled: false,
                    username: ''
                });
                setValidated(false);
            } catch (error) {
                console.error('Error adding admin:', error);
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
            <h1 className="mb-4 text-center">Add Admin</h1>
            {showSuccess && <Alert variant="success">Admin added successfully!</Alert>}
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
                                        value={adminData.firstName}
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
                                        value={adminData.lastName}
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
                                            selected={adminData.dateOfBirth}
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
                                        value={adminData.address}
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
                                        value={adminData.phoneNumber}
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
                                        value={adminData.email}
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
                                <Form.Group className="mb-3" controlId="formPassword">
                                    <Form.Label>Password</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type={showPassword ? "text" : "password"}
                                            name="password"
                                            value={adminData.password}
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
                            Add Admin
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default AddAdminForm;
