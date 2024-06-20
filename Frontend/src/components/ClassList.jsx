import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ClassList = () => {
    const [classes, setClasses] = useState([]);

    useEffect(() => {
        const fetchClasses = async () => {
            try {
                const response = await axios.get('/class');
                setClasses(response.data);
            } catch (error) {
                console.error('Error fetching classes:', error);
            }
        };

        fetchClasses();
    }, []);

    return (
        <div className="table-container">
            <h2>Class List</h2>
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Teacher</th>
                </tr>
                </thead>
                <tbody>
                {classes.map((classItem) => (
                    <tr key={classItem.id}>
                        <td>{classItem.name}</td>
                        <td>{classItem.teacher.name}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ClassList;
