
CREATE INDEX IF NOT EXISTS idx_payment_member_id ON payment(member_id);
CREATE INDEX IF NOT EXISTS idx_payment_date ON payment(payment_date);

CREATE INDEX IF NOT EXISTS idx_class_enrollment_class_id ON class_enrollment(class_id);
CREATE INDEX IF NOT EXISTS idx_class_enrollment_member_id ON class_enrollment(member_id);

CREATE INDEX IF NOT EXISTS idx_gym_class_trainer_id ON gym_class(trainer_id);

CREATE INDEX IF NOT EXISTS idx_member_membership_id ON member(membership_id);
