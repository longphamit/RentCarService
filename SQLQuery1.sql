create database RentCar
use RentCar
create table tblCategories(
	id varchar(300) primary key,
	name nvarchar(100)
)
create table tblDiscounts(
	id varchar(300) primary key,
	discount decimal(2,2),
	expired_date datetime
)
create table tblUsers(
	user_id varchar(100) primary key,
	password varchar(300),
	phone varchar(11),
	name nvarchar(50),
	address varchar(100),
	create_date date,
	status varchar(50)
)
create table tblProducts(
	id varchar(300) primary key,
	name varchar(100),
	image_address varchar(100),
	price decimal(5,2),
	carNumber varchar(50)  unique ,
	yearProduct varchar(20),
	id_categories varchar(300),
	foreign key(id_categories) references tblCategories(id)
)
create table tblOrders(
	id varchar(300) primary key,
	create_date date,
	total decimal(5,2),
	id_user varchar(100),
	sum decimal(5,2),
	foreign key (id_user) references tblUsers(user_id),
	status bit,
	id_discount varchar(300),
	foreign key (id_discount) references tblDiscounts(id)
)
create table tblOrderDetail(
	id_order varchar(300) ,
	id_product varchar(300),
	price decimal(5,2),
	to_date date,
	from_date date,
	primary key(id_order,id_product),
	foreign key(id_order) references tblOrders(id),
	foreign key(id_product) references tblProducts(id),
	feed_back nvarchar(500),
	rating int,
)

