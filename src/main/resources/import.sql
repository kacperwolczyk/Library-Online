insert into user_role (description, role_name) values ("admin role", "ROLE_ADMIN")
insert into user_role (description, role_name) values ("user role", "ROLE_USER")

insert into user (email, first_name, hire_charge, last_name, password, pesel, status) values ("admin@gmail.com", "adminFName", 0, "adminLName", "{bcrypt}$2a$10$fBQjWkZVSb4TMzvIRLoLe.FYDEJD/yTYU7971tADXyo9F/NQfndcq","00000000000", "active")
insert into user_roles (user_user_id, roles_id) values (1, 1)
