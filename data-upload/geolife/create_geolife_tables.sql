set schema 'geolife';
drop table if exists trajectories_raw; 
create table trajectories_raw(
latitude double precision,
longitude double precision,
zero float,
altitude double precision,
date_in_days double precision,
date_complete date,
time_complete time,
seconds_of_day double precision,
seconds_diff double precision,
userid float,
trajid float);

