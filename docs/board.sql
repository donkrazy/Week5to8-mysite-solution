select if(no is null, 0, no) from user;
desc board;

select ifnull(max(group_no), 0) + 1  from board;

insert into board 
     values ( null, 'hello', 'hello', now(), 0, 
			    (select ifnull( max( group_no ), 0 ) + 1  from board as b), 
			    1, 0, 20 );
			   
SELECT a.no, a.title, b.name, a.hits, DATE_FORMAT(reg_date, '%Y-%m-%d %p %h:%i:%s')  
 FROM board a,
          user b
WHERE a.user_no = b.no;	

select count(*) from board;

select * from user;

desc board;

   