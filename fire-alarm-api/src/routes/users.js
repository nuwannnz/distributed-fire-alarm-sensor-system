const express = require('express');
const router = express.Router();

const userService = require('../services/user.service');


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
  // extract email and password
  const { email, password } = req.body;

  try {
    // sign up user
    const user = await userService.signup(email, password);
    if (user) {
      return res.json({ status: true });

    }
    return res.json({ status: false });
  } catch (error) {
    console.error(error);
    res.json({ error: 'Failed to signup user' });
  }
});

router.post('/login', async (req, res, next) => {
  // extract email and password
  const { email, password } = req.body;

  try {
    // check if credentials are valid
    const loginCorrect = await userService.login(email, password);
    if (loginCorrect) {
      const token = userService.generateToken(email);
      return res.json({ isAuth: true, token });

    }
    return res.json({ isAuth: false });
  } catch (error) {
    console.error(error);
    res.json({ error: 'Failed to get has admin' });
  }
});



module.exports = router;
