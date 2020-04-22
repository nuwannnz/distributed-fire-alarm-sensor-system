const express = require('express');
const router = express.Router();

const userService = require('../services/user.service');

/* GET users listing. */
router.get('/has-admin', async (req, res, next) => {
  try {
    const hasAdmin = await userService.hasAdmin();
    res.json({ hasAdmin });
  } catch (error) {
    console.error(error);
    res.json({ error: 'Failed to get has admin' });
  }
});

router.post('/signup', async (req, res, next) => {
  const { email, password } = req.body;
  console.log('signup received', `email: ${email}, password: ${password}`);
  try {
    const user = await userService.signup(email, password);
    if (user) {
      res.json({ status: true });
      return;
    }
    res.json({ status: false });
  } catch (error) {
    console.error(error);
    res.json({ error: 'Failed to signup user' });
  }
});

router.post('/login', async (req, res, next) => {
  const { email, password } = req.body;

  try {
    const loginCorrect = await userService.login(email, password);
    if (loginCorrect) {
      const token = userService.generateToken(email);
      res.json({ isAuth: true, token });
      return;
    }
    res.json({ isAuth: false });
  } catch (error) {
    console.error(error);
    res.json({ error: 'Failed to get has admin' });
  }
});



module.exports = router;
