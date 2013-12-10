
We use what we learned in Yamba project to fulfill bookkeeping application.
 
Now we implemented:
-

* Activity: `AddItemActivtiy` and `ShowdataActivity` are extending `BaseActivity`.
* `DbHelper`
 
TODO:
-
*  User can edit tuple which he/her selects in `ShowdataActivity`. (add more method in `DbHelper`)
*  User can set a password to protect his/her data. (Use preference)
* Design a more practical and maintainable relationship(table).
 *  Provide many books.
 *  Provide class.
* And we need to check the data before we insert to DB.
*  We need to provide some analysis tool. 

When we design, we must consider the maintenance-related issue and data integrity. 
For example, when we change the schema of database, we cannot just drop the old tables, 
because user don't want to lose data due to upgrading.
