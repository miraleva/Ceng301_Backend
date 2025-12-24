-- Patch: Convert schedule column to DATE
-- Requirement: Frontend sends YYYY-MM-DD. Backend uses LocalDate.

-- 1. Alter the column type using casting
-- Note: Requires existing data to be castable to date, or valid format.
-- If existing data is "Mon 10:00", this might fail.
-- Assumption: Data has been cleaned or we accept data loss/error for invalid formats in dev.
-- If strictly format 'YYYY-MM-DD' is needed, ensure data complies first.

ALTER TABLE gym_class
ALTER COLUMN schedule TYPE DATE
USING NULLIF(schedule, '')::date;

-- Verification Query:
-- SELECT class_id, class_name, schedule, pg_typeof(schedule) FROM gym_class LIMIT 5;
