db.todos.update({}, {$set: {date_entered: Date.now()}}, {multi: true});
db.todos.update({}, {$set: {date_due: null}}, {multi: true});
db.todos.update({}, {$set: {label: null}}, {multi: true});
db.todos.update({}, {$set: {category: null}}, {multi: true});
db.todos.update({}, {$set: {date_completed: null}}, {multi: true});
db.todos.update({}, {$set: {date_deleted: null}}, {multi: true});
