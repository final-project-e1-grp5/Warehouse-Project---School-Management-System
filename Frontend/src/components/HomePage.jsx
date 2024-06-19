import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import BackgroundVideo from '../assets/Background.mp4';
import Logo from '../assets/Logo.png';
import './HomePage.css';

const HomePage = () => {
    const navigate = useNavigate();
    const [fadeOut, setFadeOut] = useState(false);

    const handleLoginClick = () => {
        setFadeOut(true);
        setTimeout(() => {
            navigate('/user/login');
        }, 1000);
    };

    useEffect(() => {
        const title = document.querySelector('.title');
        title.style.opacity = 1;
    }, []);

    return (
        <div className={`video-container ${fadeOut ? 'fade-out' : ''}`}>
            <video autoPlay muted loop className="video-background">
                <source src={BackgroundVideo} type="video/mp4" />
                Your browser does not support the video tag.
            </video>
            <h1 className="title"></h1>
            <img src={Logo} alt="Logo" className="logo" />
            <button
                className={`login-button ${fadeOut ? 'fade-out' : ''}`}
                onClick={handleLoginClick}
            >
                Login
            </button>
        </div>
    );
};

export default HomePage;
