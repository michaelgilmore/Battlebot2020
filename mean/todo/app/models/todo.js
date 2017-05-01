// app/models/todo.js

// load mongoose since we need it to define a model
var mongoose = require('mongoose');

module.exports = mongoose.model('Todo', {
    text : String,
    label : String,
    date_entered : {type: Date, default: Date.now},
    date_due : Date,
    date_completed : Date,
    date_deleted : Date
});
