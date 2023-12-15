const express = require('express');
const router = express.Router();
const multer = require('multer');
const uploads = multer({dest:'./tmp'})

const apiCtrl = require('../../controller/api/user.api.ctrl');


router.get('/users' ,apiCtrl.getUsers);
router.get('/users/login/:username/:password', apiCtrl.LoginUser);
router.post('/users/register' , uploads.single('avata') , apiCtrl.AddUser);
router.patch('/users/update/:id', uploads.single('avata') , apiCtrl.UpdatePatch);
router.delete('/users/delete/:id' , apiCtrl.DeleteUser);
router.get('/users/:id' ,apiCtrl.getOne);
module.exports= router;