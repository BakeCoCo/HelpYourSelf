# JavaScript 참고서
- [JavaScript MDN 홈페이지에 있는것 ](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference)

----
### console
```javascript
console.log('기본');          // C의 printf() 처럼 %d, %s 사용가능 -> 그런데 `${d} ${s}` 템플릿 문자열 써도됨
console.info('정보');         // 정보
console.warn('경고');         // 경고 (노란색)
console.error('에러');        // 에러 (빨간색)
console.dir(document.body);   //  document의 객체의 메소드가 뭐가 있는지
console.count('내용'); // ('내용') 몇번 호출했는지

console.time(); // time 시작
console.timeEnd(); // time 끝 시간잼
```

----
### 선언
```javascript
var       // 가변성 ( 나중에 선언해도 됨 )
let       // 가변성 ( 먼저 선언해야함 )
const     // 불변성 ( 먼저 선언해야함 )
```

-------
### 함수와 클래스

#### function // 함수의 기본  
```javascript
function name(a){ alert(a); }
name('good');
// good 알림
```

#### function* // Generator객체(yield) 반환
```javascript
function* a() { var i=0; while(true) {yield i++;} } 
var s = a();
console.log(s.next().value,s.next().value,s.next().value,s.next().value,s.return('End').value);
// 0 1 2 3 'End'
console.log(s.next());
// undefined
console.log(s.throw(new Error('The End Error'));
// Error: The End Error
```

#### async function // AsyncFunction 객체를 반환, await , Promise.resolve
```javascript
function afterCall(){ return new Promise(resolve => { setTimeout( () => {resolve('End'); }, 2000); }); }
async function nameCall(){
console.log('Start');
const result = await afterCall();
console.log(result);
}
nameCall();
// Start  // (2초후) // End 
```

```javascript
async function foo(){ return 1 } === function foo() { return Promise.resolve(1) }
async function foo(){ await 1 } === function foo() { return Promise.resolve(1).then(() => undefined) }
```




#### return // 함수 실행을 종료하고 호출한 함수에 값을 반환함
```javascript
function a(name){ return name; }
console.log(a('book'));
// book
```

#### class // class name { constructor(변수b) { this.변수a = 변수b }
```javascript
// 사용할땐 new name(변수b).변수a;
class aa{ constructor(b) { this.a = b; } }
console.log(new aa('good').a); // 'good'
```

----









