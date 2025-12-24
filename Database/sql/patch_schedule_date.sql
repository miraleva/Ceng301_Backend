
ALTER TABLE gym_class
ALTER COLUMN schedule TYPE DATE
USING NULLIF(schedule, '')::date;
