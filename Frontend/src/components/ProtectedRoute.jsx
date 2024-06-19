// Version 1.0.4 - Updated to support multiple roles.

import React from 'react';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ children, role, requiredRoles }) => {
    const isAuthenticated = role !== undefined && role !== null;
    const isAuthorized = role && requiredRoles.includes(role.toUpperCase());

    console.log(`ProtectedRoute: role = ${role}, requiredRoles = ${requiredRoles}`);
    console.log(`ProtectedRoute: isAuthenticated = ${isAuthenticated}, isAuthorized = ${isAuthorized}`);

    if (!isAuthenticated) {
        return <Navigate to="/" />;
    }

    if (!isAuthorized) {
        return <Navigate to="/unauthorized" />;
    }

    return children;
};

export default ProtectedRoute;
