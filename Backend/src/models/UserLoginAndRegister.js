const mongoose = require('mongoose')
const validator = require('validator')
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken')

const userSchema = new mongoose.Schema({
    email : {
        type: String,
        required: true ,
        trim: true ,
        lowercase: true,
        unique: true ,
        validate(value){
            if (!validator.isEmail(value))
                throw new Error('Email is Invalid')
        }
    },
    password : {
        type: String,
        require: true ,
        minlength: 7 ,
        trim: true ,
    },
    tokens: [
        {
            token : {
                type : String,
                requried: true
            }
        }
    ]
})

userSchema.methods.generateAuthToken = async function () {
    const user = this 
    const token = jwt.sign( {_id: user._id.toString() } , 'InterAR')

    user.tokens = user.tokens.concat({token})
    await user.save()

    return token 
}

userSchema.statics.findByCredentials = async function (email, password ) {
    const user = await User.findOne({email})
    if (!user)
        throw new Error ('No Such User Exist')
    const isMatch = await bcrypt.compare(password, user.password)
    if (!isMatch)
        throw new Error ('Password Incorrect !')
    
    return user 
}

const User = mongoose.model('User', userSchema)

module.exports = User