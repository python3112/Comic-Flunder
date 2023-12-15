const { default: mongoose } = require('mongoose');
var db = require('./db');

var userSchema = db.mongoose.Schema(
    {
        avata: { type: String },
        username: { type: String, required: true },
        password: { type: String, required: true },
        email: { type: String, required: true },
        fullname: { type: String, required: true },
        role: { type: String, required: true },
    },
    {
        collection: 'table_user'
    }
);

let user_model = db.mongoose.model("users_model", userSchema);
module.exports = { user_model };

