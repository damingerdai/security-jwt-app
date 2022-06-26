INSERT INTO users (
  username,
  nick_name,
  password,
  created_at,
  created_user,
  updated_at,
  updated_user
) VALUES (
	'admin',
	'管理员',
	'12345',
	CURRENT_TIMESTAMP,
	'system',
	CURRENT_TIMESTAMP,
	'system'
) RETURNING id;

INSERT INTO user_roles
    (user_id, role_id, created_at, updated_at)
 SELECT u.id AS user_id, r.id AS role_id, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP
 FROM users AS u JOIN roles AS r ON r.deleted_at IS NULL AND u.deleted_at IS NULL;