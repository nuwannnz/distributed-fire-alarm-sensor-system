require('dotenv').config();

const express = require('express');
const logger = require('morgan');
const fireAlarmRouter = require('./routes/fire-alarm');
const usersRouter = require('./routes/users');

const db = require('./db');

// init database
db.init();

const app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));


app.use('/user', usersRouter);
app.use('/fire-alarm', fireAlarmRouter);

module.exports = app;
