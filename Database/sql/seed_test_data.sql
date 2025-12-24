-- =========================================================================================
-- Seed Data Script for Gym Management System Verification
-- HOW TO RUN:
-- 1. Ensure the PostgreSQL schema (tables) are already created.
-- 2. Execute this script using psql or a database tool: psql -d gym_db -f seed_test_data.sql
-- =========================================================================================

-- 1. MEMBERSHIPS
-- Constraints: type IN ('Gold', 'Silver', 'Platinum')
INSERT INTO membership (type, duration, price) VALUES
('Silver', 30, 300.00),
('Gold', 90, 800.00),
('Platinum', 365, 2500.00)
ON CONFLICT (type) DO NOTHING;

-- 2. TRAINERS
INSERT INTO trainer (first_name, last_name, specialization, phone, email) VALUES
('Mike', 'Tyson', 'Boxing', '555-9999', 'mike@gym.com'),
('Sarah', 'Connor', 'Cardio', '555-8888', 'sarah@gym.com')
ON CONFLICT (email) DO NOTHING;

-- 3. MEMBERS
-- Constraints: gender IN ('M', 'F', 'O')
-- Using subquery to fetch membership_id dynamically based on type 'Gold', 'Silver', 'Platinum'
INSERT INTO member (first_name, last_name, gender, date_of_birth, phone, email, registration_date, membership_id) VALUES
('John', 'Doe', 'M', '1990-05-15', '555-0101', 'john@example.com', '2025-01-10', (SELECT membership_id FROM membership WHERE type='Gold' LIMIT 1)),
('Jane', 'Smith', 'F', '1985-08-22', '555-0102', 'jane@example.com', '2025-02-01', (SELECT membership_id FROM membership WHERE type='Silver' LIMIT 1)),
('Ali', 'Veli', 'M', '2000-01-10', '555-0103', 'ali@example.com', '2025-03-05', (SELECT membership_id FROM membership WHERE type='Platinum' LIMIT 1))
ON CONFLICT (email) DO NOTHING;

-- 4. GYM CLASSES
-- Note: Table name is gym_class per user request
INSERT INTO gym_class (class_name, schedule, capacity, trainer_id) VALUES
('Morning Boxing', '2025-12-01', 20, (SELECT trainer_id FROM trainer WHERE first_name='Mike' LIMIT 1)),
('Evening Yoga', '2025-12-01', 15, (SELECT trainer_id FROM trainer WHERE first_name='Sarah' LIMIT 1));

-- 5. CLASS ENROLLMENTS
-- Note: Table name is class_enrollment
-- Constraints: UNIQUE(member_id, class_id)
INSERT INTO class_enrollment (member_id, class_id, enrollment_date) VALUES
(
    (SELECT member_id FROM member WHERE email='john@example.com'),
    (SELECT class_id FROM gym_class WHERE class_name='Morning Boxing' LIMIT 1),
    '2025-12-01'
),
(
    (SELECT member_id FROM member WHERE email='jane@example.com'),
    (SELECT class_id FROM gym_class WHERE class_name='Morning Boxing' LIMIT 1),
    '2025-12-02'
)
ON CONFLICT DO NOTHING;

-- 6. PAYMENTS
-- Constraints: payment_method IN ('Credit Card', 'Cash', 'Bank Transfer')
INSERT INTO payment (payment_date, amount, payment_method, member_id) VALUES
('2025-11-20', 800.00, 'Credit Card', (SELECT member_id FROM member WHERE email='john@example.com')),
('2025-11-21', 300.00, 'Cash', (SELECT member_id FROM member WHERE email='jane@example.com'));
