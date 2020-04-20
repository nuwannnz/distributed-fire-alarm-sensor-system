

const { FireAlarmSensor } = require('../models/firealarm.model');

const createFireAlaram = async (floor, room) => {

    const fireAlarm = await FireAlarmSensor.create({ floor, room, smoke_level: 0, co2_level: 0 });

    const createdFireAlarm = fireAlarm.get({ plain: true });
    return createdFireAlarm;


}

const getAllFireAlarms = async () => {
    const fireAlarms = await FireAlarmSensor.findAll();
    return fireAlarms;
}

const getFireAlarm = async (id) => {
    const fireAlarm = await FireAlarmSensor.findByPk(id);
    return fireAlarm;
}

const updateFireAlarm = async (id, floor, room) => {
    const fireAlarm = await FireAlarmSensor.findByPk(id);
    fireAlarm.floor = floor;
    fireAlarm.room = room;
    await fireAlarm.save();
    const updatedFireAlarm = fireAlarm.get({ plain: true });
    return updatedFireAlarm;
}

const deleteFireAlarm = async (id) => {
    const fireAlarm = await FireAlarmSensor.findByPk(id);
    await fireAlarm.destroy();
}

const updateFireAlarmSmokeLevel = async (id, smokeLevel) => {
    const fireAlarm = await FireAlarmSensor.findByPk(id);
    fireAlarm.smoke_level = smokeLevel;
    await fireAlarm.save();
    const updatedFireAlarm = fireAlarm.get({ plain: true });
    return updatedFireAlarm;
}

const updateFireAlarmCo2Level = async (id, co2Level) => {
    const fireAlarm = await FireAlarmSensor.findByPk(id);
    fireAlarm.co2_level = co2Level;
    await fireAlarm.save();
    const updatedFireAlarm = fireAlarm.get({ plain: true });
    return updatedFireAlarm;
}

module.exports = {
    createFireAlaram,
    getAllFireAlarms,
    getFireAlarm,
    deleteFireAlarm,
    updateFireAlarm,
    updateFireAlarmCo2Level,
    updateFireAlarmSmokeLevel
}


