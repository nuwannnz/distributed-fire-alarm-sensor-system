
/**
 * Sends an email
 * @param {string} to Email address to send the email
 * @param {string} subject Subject of the email
 * @param {string} message Body of the email
 */
const sendEmail = (to, subject, message) => {
    console.log(`Sending email to ${to}`);
    console.log(`    Sbject: ${subject}`);
    console.log(`    Message: ${message}`);
}

module.exports = {
    sendEmail
}