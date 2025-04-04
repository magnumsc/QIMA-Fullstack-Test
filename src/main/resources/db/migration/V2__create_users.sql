-- V2__create_users.sql

CREATE TABLE "qima_interview"."users" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role INTEGER,
    guid UUID DEFAULT gen_random_uuid()
);

--userModel pass = userModel, assuming bcrypt hash key "xxx"
insert into "qima_interview"."users" (username, password, role) values ('user', '$2a$10$EPS01UgM96zcviZWpZOLzem.f/aV51cuC3Sv/EXXjYBUs4Uv3W9h.', 1);
--admin pass = admin, assuming bcrypt hash key "xxx"
insert into "qima_interview"."users" (username, password, role) values ('admin', '$2a$10$SpyGhKw/j5Xchu5EcWvAROVfUomS4lEkVcToM7WP9DzBsF55xITA2', 2);
