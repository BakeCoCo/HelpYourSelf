


```
--프로시져 찾기
SELECT OBJECT_NAME(object_id) AS PROCEDURE_NAME,
 OBJECT_DEFINITION(object_id) AS PROCEDURE_DESC
FROM sys.procedures
WHERE UPPER(OBJECT_DEFINITION(object_id)) LIKE '%프로시져명%';
```

```
-- TABLE_NAME, COLUMN_NAME, COLUMN_SEQUENCE, COMMENT
SELECT TABLE_NAME, name, column_id, value FROM
(
SELECT
	A.name AS TABLE_NAME, 'TABLE_DESC' AS name,'0' as column_id ,C.value
FROM sys.all_objects A
INNER JOIN sys.extended_properties C ON A.object_id = C.major_id AND C.minor_id = '0'
WHERE A.[type] = 'U'
UNION ALL
SELECT
	A.name AS TABLE_NAME, B.name, B.column_id, C.value
FROM sys.all_objects A
INNER JOIN sys.all_columns B ON A.object_id = B.object_id 
LEFT OUTER JOIN sys.extended_properties C ON A.object_id = C.major_id  AND B.column_id = C.minor_id
WHERE A.[type] = 'U'
) AS A1
WHERE TABLE_NAME LIKE '%%'
ORDER BY A1.TABLE_NAME, A1.column_id
;
```

```
DECLARE TEST CURSOR		-- TEST라는 커서를 생성
	FOR SELECT 1 		-- 1,2,3,4 행 생성
		UNION ALL
		SELECT 2
		UNION ALL
		SELECT 3
		UNION ALL
		SELECT 4
OPEN TEST		-- 커서를		OPEN	열기
FETCH NEXT FROM TEST	-- 다음 커서를     FETCH   가져옴
CLOSE TEST		-- 커서를 		CLOSE	닫기
DEALLOCATE TEST		-- 커서		 DEALLOCATE 할당해제
```
