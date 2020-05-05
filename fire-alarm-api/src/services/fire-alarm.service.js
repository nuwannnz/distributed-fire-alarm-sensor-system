

const { FireAlarmSensor } = require('../models/firealarm.model');

const createFireAlaram = async (floor, room) => {

    const fireAlarm = await FireAlarmSensor.create({ floor, room, smoke_level: 0, co2_level: 0 });

    const createdFireAlarm = fireAlarm.get({ plain: true });
    return createdFireAlarm;


}

const getAllFireAlarms = async () => {
    const fireAlarms = (await FireAlarmSensor.findAll()).sort((a, b) => a.id - b.id);

    return fireAlarms;
}

const getFireAlarm = async (id) => {
    const fireAlarm = await FireAlarmSensor.findByPk(id);
    return fireAlarm;
}

const updateFireAlarm = async (id, floor, room, isActive, smokeLevel, co2Level) => {
    const fireAlarm = await FireAlarmSensor.findByPk(id);

    await fireAlarm.update({ floor, room, is_active: isActive, smoke_level: smokeLevel, co2_level: co2Level });

    const updatedFireAlarm = fireAlarm.get({ plain: true });
    return updatedFireAlarm;
}

const deleteFireAlarm = async (id) => {
    const fireAlarm = await FireAlarmSensor.findByPk(id);
    await fireAlarm.destroy();
}

const updateFireAlarmStatus = async (id, isActive, smokeLevel, co2Level) => {
    // find fire alarm
    const fireAlarm = await FireAlarmSensor.findByPk(id);
    if (!fireAlarm) {
        return null;
    }

    // update fields
    if (isActive) {
        await fireAlarm.update({ is_active: isActive });
    }
    if (smokeLevel) {
        await fireAlarm.update({ smoke_level: smokeLevel });
    }
    if (co2Level) {
        await fireAlarm.update({ co2_level: co2Level });

    }

    await fireAlarm.reload();

    const updatedFireAlarm = fireAlarm.get({ plain: true });
    return updatedFireAlarm;
}


module.exports = {
    createFireAlaram,
    getAllFireAlarms,
    getFireAlarm,
    deleteFireAlarm,
    updateFireAlarm,
    updateFireAlarmStatus
}


