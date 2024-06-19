import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Line } from 'react-chartjs-2';
import { Container, Table, Form, Row, Col } from 'react-bootstrap';
import 'chart.js/auto';

const ViewStudentGrades = () => {
    const [classes, setClasses] = useState([]);
    const [students, setStudents] = useState([]);
    const [selectedClassId, setSelectedClassId] = useState('');
    const [selectedStudentId, setSelectedStudentId] = useState('');
    const [gradesData, setGradesData] = useState([]);
    const [chartData, setChartData] = useState({
        labels: [],
        datasets: []
    });

    useEffect(() => {
        fetchClasses();
    }, []);

    useEffect(() => {
        if (selectedClassId) {
            fetchStudentsByClass(selectedClassId);
        }
    }, [selectedClassId]);

    useEffect(() => {
        if (selectedStudentId) {
            fetchGrades(selectedStudentId);
        }
    }, [selectedStudentId]);

    const fetchClasses = async () => {
        try {
            const response = await axios.get('/class/all'); // Adjust endpoint as necessary
            setClasses(response.data || []);
        } catch (error) {
            console.error('Error fetching classes:', error);
        }
    };

    const fetchStudentsByClass = async (classId) => {
        try {
            const response = await axios.get(`/class/${classId}/students`); // Adjust endpoint as necessary
            setStudents(response.data || []);
        } catch (error) {
            console.error('Error fetching students:', error);
        }
    };

    const fetchGrades = async (studentId) => {
        try {
            const response = await axios.get(`/grades/${studentId}/year`);
            const grades = response.data || [];
            setGradesData(grades);
            prepareChartData(grades);
        } catch (error) {
            console.error('Error fetching grades:', error);
            setGradesData([]);
            prepareChartData([]);
        }
    };

    const prepareChartData = (grades) => {
        const months = [
            'January', 'February', 'March', 'April', 'May', 'June',
            'July', 'August', 'September', 'October', 'November', 'December'
        ];

        const data = months.map((month, index) => {
            const gradeEntry = grades.find(g => new Date(g.date).getMonth() === index);
            return gradeEntry ? gradeEntry.grade : null;
        });

        setChartData({
            labels: months,
            datasets: [
                {
                    label: 'Grades',
                    data: data,
                    fill: false,
                    backgroundColor: 'rgba(75,192,192,0.4)',
                    borderColor: 'rgba(75,192,192,1)',
                    borderWidth: 1
                }
            ]
        });
    };

    return (
        <Container className="mt-4">
            <h1 className="mb-4 text-center">View Student Grades</h1>
            <Form>
                <Row className="mb-3">
                    <Col md={6}>
                        <Form.Group controlId="formClass">
                            <Form.Label>Select Class</Form.Label>
                            <Form.Control
                                as="select"
                                value={selectedClassId}
                                onChange={(e) => setSelectedClassId(e.target.value)}
                                required
                            >
                                <option value="">Choose...</option>
                                {classes.map((classItem) => (
                                    <option key={classItem.id} value={classItem.id}>
                                        {classItem.name}
                                    </option>
                                ))}
                            </Form.Control>
                        </Form.Group>
                    </Col>
                    <Col md={6}>
                        <Form.Group controlId="formStudent">
                            <Form.Label>Select Student</Form.Label>
                            <Form.Control
                                as="select"
                                value={selectedStudentId}
                                onChange={(e) => setSelectedStudentId(e.target.value)}
                                required
                                disabled={!selectedClassId}
                            >
                                <option value="">Choose...</option>
                                {students.map((student) => (
                                    <option key={student.id} value={student.id}>
                                        {student.firstName} {student.lastName}
                                    </option>
                                ))}
                            </Form.Control>
                        </Form.Group>
                    </Col>
                </Row>
            </Form>
            <div className="mb-5">
                {chartData.labels.length > 0 && chartData.datasets.length > 0 ? (
                    <Line data={chartData} />
                ) : (
                    <p className="text-center">No grades data available for the chart.</p>
                )}
            </div>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>Month</th>
                    <th>Grade</th>
                </tr>
                </thead>
                <tbody>
                {gradesData && gradesData.length > 0 ? (
                    gradesData.map((grade, index) => (
                        <tr key={index}>
                            <td>{new Date(grade.date).toLocaleString('default', { month: 'long' })}</td>
                            <td>{grade.grade}</td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="2" className="text-center">No grades available</td>
                    </tr>
                )}
                </tbody>
            </Table>
        </Container>
    );
};

export default ViewStudentGrades;
