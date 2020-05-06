const app = require('./app');
const port = process.env.PORT || 5000;


// create the server
app.listen(port, (err) => {
    if (err) {
        console.error(err);
    }
    console.log(`Listening on port ${port}`);

})
