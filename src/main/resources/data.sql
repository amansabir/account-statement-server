insert into account (id,created_date,last_modified_date,created_by, last_modified_by, account_type, account_number) values (1201,SYSDATE, SYSDATE, 'SYSTEM','SYSTEM', 'SAVINGS', '123456723');
insert into account (id,created_date,last_modified_date,created_by, last_modified_by, account_type, account_number) values (1202,SYSDATE, SYSDATE, 'SYSTEM','SYSTEM', 'SAVINGS', '123456724');



insert into statement (id,created_date,last_modified_date,created_by, last_modified_by, account_id, date, amount) values(10001, SYSDATE, SYSDATE, 'SYSTEM','SYSTEM', 1201, '2021-01-01','10000');
insert into statement (id,created_date,last_modified_date,created_by, last_modified_by, account_id, date, amount) values(10002, SYSDATE, SYSDATE, 'SYSTEM','SYSTEM', 1201, '2021-01-02','20000');
insert into statement (id,created_date,last_modified_date,created_by, last_modified_by, account_id, date, amount) values(10003, SYSDATE, SYSDATE, 'SYSTEM','SYSTEM', 1201, '2021-01-03','34000');
insert into statement (id,created_date,last_modified_date,created_by, last_modified_by, account_id, date, amount) values(10004, SYSDATE, SYSDATE, 'SYSTEM','SYSTEM', 1201, '2021-02-01','40000');
insert into statement (id,created_date,last_modified_date,created_by, last_modified_by, account_id, date, amount) values(10005, SYSDATE, SYSDATE, 'SYSTEM','SYSTEM', 1201, '2021-03-02','50000');
insert into statement (id,created_date,last_modified_date,created_by, last_modified_by, account_id, date, amount) values(10006, SYSDATE, SYSDATE, 'SYSTEM','SYSTEM', 1201, '2021-04-03','34000');
insert into statement (id,created_date,last_modified_date,created_by, last_modified_by, account_id, date, amount) values(10007, SYSDATE, SYSDATE, 'SYSTEM','SYSTEM', 1202, '2021-04-02','54000');


insert into user(id,created_date,last_modified_date,created_by, last_modified_by,username,password,role) values (10001, SYSDATE, SYSDATE, 'SYSTEM','SYSTEM','admin','$2y$12$5.wtJ8/ys6IDyU5a0FKCt.aue30okSlCIYcIIkQNiH/AfyRLXzbh2','ADMIN');
insert into user(id,created_date,last_modified_date,created_by, last_modified_by,username,password,role) values (10002, SYSDATE, SYSDATE, 'SYSTEM','SYSTEM','user','$2y$12$wGgRMPpadyJjV6yPU6fUvOcDc.2814WWSQdsnkGCZuxAmnMcQo8my','USER');
