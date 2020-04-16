const User = require('../models/UserLoginAndRegister')


const checkAuth = async (req, res, next) => {
    const user = await User.findByCredentials(req.body.email, req.body.password)
    user.generateAuthToken()
    res.send(user)
    next();
}

module.exports = checkAuth