INSERT INTO usuario (
    id, nombre, correo, contraseña, token, activo,
    creado, modificado, ultimo_login, role
) VALUES (
    '11111111-1111-1111-1111-111111111111',
    'Juan Pérez',
    'juan@example.com',
    'Secret@123.$',
    'token-abc-123',
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'ADMIN'
);
INSERT INTO usuario (
    id, nombre, correo, contraseña, token, activo,
    creado, modificado, ultimo_login, role
) VALUES (
    '22222222-2222-2222-2222-222222222222',
    'Pedro Ramirez',
    'pedro@gmail.com',
    'Se-ret@098.$',
    'token-abc-123',
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'ADMIN'
);
INSERT INTO usuario (
    id, nombre, correo, contraseña, token, activo,
    creado, modificado, ultimo_login, role
) VALUES (
    '33333333-3333-3333-3333-333333333333',
    'Elias Cornejo',
    'elias.cornejo@coagra.cl',
    'Eli@sC123.$',
    'token-abc-123',
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'USER'
);