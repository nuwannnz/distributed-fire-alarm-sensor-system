

const { FireAlarmSensor } = require('../models/firealarm.model');

/**
 * Create a new fire alarm
 * @param {string} floor Floor of the fire alarm
 * @param {string} room Room of the fire alarm
 */
const createFireAlaram = async (floor, room) => {
    // create a new fire alarm
    const fireAlarm = await FireAlarmSensor.create({ floor, room, smoke_level: 0, co2_level: 0 });

    const createdFireAlarm = fireAlarm.get({ plain: true });
    return createdFireAlarm;


}

/**
 * This function will return an array of
 * all the fire alarms
 */
const getAllFireAlarms = async () => {
    // get all fire alarms
    const fireAlarms = (await FireAlarmSensor.findAll()).sort((a, b) => a.id - b.id);

    return fireAlarms;
}

/**
 * This function will return the fire alarm
 * of the given id
 * @param {number} id Id of the fire alarm
 */
const getFireAlarm = async (id) => {
    // get the fire alarm by id
    const fireAlarm = await FireAlarmSensor.findByPk(id);
    return fireAlarm;
}

/**
 * This function will update the fire alarm of the 
 * given id with the given details
 * @param {number} id Id of the fire alarm
 * @param {string} floor Floor of the fire alarm
 * @param {string} room Room of the fire alarm
 * @param {boolean} isActive Active status of the fire alarm
 * @param {number} smokeLevel Smoke level of the fire alarm
 * @param {number} co2Level CO2 level of the fire alarm
 */
const updateFireAlarm = async (id, floor, room, isActive, smokeLevel, co2Level) => {
    // find the fire alarm
    const fireAlarm = await FireAlarmSensor.findByPk(id);

    // update the fire alarm
    await fireAlarm.update({ floor, room, is_active: isActive, smoke_level: smokeLevel, co2_level: co2Level });

    const updatedFireAlarm = fireAlarm.get({ plain: true });
    return updatedFireAlarm;
}


/**
 * This function will delete the fire alarm
 * of the given id
 * @param {number} id Id of the fire alarm
 */
const deleteFireAlarm = async (id) => {
    // find and delete the fire alarm
    const fireAlarm = await FireAlarmSensor.findByPk(id);
    await fireAlarm.destroy();
}


/**
 * This function will update the status of the 
 * fire alarm of the given id with the given details
 * @param {number} id Id of the fire alarm
 * @param {boolean} isActive Active status of the fire alarm
 * @param {number} smokeLevel Smoke level of the fire alarm
 * @param {number} co2Level CO2 level of the fire alarm
 */
const updateFireAlarmStatus = async (id, isActive, smokeLevel, co2Level) => {
    // find fire alarm
    const fireAlarm = await FireAlarmSensor.findByPk(id);
    if (!fireAlarm) {
        return null;
    }

    // update fields
    if (isActive !== undefined) {
        await fireAlarm.update({ isActive: isActive });
    }
    if (smokeLevel !== undefined) {
        await fireAlarm.update({ smoke_level: smokeLevel });
    }
    if (co2Level !== undefined) {
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


