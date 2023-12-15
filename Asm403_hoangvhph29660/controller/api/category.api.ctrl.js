var cateModel = require('../../model/category');

exports.addCate = async (req, res) => {
    try {
        const check = await cateModel.category_Model.findOne({ nameCate: req.body.nameCate });
        if (!check) {
            const cate = new cateModel.category_Model(req.body);

            const kq = await cate.save();

            if (kq) {
                return res.status(200).json({ kq, message: "thành công !" })

            } else {
                return res.status(400).json("thêm không thành công !")
            }

        }
        return res.status(400).json("thể loại đã tôn tại !")


    } catch (error) {
        console.log(error);
        res.status(500).json({ error });
    }
}
exports.getOneCate=async(req , res) =>{
    try {
        data = await cateModel.category_Model.findById(req.params.id)
        if(data){
            return res.status(200).json(data);
        }else{
            return res.status(400).json({message :  "không tìm thấy cate !"});
        }
    } catch (error) {
        res.status(500).json({message :` lỗi getOne cate : ${error} ` })
    }
}

exports.getCate = async (req, res) => {
    try {
        data = await cateModel.category_Model.find()
        return res.status(200).json(data);

    } catch (error) {
        console.log(error)
        res.status().json({ error });
    }

}

exports.getCatelimit = async (req, res) => {
    try {
        data = await cateModel.category_Model.find().limit(5)

        if(data){
            return res.status(200).json(data);
        }else{
            return res.status(400).json({message :  "lấy thể loại không thành công "});
        }
       

    } catch (error) {
        console.log(error)
        res.status().json({ error });
    }

}




exports.patchCate = async (req, res) => {
    try {
        var id_Cate = req.params.id
        const updateCate = await cateModel.category_Model.findByIdAndUpdate(id_Cate, req.body, { new: true });
        const cate = await cateModel.category_Model.findById(id_Cate);
        if (updateCate) {
            return res.status(200).json({ cate, message: "update cate thành công !" })
        } else {
            return res.status(400).json({ message: "update cate không thành công !" })
        }
    } catch (error) {
        res.status(500).json(` lỗi sửa cate : ${error} `)
    }
}

exports.getCatebyidComic=async(req , res) =>{
    console.log(req.params.id)
    try {
        data = await cateModel.category_Model.findOne({id_comics : req.params.id})
        
        if(data){
            return res.status(200).json(data);
        }else{
            return res.status(400).json({message :  "không tìm thấy cate !"});
        }
    } catch (error) {
        res.status(500).json({message :` lỗi getOne cate : ${error} ` })
    }
}

