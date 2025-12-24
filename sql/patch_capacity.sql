-- Patch: Add capacity column to gym_class
-- Requirement: Frontend requires a 'capacity' field for class management.

ALTER TABLE gym_class
ADD COLUMN capacity INT CHECK (capacity > 0);

-- Verification Query:
-- SELECT class_id, class_name, capacity FROM gym_class LIMIT 5;
