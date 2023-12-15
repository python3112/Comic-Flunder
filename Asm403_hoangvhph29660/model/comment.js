const db = require('./db');

const CommentSchema = db.mongoose.Schema({
    userId: {
        type: db.mongoose.Schema.Types.ObjectId,
        ref: 'users_model',
        required: true

    },
    comicId: {
        type: db.mongoose.Schema.Types.ObjectId,
        ref: 'comics',
    },
    content: {
        type: String,
        required: true
    },
    dateTime: {
        type: Date,
        default: Date.now
    }

},
    {
        collection: 'tb_comments'
    }
);

const CommentModel = db.mongoose.model('Comment_model', CommentSchema);

module.exports = { CommentModel };