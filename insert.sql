delimiter $$$
create procedure zqtest()
begin
declare i int default 0;
set i=0;
start transaction;
while i<200000 do
 //your insert sql
set i=i+1;
end while;
commit;
end
$$$
delimiter;
call zqtest();