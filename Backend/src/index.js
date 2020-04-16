const express = require('express')
const bodyParser = require('body-parser') 
const loginAndRegisterRoutes = require('./routers/LoginAndRegisterRoutes')
const fetchRoutesModels = require('./routers/FetchRoutesModels')

const app = express();
const port = process.env.PORT || 3000 

app.use(bodyParser.urlencoded({extended: false}))
app.use(bodyParser.json())

app.use(fetchRoutesModels)
//app.use(loginAndRegisterRoutes)

app.listen(port , (req, res) => {
    console.log(`Server Running at Port ${port}`)
})