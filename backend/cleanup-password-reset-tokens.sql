-- Cleanup script for password reset tokens
-- This script removes duplicate and expired tokens to resolve constraint violations

-- First, let's see what we have
SELECT 'Current password reset tokens:' as info;
SELECT user_id, COUNT(*) as token_count, 
       GROUP_CONCAT(id) as token_ids,
       GROUP_CONCAT(used) as used_status,
       GROUP_CONCAT(expiry_date) as expiry_dates
FROM password_reset_tokens 
GROUP BY user_id 
HAVING COUNT(*) > 1;

-- Remove expired tokens
DELETE FROM password_reset_tokens 
WHERE expiry_date < NOW();

-- Remove used tokens
DELETE FROM password_reset_tokens 
WHERE used = TRUE;

-- For users with multiple tokens, keep only the most recent one
DELETE t1 FROM password_reset_tokens t1
INNER JOIN password_reset_tokens t2 
WHERE t1.user_id = t2.user_id 
  AND t1.id < t2.id;

-- Verify cleanup
SELECT 'After cleanup:' as info;
SELECT user_id, COUNT(*) as token_count 
FROM password_reset_tokens 
GROUP BY user_id 
HAVING COUNT(*) > 1;

-- Show remaining tokens
SELECT 'Remaining tokens:' as info;
SELECT id, user_id, token, expiry_date, used, created_at 
FROM password_reset_tokens 
ORDER BY user_id, created_at DESC;