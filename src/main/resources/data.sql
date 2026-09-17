-- Insertar Clientes
INSERT INTO customers (id, full_name, email, national_id) VALUES
(1, 'Benito Juarez', 'benito@ejemplo.com', '11111111'),
(2, 'Sor Juana Ines', 'sorjuana@ejemplo.com', '22222222'),
(3, 'Miguel Hidalgo', 'hidalgo@ejemplo.com', '33333333')
ON CONFLICT (id) DO NOTHING;

-- Insertar Cuentas
-- 2. Insertar cuentas asignando customer_id
INSERT INTO accounts (account_number, account_type, balance, customer_id)
VALUES
  ('12345678901', 'SAVINGS', 1500.00, 1),
  ('12345678902', 'CHECKING', 5000.00, 2),
  ('12345678903', 'SAVINGS', 250.00, 3);
