import React from 'react';
import logo from './logo.svg'; // Ensure logo.svg is in the same folder or adjust the import path
import './HeaderLogo.css';

const HeaderLogo = () => {
    return (
        <div className="header-container">
            <img src={logo} alt="Logo" className="header-logo" />
            <div className="header-title">School Management System</div>
        </div>
    );
};

export default HeaderLogo;
