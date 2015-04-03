#!/bin/sh

for infile in ./trajectory_files/*.csv
do
   cat $infile | psql -U postgres -d location_data -c "\COPY geolife.trajectories_raw from stdin with delimiter as ',' NULL AS 'NA' CSV HEADER"
done