const Sequelize = require('sequelize');
const config = require('./config');



// const sequelize = new Sequelize(config.db.database, config.db.username, config.db.password, {
//     host: config.db.host,
//     dialect: 'postgres'
// });

const sequelize = new Sequelize(config.db.url);
exports.init = () => {

    sequelize.authenticate()
        .then(() => {
            console.log('Database connected');
            require('./models/firealarm.model').init(sequelize);
            require('./models/user.model').init(sequelize);

            sequelize.sync();
        })
        .catch(err => {
            console.log('Failed to connect to database', err);
        });

}

exports.db = sequelize;