const Sequelize = require('sequelize');



class User extends Sequelize.Model { }


exports.init = (sequelize) => {

    User.init({
        id: {
            type: Sequelize.INTEGER,
            primaryKey: true,
            autoIncrement: true
        },
        email: {
            type: Sequelize.STRING,
        },
        password: {
            type: Sequelize.STRING
        }

    }, {
        sequelize,
        modelName: 'user',
    }
    )

}
exports.User = User;