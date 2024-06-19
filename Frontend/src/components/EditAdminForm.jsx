import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, InputGroup, ListGroup } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const EditAdminForm = () => {
    const [searchQuery, setSearchQuery] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [selectedAdminId, setSelectedAdminId] = useState(null);
    const [adminData, setAdminData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        password: '********', // Placeholder for password
        birthday: null,
        phone: '',
        address: '',
        gender: '',
        role: 'ADMIN' // Assuming the role is always ADMIN for this form
    });
    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);

    useEffect(() => {
        if (selectedAdminId) {
            fetchAdminData(selectedAdminId);
        }
    }, [selectedAdminId]);

    const fetchAdminData = async (id) => {
        try {
            const response = await axios.get(`/admin/${id}`);
            const admin = response.data;
            setAdminData({
                firstName: admin.firstName,
                lastName: admin.lastName,
                email: admin.email,
                username: admin.username,
                password: '********', // Placeholder for password
                birthday: new Date(admin.birthday),
                phone: admin.phone,
                address: admin.address,
                gender: admin.gender,
                role: admin.role // Assuming the role is available from the backend
            });
        } catch (error) {
            console.error('Error fetching admin data:', error);
        }
    };

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
    };

    const handleSearch = async () => {
        try {
            const response = await axios.get(`/admin?search=${searchQuery}`);
            setSearchResults(response.data);
        } catch (error) {
            console.error('Error searching for admin:', error);
        }
    };

    const handleViewAll = async () => {
        try {
            const response = await axios.get(`/admin`);
            setSearchResults(response.data);
        } catch (error) {
            console.error('Error fetching all admins:', error);
        }
    };

    const handleAdminSelect = (admin) => {
        setSelectedAdminId(admin.id);
        setAdminData({
            firstName: admin.firstName,
            lastName: admin.lastName,
            email: admin.email,
            username: admin.username,
            password: '********', // Placeholder for password
            birthday: new Date(admin.birthday),
            phone: admin.phone,
            address: admin.address,
            gender: admin.gender,
            role: admin.role // Assuming the role is available from the backend
        });
        setSearchResults([]);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        const updatedAdminData = { ...adminData, [name]: value };

        if (name === 'firstName' || name === 'lastName' || name === 'birthday') {
            updatedAdminData.username = generateUsername(updatedAdminData);
        }

        setAdminData(updatedAdminData);
    };

    const handleDateChange = (date) => {
        const updatedAdminData = { ...adminData, birthday: date };
        updatedAdminData.username = generateUsername(updatedAdminData);
        setAdminData(updatedAdminData);
    };

    const generateUsername = ({ firstName, lastName, birthday, role }) => {
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

                // Create a copy of adminData without the placeholder password
                const updatedAdminData = { ...adminData };
                if (updatedAdminData.password === '********') {
                    delete updatedAdminData.password;
                }

                const response = await axios.put(`/admin/${selectedAdminId}`, updatedAdminData, {
                    headers: {
                        'Authorization': `Bearer ${token}`, // Add Authorization header
                        'X-XSRF-TOKEN': csrfToken, // Add CSRF token header
                        'Content-Type': 'application/json'
                    }
                });
                console.log(`Admin updated successfully:`, response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000); // Hide success message after 3 seconds
            } catch (error) {
                console.error(`Error updating admin:`, error);
            }
        }
    };

    const handleDelete = async () => {
        try {
            const token = localStorage.getItem('token'); // Ensure the token is retrieved correctly
            if (!token) {
                throw new Error("No token found");
            }

            const csrfToken = document.cookie.split('; ').find(row => row.startsWith('XSRF-TOKEN')).split('=')[1]; // Get CSRF token from cookie

            const response = await axios.delete(`/admin/${selectedAdminId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`, // Add Authorization header
                    'X-XSRF-TOKEN': csrfToken, // Add CSRF token header
                    'Content-Type': 'application/json'
                }
            });
            console.log(`Admin deleted successfully:`, response.data);

            setSelectedAdminId(null);
            setAdminData({
                firstName: '',
                lastName: '',
                email: '',
                username: '',
                password: '',
                birthday: null,
                phone: '',
                address: '',
                gender: '',
                role: 'ADMIN'
            });
        } catch (error) {
            console.error(`Error deleting admin:`, error);
        }
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Edit Admin</h1>
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
                            {searchResults.map(admin => (
                                <ListGroup.Item
                                    key={admin.id}
                                    action
                                    onClick={() => handleAdminSelect(admin)}
                                    active={admin.id === selectedAdminId}
                                    className={admin.id === selectedAdminId ? 'bg-primary text-white' : 'bg-light'}
                                >
                                    <div><strong>Name:</strong> {admin.firstName} {admin.lastName}</div>
                                    <div><strong>Username:</strong> {admin.username}</div>
                                    <div><strong>Email:</strong> {admin.email}</div>
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    )}
                </Col>
            </Row>
            {selectedAdminId && (
                <Row className="justify-content-center">
                    <Col md={8} lg={6}>
                        {showSuccess && <Alert variant="success">Admin updated successfully!</Alert>}
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
                                    <Form.Group className="mb-3" controlId="formBirthday">
                                        <Form.Label>Birthday</Form.Label>
                                        <DatePicker
                                            selected={adminData.birthday}
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
                                            value={adminData.gender}
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
                                            value={adminData.address}
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
                                            value={adminData.phone}
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
                                            value={adminData.email}
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
                                            value={adminData.username}
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
                        </Form>
                    </Col>
                </Row>
            )}
        </Container>
    );
};

export default EditAdminForm;
