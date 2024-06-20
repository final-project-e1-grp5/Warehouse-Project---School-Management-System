// models/Attendance.js
const { Sequelize, DataTypes } = require('sequelize');
const sequelize = require('./database'); // Adjust the path as necessary

const Attendance = sequelize.define('Attendance', {
  studentId: {
    type: DataTypes.INTEGER,
    allowNull: false,
    references: {
      model: 'Users',
      key: 'id'
    }
  },
  date: {
    type: DataTypes.DATEONLY,
    allowNull: false
  },
  status: {
    type: DataTypes.STRING,
    allowNull: false // Present, Absent, Late
  }
}, {
  timestamps: false
});

module.exports = Attendance;
