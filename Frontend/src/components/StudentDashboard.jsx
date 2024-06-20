import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Container, Card, Button, Carousel, ButtonGroup } from 'react-bootstrap';
import './StudentDashboard.css'; // Import the custom CSS

const StudentDashboard = () => {
    useEffect(() => {
        console.log("StudentDashboard component mounted");
    }, []);

    return (
        <Container className="d-flex justify-content-center mt-5">
            <div style={{ maxWidth: '650px', width: '100%' }}>
                <h1 className="my-4 text-center">Student Dashboard</h1>
                <Carousel
                    controls={true}
                    indicators={false}
                    nextIcon={<span aria-hidden="true" className="carousel-control-next-icon" />}
                    prevIcon={<span aria-hidden="true" className="carousel-control-prev-icon" />}
                    className="custom-carousel" // Add a custom class
                >
                    <Carousel.Item>
                        <Card className="text-center">
                            <Card.Body className="d-flex flex-column align-items-center">
                                <Card.Title>Attendance</Card.Title>
                                <ButtonGroup vertical className="button-group-centered">
                                    <Link to="/attendance/view">
                                        <Button variant="primary" size="lg" className="mb-2">View Attendance</Button>
                                    </Link>
                                </ButtonGroup>
                            </Card.Body>
                        </Card>
                    </Carousel.Item>
                    <Carousel.Item>
                        <Card className="text-center">
                            <Card.Body className="d-flex flex-column align-items-center">
                                <Card.Title>Grades</Card.Title>
                                <ButtonGroup vertical className="button-group-centered">
                                    <Link to="/grades/view">
                                        <Button variant="secondary" size="lg" className="mb-2">View Grades</Button>
                                    </Link>
                                </ButtonGroup>
                            </Card.Body>
                        </Card>
                    </Carousel.Item>
                    <Carousel.Item>
                        <Card className="text-center">
                            <Card.Body className="d-flex flex-column align-items-center">
                                <Card.Title>Schedule</Card.Title>
                                <ButtonGroup vertical className="button-group-centered">
                                    <Link to="/schedule/view">
                                        <Button variant="success" size="lg" className="mb-2">View Schedule</Button>
                                    </Link>
                                </ButtonGroup>
                            </Card.Body>
                        </Card>
                    </Carousel.Item>
                </Carousel>
            </div>
        </Container>
    );
};

export default StudentDashboard;
