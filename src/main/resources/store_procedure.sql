-- CREATE PROCEDURE untuk membuat stored procedure
CREATE OR REPLACE PROCEDURE create_user(
    IN user_id UUID,
    IN user_name varchar,
    IN user_email varchar,
    IN user_password varchar
)
LANGUAGE SQL
AS $$
INSERT INTO "users" (id, username, email, password) VALUES (user_id, user_name, user_email, user_password);
$$;

CREATE OR REPLACE PROCEDURE read_user(
    IN user_id UUID
)
LANGUAGE SQL
AS $$
SELECT id, username, email, password FROM "users" WHERE id = user_id;
$$;

CREATE OR REPLACE PROCEDURE update_user(
    IN user_id UUID,
    IN user_name varchar,
    IN user_email varchar,
    IN user_password varchar
)
LANGUAGE SQL
AS $$
UPDATE "users" SET username = user_name, email = user_email, password = user_password WHERE id = user_id;
$$;

CREATE OR REPLACE PROCEDURE delete_user(
    IN user_id UUID
)
LANGUAGE SQL
AS $$
DELETE FROM "users" WHERE id = user_id;
$$;
