const express = require('express');
const router = express.Router();
const fireAlarmService = require('../services/fire-alarm.service');

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

router.post('/', async (req, res, next) => {
    const { floor, room } = req.body;
    try {

        const fireAlarm = await fireAlarmService.createFireAlaram(floor, room);


        res.json(fireAlarm);

    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to create' });
    }
});

router.patch('/:id', async (req, res, next) => {
    const { floor, room } = req.body;
    const id = req.params.id;
    try {
        const updatedFireAlarm = await fireAlarmService.updateFireAlarm(id, floor, room);
        res.json(updatedFireAlarm);
    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to update fire alarm' });
    }
});

router.patch('/:id/smoke', async (req, res, next) => {
    const { smoke_level } = req.body;
    const id = req.params.id;
    try {
        const updatedFireAlarm = await fireAlarmService.updateFireAlarmSmokeLevel(id, smoke_level);
        res.json(updatedFireAlarm);
    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to update smoke level' });
    }
});


router.patch('/:id/co2', async (req, res, next) => {
    const { co2_level } = req.body;
    const id = req.params.id;
    try {
        const updatedFireAlarm = await fireAlarmService.updateFireAlarmCo2Level(id, co2_level);
        res.json(updatedFireAlarm);
    } catch (error) {
        console.error(error);
        res.json({ error: 'Failed to update co2 level' });
    }
});

router.delete('/:id', async (req, res, next) => {
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
