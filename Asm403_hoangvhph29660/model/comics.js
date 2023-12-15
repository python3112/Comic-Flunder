const db = require('./db');

var comicSchema = new db.mongoose.Schema(
    {
        nameComic: { type: String, required: true },
        description: { type: String , required:true},
        author: { type: String, required: true },
        dateRelease: { type: Date},
        coverImage: { type: String, required: true },
        contentImage: { type: Array },
        id_category: {
            type: db.mongoose.Schema.Types.ObjectId,
            ref: 'categoryModel'
        },
        id_coment:[{
            type: db.mongoose.Schema.Types.ObjectId,
            ref: 'Comment_model'
        }],
      
        
    },
    {
        collection: "tb_comics"
    }
)
let comic_model = db.mongoose.model("comics" , comicSchema);
module.exports =  {comic_model}
