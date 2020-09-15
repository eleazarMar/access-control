CREATE TABLE member (
  `username` VARCHAR(50) NOT NULL,
  `member_forename` VARCHAR(128) NOT NULL,
  `member_surname` VARCHAR(128) NOT NULL,
  `active` BOOL NOT NULL,
  `rfid` VARCHAR(14),
  PRIMARY KEY (`username`)
  );

CREATE TABLE device (
  `device_id` VARCHAR(50) NOT NULL,
  `enable_at` TIME,
  `disable_at` TIME,
  `trigger_duration_ms` INT,
  PRIMARY KEY (`device_id`)
);


CREATE TABLE member_device (
  `username` VARCHAR(50) NOT NULL,
  `device_id` VARCHAR(50) NOT NULL,
  `trained_by` VARCHAR(50) NOT NULL,
  PRIMARY KEY (username, device_id),
  FOREIGN KEY (username) REFERENCES member(username),
  FOREIGN KEY (device_id) REFERENCES device(device_id),
  FOREIGN KEY (trained_by) REFERENCES member(username)
);

CREATE TABLE device_event (
  `event_id` INT NOT NULL AUTO_INCREMENT,
  `rfid` VARCHAR(14) NOT NULL,
  `device_id` VARCHAR(50) NOT NULL,
  `event_time` TIMESTAMP NOT NULL,
  `event` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`event_id`),
  FOREIGN KEY (rfid) REFERENCES member(rfid),
  FOREIGN KEY (device_id) REFERENCES device(device_id)
  );

CREATE INDEX IX_event_time on device_event(event_time);
