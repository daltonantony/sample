/*TBLvendor*/
INSERT INTO TBLvendor(VenID,venname,venadd1,venadd2,venadd3,Contact_Number) VALUES('v001','Hari',12,'KFG Street','Chennai',452123);

INSERT INTO TBLvendor(VenID,venname,venadd1,venadd2,venadd3,Contact_Number) VALUES('v002','James',2,'MCN Street','Bangalore',524125);

INSERT INTO TBLvendor(VenID,venname,venadd1,venadd2,venadd3,Contact_Number) VALUES('v003','Kishore',11,'Santa Street','Kolkata',896452);

INSERT INTO TBLvendor(VenID,venname,venadd1,venadd2,venadd3,Contact_Number) VALUES('v004','Savin',2,'Victor Street','Chennai',253652);

INSERT INTO TBLvendor(VenID,venname,venadd1,venadd2,venadd3,Contact_Number) VALUES('v005','Gupta',9,'Cape Street','Bangalore',854789);

INSERT INTO TBLvendor(VenID,venname,venadd1,venadd2,venadd3,Contact_Number) VALUES('v006','Manoj',112,'Ringo Street','Delhi',412536);

INSERT INTO TBLvendor(VenID,venname,venadd1,venadd2,venadd3,Contact_Number) VALUES('v007','Karthik',201,'Welsh Street','Hyderabad',741258);

INSERT INTO TBLvendor(VenID,venname,venadd1,venadd2,venadd3,Contact_Number) VALUES('v008','Nilamnu',256,'Starr Street','Chennai',125487);

INSERT INTO TBLvendor(VenID,venname,venadd1,venadd2,venadd3,Contact_Number) VALUES('v009','Thara',785,'Robin Street','Kolkata',423542);

INSERT INTO TBLvendor(VenID,venname,venadd1,venadd2,venadd3,Contact_Number) VALUES('v010','Nikil',36,'Golf Street','Bangalore',478512);
commit;
/*TBLvendor*/



/*TBLorderMaster*/

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o001',TO_DATE('23-1-2014','DD-MM-YYYY'),'v001','C',TO_DATE('27-1-2014','DD-MM-YYYY'));

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o002',TO_DATE('1-3-2014','DD-MM-YYYY'),'v002','P',TO_DATE('5-3-2014','DD-MM-YYYY'));

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o003',TO_DATE('12-1-2014','DD-MM-YYYY'),'v003','R',NULL);

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o004',TO_DATE('22-2-2014','DD-MM-YYYY'),'v004','C',TO_DATE('25-2-2014','DD-MM-YYYY'));

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o005',TO_DATE('12-2-2014','DD-MM-YYYY'),'v005','C',TO_DATE('27-2-2014','DD-MM-YYYY'));

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o006',TO_DATE('1-3-2014','DD-MM-YYYY'),'v006','P',TO_DATE('5-3-2014','DD-MM-YYYY'));

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o007',TO_DATE('23-1-2014','DD-MM-YYYY'),'v007','R',NULL);

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o008',TO_DATE('28-2-2014','DD-MM-YYYY'),'v008','P',TO_DATE('5-3-2014','DD-MM-YYYY'));

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o009',TO_DATE('12-2-2014','DD-MM-YYYY'),'v009','P',TO_DATE('12-3-2014','DD-MM-YYYY'));

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o010',TO_DATE('15-2-2014','DD-MM-YYYY'),'v010','R',NULL);

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o011',TO_DATE('23-10-2014','DD-MM-YYYY'),'v001','C',TO_DATE('27-1-2014','DD-MM-YYYY'));

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o012',TO_DATE('23-1-2014','DD-MM-YYYY'),'v002','C',TO_DATE('27-1-2014','DD-MM-YYYY'));

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o013',TO_DATE('23-11-2014','DD-MM-YYYY'),'v005','P',TO_DATE('27-1-2014','DD-MM-YYYY'));

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o014',TO_DATE('23-10-2014','DD-MM-YYYY'),'v007','P',TO_DATE('27-1-2014','DD-MM-YYYY'));

INSERT INTO TBLorderMaster(OrderID,odate,VenID,ostatus,delivery_date) 
VALUES('o015',TO_DATE('23-10-2014','DD-MM-YYYY'),'v004','C',TO_DATE('27-1-2014','DD-MM-YYYY'));

commit;
/*TBLorderMaster*/



/*TBLitem*/

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i101','Electronis','MP4 Players',100,150,250.56,'v001');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i102','Electronis','Batteries',100,150,450,'v002');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i103','Electronis','Projectors',150,150,300,'v003');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid)  
VALUES('i104','Personal Care','Hair Spray',150,150,300,'v004');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i105','Arts','Glue Primer',150,200,250.50,'v005');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i106','Personal Care','Make Up',150,200,500,'v006');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid)  
VALUES('i107','Arts','Varnish',200,200,500,'v007');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i108','Auto Products','Lubricant',100,100,450,'v008');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i109','Auto Products','Brake Fluid',100,150,250,'v009');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i110','Home Office','Ink',100,100,50,'v010');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i111','Home Office','Pen',100,100,20,'v001');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i112','Home Office','Correction Fluid',100,150,50,'v003');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i113','Pet Care','Flea',100,150,850,'v005');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i114','Pet Care','Odor Remover',100,100,150,'v007');

INSERT INTO TBLitem(itemID,itemdesc,p_category,qty_hand,max_level,itemrate,venid) 
VALUES('i115','Home Office','Toner',100,100,150,'v008');



commit;


INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o001','i101',50,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o001','i109',125,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o001','i112',50,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o001','i102',50,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o001','i115',100,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o002','i103',100,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o002','i104',200,150);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o003','i112',100,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o003','i111',100,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o004','i113',100,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o004','i114',100,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o005','i101',100,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o005','i102',100,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o006','i105',100,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o006','i107',50,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o007','i110',50,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o007','i111',100,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o008','i108',50,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o008','i109',150,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o009','i113',10,10);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o009','i115',20,20);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o010','i104',150,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o010','i106',150,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o011','i110',50,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o011','i111',150,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o011','i112',50,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o012','i107',150,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o012','i109',150,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o013','i106',150,150);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o013','i106',50,50);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o014','i106',150,100);

INSERT INTO TBLOrderDtl(OrderID,ItemID,Qty_Ordered,Qty_Delivered)
VALUES('o015','i106',250,100);


commit;