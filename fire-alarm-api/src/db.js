const Sequelize = require('sequelize');



const sequelize = new Sequelize('firealarm', 'postgres', '9896', {
    host: 'localhost',
    dialect: 'postgres'
});


exports.init = () => {

    sequelize.authenticate()
        .then(() => {
            console.log('Database connected');
            require('./models/firealarm.model').init(sequelize);
            sequelize.sync();
        })
        .catch(err => {
            console.log('Failed to connect to database', err);
        });

}

exports.db = sequelize;