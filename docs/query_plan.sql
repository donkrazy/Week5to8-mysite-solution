desc user;

-- 회원가입 (insert)
INSERT INTO user
       VALUES (null, '둘리', 'dooly@gmail.com', password('1234'), 'M' );

--로그인 (select)
SELECT no, name, email
 FROM user
WHERE email='dooly@gmail.com' 
   AND passwd=password('1234');

-- 수정
SELECT no, name, email, gender
 FROM user
WHERE no = 1;

UPDATE user
     SET  name='둘리',
	        email='dooly2@gmail.com',
			gender='M'
WHERE no = 1;

			

   
commit;
SELECT * FROM user;	   