
create table target_data (
  id INTEGER GENERATED ALWAYS AS IDENTITY
     PRIMARY KEY NOT NULL,
  text1 VARCHAR(128),
  text2 VARCHAR(128),
  text3 VARCHAR(128),
  text4 VARCHAR(128),
  amount1 DECIMAL(17,2),
  amount2 DECIMAL(17,2)
);

create index idx_target_data_text1 on target_data(text1);
