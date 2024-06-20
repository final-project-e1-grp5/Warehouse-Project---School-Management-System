import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, Modal } from 'react-bootstrap';

const EditScheduleForm = ({ scheduleId }) => {
    const [scheduleData, setScheduleData] = useState({
        classId: '',
        teacherName: '',
        dayOfWeek: '',
        startTime: '',
        endTime: ''
    });
    const [classes, setClasses] = useState([]);
    const [validated, setValidated] = useState(false);
    const [showSuccess, setShowSuccess] = useState(false);
    const [showDeleteSuccess, setShowDeleteSuccess] = useState(false);
    const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);

    useEffect(() => {
        fetchClasses();
        if (scheduleId) {
            fetchScheduleData(scheduleId);
        }
    }, [scheduleId]);

    const fetchClasses = async () => {
        try {
            const response = await axios.get('/class');
            setClasses(response.data || []);
        } catch (error) {
            console.error('Error fetching classes:', error);
        }
    };

    const fetchScheduleData = async (id) => {
        try {
            const response = await axios.get(`/schedule/${id}`);
            const schedule = response.data;
            setScheduleData({
                classId: schedule.classEntity.id,
                teacherName: schedule.classEntity.teacher ? `${schedule.classEntity.teacher.firstName} ${schedule.classEntity.teacher.lastName}` : 'N/A',
                dayOfWeek: schedule.dayOfWeek,
                startTime: schedule.startTime,
                endTime: schedule.endTime
            });
        } catch (error) {
            console.error('Error fetching schedule data:', error);
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

                const schedulePayload = {
                    classEntity: {
                        id: scheduleData.classId
                    },
                    dayOfWeek: scheduleData.dayOfWeek,
                    startTime: scheduleData.startTime,
                    endTime: scheduleData.endTime
                };

                const response = await axios.put(`/schedule/${scheduleId}`, schedulePayload, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'X-XSRF-TOKEN': csrfToken,
                        'Content-Type': 'application/json'
                    }
                });
                console.log('Schedule updated successfully:', response.data);

                setShowSuccess(true);
                setTimeout(() => setShowSuccess(false), 3000);

                // Reset validation state
                setValidated(false);
            } catch (error) {
                console.error('Error updating schedule:', error);
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

            const response = await axios.delete(`/schedule/${scheduleId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'X-XSRF-TOKEN': csrfToken,
                    'Content-Type': 'application/json'
                }
            });
            console.log(`Schedule deleted successfully:`, response.data);

            setShowDeleteSuccess(true);
            setTimeout(() => setShowDeleteSuccess(false), 3000);
            setShowDeleteConfirm(false);
        } catch (error) {
            console.error(`Error deleting schedule:`, error);
        }
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">Edit Schedule</h1>
            {showSuccess && <Alert variant="success">Schedule updated successfully!</Alert>}
            {showDeleteSuccess && <Alert variant="danger">Schedule deleted successfully!</Alert>}
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
                        <Button variant="success" type="submit" className="me-2 mb-4">
                            Update Schedule
                        </Button>
                        <Button variant="danger" onClick={handleDeleteConfirm} className="mb-4">
                            Delete Schedule
                        </Button>
                    </Form>
                </Col>
            </Row>
            <Modal show={showDeleteConfirm} onHide={handleDeleteCancel}>
                <Modal.Header closeButton>
                    <Modal.Title>Confirm Delete</Modal.Title>
                </Modal.Header>
                <Modal.Body>Are you sure you want to delete this schedule?</Modal.Body>
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

export default EditScheduleForm;
