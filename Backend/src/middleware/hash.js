const bcrypt = require('bcrypt')
const User = require('../models/UserLoginAndRegister')



const hash = async (req, res, next) => {
    email = req.body.email
    password = await bcrypt.hash(req.body.password, 8)

    const user = new User ({email, password })

    try {
        const token = await user.generateAuthToken()
        res.status(201).send({user, token})
    } catch(e){
        res.status(400).send(e)
    }

    next ();
}

module.exports = hash