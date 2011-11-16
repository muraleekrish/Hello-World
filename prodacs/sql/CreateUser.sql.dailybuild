connect sys/datrics@test as sysdba

BEGIN 
 FOR I IN (SELECT SID, SERIAL# FROM V$SESSION WHERE USERNAME = 'PRODACSDAILYBUILD') LOOP 
  EXECUTE IMMEDIATE 'ALTER SYSTEM KILL SESSION ''' || I.SID || ',' || I.SERIAL# || '''';
 END LOOP; 
END;
/

DROP USER PRODACSDAILYBUILD CASCADE;

CREATE USER prodacsdailybuild IDENTIFIED BY prodacsdailybuild;

GRANT connect,resource TO prodacsdailybuild;

GRANT dba TO prodacsdailybuild;

CONNECT prodacsdailybuild/prodacsdailybuild@test;

@PRODACS_ORACLE.sql;

commit;
