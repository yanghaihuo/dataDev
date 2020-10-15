## 数据库相关
* 创建数据库

``` sql  
create database [if not exists] database_name [comment database_comment];
-- 改变数据库目录存放位置
-- 默认位置：/user/hive/warehouse
create database [if not exists] database_name
location '/my/prefered/directory'
;  
```
* 显示数据库

``` sql  
show databases;
-- 使用正则筛选符合条件的数据库
show databases like 'd.*';
```
* 查看数据库描述

``` sql  
desc database database_name;
```
* 使用数据库

``` sql  
use database_name;
```
* 删除数据库

``` sql  
drop database [if exists] database_name;
-- 级联删除(默认为 restrict )
drop database [if exists] database_name cascade;
```
## 表相关
* 建表

``` sql  
create table [if not exists] db_name.table_name (
 uid int comment '用户id'
,user_type string comment '用户类型'
,event_set array<sring> comment '事件集合,默认:[]'
,...
) comment '用户信息表'
partitioned by (p_dt string comment '日期(yyyy-MM-dd)')
row format delimited
  fields terminated by '\t'
  collection items terminated by '\n'
stored as [orc|textfile]
;

-- 备注1：
-- partitioned by：指定分区
-- row format delimited：分隔符设置开始语句
-- fields terminated by：设置字段与字段之间的分隔符(即列分隔符，默认为^A，八进制体现为'\001')
-- collection items terminated by：设置一个复杂类型(array,struct)字段的各个 item 之间的分隔符
-- map keys terminated by：设置一个复杂类型(map)字段的 key 和 value 之间的分隔符
-- lines terminated by：设置行与行之间的分隔符(即行分隔符，默认为'\n')
-- stored as：指定表的存储格式
-- 备注2：
-- \t：制表符(table)
-- \n：换行符(newline)
-- \r：回车符(return)
-- 备注3:
-- 空数组实现方式: array('')
```
* 数据导入  

1）本地文件导入数据到 hive 表  

``` sql  
load data local inpath '本地文件路径' [overwrite] into table table_name [partition (partcol1=val1, partcol2=val2 ...)];
```
2）hdfs 文件导入数据到 hive 表  

``` sql  
load data inpath 'hdfs文件路径' [overwrite] into table table_name [partition (partcol1=val1, partcol2=val2 ...)];
```
3）hive 表导入数据到 hive 表  

``` sql  
insert overwrite table table_a [partition (partcol1=val1, partcol2=val2 ...)]
select
 ...
from table_b;
```
4）利用 sql 语句直接插入数据到 hive 表  

``` sql  
insert overwrite|into table table_name values (value1,value2,...)
```
5）创建表过程中使用 sql 查询导入数据到 hive 表  

``` sql  
create table table_a
as
select
 ...
from table_b; 
```
6）使用第三方软件(如 datax、sqoop)将数据导入到 hive 表

* 数据导出  

1）导出数据到本地文件系统

``` sql  
insert overwrite local directory '本地文件系统路径'
row format delimited
  fields terminated by '\t'
  null defined as ''
stored as textfile
select * from table_a
;
```
2）导出数据到 hdfs

``` sql  
insert overwrite directory 'hdfs文件系统路径'
row format delimited
  fields terminated by '\t'
  null defined as ''
stored as textfile
select * from table_a
;
```
* 改表  

1）修改表名

``` sql  
alter table table_name1 rename to table_name2;
```
2）修改列

``` sql  
alter table table_name change [cloumn] col_old_name col_new_name column_type [comment col_comment] [first|after column_name];
```
3）新增列

``` sql  
-- 非分区表
alter table table_name add columns (
 column1 int comment '新增字段1'
,column2 string comment '新增字段2'    
)
;
-- 分区表
-- (hive 1.1.0之后可以使用)关键字 cascade 的作用是不仅变更新分区的表结构，同时也变更旧分区的表结构。默认为：restrict
alter table table_name add columns (
 column1 int comment '新增字段1'
) cascade
;
```
4）操作分区

``` sql  
-- 增加：hive 0.8.0以上可以在一个查询中同时增加多个分区
alter table table_name add [if not exists]
partition (partcol1=val1, partcol2=val2 ...) location 'hdfs文件系统路径'
partition (partcol1=val1, partcol2=val2 ...) location 'hdfs文件系统路径'
...
;
-- 修改
alter table table_name partition partition (partcol1=val1, partcol2=val2 ...)
set location 'hdfs文件系统路径';
-- 删除
alter table table_name drop [if exists] partition (partcol1=val1, partcol2=val2 ...);
```
* 删表

``` sql  
drop table [if exists] db_name.table_name;
```
* 常用命令

``` sql  
-- 显示表
show tables;
--正则匹配
show tables [like] '*hbtt*';
-- 显示建表语句
show create table table_name;
-- 显示表结构
desc table_name;
-- 显示表结构: 详细、格式化
desc formatted table_name;
-- 显示表分区
show partitions table_name;
-- 显示函数用法
desc function function_name;
```
