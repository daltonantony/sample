CREATE TABLE TBLvendor
(
VenID VARCHAR2(5) PRIMARY KEY,
venname VARCHAR2(20),
venadd1 VARCHAR2(20),
venadd2 VARCHAR2(20),
venadd3 VARCHAR2(20),
Contact_Number NUMBER(6)
);

CREATE TABLE TBLorderMaster
(
OrderID VARCHAR2(5) PRIMARY KEY,
odate DATE,
VenID VARCHAR2(5) REFERENCES TBLvendor(VenID),
ostatus CHAR(1),
delivery_date DATE
);


CREATE TABLE TBLitem
(   
ItemID VARCHAR2(5) PRIMARY KEY,
itemdesc VARCHAR2(50),
p_category VARCHAR2(20),
qty_hand NUMBER(5),
max_level NUMBER(5),
itemrate NUMBER(9,2),
VenID VARCHAR2(5) REFERENCES TBLvendor(VenID)
);

CREATE TABLE TBLOrderDtl
(
OrderID VARCHAR2(5) REFERENCES TBLorderMaster(OrderID),
ItemID VARCHAR2(5) REFERENCES TBLitem(ItemID),
Qty_Ordered NUMBER,
Qty_Delivered NUMBER
);

CREATE TABLE TBLvendor_history
(
vencode VARCHAR2(5) REFERENCES TBLvendor(VenID),
venname VARCHAR2(5),
Item_desc VARCHAR2(10)
);




commit;