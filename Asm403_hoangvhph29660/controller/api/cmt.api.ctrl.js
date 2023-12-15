var cmttModel = require('../../model/comment');
var userModel = require('../../model/userModel');
var comicModel = require('../../model/comics');


exports.addCmt = async (req, res) => {
    try {
        const cmt = new cmttModel.CommentModel(req.body);
        const kq = await cmt.save();
        if (req.body.userId) {
            const comic = await comicModel.comic_model.findById(req.body.comicId);
            await comic.updateOne({ $push: { id_coment: cmt._id } })
        }

        if (kq) {
            return res.status(200).json(cmt)
        }else{
            return res.status(200).json({message :  "thêm cmt không thành công "})
        }

    } catch (error) {
        console.log(error);
        res.status(500).json({ error });
    }
}

exports.getCmtbyComic = async (req, res) => {
    try {
        const check = await cmttModel.CommentModel.find({ comicId: req.params.id });
        if (check) {
            return res.status(200).json( check )
        }else{
            return res.status(400).json({message :  "không lấy được comments "} )
        }
    } catch (error) {
        console.log(error);
        res.status(500).json({ error });
    }
}

exports.getCmtbyuser = async (req, res) => {
    let id = req.params.id;
    console.log(id);
    try {
        const comments = await cmttModel.CommentModel.find({ userId: id });
        if (comments) {
            return res.status(200).json({ comments, message: "Lấy thành công cmt theo id người dùng" });
        } else {
            return res.status(400).json({ message: "Không tìm thấy bình luận cho userId đã cho" });
        }
    } catch (error) {
        console.log(error);
        res.status(500).json({ error });
    }
};

exports.PatchCmt = async (req, res) => {
    try {
        const kq = await cmttModel.CommentModel.findByIdAndUpdate(req.params.id , req.body , {new : true});
        if (kq) {
            return res.status(200).json(kq)
        }else{
            return res.json({message : "sửa không thành công "})
        }

    } catch (error) {
        console.log(error);
        res.status(500).json(error);
    }
}

exports.deleteCmt = async (req, res) => {

    try {
        const id_cmt = req.params.id;
        const kq = await cmttModel.CommentModel.findByIdAndDelete(id_cmt);
        await comicModel.comic_model.updateMany({id_coment:id_cmt} , {$pull:{id_coment:id_cmt}});
        if (kq) {
            res.status(200).json({message:"xóa thành công !"});
        }
    } catch (error) {
        console.log(` lỗi xóa cmmt : ${error} `)
        res.status(500).json("lỗi xóa cmt : " + error)
    }



}






