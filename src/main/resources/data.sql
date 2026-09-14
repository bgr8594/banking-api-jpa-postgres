-- Insertar Clientes
INSERT INTO customers (id, full_name, email, national_id) VALUES
(1, 'Benito Juarez', 'benito@ejemplo.com', '11111111'),
(2, 'Sor Juana Ines', 'sorjuana@ejemplo.com', '22222222'),
(3, 'Miguel Hidalgo', 'hidalgo@ejemplo.com', '33333333')
ON CONFLICT (id) DO NOTHING;

SELECT setval('customers_id_seq', (SELECT MAX(id) FROM customers), true);

-- Insertar Cuentas
INSERT INTO accounts (id, account_number, account_type, balance, customer_id) VALUES
(1, 'ACC-001', 'SAVINGS', 15000.00, 1),
(2, 'ACC-002', 'CHECKING', 5400.50, 1),
(3, 'ACC-003', 'SAVINGS', 23000.00, 2),
(4, 'ACC-004', 'CHECKING', 1200.00, 3)
ON CONFLICT (id) DO NOTHING;

SELECT setval('accounts_id_seq', (SELECT MAX(id) FROM accounts), true);