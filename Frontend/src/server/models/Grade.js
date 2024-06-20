// models/Grade.js
const { Sequelize, DataTypes } = require('sequelize');
const sequelize = require('./database'); // Adjust the path as necessary

const Grade = sequelize.define('Grade', {
  studentId: {
    type: DataTypes.INTEGER,
    allowNull: false,
    references: {
      model: 'Users',
      key: 'id'
    }
  },
  classId: {
    type: DataTypes.INTEGER,
    allowNull: false,
    references: {
      model: 'Classes',
      key: 'id'
    }
  },
  grade: {
    type: DataTypes.INTEGER,
    allowNull: false // Assuming 1 is the highest grade
  },
  date: {
    type: DataTypes.DATEONLY,
    allowNull: false
  }
}, {
  timestamps: false
});

module.exports = Grade;
