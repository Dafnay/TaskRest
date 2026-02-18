INSERT INTO task (created_at, deadline, id, title, description) VALUES ('2026-02-11 10:00:00', '2026-02-20 23:59:59', 1, 'Mi tarea', 'Descripción de la tarea');
INSERT INTO task (created_at, deadline, id, title, description) VALUES (CURRENT_TIMESTAMP, TIMESTAMPADD(DAY, 2, CURRENT_TIMESTAMP), NEXTVAL('task_seq'), 'Estudiar para el examen', 'Revisar los temas de matemáticas y física para el próximo examen.');

INSERT INTO user_entity (id, email, username, password, is_admin) VALUES (NEXTVAL('user_entity_seq'), 'pepe@example.com','pepe','{noop}12345',false);

UPDATE task SET author_id = CURRVAL('user_entity_seq');