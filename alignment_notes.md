# Backend & DB Alignment Notes

## Integration Ready Checklist
- [x] **DB Patch**: Created `sql/patch_capacity.sql`. Run this to add `capacity` column.
- [x] **Entity Updated**: `ClassEntity` now maps `capacity`.
- [x] **DTOs Enriched**: `ClassResponse` has `trainerName`; `EnrollmentResponse` has `memberName`, `className`.
- [x] **Exception Handling**: Duplicate enrollments (table `uq_enrollment`) return `409 Conflict`.

## SQL Query Examples

### List Classes (Includes Capacity & Trainer Name)
```sql
SELECT 
    c.class_id,
    c.class_name,
    c.schedule,
    c.capacity,
    t.first_name || ' ' || t.last_name AS trainer_name
FROM gym_class c
LEFT JOIN trainer t ON c.trainer_id = t.trainer_id;
```

### Create Class
```sql
INSERT INTO gym_class (class_name, schedule, capacity, trainer_id)
VALUES ('Advanced Yoga', 'Mon 10:00', 20, 1);
```

### Update Class
```sql
UPDATE gym_class
SET capacity = 25, schedule = 'Mon 10:30'
WHERE class_id = 99;
```

### Delete Class
```sql
DELETE FROM gym_class WHERE class_id = 99;
```

### List Enrollments (With Names)
```sql
SELECT 
    ce.enrollment_id,
    ce.member_id,
    m.first_name || ' ' || m.last_name AS member_name,
    ce.class_id,
    c.class_name,
    ce.enrollment_date
FROM class_enrollment ce
JOIN member m ON ce.member_id = m.member_id
JOIN gym_class c ON ce.class_id = c.class_id;
```
