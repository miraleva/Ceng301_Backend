-- Index Suggestions
-- Description: These indexes improve the performance of foreign key joins and common filtering criteria used in the reports.

-- 1. Optimize Payment Lookups (used in Revenue & Member Summary)
-- Payments are frequently filtered by member_id or date.
CREATE INDEX IF NOT EXISTS idx_payment_member_id ON payment(member_id);
CREATE INDEX IF NOT EXISTS idx_payment_date ON payment(payment_date);

-- 2. Optimize Enrollment Lookups (used in Popularity & Workload)
-- Critical for joining enrollments to classes and members.
CREATE INDEX IF NOT EXISTS idx_class_enrollment_class_id ON class_enrollment(class_id);
CREATE INDEX IF NOT EXISTS idx_class_enrollment_member_id ON class_enrollment(member_id);

-- 3. Optimize Class Lookups (used in Trainer Workload)
-- Helps join classes to trainers efficiently.
CREATE INDEX IF NOT EXISTS idx_gym_class_trainer_id ON gym_class(trainer_id);

-- 4. Optimize Member Membership Lookups (used in Members by Type)
CREATE INDEX IF NOT EXISTS idx_member_membership_id ON member(membership_id);
