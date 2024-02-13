insert into users(id, join_date, name, password, ssn) values (90001, now(),'user1','1234','1111-1111');
insert into users(id, join_date, name, password, ssn) values (90002, now(),'user2','2222','2222-2222');
insert into users(id, join_date, name, password, ssn) values (90003, now(),'user3','3333','3333-3333');

insert into post(description, user_id) values ('post 1', 90001);
insert into post(description, user_id) values ('post 2', 90001);
