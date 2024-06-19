import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, InputGroup } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import './AddScheduleForm.css'; // Import CSS file for styling

const AddScheduleForm = () => {
    const [classes, setClasses] = useState([]);
    const [scheduleData, setScheduleData] = useState({
        classId: '',
        teacherName: '',
        dayOfWeek: '',
        startTime: '',
        endTime: ''
    });
    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);

    useEffect(() => {
        fetchClasses();
    }, []);

    const fetchClasses = async () => {
        try {
            const response = await axios.get('/class');
            setClasses(response.data || []);
        } catch (error) {
            console.error('Error fetching classes:', error);
        }
    };

    const handleClassChange = async (e) => {
        const classId = e.target.value;
        setScheduleData({ ...scheduleData, classId });

        if (classId) {
            try {
                const response = await axios.get(`/class/${classId}`);
                const classEntity = response.data;
                setScheduleData({
                    ...scheduleData,
                    classId,
                    teacherName: classEntity.teacher ? `${classEntity.teacher.firstName} ${classEntity.teacher.lastName}` : 'N/A'
                });
            } catch (error) {
                console.error('Error fetching class data:', error);
            }
        } else {
            setScheduleData({
                ...scheduleData,
                teacherName: ''
            });
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setScheduleData({ ...scheduleData, [name]: value });
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

                const response = await axios.post('/schedule', scheduleData, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'X-XSRF-TOKEN': csrfToken,
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Schedule added successfully:', response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000);

                // Reset form
                setScheduleData({
                    classId: '',
                    teacherName: '',
                    dayOfWeek: '',
                    startTime: '',
                    endTime: ''
                });
                setValidated(false);
            } catch (error) {
                console.error('Error adding schedule:', error);
            }
        }
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Add Schedule</h1>
            {showSuccess && <Alert variant="success">Schedule added successfully!</Alert>}
            <Row className="justify-content-center">
                <Col md={8} lg={6}>
                    <Form noValidate validated={validated} onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="formClass">
                            <Form.Label>Class</Form.Label>
                            <Form.Control
                                as="select"
                                name="classId"
                                value={scheduleData.classId}
                                onChange={handleClassChange}
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
                        {scheduleData.teacherName && (
                            <Form.Group className="mb-3" controlId="formTeacher">
                                <Form.Label>Teacher</Form.Label>
                                <Form.Control
                                    type="text"
                                    value={scheduleData.teacherName}
                                    readOnly
                                    className="bg-light"
                                />
                            </Form.Group>
                        )}
                        <Form.Group className="mb-3" controlId="formDayOfWeek">
                            <Form.Label>Day of Week</Form.Label>
                            <Form.Control
                                as="select"
                                name="dayOfWeek"
                                value={scheduleData.dayOfWeek}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Select Day</option>
                                <option value="MONDAY">Monday</option>
                                <option value="TUESDAY">Tuesday</option>
                                <option value="WEDNESDAY">Wednesday</option>
                                <option value="THURSDAY">Thursday</option>
                                <option value="FRIDAY">Friday</option>
                                <option value="SATURDAY">Saturday</option>
                                <option value="SUNDAY">Sunday</option>
                            </Form.Control>
                            <Form.Control.Feedback type="invalid">
                                Please select a day of the week.
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formStartTime">
                            <Form.Label>Start Time</Form.Label>
                            <Form.Control
                                type="time"
                                name="startTime"
                                value={scheduleData.startTime}
                                onChange={handleChange}
                                required
                            />
                            <Form.Control.Feedback type="invalid">
                                Please provide a valid start time.
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formEndTime">
                            <Form.Label>End Time</Form.Label>
                            <Form.Control
                                type="time"
                                name="endTime"
                                value={scheduleData.endTime}
                                onChange={handleChange}
                                required
                            />
                            <Form.Control.Feedback type="invalid">
                                Please provide a valid end time.
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Button variant="success" type="submit" className="w-100 mt-3 mb-4">
                            Add Schedule
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default AddScheduleForm;
