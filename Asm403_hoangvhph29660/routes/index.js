var express = require('express');
var router = express.Router();
var HomeCtrl = require('../controller/homeCtrl');

/* GET home page. */
router.get('/', HomeCtrl.getHome);

module.exports = router;
