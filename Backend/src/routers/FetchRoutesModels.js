require('../DataBase/mongoose')
const express = require('express')
const fetch = require('node-fetch')

const router = new express.Router()


// Only Displaying the Images .. Not Storing them in Local Database

router.get('/postImages', async (req, res) => {
    const isAvailable = await fetch(`https://interiardatabase.s3.ap-south-1.amazonaws.com/${req.body.imageName}`)
    if (!isAvailable)
        res.send('Not Available')
    res.send(isAvailable)
})

module.exports = router ;