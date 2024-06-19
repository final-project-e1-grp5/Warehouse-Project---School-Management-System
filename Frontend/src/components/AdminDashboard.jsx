import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Button, Container, Card, Row, Col } from 'react-bootstrap';

const AdminDashboard = () => {
    useEffect(() => {
        console.log("AdminDashboard component mounted");
    }, []);

    return (
        <Container>
            <h1 className="my-4">Admin Dashboard</h1>
            <Row className="g-4">
                <Col md={4}>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>Admins</Card.Title>
                            <Link to="/admin/add">
                                <Button variant="primary" size="lg" className="w-100 mb-2">Add Admin</Button>
                            </Link>
                            <Link to="/admin/edit/:id">
                                <Button variant="outline-primary" size="lg" className="w-100 mb-2">Edit Admin</Button>
                            </Link>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>Students</Card.Title>
                            <Link to="/student/add">
                                <Button variant="secondary" size="lg" className="w-100 mb-2">Add Student</Button>
                            </Link>
                            <Link to="/student/edit/:id">
                                <Button variant="outline-secondary" size="lg" className="w-100 mb-2">Edit Student</Button>
                            </Link>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>Teachers</Card.Title>
                            <Link to="/teacher/add">
                                <Button variant="success" size="lg" className="w-100 mb-2">Add Teacher</Button>
                            </Link>
                            <Link to="/teacher/edit/:id">
                                <Button variant="outline-success" size="lg" className="w-100 mb-2">Edit Teacher</Button>
                            </Link>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>Classes</Card.Title>
                            <Link to="/class/add">
                                <Button variant="warning" size="lg" className="w-100 mb-2">Add Class</Button>
                            </Link>
                            <Link to="/class/edit/:id">
                                <Button variant="outline-warning" size="lg" className="w-100 mb-2">Edit Class</Button>
                            </Link>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>Schedules</Card.Title>
                            <Link to="/schedule/add">
                                <Button variant="info" size="lg" className="w-100 mb-2">Add Schedule</Button>
                            </Link>
                            <Link to="/schedule/edit/:id">
                                <Button variant="outline-info" size="lg" className="w-100 mb-2">Edit Schedule</Button>
                            </Link>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>Grades</Card.Title>
                            <Link to="/grade/add">
                                <Button variant="dark" size="lg" className="w-100 mb-2">Add Grade</Button>
                            </Link>
                            <Link to="/grade/edit/:id">
                                <Button variant="outline-dark" size="lg" className="w-100 mb-2">Edit Grade</Button>
                            </Link>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default AdminDashboard;
