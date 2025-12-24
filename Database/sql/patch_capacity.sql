
ALTER TABLE gym_class
ADD COLUMN capacity INT CHECK (capacity > 0);
