-- Sample Case Study

select * from TBLVENDOR;
select * from TBLORDERMASTER;
select * from TBLITEM;
select * from TBLORDERDTL;
select * from tblvendor_history;

--set serveroutput on;

---------------------------------------------------------------------------------------------------
--Requirement 1
--(Explicit cursor and for loop)

CREATE OR REPLACE PROCEDURE get_pending_order(p_ostatus IN TBLorderMaster.ostatus%TYPE)
as

    --cursor order_cursor is select orderid, ostatus from tblordermaster order by orderid;
    cursor order_cursor is select orderid, ostatus from tblordermaster ;--where ostatus = p_ostatus order by orderid;
    order_rec order_cursor%rowtype;
  
  BEGIN
    for order_rec in order_cursor
    loop
      if order_rec.ostatus = p_ostatus then
        dbms_output.put_line('We have ' || order_rec.orderid || ' in ' || order_rec.ostatus || ' status');
      end if;
    end loop;

END;

--OR
--(Explicit cursor and simple loop)

CREATE OR REPLACE PROCEDURE get_pending_order(p_ostatus IN TBLorderMaster.ostatus%TYPE)
as

    cursor order_cursor is 
      select orderid, ostatus from tblordermaster
      where ostatus = p_ostatus order by orderid;
    order_rec order_cursor%rowtype;
  
  begin
    open order_cursor;
    loop
      fetch order_cursor into order_rec;
      exit when order_cursor%NOTFOUND;
      dbms_output.put_line('We have ' || order_rec.orderid || ' in ' || order_rec.ostatus || ' status');
    end loop;

end;

exec get_pending_order('P');

---------------------------------------------------------------------------------------------------
--Requirement 2
--(Cursor variable)

CREATE OR REPLACE PROCEDURE get_order_quantity(v_option in NUMBER, p_order_id in TBLorderMaster.orderid%TYPE)
IS

type ref_cur is ref cursor;
ref_var ref_cur;
order_date tblordermaster.odate%type; -- date; -- tblordermaster.odate%type;
item_id tblorderdtl.orderid%type; -- varchar2(10); -- tblorderdtl.orderid%type;
order_qty tblorderdtl.qty_delivered%type; -- varchar2(10); -- tblorderdtl.qty_delivered%type;

begin
  if v_option = 1 then
    open ref_var for
      select odate from tblordermaster where orderid = p_order_id;
    fetch ref_var into order_date;
    dbms_output.put_line('Order date is ' || order_date);
    close ref_var;
  else
    open ref_var for
      select itemid, qty_ordered from tblorderdtl where orderid = p_order_id;
    loop
      fetch ref_var into item_id, order_qty;
      exit when ref_var%notfound;
      dbms_output.put_line('Quantity ordered for ' || item_id || ' is ' || order_qty);
    end loop;
    close ref_var;
  end if;

end;

exec get_order_quantity(1, 'o001');
exec get_order_quantity(2, 'o001');

---------------------------------------------------------------------------------------------------
--Requirement 3
--(normal procedure)

CREATE OR REPLACE PROCEDURE check_delivery(p_order_id IN TBLOrderDtl.orderid%TYPE)
AS

balance_orders number;

begin

for order_detail in (select * from tblorderdtl where orderid = p_order_id)
loop
  balance_orders := order_detail.qty_ordered - order_detail.qty_delivered;
  dbms_output.put_line('The item code ' || order_detail.itemid || ' with order ID ' || order_detail.orderid || ' need to deliver ' || balance_orders || ' more items.');
end loop;

end;

exec check_delivery('o001');

---------------------------------------------------------------------------------------------------
--Requirement 4
--(Function)

CREATE OR REPLACE FUNCTION func_getmax(f_item_id IN TBLitem.itemid%TYPE)
RETURN VARCHAR2 AS

item_detail tblitem%rowtype;
max_items varchar2(100);

begin

  select * into item_detail from tblitem where itemid = f_item_id;
  max_items := item_detail.p_category || ' with item id ' || item_detail.itemid ||' maximum level is ' || item_detail.max_level;
  return max_items;

end;   


--Execution
begin
  dbms_output.put_line(func_getmax('i105'));
end;

---------------------------------------------------------------------------------------------------
--Requirement 5
--(Trigger)

create or replace trigger trigger_order 
before insert or update on TBLOrderDtl
  for each row

begin

  if inserting then
    if :new.qty_delivered > :new.qty_ordered then
      raise_application_error(-20001, 'Quantity delivered should not be greater than Quantity Ordered');
    end if;
  elsif updating then
    if :new.qty_delivered > :old.qty_ordered or :old.qty_delivered > :new.qty_ordered then
      raise_application_error(-20001, 'Quantity delivered should not be greater than Quantity Ordered');
    end if;
  end if;
  
exception
  when others then
    raise_application_error(-20001, 'An error was encountered - ' || SQLCODE || ' -ERROR- ' || SQLERRM);

