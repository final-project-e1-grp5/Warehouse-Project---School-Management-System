import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, InputGroup, ListGroup, Modal } from 'react-bootstrap';
import './EditClassForm.css'; // Import the CSS file for animations

const EditClassForm = () => {
    const [searchQuery, setSearchQuery] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [selectedClassId, setSelectedClassId] = useState(null);
    const [classData, setClassData] = useState({
        name: '',
        teacher: {},
        students: []
    });
    const [teachers, setTeachers] = useState([]);
    const [students, setStudents] = useState([]);
    const [filteredStudents, setFilteredStudents] = useState([]);
    const [selectedStudents, setSelectedStudents] = useState(new Set());
    const [searchStudentQuery, setSearchStudentQuery] = useState('');
    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);
    const [showDeleteSuccess, setShowDeleteSuccess] = useState(false);
    const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
    const [flashyStudent, setFlashyStudent] = useState(null);

    useEffect(() => {
        fetchTeachers();
        fetchStudents();
    }, []);

    useEffect(() => {
        if (selectedClassId) {
            fetchClassData(selectedClassId);
        }
    }, [selectedClassId]);

    const fetchTeachers = async () => {
        try {
            const response = await axios.get('/teacher/all');
            setTeachers(response.data || []);
        } catch (error) {
            console.error('Error fetching teachers:', error);
        }
    };

    const fetchStudents = async () => {
        try {
            const response = await axios.get('/student/all');
            setStudents(response.data || []);
            setFilteredStudents(response.data || []);
        } catch (error) {
            console.error('Error fetching students:', error);
        }
    };

    const fetchClassData = async (id) => {
        try {
            const response = await axios.get(`/class/${id}`);
            const classEntity = response.data;
            setClassData({
                name: classEntity.name,
                teacher: classEntity.teacher || {},
                students: (classEntity.students || []).map(student => student.id)
            });
            setSelectedStudents(new Set((classEntity.students || []).map(student => student.id)));
        } catch (error) {
            console.error('Error fetching class data:', error);
        }
    };

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
    };

    const handleSearch = async () => {
        try {
            const response = await axios.get(`/class?search=${searchQuery}`);
            setSearchResults(response.data);
        } catch (error) {
            console.error('Error searching for class:', error);
        }
    };

    const handleViewAll = async () => {
        try {
            const response = await axios.get(`/class`);
            setSearchResults(response.data);
        } catch (error) {
            console.error('Error fetching all classes:', error);
        }
    };

    const handleClassSelect = (classEntity) => {
        setSelectedClassId(classEntity.id);
        setClassData({
            name: classEntity.name,
            teacher: classEntity.teacher || {},
            students: (classEntity.students || []).map(student => student.id)
        });
        setSelectedStudents(new Set((classEntity.students || []).map(student => student.id)));
        setSearchResults([]);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === 'teacher') {
            const teacher = teachers.find(t => t.id === value);
            setClassData({ ...classData, teacher: teacher || {} });
        } else {
            setClassData({ ...classData, [name]: value });
        }
    };

    const handleStudentSearchChange = (e) => {
        const query = e.target.value;
        setSearchStudentQuery(query);
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
        setFilteredStudents(filteredStudents.filter(student => !newSelectedStudents.has(student.id)));

        // Set flashyStudent for visual feedback
        setFlashyStudent(studentId);
        setTimeout(() => setFlashyStudent(null), 500); // Remove flashyStudent after 500ms
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

                const response = await axios.put(`/class/${selectedClassId}`, {
                    ...classData,
                    students: Array.from(selectedStudents)
                }, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'X-XSRF-TOKEN': csrfToken,
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Class updated successfully:', response.data);

                setShowSuccess(true);
                setTimeout(() => {
                    setShowSuccess(false);

                    // Reset form
                    setSelectedClassId(null);
                    setClassData({
                        name: '',
                        teacher: {},
                        students: []
                    });
                    setSelectedStudents(new Set());
                    setSearchStudentQuery('');
                    setFilteredStudents(students);
                    setValidated(false);
                }, 3000);
            } catch (error) {
                console.error('Error updating class:', error);
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

            const response = await axios.delete(`/class/${selectedClassId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'X-XSRF-TOKEN': csrfToken,
                    'Content-Type': 'application/json'
                }
            });
            console.log('Class deleted successfully:', response.data);

            // Reset form
            setSelectedClassId(null);
            setClassData({
                name: '',
                teacher: {},
                students: []
            });
            setSelectedStudents(new Set());
            setSearchStudentQuery('');
            setFilteredStudents(students);

            setShowDeleteSuccess(true);
            setTimeout(() => setShowDeleteSuccess(false), 3000);
            setShowDeleteConfirm(false);
        } catch (error) {
            console.error('Error deleting class:', error);
        }
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Edit Class</h1>
            <Row className="justify-content-center mb-4">
                <Col md={8} lg={6}>
                    <InputGroup>
                        <Form.Control
                            type="text"
                            placeholder="Search by class name"
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
                            {searchResults.map(classEntity => (
                                <ListGroup.Item
                                    key={classEntity.id}
                                    action
                                    onClick={() => handleClassSelect(classEntity)}
                                    active={classEntity.id === selectedClassId}
                                    className={classEntity.id === selectedClassId ? 'bg-primary text-white' : 'bg-light'}
                                >
                                    <div><strong>Class Name:</strong> {classEntity.name}</div>
                                    <div><strong>Teacher:</strong> {classEntity.teacher ? `${classEntity.teacher.firstName} ${classEntity.teacher.lastName}` : 'N/A'}</div>
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    )}
                </Col>
            </Row>
            {selectedClassId && (
                <Row className="justify-content-center">
                    <Col md={8} lg={6}>
                        {showSuccess && <Alert variant="success">Class updated successfully!</Alert>}
                        {showDeleteSuccess && <Alert variant="danger">Class deleted successfully!</Alert>}
                        <Form noValidate validated={validated} onSubmit={handleSubmit}>
                            <Form.Group className="mb-3" controlId="formClassName">
                                <Form.Label>Class Name</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="name"
                                    value={classData.name}
                                    onChange={handleChange}
                                    required
                                    maxLength="100"
                                />
                                <Form.Control.Feedback type="invalid">
                                    Please provide a valid class name.
                                </Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="formTeacher">
                                <Form.Label>Teacher</Form.Label>
                                <Form.Control
                                    as="select"
                                    name="teacher"
                                    value={classData.teacher.id || ''}
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
                                    value={searchStudentQuery}
                                    onChange={handleStudentSearchChange}
                                />
                            </InputGroup>
                            <ListGroup>
                                {searchStudentQuery !== '' && filteredStudents.map(student => (
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

                            <Button variant="primary" type="submit" className="me-2 mb-4">
                                Update
                            </Button>
                            <Button variant="danger" onClick={handleDeleteConfirm} className="mb-4">
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
                <Modal.Body>Are you sure you want to delete this class?</Modal.Body>
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

export default EditClassForm;
