const Sequelize = require('sequelize');



class FireAlarmSensor extends Sequelize.Model { }


exports.init = (sequelize) => {

    FireAlarmSensor.init({
        id: {
            type: Sequelize.INTEGER,
            primaryKey: true,
            autoIncrement: true
        },
        isActive: {
            type: Sequelize.BOOLEAN,
            defaultValue: false
        },
        floor: {
            type: Sequelize.STRING,
        },
        room: {
            type: Sequelize.STRING
        },
        smoke_level: {
            type: Sequelize.INTEGER
        },
        co2_level: {
            type: Sequelize.INTEGER
        },

    }, {
        sequelize,
        modelName: 'fire-alarm-sensor',
        timestamps: false
    }
    )

}
exports.FireAlarmSensor = FireAlarmSensor;