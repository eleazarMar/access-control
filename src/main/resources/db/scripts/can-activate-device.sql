SELECT COUNT(*)
FROM member m
INNER JOIN member_device md
ON m.username = md.username
INNER JOIN device d
ON d.device_id = md.device_id
WHERE m.rfid = :rfid
AND m.active = true
AND d.device_id = :device
