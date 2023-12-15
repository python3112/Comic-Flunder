var comicModel = require('../../model/comics');
var cateModel = require('../../model/category');
var cmt_model = require('../../model/comment');
const  user_Model = require('../../model/userModel');
const fspro = require('fs').promises;
const path = require('path');
const url_Save_Coverimg = './public/uploads/coverImage/';
const url_Save_contentImg = './public/uploads/contentImage/';


const renameComic = (name) => {
   const withoutAccents  = name.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");
    const withoutSpaces = withoutAccents.replace(/\s/g, '');
    return withoutSpaces;
}

exports.postComic = async (req, res) => {
    try {
        var coverImagePost = "";
        var contentImagePost = [];
        const nam = await comicModel.comic_model.findOne({ nameComic: req.body.nameComic });
        if (!nam) {
            const comic = new comicModel.comic_model();
            const nameSave = renameComic(req.body.nameComic);
            const files = req.files;
            if (files.length > 0) {
                files.forEach(link => {
                    if (link.fieldname === 'coverImage') {
                        //// dùng fs thay đổi tên cover ///////
                        fspro.rename(link.path, url_Save_Coverimg + nameSave + "_cover_" + link.originalname);

                        coverImagePost = nameSave + "_cover_" + link.originalname;

                    } else if (link.fieldname === 'contentImage') {

                        fspro.rename(link.path, url_Save_contentImg + nameSave + '_content_' + link.originalname);

                        contentImagePost.push(nameSave + '_content_' + link.originalname)
                    }

                });
            };
            comic.nameComic = req.body.nameComic;
            comic.description = req.body.description;
            comic.author = req.body.author;
            comic.dateRelease = req.body.dateRelease;
            comic.coverImage = coverImagePost;
            comic.contentImage = contentImagePost;
            comic.id_category = req.body.id_category;
            const kq = await comic.save();
            if (req.body.id_category) {
                const cate = await cateModel.category_Model.findById(req.body.id_category);

                await cate.updateOne({ $push: { id_comics: comic._id } })
            }

            if (kq) {
                return res.status(200).json({ comic, message: "thêm thanh công !" });
            } else {
                return res.status(400).json({ message: "thêm thất bại !" });
            }


        }
        return res.status(400).json({ message: "đã có truyền cùng tên !" })
    } catch (error) {
        console.log("lỗi khi thêm " + error);
        res.status(500).json({ message: "lỗi " + error })
    }
}


exports.getAllComic = async (req, res) => {
    try {
        const comcis = await comicModel.comic_model.find();
        if (comcis) {
            return res.status(200).json({ comcis, message: "lấy all truyện thành công" });
        }
    } catch (error) {
        console.log("getAllComic err : " + error)
        res.status(500).json({ message: "getAllComic err : " + error })
    }
}

exports.getComicLimit = async (req, res) => {
    try {
        const comics = await comicModel.comic_model.aggregate([
            { $sample: { size: 12 } }
        ]);

        if (comics) {
            return res.status(200).json(comics);
        }
    } catch (error) {
        console.log("getAllComic err : " + error);
        res.status(500).json({ message: "getAllComic err : " + error });
    }
}


exports.getComicSlide = async (req, res) => {
    try {
        const comics = await comicModel.comic_model.aggregate([
            { $sample: { size: 4 } }
        ]);

        if (comics) {
            return res.status(200).json(comics);
        }
    } catch (error) {
        console.log("getAllComic err : " + error);
        res.status(500).json({ message: "getAllComic err : " + error });
    }
}

exports.getContentImage = async (req, res) => {
    try {
        const contenImg = await comicModel.comic_model.findById(req.params.id);
        if (contenImg) {
            return res.status(200).json(contenImg.contentImage)
        }
        return res.status(400).json({ message: "getContentImage : không lấy được ảnh để đọc " })
    } catch (error) {
        console.log(" getContentImage : " + error)
        res.status(500).json({ message: "getContentImage : " + error })
    }
}

exports.getOneComic = async (req, res) => {
    try {
        const comic = await comicModel.comic_model.findById(req.params.id)

        if (comic) {
            return res.status(200).json(comic)

        }
        return res.status(400).json("không tìm thấy truyện này !")
    } catch (error) {
        console.log(" xem chi tiết comic : " + error)
        res.status(500).json({ message: "getOneComic  : " + error })
    }
}

exports.editComic = async (req, res) => {
    try {
        const comicUpdate = {};


    } catch (error) {
        console.log("lỗi sửa comic :  " + error);
        res.status(500).json({
            message: error
        })
    }
}


exports.deleteComic = async (req, res) => {
    try {
        const  comicId  = req.params.id;
        // Tìm thông tin của comic cần xóa
        const comic = await comicModel.comic_model.findById(comicId);

        if (!comic) {
            console.log('Comic not found.');
            return;
        }

        // Xóa các comment liên quan đến comic
        await cmt_model.CommentModel.deleteMany({ comicId: comicId });

        // Xóa id của comic khỏi bảng category
        if (comic.id_category) {
            await cateModel.category_Model.updateOne(
                { _id: comic.id_category },
                { $pull: { id_comics: comic._id } }
            );
        }
        
        // Xóa comic
        const kq = await comicModel.comic_model.findByIdAndDelete(comicId);
        const oldImagePath = path.join("./public/uploads/coverImage", kq.coverImage);
        
       await fspro.unlink(oldImagePath);
        for (const image of kq.contentImage) {
          const oldImagePath = path.join("./public/uploads/contentImage/", image);
         await fspro.unlink(oldImagePath);
        }
       
    } catch (error) {
        console.error('Error deleting comic:', error);
    }
    return res.status(200).json({message : 'delete thành công !'})
}

exports.getAllComicByCate = async (req, res) => {
    try {
        const comcis = await comicModel.comic_model.find({id_category :  req.params.id});
        if (comcis) {
            return res.status(200).json({ comcis, message: "lấy all truyện thành công" });
        }
    } catch (error) {
        console.log("getAllComic err : " + error)
        res.status(500).json({ message: "getAllComic err : " + error })
    }
}