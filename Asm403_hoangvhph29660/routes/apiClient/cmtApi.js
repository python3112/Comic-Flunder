var express = require('express');
var router = express.Router();
var cmtCtrl = require('../../controller/api/cmt.api.ctrl');

router.post('/addCmt'  , cmtCtrl.addCmt );
router.get('/getCmt/:id' , cmtCtrl.getCmtbyComic);
router.get('/getCmt/userid/:id' , cmtCtrl.getCmtbyuser);
router.patch('/patchCmt/:id' , cmtCtrl.PatchCmt);
router.delete('/deleteCmt/:id'  , cmtCtrl.deleteCmt);

module.exports = router;