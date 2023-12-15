var userModel = require('../../model/userModel');
var cmttModel = require('../../model/comment');
const { json } = require('express');
const fsPromises = require('fs').promises;
const path = require("path");
const url_fileSave_avata = './public/uploads/avata/';


exports.getUsers = async (req, res) => {
   
    try {
        data = await userModel.user_model.find({role : "user"});
        return res.status(200).json(data);

    } catch (error) {
        console.log(error)
        res.status().json({ error });
    }
}

exports.LoginUser = async (req, res) => {
    try {
        console.log(req.params.username , req.params.password )
        const data = await userModel.user_model.findOne({ username: req.params.username, password: req.params.password });
           
        if (!data) {
            return res.status(400).json({message:"không có user "});
        }
        return res.status(200).json(data);

    } catch (error) {
        console.log(error);
        res.status(500).json({ error });
    }
}

exports.getOne = async (req, res) => {
    const id = req.params.id;  
    try {
       
        const data = await userModel.user_model.findById(id);
        return res.status(200).json(data);

    } catch (error) {
        console.log(error);
        res.status(500).json({ error });
    }
}

exports.AddUser = async (req, res) => {
    
    try {
        const check = await userModel.user_model.findOne({ username: req.body.username });

        if (!check) {
            const user = new userModel.user_model();

            if (req.file) {
                try {
                    await fsPromises.rename(req.file.path, url_fileSave_avata + req.body.username + '-' + req.file.originalname);

                    user.avata = req.body.username + '-' + req.file.originalname;
                } catch (err) {
                    console.log("Lỗi thêm người dùng: " + err);
                    res.json(`lỗi thêm người dùng  :  ${err.message}${req.file.originalname}`);
                    throw err;
                }
            } else {
                user.avata = "nomarl_avata.png";
            }


            user.username = req.body.username;
            user.password = req.body.password;
            user.email = req.body.email;
            user.fullname = req.body.fullname;
            user.role = req.body.role;
           
            const kq = await user.save();

            if (kq) {
                return res.status(200).json({ kq, message: "thành công !" })

            } else {
                return res.status(400).json({ message: "thêm không thành công !" })
            }

        }
        return res.status(401).json({message:"user đã tôn tại !"})
    } catch (error) {
        res.json("lỗi thêm user : " + error);
    }
}

exports.UpdatePatch = async (req, res) => {

    const id_req = req.params.id;
    console.log(req.file)
    console.log(req.body)

    try {
        const updateUserFields = {};
        const user_update_none = await userModel.user_model.findById(id_req);
        const Old_avata = user_update_none.avata;
        if (req.file) {
            if (Old_avata !== "nomarl_avata.png") {
                await fsPromises.unlink(path.join(url_fileSave_avata, user_update_none.avata))
            }
            const newlinkAvata = path.join(url_fileSave_avata, req.body.username + "-" + req.file.originalname);
            await fsPromises.rename(req.file.path, newlinkAvata, (err) => {
                if (err) {
                    res.json("lỗi sửa ảnh mới : " + err);
                    throw err;

                }
            })
            updateUserFields.avata = req.body.username + '-' + req.file.originalname;

        }
        if (req.body.username) updateUserFields.username = req.body.username;
        if (req.body.password) updateUserFields.password = req.body.password;
        if (req.body.email) updateUserFields.email = req.body.email;
        if (req.body.fullname) updateUserFields.fullname = req.body.fullname;
        if (req.body.role) updateUserFields.role = req.body.role;

        const kq = await userModel.user_model.findByIdAndUpdate(id_req, updateUserFields, { new: true });
        if (kq) {
            return res.status(200).json({ kq, message: "update thành công !" })
        } else {
            return res.status(400).json({ message: "update không thành công !" })
        }


    } catch (error) {
        res.json("lỗi updatePatch : " + error);

    }

}



exports.DeleteUser = async (req, res) => {
    var err = "";
    let id = req.params.id;
    try {
        const kqDelete = await userModel.user_model.findByIdAndDelete(id);
        await cmttModel.CommentModel.deleteMany({userId : id})
        if (kqDelete) {
            err = "delete thành công !"
            
            return res.status(200).json({ message: err });
        } else {
            err = ` lỗi delete id :  ${id}`
            return res.status(400).json({ message: err })
        }
    } catch (error) {
        res.status(500).json(error);
    }


}


