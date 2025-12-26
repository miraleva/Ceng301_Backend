-- Routine: Get Member Payment Summary
-- Description: A table-valued function that returns a consolidated financial summary for a given member.
-- Input: p_member_id (INT)
-- Output: member_id, full_name, total_paid, payment_count, last_payment_date
-- Usage: SELECT * FROM get_member_payment_summary(1);

CREATE OR REPLACE FUNCTION get_member_payment_summary(p_member_id INT)
RETURNS TABLE (
    member_id INT,
    full_name TEXT,
    total_paid DECIMAL(10,2),
    payment_count BIGINT,
    last_payment_date DATE
)
AS $$
BEGIN
    RETURN QUERY
    SELECT 
        m.member_id,
        (m.first_name || ' ' || m.last_name)::TEXT AS full_name,
        COALESCE(SUM(p.amount), 0.00) AS total_paid,
        COUNT(p.payment_id) AS payment_count,
        MAX(p.payment_date) AS last_payment_date
    FROM 
        member m
    LEFT JOIN 
        payment p ON m.member_id = p.member_id
    WHERE 
        m.member_id = p_member_id
    GROUP BY 
        m.member_id, m.first_name, m.last_name;
END;
$$ LANGUAGE plpgsql;
