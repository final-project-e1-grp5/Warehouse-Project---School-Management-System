import React, { useEffect, useState } from 'react';
import { Container, Navbar, Nav, NavDropdown } from 'react-bootstrap';
import { useNavigate, useLocation } from 'react-router-dom';
import './Layout.css';
import BackgroundVideo from '../assets/Background.mp4';
import Logo from '../assets/Logo.png';

const Layout = ({ children, userRole }) => {
    const [currentRole, setCurrentRole] = useState(userRole);
    const navigate = useNavigate();
    useLocation();
    useEffect(() => {
        console.log("Layout component mounted");
        console.log(`Layout userRole: ${userRole}`);
    }, [userRole]);

    const handleLogout = () => {
        localStorage.removeItem('user');
        localStorage.removeItem('token');
        navigate('/user/login');
    };

    const getDashboardLink = (role) => {
        switch (role) {
            case 'ADMIN':
                return '/admin/dashboard';
            case 'STUDENT':
                return '/student/dashboard';
            case 'TEACHER':
                return '/teacher/dashboard';
            default:
                return '/';
        }
    };

    const handleRoleSwitch = (role) => {
        setCurrentRole(role);
        navigate(getDashboardLink(role));
    };

    return (
        <div className="d-flex flex-column min-vh-100">
            <div className="video-foreground">
                <video autoPlay muted loop className="video-background">
                    <source src={BackgroundVideo} type="video/mp4" />
                    Your browser does not support the video tag.
                </video>
            </div>
            <Navbar bg="dark" variant="dark" expand="lg">
                <Container>
                    <Navbar.Brand href={getDashboardLink(currentRole)}>
                        <img
                            src={Logo}
                            width="55"
                            height="50"
                            className="d-inline-block align-top"
                            alt="School Management System Logo"
                        />
                        ClassOrbit
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" className="d-lg-none" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                        </Nav>
                        <Nav className="ms-auto">
                            {userRole === 'ADMIN' && (
                                <NavDropdown title="Switch Dashboard" id="dashboard-switch-dropdown" className="me-2">
                                    <NavDropdown.Item onClick={() => handleRoleSwitch('ADMIN')}>
                                        Admin Dashboard
                                    </NavDropdown.Item>
                                    <NavDropdown.Item onClick={() => handleRoleSwitch('TEACHER')}>
                                        Teacher Dashboard
                                    </NavDropdown.Item>
                                </NavDropdown>
                            )}
                            <NavDropdown title="Profile" id="basic-nav-dropdown">
                                <NavDropdown.Item href="/user/edit-profile">Edit Profile</NavDropdown.Item>
                                <NavDropdown.Item onClick={handleLogout}>Logout</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <Container className="flex-grow-1 mt-3">
                <main>
                    {children}
                </main>
            </Container>
            <footer className="bg-dark text-white text-center py-3 mt-auto">
                <p>Group FiVE GmBH®</p>
            </footer>
        </div>
    );
};

export default Layout;
