var user_model = require('../model/userModel')
exports.getHome = (req , res , next) =>{
       
        res.render('home/home');
}