var express = require('express');
var router = express.Router();
const cateCtrl = require('../../controller/api/category.api.ctrl');


router.post('/addCate', cateCtrl.addCate);
router.get("/getCate", cateCtrl.getCate);
router.get('/getOneCate/:id' , cateCtrl.getOneCate);
router.get('/getCateByid/:id' , cateCtrl.getCatebyidComic);
router.get("/getCateLimit" , cateCtrl.getCatelimit);
router.patch('/updateCate/:id', cateCtrl.patchCate);

module.exports = router;