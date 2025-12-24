CREATE TABLE membership (

    membership_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    type VARCHAR(20) UNIQUE NOT NULL CHECK (type IN ('Gold', 'Silver', 'Platinum')),

    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),

    duration INT NOT NULL CHECK (duration > 0) -- Duration in days

);



CREATE TABLE trainer (

    trainer_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    first_name VARCHAR(50) NOT NULL,

    last_name VARCHAR(50) NOT NULL,

    phone VARCHAR(20) NOT NULL,

    email VARCHAR(255) UNIQUE,

    specialization VARCHAR(100)

);



CREATE TABLE member (

    member_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    first_name VARCHAR(50) NOT NULL,

    last_name VARCHAR(50) NOT NULL,

    gender CHAR(1) CHECK (gender IN ('M', 'F', 'O')), 

    date_of_birth DATE NOT NULL,

    phone VARCHAR(20) NOT NULL,

    email VARCHAR(255) UNIQUE,

    registration_date DATE DEFAULT CURRENT_DATE,

    membership_id INT NOT NULL,

    CONSTRAINT fk_member_membership

        FOREIGN KEY (membership_id)

        REFERENCES membership(membership_id)

);



CREATE TABLE gym_class (

    class_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    class_name VARCHAR(100) NOT NULL,

    schedule VARCHAR(50), 

    trainer_id INT NOT NULL,

    CONSTRAINT fk_class_trainer

        FOREIGN KEY (trainer_id)

        REFERENCES trainer(trainer_id)

);



CREATE TABLE class_enrollment (

    enrollment_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    member_id INT NOT NULL,

    class_id INT NOT NULL,

    enrollment_date DATE DEFAULT CURRENT_DATE,

    CONSTRAINT fk_enrollment_member

        FOREIGN KEY (member_id)

        REFERENCES member(member_id),

    CONSTRAINT fk_enrollment_class

        FOREIGN KEY (class_id)

        REFERENCES gym_class(class_id),

    CONSTRAINT uq_enrollment UNIQUE (member_id, class_id)

);



CREATE TABLE payment (

    payment_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    payment_date DATE DEFAULT CURRENT_DATE,

    amount DECIMAL(10,2) NOT NULL CHECK (amount >= 0),

    payment_method VARCHAR(50) CHECK (payment_method IN ('Credit Card', 'Cash', 'Bank Transfer')),

    member_id INT NOT NULL,

    CONSTRAINT fk_payment_member

        FOREIGN KEY (member_id)

        REFERENCES member(member_id)

);

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;


SELECT 'membership' AS table, COUNT(*) FROM membership
UNION ALL SELECT 'trainer', COUNT(*) FROM trainer
UNION ALL SELECT 'member', COUNT(*) FROM member
UNION ALL SELECT 'gym_class', COUNT(*) FROM gym_class
UNION ALL SELECT 'class_enrollment', COUNT(*) FROM class_enrollment
UNION ALL SELECT 'payment', COUNT(*) FROM payment;

SELECT column_name, data_type
FROM information_schema.columns
WHERE table_name = 'gym_class'
ORDER BY ordinal_position;

ALTER TABLE gym_class
ADD COLUMN capacity INT CHECK (capacity > 0);

ALTER TABLE gym_class
ALTER COLUMN schedule TYPE DATE
USING schedule::date;


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


SELECT 'membership' as table_name, COUNT(*) FROM membership
UNION ALL
SELECT 'trainer', COUNT(*) FROM trainer
UNION ALL
SELECT 'member', COUNT(*) FROM member
UNION ALL
SELECT 'gym_class', COUNT(*) FROM gym_class
UNION ALL
SELECT 'class_enrollment', COUNT(*) FROM class_enrollment
UNION ALL
SELECT 'payment', COUNT(*) FROM payment;
