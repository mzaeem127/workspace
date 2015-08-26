/* intel oem context creation */
insert into context values(0, true, null, 'Intel OEM', 'inteloem', null, 'en-US', 'America/New_York');
set @ctx = LAST_INSERT_ID();

/*catalogs*/
insert into catalog_ values(0, true, 'default', @ctx, 0, true, null);

/* credit type */
insert into catalog_credit_type values (0,'ITP Credits',@ctx);
insert into catalog_credit_type values (0,'IREP Chips/Points',@ctx);
