import React, { useState } from 'react';
import axios from 'axios';
import './LoginForm.css';
import BackgroundVideo from '../assets/Background.mp4';  // Adjust the path to your video file

const LoginForm = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log('Login form submitted');
        try {
            const response = await axios.post('/user/login', { email, password });
            console.log('Response from backend:', response);
            if (response.data && response.data.user) {
                console.log('User data received:', response.data.user);
                // Store user data in localStorage
                localStorage.setItem('user', JSON.stringify(response.data.user));
                localStorage.setItem('token', response.data.token);
                console.log('User data stored in localStorage');
                // Redirect based on user role
                const userRole = response.data.user.role.toLowerCase();
                window.location.href = `/${userRole}/dashboard`;
            } else {
                console.error('Invalid response from server', response.data);
            }
        } catch (error) {
            console.error('Login error:', error);
        }
    };

    return (
        <div className="login-page">
            <div className="video-foreground">
                <video autoPlay muted loop className="video-background">
                    <source src={BackgroundVideo} type="video/mp4" />
                    Your browser does not support the video tag.
                </video>
            </div>
            <div className="login-container">
                <div className="login-box card">
                    <div className="card-body">
                        <h2 className="card-title">Login</h2>
                        <form onSubmit={handleSubmit}>
                            <div className="mb-3">
                                <label htmlFor="email" className="form-label">Email</label>
                                <input
                                    type="email"
                                    className="form-control"
                                    id="email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="password" className="form-label">Password</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    id="password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    required
                                />
                            </div>
                            <button type="submit" className="btn btn-primary">Login</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginForm;
