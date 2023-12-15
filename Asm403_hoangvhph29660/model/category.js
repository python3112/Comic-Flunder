var db = require('./db');

var categorySchema = new db.mongoose.Schema(
    {
        nameCate: { type: String , required: true }, 
        id_comics:[{
            type:db.mongoose.Schema.Types.ObjectId,
            ref:'comics'
        }]
    },
    {
        collection:'tb_categories'
    }
);

let category_Model = db.mongoose.model("categoryModel",categorySchema);

module.exports = {category_Model};

