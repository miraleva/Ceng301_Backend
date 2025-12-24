-- Report 1: Oldest Member
-- Returns: member_id, first_name, last_name, date_of_birth, age_years
-- Description: Finds the single oldest member based on date_of_birth.
-- Efficiency: Uses ORDER BY with LIMIT 1 on the DB side.

SELECT 
    member_id, 
    first_name, 
    last_name, 
    date_of_birth,
    EXTRACT(YEAR FROM AGE(CURRENT_DATE, date_of_birth)) AS age_years
FROM 
    member
ORDER BY 
    date_of_birth ASC
LIMIT 1;

--------------------------------------------------------------------------------

-- Report 2: Most Popular Class
-- Returns: class_id, class_name, enrollment_count
-- Description: Classes ranked by number of active enrollments.
-- Efficiency: Performs aggregation (COUNT) and sorting on the DB side.

SELECT 
    c.class_id, 
    c.class_name, 
    COUNT(ce.enrollment_id) AS enrollment_count
FROM 
    gym_class c
LEFT JOIN 
    class_enrollment ce ON c.class_id = ce.class_id
GROUP BY 
    c.class_id, c.class_name
ORDER BY 
    enrollment_count DESC
LIMIT 1;

--------------------------------------------------------------------------------

-- Report 3: Monthly Revenue
-- Returns: year, month, total_revenue
-- Description: Calculates total revenue for a specific month/year.
-- Efficiency: Aggregates payment amounts on the DB side.
-- Note: Replace 2024 and 12 with desired Year/Month parameters.

SELECT 
    EXTRACT(YEAR FROM payment_date) AS year,
    EXTRACT(MONTH FROM payment_date) AS month,
    SUM(amount) AS total_revenue
FROM 
    payment
WHERE 
    EXTRACT(YEAR FROM payment_date) = 2024
    AND EXTRACT(MONTH FROM payment_date) = 12
GROUP BY 
    EXTRACT(YEAR FROM payment_date), 
    EXTRACT(MONTH FROM payment_date);

--------------------------------------------------------------------------------

-- Report 4: Members by Membership Type
-- Returns: membership_id, type, member_count
-- Description: Shows how many members hold each membership type.
-- Efficiency: Grouping and counting on foreign keys.

SELECT 
    ms.membership_id, 
    ms.type, 
    COUNT(m.member_id) AS member_count
FROM 
    membership ms
LEFT JOIN 
    member m ON ms.membership_id = m.membership_id
GROUP BY 
    ms.membership_id, ms.type
ORDER BY 
    member_count DESC;

--------------------------------------------------------------------------------

-- Report 5: Trainer Workload
-- Returns: trainer_id, first_name, last_name, class_count, total_enrollments
-- Description: Combines number of classes assigned dependent enrollments.
-- Efficiency: Complex JOIN and aggregations handled by the DB engine.

SELECT 
    t.trainer_id,
    t.first_name,
    t.last_name,
    COUNT(DISTINCT c.class_id) AS class_count,
    COUNT(ce.enrollment_id) AS total_enrollments
FROM 
    trainer t
LEFT JOIN 
    gym_class c ON t.trainer_id = c.trainer_id
LEFT JOIN 
    class_enrollment ce ON c.class_id = ce.class_id
GROUP BY 
    t.trainer_id, t.first_name, t.last_name
ORDER BY 
    total_enrollments DESC;
