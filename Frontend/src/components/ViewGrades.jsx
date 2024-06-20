import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Line } from 'react-chartjs-2';
import { Container, Table } from 'react-bootstrap';
import 'chart.js/auto';

const ViewGrades = ({ studentId }) => {
    const [gradesData, setGradesData] = useState([]);
    const [chartData, setChartData] = useState({
        labels: [],
        datasets: []
    });

    useEffect(() => {
        fetchGrades();
    }, [studentId]);

    const fetchGrades = async () => {
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
            <h1 className="mb-4 text-center">View Grades</h1>
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

export default ViewGrades;
