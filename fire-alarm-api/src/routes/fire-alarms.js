const express = require('express');
const router = express.Router();
const fireAlarmService = require('../services/fire-alarm.service');
const emailService = require('../services/email.service');
const userService = require('../services/user.service');
const { verifyJWTToken } = require('./middleware');


router.get('/', async (req, res, next) => {
    try {
        // get all fire alarms
        const fireAlarms = await fireAlarmService.getAllFireAlarms();
        res.json(fireAlarms);
    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to get all fire alarms' });
    }
});

router.get('/:id', async (req, res, next) => {
    // extract the id
    const id = req.params.id;
    try {
        // get fire alarm by id
        const fireAlarm = await fireAlarmService.getFireAlarm(id);
        return res.json(fireAlarm);
    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to get all fire alarms' });
    }
});

router.post('/', verifyJWTToken, async (req, res, next) => {
    // extract the floor and the room
    const { floor, room } = req.body;
    try {

        // create the alarm
        const fireAlarm = await fireAlarmService.createFireAlaram(floor, room);

        return res.json(fireAlarm);

    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to create' });
    }
});

router.post('/:id/notify', async (req, res, next) => {
    const { message } = req.body;
    const id = req.params.id;
    try {
        // find the fire alarm and the user emails
        const fireAlarm = await fireAlarmService.getFireAlarm(id);
        const userEmails = await userService.getUserEmails();

        // email all the users
        userEmails.forEach(email => {
            emailService.sendEmail(
                email,
                `WARNING: Status of the fire alarm in ${fireAlarm.room} room on ${fireAlarm.floor} floor`,
                message
            )
        })

        res.json({ success: true });

    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to notify' });
    }
});



router.put('/:id', verifyJWTToken, async (req, res, next) => {
    const { floor, room, is_active, smoke_level, co2_level } = req.body;
    const id = req.params.id;
    try {
        // update the fire alarm
        const updatedFireAlarm = await fireAlarmService.updateFireAlarm(id, floor, room, is_active, smoke_level, co2_level);
        res.json(updatedFireAlarm);
    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to update fire alarm' });
    }
});

router.patch('/:id', async (req, res, next) => {
    // extract the fields
    const { is_active, smoke_level, co2_level } = req.body;
    const id = req.params.id;

    try {
        // update the fire alarm
        const updatedFireAlarm = await fireAlarmService.updateFireAlarmStatus(id, is_active, smoke_level, co2_level);
        res.json(updatedFireAlarm);
    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to update smoke level' });
    }
});


router.delete('/:id', verifyJWTToken, async (req, res, next) => {
    // extract the id
    const id = req.params.id;
    try {
        await fireAlarmService.deleteFireAlarm(id);
        res.json({ deleted: true });
    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to update co2 level' });
    }
});


module.exports = router;
