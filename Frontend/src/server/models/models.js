const { Sequelize, DataTypes } = require('sequelize');

const sequelize = new Sequelize('school_management', 'postgres', 'password', {
  host: 'localhost',
  dialect: 'postgres',
  logging: false
});

const User = sequelize.define('User', {
  role: {
    type: DataTypes.STRING,
    allowNull: false
  },
  name: {
    type: DataTypes.STRING,
    allowNull: false
  },
  email: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true
  },
  password: {
    type: DataTypes.STRING,
    allowNull: false
  }
}, {
  timestamps: true
});

const Class = sequelize.define('Class', {
  name: {
    type: DataTypes.STRING,
    allowNull: false
  },
  teacherId: {
    type: DataTypes.INTEGER,
    references: {
      model: 'Users',
      key: 'id'
    }
  }
}, {
  timestamps: true
});

const ClassStudents = sequelize.define('ClassStudents', {
  classId: {
    type: DataTypes.INTEGER,
    references: {
      model: Class,
      key: 'id'
    },
    field: 'classId'
  },
  userId: {
    type: DataTypes.INTEGER,
    references: {
      model: User,
      key: 'id'
    },
    field: 'userId'
  }
}, {
  timestamps: true
});

Class.belongsTo(User, { as: 'teacher', foreignKey: 'teacherId' });
Class.belongsToMany(User, { as: 'students', through: ClassStudents, foreignKey: 'classId' });
User.belongsToMany(Class, { as: 'studentClasses', through: ClassStudents, foreignKey: 'userId' });

module.exports = {
  sequelize,
  User,
  Class,
  ClassStudents
};
