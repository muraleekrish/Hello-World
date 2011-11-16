sqlplus /nolog @CreateUser.sql

mysql --user=datrics --password=datrics --host=192.168.1.201 mysql -e "source createuser_mysql.sql"