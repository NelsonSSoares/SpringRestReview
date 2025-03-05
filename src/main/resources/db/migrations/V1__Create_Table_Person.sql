CREATE TABLE `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(100) NOT NULL,
  `first_name` varchar(80) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `last_name` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `person` VALUES
(1,'Rua 1','Fernando','Male','Soares'),
(2,'Rua 1','Nelson','Male','Soares'),
(3,'Rua 1','Mohamed','Male','Bakar'),
(4,'Rua 151','Bashar','Male','Al-assad'),
(5,'Rua 30','Bashar','Male','Al-assad');
