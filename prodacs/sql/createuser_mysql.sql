use mysql;
drop database IF EXISTS prodacsdailybuild;
create database prodacsdailybuild;
use prodacsdailybuild;
source prodacs_mysql.sql;
commit;