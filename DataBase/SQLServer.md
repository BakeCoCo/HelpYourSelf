


```
--프로시져 찾기
SELECT OBJECT_NAME(object_id) AS PROCEDURE_NAME,
 OBJECT_DEFINITION(object_id) AS PROCEDURE_DESC
FROM sys.procedures
WHERE UPPER(OBJECT_DEFINITION(object_id)) LIKE '%프로시져명%';
```
```
--락찾기
EXEC sp_lock
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

```
--PIVOT + WITH 사용하기
WITH KSS AS
(
	SELECT 'X' AS T, 'S' AS AA, 10 AS BB 
	UNION ALL
	SELECT 'X' AS T, 'D' AS AA, 20 AS BB 
	UNION ALL
	SELECT 'X' AS T, 'Z' AS AA, 30 AS BB 
) 
SELECT * FROM KSS
PIVOT (SUM(KSS.BB) FOR KSS.AA IN([S],[D],[Z])) AS PIVOT_RESULT
;

RESULT
---------------------------
T	S	D	Z
X	10	20	30
---------------------------

WITH PIVOT_TABLE AS
(
 SELECT '1반' AS 반정보, '국어' AS 과목, 90 AS 점수
 UNION ALL
 SELECT '1반' AS 반정보, '수학' AS 과목, 80 AS 점수
 UNION ALL
 SELECT '2반' AS 반정보, '국어' AS 과목, 70 AS 점수
 UNION ALL
 SELECT '2반' AS 반정보, '수학' AS 과목, 60 AS 점수
 UNION ALL
 SELECT '3반' AS 반정보, '영어' AS 과목, 50 AS 점수
)
SELECT * FROM PIVOT_TABLE
PIVOT (SUM(점수) FOR 과목 IN ([국어], [수학], [영어])) AS PVT
;

RESULT
---------------------------
반정보   국어    수학    영어
1반	90	80	[NULL]
2반	70	60	[NULL]
3반	[NULL]	[NULL]	50
---------------------------
```
