create table REQUESTS (
  ID INT IDENTITY primary key,
  PAYLOAD VARCHAR(2000)
);

create table CONFIGURATION (
  KEY VARCHAR(100) primary key,
  VALUE VARCHAR(250) not null
);

insert into CONFIGURATION VALUES
  ('jdbcUrl', 'jdbc:h2:~/workspace/h2/spring-noxmal;DB_CLOSE_ON_EXIT=FALSE;TRACE_LEVEL_FILE=4;AUTO_SERVER=TRUE'),
  ('jdbcUserName', 'sa'),
  ('activeMqUrl', 'tcp://localhost:61616'),
  ('activeMqConcurrentConsumers', '5');