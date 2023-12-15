var express = require('express');
var router = express.Router();
var comic_Ctrl = require('../../controller/api/comic.api.ctrl');
const multer = require('multer');
const updloads = multer({dest : './tmp'});

router.post('/addComic' , updloads.any() , comic_Ctrl.postComic);
router.get('/:id/readComic' ,comic_Ctrl.getContentImage );
router.get('/getAll' ,comic_Ctrl.getAllComic );
router.get('/getComicRamdom' , comic_Ctrl.getComicLimit);
router.get('/getComicSlide' , comic_Ctrl.getComicLimit);
router.get('/:id' , comic_Ctrl.getOneComic);
router.patch('/update/:id' , updloads.any() , comic_Ctrl.editComic);
router.delete('/delete/:id', comic_Ctrl.deleteComic);
router.get('/getComicByidCate/:id' , comic_Ctrl.getAllComicByCate)
module.exports = router;