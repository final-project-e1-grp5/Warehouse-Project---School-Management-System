import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, InputGroup } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';

const EditProfileForm = () => {
    const [userData, setUserData] = useState({
        address: '',
        phone: '',
        email: '',
        oldPassword: '',
        newPassword: ''
    });
    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);
    const [showOldPassword, setShowOldPassword] = useState(false);
    const [showNewPassword, setShowNewPassword] = useState(false);

    useEffect(() => {
        fetchUserData();
    }, []);

    const fetchUserData = async () => {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                throw new Error("No token found");
            }

            const response = await axios.get('/user/profile', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            const user = response.data;
            setUserData({
                address: user.address,
                phone: user.phone,
                email: user.email,
                oldPassword: '',
                newPassword: ''
            });
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUserData({ ...userData, [name]: value });
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

                const updateData = {
                    address: userData.address,
                    phone: userData.phone,
                    email: userData.email,
                    oldPassword: userData.oldPassword,
                };

                if (userData.newPassword) {
                    updateData.newPassword = userData.newPassword;
                }

                const response = await axios.put('/user/edit-profile', updateData, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'X-XSRF-TOKEN': csrfToken,
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Profile updated successfully:', response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000);
                setValidated(false);
            } catch (error) {
                console.error('Error updating profile:', error);
            }
        }
    };

    const toggleOldPasswordVisibility = () => {
        setShowOldPassword(!showOldPassword);
    };

    const toggleNewPasswordVisibility = () => {
        setShowNewPassword(!showNewPassword);
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Edit Profile</h1>
            {showSuccess && <Alert variant="success">Profile updated successfully!</Alert>}
            <Row className="justify-content-center">
                <Col md={8} lg={6}>
                    <Form noValidate validated={validated} onSubmit={handleSubmit}>
                        <Row>
                            <Col md={12}>
                                <Form.Group className="mb-3" controlId="formAddress">
                                    <Form.Label>Address</Form.Label>
                                    <Form.Control
                                        type="text"
                                        name="address"
                                        value={userData.address}
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
                            <Col md={12}>
                                <Form.Group className="mb-3" controlId="formPhone">
                                    <Form.Label>Phone</Form.Label>
                                    <Form.Control
                                        type="tel"
                                        name="phone"
                                        value={userData.phone}
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
                            <Col md={12}>
                                <Form.Group className="mb-3" controlId="formEmail">
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control
                                        type="email"
                                        name="email"
                                        value={userData.email}
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
                            <Col md={12}>
                                <Form.Group className="mb-3" controlId="formOldPassword">
                                    <Form.Label>Old Password</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type={showOldPassword ? "text" : "password"}
                                            name="oldPassword"
                                            value={userData.oldPassword}
                                            onChange={handleChange}
                                            required
                                            minLength="6"
                                        />
                                        <Button variant="outline-secondary" onClick={toggleOldPasswordVisibility}>
                                            <FontAwesomeIcon icon={showOldPassword ? faEyeSlash : faEye} />
                                        </Button>
                                        <Form.Control.Feedback type="invalid">
                                            Please provide your current password.
                                        </Form.Control.Feedback>
                                    </InputGroup>
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row>
                            <Col md={12}>
                                <Form.Group className="mb-3" controlId="formNewPassword">
                                    <Form.Label>New Password</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type={showNewPassword ? "text" : "password"}
                                            name="newPassword"
                                            value={userData.newPassword}
                                            onChange={handleChange}
                                            minLength="6"
                                        />
                                        <Button variant="outline-secondary" onClick={toggleNewPasswordVisibility}>
                                            <FontAwesomeIcon icon={showNewPassword ? faEyeSlash : faEye} />
                                        </Button>
                                        <Form.Control.Feedback type="invalid">
                                            Please provide a password with at least 6 characters.
                                        </Form.Control.Feedback>
                                    </InputGroup>
                                </Form.Group>
                            </Col>
                        </Row>
                        <Button variant="success" type="submit" className="mb-4">
                            Update Profile
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default EditProfileForm;
