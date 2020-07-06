core.js:15724 ERROR Error: Uncaught (in promise): SecurityError: Failed to register a ServiceWorker for scope ('https://smart-services.herokuapp.com/') 
with script ('https://smart-services.herokuapp.com/ngsw-worker.js'): The script has an unsupported MIME type 


'http://secure-reaches-93881.herokuapp.com/Product/' from origin 'http://localhost:1337' 
has been blocked by CORS policy: Response to preflight request doesn't pass access control check:
 No 'Access-Control-Allow-Origin' header is present on the requested resource.
 
 21:24:51	delete from  heroku_b4de311def4c120.product    WHERE  category = 'CLEANING'	Error Code: 1175. You are using safe update mode and you tried to update a table without a WHERE that uses a KEY column To disable safe mode, toggle the option in Preferences -> SQL Editor and reconnect.	0.141 sec
 21:26:31	delete from  heroku_b4de311def4c120.product    WHERE  id in(1602,1603)	Error Code: 1451. Cannot delete or update a parent row: a foreign key constraint fails (`heroku_b4de311def4c120`.`orders_products`, CONSTRAINT `FK_orders_products_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`))	0.281 sec

ALTER TABLE `heroku_b4de311def4c120`.`product` 
CHANGE COLUMN `name_ar` `name_ar` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL DEFAULT '' ,
CHANGE COLUMN `details` `details` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL DEFAULT '' ;

PROGRAMMER
ShehabSoft96@


select
 
         p2.user_id 
         from 
         pandemic_track.person p 
         where 
         status =2  
         and p2.user_id in( 
         select 
         user_id 
        from 
        pandemic_track.user_test_results utr 
         where  
          utr.test_result = 2 
         and utr.test_type = 1  
         and now()>= utr.test_date + interval '14 days')",nativeQuery = true)
		 
		 
		 ALTER TABLE pandemic_track.person ADD quarantine_status int NULL;

