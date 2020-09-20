SELECT d.enable_at, d.disable_at
FROM member_rfid mr
INNER JOIN member m
    ON mr.username = m.username
INNER JOIN member_device md
    ON m.username = md.username
INNER JOIN device d
    ON d.device_id = md.device_id
WHERE mr.rfid = :rfid
    and mr.enabled = true
    AND m.active = true
    AND d.device_id = :device
