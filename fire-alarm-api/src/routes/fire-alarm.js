const express = require('express');
const router = express.Router();
const fireAlarmService = require('../services/fire-alarm.service');
const emailService = require('../services/email.service');
const userService = require('../services/user.service');
const { verifyJWTToken } = require('./middleware');

/* GET users listing. */
router.get('/', async (req, res, next) => {
    try {

        const fireAlarms = await fireAlarmService.getAllFireAlarms();
        res.json(fireAlarms);
    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to get all fire alarms' });
    }
});

router.get('/:id', async (req, res, next) => {
    const id = req.params.id;
    try {
        const fireAlarm = await fireAlarmService.getFireAlarm(id);
        res.json(fireAlarm);
    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to get all fire alarms' });
    }
});

router.post('/', verifyJWTToken, async (req, res, next) => {
    const { floor, room } = req.body;
    try {

        const fireAlarm = await fireAlarmService.createFireAlaram(floor, room);


        res.json(fireAlarm);

    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to create' });
    }
});

router.post('/:id/notify', async (req, res, next) => {
    const { message } = req.body;
    const id = req.params.id;
    try {

        const fireAlarm = await fireAlarmService.getFireAlarm(id);
        const userEmails = await userService.getUserEmails();

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
    const { is_active, smoke_level, co2_level } = req.body;
    const id = req.params.id;

    try {

        const updatedFireAlarm = await fireAlarmService.updateFireAlarmStatus(id, is_active, smoke_level, co2_level);
        res.json(updatedFireAlarm);
    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to update smoke level' });
    }
});


router.delete('/:id', verifyJWTToken, async (req, res, next) => {
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
