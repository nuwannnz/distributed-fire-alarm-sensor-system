module.exports = {
    jwt: {
        secret: process.env.JWT_SECRET || "fire-alarm-api",
        tokenOptions: {
            expiresIn: "1h",
            issuer: process.env.JWT_ISSUER || "fire-alarm-api",
        },
    },
    db: {
        host: process.env.DB_HOST || "localhost",
        database: process.env.DB_NAME || "firealarm",
        username: process.env.DB_USERNAME || "admin",
        password: process.env.DB_PASSWORD || "password",
        url: process.env.DATABASE_URL || 'postgres://postgres:9896@localhost:5432/firealarm'
    }
}