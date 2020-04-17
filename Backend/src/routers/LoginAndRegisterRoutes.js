require('../DataBase/mongoose')
const express = require('express')
const User = require('../models/UserLoginAndRegister')
const hash = require('../middleware/hash')
const checkAuth = require('../middleware/checkAuth')

const router = new express.Router()

router.post('/register',hash, (req, res) => {
    res.status(201).send(req.body)
})

router.post('/login', checkAuth, (req, res) => {
    res.status(401).send()
})

module.exports = router
