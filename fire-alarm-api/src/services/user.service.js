const { User } = require('../models/user.model');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const config = require('../config');


const hasAdmin = async () => {
    const adminCount = await User.count();
    return adminCount > 0;
}

const signup = async (email, password) => {
    const hashedPassword = await bcrypt.hash(password, 10);
    const user = await User.create({ email, password: hashedPassword });
    return user;
}

const login = async (email, password) => {
    const user = await User.findOne({ where: { email } });
    if (user === null) {
        return false;
    }

    const passwordMatch = await bcrypt.compare(password, user.password)
    return passwordMatch;
}

const getUserEmails = async () => {
    const users = await User.findAll();

    return users.map(user => user.email);
}

const generateToken = (email) => {
    const token = jwt.sign({ email }, config.jwt.secret, config.jwt.tokenOptions);
    return token;
}

module.exports = {
    hasAdmin,
    login,
    signup,
    generateToken,
    getUserEmails
}