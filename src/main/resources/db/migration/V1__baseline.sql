CREATE TABLE IF NOT EXISTS member (
  `member_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `member_forename` VARCHAR(128) NOT NULL,
  `member_surname` VARCHAR(128) NOT NULL,
  `active` BOOL NOT NULL,
  `rfid` VARCHAR(14),
  PRIMARY KEY (`member_id`)
  );

CREATE TABLE IF NOT EXISTS device (
  `device_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `enable_at` TIME,
  `disable_at` TIME,
  `trigger_duration_ms` INT,
  PRIMARY KEY (`device_id`)
  );


  CREATE TABLE IF NOT EXISTS member_device (
  `member_id` INT NOT NULL,
  `device_id` INT NOT NULL,
  `trained_by` INT NOT NULL,
  PRIMARY KEY (`member_id`,`device_id`),
  FOREIGN KEY (member_id) REFERENCES member(member_id),
  FOREIGN KEY (device_id) REFERENCES device(device_id)
  );