end;

--Test
insert into tblorderdtl values ('o123', 'i123', 50, 60);
update tblorderdtl set qty_delivered = 60 where orderid = 'o001' and itemid = 'i101';
update tblorderdtl set qty_ordered = 40 where orderid = 'o001' and itemid = 'i101';

---------------------------------------------------------------------------------------------------
--Requirement 6
--(User defined exception & handling exception)

CREATE OR REPLACE PROCEDURE proc_invalid_item (p_item_id IN TBLitem.Itemid%TYPE)
AS

invalid_id_exception exception;
item_rec tblitem%rowtype;

begin

  if p_item_id not like 'i%' then
    raise invalid_id_exception;
  else 
    select * into item_rec from tblitem where itemid = p_item_id;
    dbms_output.put_line('Item catrgory: ' || item_rec.p_category);
  end if;
  
exception
  when invalid_id_exception then
    dbms_output.put_line('Item ID must start with i!');
  when no_data_found then
    dbms_output.put_line('No such Item!');
  when others then
    dbms_output.put_line('Error! ' || SQLCODE || ' -ERROR- ' || SQLERRM);

end;

--Test
exec proc_invalid_item('101');
exec proc_invalid_item('i999');
exec proc_invalid_item('i101');

---------------------------------------------------------------------------------------------------

--Requirement 7
--Query using joins

select distinct /*om.orderid, om.odate,*/ i.* from tblitem i
inner join tblorderdtl od on od.itemid = i.itemid
inner join tblordermaster om on om.orderid = od.orderid
where om.odate between to_date('2014-09-01', 'yyyy-MM-dd') and to_date('2014-12-31', 'yyyy-MM-dd')
;--order by om.odate;

---------------------------------------------------------------------------------------------------

--Requirement 8
--Procedure call as dynamic sql within a procedure

--Inner procedure
CREATE OR REPLACE PROCEDURE proc_ven_history (p_venid  IN  VARCHAR2, p_vename IN  VARCHAR2, p_item_desc IN VARCHAR2) 
AS

BEGIN

  insert into tblvendor_history(vencode, venname, item_desc) values (p_venid, p_vename, p_item_desc);
  commit;

END;

--Outer procedure
CREATE OR REPLACE PROCEDURE proc_item(p_item IN TBLitem.itemdesc%TYPE)
AS

sql_statement varchar2(100);

BEGIN

  -- Altering table since the column lengths do not match between tblvendor, tblitem and tblvendor_history
  execute immediate 'alter table tblvendor_history modify venname varchar2(20)';
  execute immediate 'alter table tblvendor_history modify item_desc varchar2(50)';

  for item_detail in
    (select v.venid, v.venname, i.itemdesc from tblvendor v
      join tblitem i on i.venid = v.venid
      where i.itemdesc = p_item)
  loop
    -- Normal execution:
    -- begin proc_ven_history(item_detail.vencode, item_detail.venname, item_detail.item_desc); end;
    
    -- Execution using dynamic sql:
    sql_statement := 'begin proc_ven_history(:item_detail.venid, :item_detail.venname, :item_detail.itemdesc); end;';
    execute immediate sql_statement using in item_detail.venid, item_detail.venname, item_detail.itemdesc;
  end loop;

END;

--Test
exec proc_item('Electronis');
truncate table tblvendor_history;

---------------------------------------------------------------------------------------------------

--Requirement 9
--Procedure with user defined exception using PRAGMA EXCEPTION_INIT

create or replace procedure proc_duplicate_vendor(p_venid in tblvendor.venid%type, p_venname in tblvendor.venname%type, p_venadd1 in tblvendor.venadd1%type,
  p_venadd2 in tblvendor.venadd2%type, p_venadd3 in tblvendor.venadd3%type, p_Contact_Number in tblvendor.Contact_Number%type)
is

duplicate_vendor_exception exception;
pragma exception_init(duplicate_vendor_exception, -1);

begin
  insert into tblvendor values(p_venid, p_venname, p_venadd1, p_venadd2, p_venadd3, p_contact_number);
  commit;
  
exception
    when duplicate_vendor_exception then
      dbms_output.put_line('Duplicate Vendor ID'||' '||p_venid||' '||'should not added in the record');
    
end;

--Test
exec proc_duplicate_vendor('v001','Hari',12,'KFG Street','Chennai',452123);
exec proc_duplicate_vendor('v011','Dalt',11,'Goedstraat','Utrecht',123456);

---------------------------------------------------------------------------------------------------

--Requirement 10
--Query using group by

select itemid as item_id, sum(qty_ordered) as quantity from tblorderdtl od
join tblordermaster om on om.orderid = od.orderid
where om.odate between to_date('2014-09-01', 'yyyy-MM-dd') and to_date('2014-12-31', 'yyyy-MM-dd')
group by itemid order by itemid;

---------------------------------------------------------------------------------------------------