import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Container, Card, Button, Carousel } from 'react-bootstrap';
import './TeacherDashboard.css'; // Import the custom CSS

const TeacherDashboard = () => {
    useEffect(() => {
        console.log("TeacherDashboard component mounted");
    }, []);

    return (
        <Container className="d-flex justify-content-center mt-5">
            <div style={{ maxWidth: '650px', width: '100%' }}>
                <h1 className="my-4 text-center">Teacher Dashboard</h1>
                <Carousel
                    controls={true}
                    indicators={false}
                    nextIcon={<span aria-hidden="true" className="carousel-control-next-icon" />}
                    prevIcon={<span aria-hidden="true" className="carousel-control-prev-icon" />}
                    className="custom-carousel" // Add a custom class
                >
                    <Carousel.Item>
                        <Card className="text-center">
                            <Card.Body>
                                <Card.Title>Attendance</Card.Title>
                                <Link to="/attendance/add">
                                    <Button variant="primary" size="lg" className="w-100 mb-2">Take Attendance</Button>
                                </Link>
                                <Link to="/attendance/view">
                                    <Button variant="outline-primary" size="lg" className="w-100 mb-2">Check Own Attendance</Button>
                                </Link>
                                <Link to="/attendance/student/view">
                                    <Button variant="outline-primary" size="lg" className="w-100 mb-2">Check Student Attendance</Button>
                                </Link>
                            </Card.Body>
                        </Card>
                    </Carousel.Item>
                    <Carousel.Item>
                        <Card className="text-center">
                            <Card.Body>
                                <Card.Title>Grades</Card.Title>
                                <Link to="/grades/assign">
                                    <Button variant="success" size="lg" className="w-100 mb-2">Assign Grades</Button>
                                </Link>
                                <Link to="/grades/student/view">
                                    <Button variant="outline-success" size="lg" className="w-100 mb-2">Get Student Grades</Button>
                                </Link>
                            </Card.Body>
                        </Card>
                    </Carousel.Item>
                    <Carousel.Item>
                        <Card className="text-center">
                            <Card.Body>
                                <Card.Title>Schedule</Card.Title>
                                <Link to="/schedule/view">
                                    <Button variant="info" size="lg" className="w-100 mb-2">View Schedule</Button>
                                </Link>
                            </Card.Body>
                        </Card>
                    </Carousel.Item>
                </Carousel>
            </div>
        </Container>
    );
};

export default TeacherDashboard;
