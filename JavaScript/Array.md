## 배열

### 배열 사용하기

```javascript

var arr1 = ['milk', 'bread', 'good', 'cheese', 'humman', 'power'];
arr1; // milk,bread,good,cheese,humman,power
arr1[0] = 'look';
arr1; // look,bread,good,cheese,humman,power

var arr2 = ['good',214,[0,1,2]];
arr2[2][0]; // 0
```

### split, join 사용하기
```javascript
var s = 'practice,good,my,help,your,self';

var ss = s.split(',');
ss; // ['practice','good','my','help','your','self'];

var sss = ss.join(',');
sss; // 'practice,good,my,help,your,self'
```

## push, pop, unshift, shift

### push, pop 사용하기
```javascript
var arar = ['help','your','self','javascript'];
arar.push('good'); // 맨 뒤에 추가됨
arar; // ['help','your','self','javascript','good']
arar.push('notebook','galaxy'];
arar; // ['help','your','self','javascript','good','notebook','galaxy']

arar.pop(); // 맨 뒤에것 제거
arar // ['help','your','self','javascript','good','notebook']

// 길이 저장
var len = arar.push('abcdefg');
len; // 7

```
### unshift, shift 사용하기
```javascript
arar.unshift('docker'); // 맨 앞에 추가됨
arar; // ['docker','help','your','self','javascript','good','notebook','abcdefg']
var item = arar.shift();
item // 'docker'
```










