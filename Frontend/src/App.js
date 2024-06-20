import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import LoginForm from './components/LoginForm';
import HomePage from './components/HomePage';
import Layout from './components/Layout';
import AddAdminForm from './components/AddAdminForm';
import EditAdminForm from './components/EditAdminForm';
import AddStudentForm from './components/AddStudentForm';
import EditStudentForm from './components/EditStudentForm';
import AddTeacherForm from './components/AddTeacherForm';
import EditTeacherForm from './components/EditTeacherForm';
import AddClassForm from './components/AddClassForm';
import EditClassForm from './components/EditClassForm';
import ClassList from './components/ClassList';
import AddScheduleForm from './components/AddScheduleForm';
import ViewAttendance from './components/ViewAttendance';
import ViewGrades from './components/ViewGrades';
import StudentDashboard from './components/StudentDashboard';
import TeacherDashboard from './components/TeacherDashboard';
import AdminDashboard from './components/AdminDashboard';
import Unauthorized from './components/Unauthorized';
import ProtectedRoute from './components/ProtectedRoute';
import './App.css';
import axios from 'axios';
import EditScheduleForm from "./components/EditScheduleForm";
import AddGradeForm from "./components/AddGradeForm";
import EditGradeForm from "./components/EditGradeForm";
import EditProfile from "./components/EditProfile";
import ViewSchedule from "./components/ViewSchedule";
import TakeAttendance from "./components/TakeAttendance";
import AssignGrades from "./components/AssignGrades";
import ViewStudentAttendance from "./components/ViewStudentAttendance";
import ViewStudentGrades from "./components/ViewStudentGrades";

function App() {
  let user = null;
  try {
    const userString = localStorage.getItem('user');
    const token = localStorage.getItem('token');
    if (userString && userString !== "undefined") {
      user = JSON.parse(userString);
      console.log("Parsed user from localStorage:", user);
      // Set default authorization header
      if (token) {
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      }
    } else {
      localStorage.removeItem('user');
      user = null;
    }
  } catch (error) {
    console.error("Error parsing user from localStorage", error);
    localStorage.removeItem('user');
    user = null;
  }

  console.log(`App: user = ${JSON.stringify(user)}`);

  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<HomePage/>}/>
          <Route path="/user/login" element={<LoginForm/>}/>
          <Route path="/admin/dashboard" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><AdminDashboard/></Layout></ProtectedRoute>}/>
          <Route path="/student/dashboard"
                 element={<ProtectedRoute role={user?.role} requiredRoles={['STUDENT']}><Layout
                   userRole={user?.role}><StudentDashboard/></Layout></ProtectedRoute>}/>
          <Route path="/teacher/dashboard"
                 element={<ProtectedRoute role={user?.role} requiredRoles={['TEACHER', 'ADMIN']}><Layout
                   userRole={user?.role}><TeacherDashboard/></Layout></ProtectedRoute>}/>
          <Route path="/admin/add" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><AddAdminForm/></Layout></ProtectedRoute>}/>
          <Route path="/admin/edit/:id" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><EditAdminForm/></Layout></ProtectedRoute>}/>
          <Route path="/student/add" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><AddStudentForm/></Layout></ProtectedRoute>}/>
          <Route path="/admin/student/edit" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><EditStudentForm/></Layout></ProtectedRoute>}/>
          <Route path="/teacher/add" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><AddTeacherForm/></Layout></ProtectedRoute>}/>
          <Route path="/teacher/edit/:id" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><EditTeacherForm/></Layout></ProtectedRoute>}/>
          <Route path="/admin/class/add" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><AddClassForm/></Layout></ProtectedRoute>}/>
          <Route path="/class/edit/:id" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><EditClassForm/></Layout></ProtectedRoute>}/>
          <Route path="/class/list" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><ClassList/></Layout></ProtectedRoute>}/>
          <Route path="/schedule/add" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><AddScheduleForm/></Layout></ProtectedRoute>}/>
          <Route path="/schedule/edit/:id" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><EditScheduleForm/></Layout></ProtectedRoute>}/>
          <Route path="/attendance/view"
                 element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN', 'TEACHER', 'STUDENT']}><Layout
                   userRole={user?.role}><ViewAttendance studentId={user?.id}/></Layout></ProtectedRoute>}/>
          <Route path="/grades/view" element={<ProtectedRoute role={user?.role} requiredRoles={['STUDENT']}><Layout
            userRole={user?.role}><ViewGrades studentId={user?.id}/></Layout></ProtectedRoute>}/>
          <Route path="/grade/add" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><AddGradeForm/></Layout></ProtectedRoute>}/>
          <Route path="/grade/edit/:id" element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN']}><Layout
            userRole={user?.role}><EditGradeForm/></Layout></ProtectedRoute>}/>
          <Route path="/user/edit-profile"
                 element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN', 'TEACHER', 'STUDENT']}><Layout
                   userRole={user?.role}><EditProfile/></Layout></ProtectedRoute>}/>
          <Route path="/schedule/view"
                 element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN', 'TEACHER', 'STUDENT']}><Layout
                   userRole={user?.role}><ViewSchedule/></Layout></ProtectedRoute>}/>
          <Route path="/grades/view"
                 element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN', 'TEACHER', 'STUDENT']}><Layout
                   userRole={user?.role}><ViewGrades studentId={user?.id}/></Layout></ProtectedRoute>}/> <Route
          path="/unauthorized" element={<Unauthorized/>}/>
          <Route path="/attendance/add"
                 element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN', 'TEACHER']}><Layout
                   userRole={user?.role}><TakeAttendance/></Layout></ProtectedRoute>}/>
          <Route path="/attendance/student/view"
                 element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN', 'TEACHER']}><Layout
                   userRole={user?.role}><ViewStudentAttendance/></Layout></ProtectedRoute>}/>
          <Route path="/grades/assign"
                 element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN', 'TEACHER']}><Layout
                   userRole={user?.role}><AssignGrades/></Layout></ProtectedRoute>}/>
          <Route path="/grades/student/view"
                 element={<ProtectedRoute role={user?.role} requiredRoles={['ADMIN', 'TEACHER']}><Layout
                   userRole={user?.role}><ViewStudentGrades/></Layout></ProtectedRoute>}/>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
