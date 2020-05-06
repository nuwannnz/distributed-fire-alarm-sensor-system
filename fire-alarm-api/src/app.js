require('dotenv').config();

const express = require('express');
const methodOverride = require('method-override');
const logger = require('morgan');
const cors = require('cors');
const fireAlarmRouter = require('./routes/fire-alarms');
const usersRouter = require('./routes/users');

const db = require('./db');

// init database
db.init();

// init app
const app = express();

// apply middleware 
app.use(cors({ credentials: true }));
app.use(methodOverride('X-HTTP-Method-Override'));
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

// apply routes
app.use('/users', usersRouter);
app.use('/fire-alarms', fireAlarmRouter);

module.exports = app;
