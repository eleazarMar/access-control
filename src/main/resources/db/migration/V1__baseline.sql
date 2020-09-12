CREATE TABLE IF NOT EXISTS member (
  `member_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `member_forename` VARCHAR(128) NOT NULL,
  `member_surname` VARCHAR(128) NOT NULL,
  `active` BOOL NOT NULL,
  `rfid` VARBINARY(7) NULL,
  PRIMARY KEY (`member_id`)
  );

CREATE TABLE IF NOT EXISTS service (
  `service_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `enable_at` TIME NOT NULL,
  `disable_at` TIME NOT NULL,
  `trigger_duration_ms` INT NOT NULL,
  PRIMARY KEY (`service_id`)
  );


  CREATE TABLE IF NOT EXISTS member_service (
  `member_id` INT NOT NULL,
  `service_id` INT NOT NULL,
  `trained_by` INT NOT NULL,
  PRIMARY KEY (`member_id`,`service_id`),
  FOREIGN KEY (member_id) REFERENCES member(member_id),
  FOREIGN KEY (service_id) REFERENCES service(service_id)
  );
