


```
--프로시져 찾기
SELECT OBJECT_NAME(object_id) AS PROCEDURE_NAME,
 OBJECT_DEFINITION(object_id) AS PROCEDURE_DESC
FROM sys.procedures
WHERE UPPER(OBJECT_DEFINITION(object_id)) LIKE '%프로시져명%';
```
