--Descritize Locations g=1024
drop table if exists brinkhoff.b25k_modified_g_1024;
with dimensions as
(
select min(y) as minY, ((max(y)+2.0)-min(y))/1024 as ySize, min(x) as minX, ((max(x)+2.0)-min(x))/1024 as xSize
from brinkhoff.b25k_modified_raw
)
SELECT id,x,y,vx,vy,t,trunc((x-minX)/xSize)*1024+trunc((y-minY)/ySize) as r,trunc((x-minX)/xSize) as rx,trunc((y-minY)/ySize) as ry, (trunc((x-minX)/xSize) * xSize + xSize/2) as cx,(trunc((y-minY)/ySize) * ySize + ySize/2) as cy
into brinkhoff.b25k_modified_g_1024
from brinkhoff.b25k_modified_raw,dimensions