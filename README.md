# todoApp

The ToDo backend app can:
- signup and login to app,
- create ToDo tasks,
- update status for ToDo tasks,
- show all tasks to do and done,
- email sender for everyone todos tasks at 7:00 a.m.

# Endpoints description
POST /auth/singup - register new user
POST /auth/login - login user and return token for him
GET /account/todo - getting all todos tasks for user
GET /account/done - getting all done tasks for user
POST /account/todo - create new task 
PUT /account/todo - update status for task

All endpoints in /account need to have valid token
