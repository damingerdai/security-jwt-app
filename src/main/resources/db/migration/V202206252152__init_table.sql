-- Extension for generating random uuids
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS users (
    id           UUID NOT NULL DEFAULT Gen_random_uuid (),
    username     VARCHAR(50) NOT NULL,
    nick_name    VARCHAR(100) NOT NULL,
    password     VARCHAR(100) NOT NULL,
    created_at   TIMESTAMPTZ NOT NULL,
    created_user VARCHAR(50) NOT NULL,
    updated_at   TIMESTAMPTZ NOT NULL,
    updated_user VARCHAR(50) NOT NULL,
    deleted_at   TIMESTAMPTZ,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS users_username ON users USING btree (username);

CREATE TABLE IF NOT EXISTS roles
  (
     id                 UUID NOT NULL DEFAULT Gen_random_uuid ()
     , role_name        VARCHAR(50) NOT NULL
     , role_description VARCHAR(50) NOT NULL
     , created_at       TIMESTAMPTZ NOT NULL
     , updated_at       TIMESTAMPTZ
     , deleted_at       TIMESTAMPTZ,
     PRIMARY KEY (id),
     CONSTRAINT roles_unique_name UNIQUE (role_name)
  );

 CREATE TABLE IF NOT EXISTS user_roles
    (
       id           UUID NOT NULL DEFAULT Gen_random_uuid ()
       , user_id    UUID NOT NULL
       , role_id    UUID NOT NULL
       , created_at TIMESTAMPTZ NOT NULL
       , updated_at TIMESTAMPTZ
       , deleted_at TIMESTAMPTZ,
       PRIMARY KEY (id),
       FOREIGN KEY (user_id) REFERENCES users (id),
       FOREIGN KEY (role_id) REFERENCES roles (id),
       CONSTRAINT user_roles_unique_user_role_id UNIQUE (user_id, role_id)
   );

CREATE INDEX IF NOT EXISTS user_roles_user_id_idx ON user_roles USING btree (user_id);

CREATE INDEX IF NOT EXISTS user_roles_role_id_idx ON user_roles USING btree (role_id);

INSERT INTO roles
            (role_name
             , role_description
             , created_at
             , updated_at)
VALUES      ('ADMIN'
             , 'System administrator access'
             , CURRENT_TIMESTAMP
             , CURRENT_TIMESTAMP),
            ('USER'
             , 'Standard User'
             , CURRENT_TIMESTAMP
             , CURRENT_TIMESTAMP)