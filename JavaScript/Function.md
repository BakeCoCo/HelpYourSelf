# Function 사용법



# 화살표 함수 사용법

화살표 함수는 기본적으로 return이라고 생각하면 됨. // ES6

### 기본 구문
```javascript
(param1, param1, ..., paramN) => { body }
(param1, param1, ..., paramN) => body
// 다음과 같다 : => { return body; }

(Param1) => { body }
Param1 => { body }            // Parameter가 1개 뿐이면 괄호는 생략 가능.
() => { body }                // Parameter가 없는 함수는 괄호를 입력.
```

### 고급 구문
```javascript
// 객체 리터럴 표현을 반환하기 위해 함수 본문(body)을 괄호 속에 넣음
params => ({foo:bar});

// Parameter 목록 내 구조분해할당도 지원됨 Destructuring
var f = ([a,b] = [1,2], {x:c} = {x:a+b}) => a+b+c;
f(); // 6

```



## TEST

```javascript
// 배열 m 선언
const m = [ 'Help','Your','Self','Good','off work' ];

console.log(m);
// (5) ['Help', 'Your', 'Self', 'Good', 'off work']

m.length;
// 5
```

```javascript
m.map(e=>e);
//(5) ['Help', 'Your', 'Self', 'Good', 'off work']

m.map( function(e) { return e.length } );   // 일반적인 함수 표현
m.map( (e) => { return e.length; } );       // 일반적인 함수 표현 화살표로 변경.
m.map(e => { return e.length; } );          // Parameter가 1개이면 () 괄호 생략 가능.
m.map(e => e.length);                       // return만 할 경우 return과 {} 생략 가능.
m.map( ({ length: goood}) => goood);        // destructuring 으로 변수명 변경가능. (length -> goood로 변경)
m.map( ({ length }) => length);             // 꺼내오고 싶은 속성 사용.
// (5)[4, 4, 4, 4, 8]

// Parameter가 1개일때
f1 = x => { y=x; return x+1; }
f1(3);        // 4
// y = 3

// Parameter가 2개 이상일때
fs = (a,b) => {k=a; q=b; return a+b+10;}
fs(10,20);    // 40
// k=10, q=20

// return 값만 받을때
fs1 = (a,b) => (z = a+b+7);
fs1(1,2);     // 10
// z = 10

```


## call(), apply()를 통한 피호출

```javascript
var adder = {
  base : 1,

  add : function(a) {
    var f = v => v + this.base;
    return f(a);
  },

  addThruCall: function(a) {
    var f = v => v + this.base;
    var b = {
      base : 2
    };
    
    // call함수 = call( this, arg1, ...., argN );
    return f.call(b, a);  // 화살표 에서는 this가 바인딩되지 않았기 때문에 base:2 는 무시됨.
  }
  
};

console.log(adder.add(1));         // 이는 2가 콘솔에 출력될 것임
console.log(adder.addThruCall(1)); // 이도 2가 콘솔에 출력될 것임
```

### 바인딩 되지 않은 arguments
```javascript
// n 입력받는 ks 함수
function ks(n){
  // f(입력값 배열 : a ) => a[0]번째 + 입력값 n
  var f = (...a) => a[0] + n; 
  return f(2); // a의 배열값 가능
}

```
