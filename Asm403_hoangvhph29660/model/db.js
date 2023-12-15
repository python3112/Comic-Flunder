var mongoose = require('mongoose');

mongoose.connect(process.env.MONGODB_URL).then(()=>{
    console.log("kết nối thành công !")
}).catch((e)=>{
    console.log("lỗi kết nối mongodb : " + e)
})

module.exports = {mongoose};