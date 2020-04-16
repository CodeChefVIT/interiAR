const express = require('express')
const bodyParser = require('body-parser') 
require('./DataBase/mongoose')
const User = require('./models/UserLoginAndRegister')
const hash = require('./middleware/hash')
const checkAuth = require('./middleware/checkAuth')

const app = express();
const port = process.env.PORT || 3000 

app.use(bodyParser.urlencoded({extended: false}))
app.use(bodyParser.json())

app.post('/register',hash, (req, res) => {
    res.status(201).send(req.body)
})

app.post('/login', checkAuth, (req, res) => {
    res.status(401).send()
})

app.listen(port , (req, res) => {
    console.log(`Server Running at Port ${port}`)
})