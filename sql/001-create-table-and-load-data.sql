DROP TABLE IF EXISTS names;

CREATE TABLE names (
  id int unsigned AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  birth VARCHAR(100) NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO names (name,birth) VALUES ("tomoyasu",202301);
INSERT INTO names (name,birth) VALUES ("tanaka",202302);
INSERT INTO names (name,birth) VALUES ("yamada",202303);
