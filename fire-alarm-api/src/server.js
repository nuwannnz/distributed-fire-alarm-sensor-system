const app = require('./app');
const port = process.env.PORT || 3000;

app.listen(port, (err) => {
    if (err) {
        console.error(err);
    }

    console.log(`Listening on port ${port}`);

})
