INSERT INTO device_event(rfid, device_id, event_time, event)
SELECT :rfid, :device_id, now(), :event
