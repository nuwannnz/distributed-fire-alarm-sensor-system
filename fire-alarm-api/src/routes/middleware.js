
const jwt = require("jsonwebtoken");
const config = require("../config");


const verifyJWTToken = (req, res, next) => {
    // extract auth header
    const authHeader = req.headers.authorization;
    if (!authHeader) {
        res.status(403);
        res.json({ error: 'Authentication token is missing' })
        return;
    }

    // extract token from auth header
    // Bearer <token>
    const token = authHeader.split(" ")[1];

    try {
        const decodedResult = jwt.verify(
            token,
            config.jwt.secret,
            config.jwt.tokenOptions
        );

        // add the decoded token to the request
        req.decoded = decodedResult;

        // continue
        next();
    } catch (error) {
        // invalid token
        console.log(error);
        res.status(403);
        res.json({ error: 'Authentication error. Invalid token' })
        return;
    }
};

module.exports = {
    verifyJWTToken,
};
