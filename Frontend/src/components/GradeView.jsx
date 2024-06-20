import React, { useEffect, useState } from 'react';
import axios from 'axios';

const GradeView = () => {
    const [grades, setGrades] = useState([]);

    useEffect(() => {
        const fetchGrades = async () => {
            try {
                const response = await axios.get('/grades');
                setGrades(response.data);
            } catch (error) {
                console.error('Error fetching grades:', error);
            }
        };

        fetchGrades();
    }, []);

    return (
        <div>
            <h2>Grades</h2>
            <ul>
                {grades.map((grade) => (
                    <li key={grade.id}>{grade.subject} - {grade.grade}</li>
                ))}
            </ul>
        </div>
    );
};

export default GradeView;
