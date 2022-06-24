## 자바스크립트 화살표[함수]

```javascript
var s = function(k){ k*100; };
var s = k => k*100;
var s = k =>{
  k*100;
}
```
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

## 생성자 Generator

```javascript
function* g() {
  yield 200;
  yield 68;
  yield 379;
}

var k = g();
console.log(k.next()); //200
console.log(k.next()); //68
console.log(k.next()); //379
console.log(k.next()); //undefined 3개를 생성했으니 3개를 출력한다.
```

생성자(function*) 에서 yield의 갯수만큼 출력한다.

```javascript
function* f(){
    var i = 0;
    while(true){
        yield i++;
    }
}

var s = foo();
console.log(s.next()); // 무한대로 호출 가능.
```
# Function.prototype.call()

`call()` 메소드는 주어진 `this`값 및 각각 전달된 인수와 함께 함수를 호출합니다.

```
이 함수 구문은 apply()와 거의 동일하지만.
call()은 인수목록을,
apply()는 인수배열 하나를 받는다는 차이가 있다.
```

```javascript
function Product(name, price){
  this.name = name;
  this.price = price;
}

function Food(name, price){
  Product.call(this, name, price);
  this.category = 'food';
}
console.log(new Food('cheese', 5).name); // cheese
```

## 설명

`call()`은 이미 할당되어있는 다른 객체의 함수/메소드를 호출하는 해당 객체에 재할당할때 사용된다.

`this`는 현재 객체(호출하는)를 참조한다.

메소드를 한번 작성하면 새 객체를 위한 메소드를 재작성 할 필요 없이 `call()`을 이용해 다른 객체에 상속할 수 있다.

## 객체의 생성자 연결에 `call` 사용

Java와 비슷하게, 객체의 생성자 연결(chain)에 call을 사용할 수 있다.

다음 예에서 `Product` 객체의 생성자는 `name , price`를 매개변수로 정의하고 있으며

다른 두 함수 `Food , Toy`는 `this`및 `name`과 `price`를 전달하는 `Product`를 호출한다.

`Product`는 `name, price` 속성을 초기화 하고 특수한 두 함수(`Food , Toy`)는 `category`를 정의한다.

```javascript

// Product함수에 name, price 매개변수 입력받게
function Product(name, price){
// 입력받은 매개변수 저장
  this.name = name;
  this.price = price;
  
// price가 0보다 작으면 Error 출력
  if(price <0 ){
  throw RangeError('Cannot create product ' + this.name + ' with a negative price');
  }
}

// name, price  매개변수 입력한 Food 함수
function Food(name,price){
// Product를 call해서 name, price 가져옴
  Product.call(this, name, price);
  this.category = 'food';
  this.home = 'Earth';
  this.computer = 'Galaxy Book pro 360';
}

// name, price 매개변수 입력한 Toy 함수
function Toy(name, price){
// Product를 call해서 name, price 가져옴
  Product.call(this, name, price);
  this.category = 'toy';
}

var cheese = new Food('feta',5); // Food에 name, price 입력.
var fun = new Toy('robot', 40); // Toy에 name, price 입력.

console.log(cheese);
console.log(fun);
```

### 익명 함수 호출에 `call` 사용

이 예제에서는 익명 함수를 만들고 배열 내 모든 객체에서 이를 호출하기 위해 `call`을 사용한다.

여기서 익명 함수의 목적은 배열 내 객체의 정확한 인덱스를 출력할 수 있는 모든 객체에  `print`함수를 추가하는것.

```javascript

// species = Lion, Whale , name = King, Fail 선언
var animals = [
  { species : 'Lion', name : 'King' },
  { species : 'Whale', name : 'Fail' }
  ];

// animals 배열의 크기만큼 
for ( var i = 0; i < animals.length; i++) {

// function ( i ) 호출
  ( function(i) {
  
  // this.print = console.log ( #i 만큼 this.species : this.name )
    this.print = function() {
      console.log('#' + i + ' ' + this.species + ' : ' + this.name);
    }
    // this.print() 호출
    this.print();
  }).call(animals[i], i ); // call ( this , i )
  // animals[i] === this.animals[i] === globalThis.animals[i]
}
```

### 함수 호출 및 `this`를 위한 문맥 지정에 `call` 사용

아래 예제에서 `greet`를 호출하면 `this` 값은 객체 `obj`에 바인딩된다.

```javascript
// greet 함수 선언
function greet(){
  // 변수 reply = this.animal, this.sleepDuration . join(' ')
  var reply = [this.animal, 'typically sleep between', this.sleepDuration].join(' ');
  console.log(reply);
}

// 변수 obj 선언
var obj = {
  animal : 'cats' , sleepDuration : '12 and 16 hours'
};

greet.call(obj); // cats typically sleep between 12 and 16 hours

```


```javascript
'use strict';

var sData = 'Wisen';
function display() { console.log('sData value is %s ', this.sData); }
// display.call(); // sData value is Wisen ( use strict 없으면 )
display.call(); // Cannot read the property of 'sData' of undefined
```

# Function.prototype.apply()

`apply()` 메서드는 주어진 `this` 값과 배열( 또는 유사 배열 객체 )로 제공되는 `arguments`로 함수를 호출한다.

```javascript
const numbers = [5,6,2,3,7];
const max = Math.max.apply(null, numbers);
const min = Math.min.apply(null, numbers);

console.log(max); // 7
console.log(min); // 2
```

```javascript
console.log(Math.max.apply(null, numbers)); // 7
console.log(Math.min.apply(null, numbers)); // 2
```


## 배열에 배열을 붙이기 위해 `aply` 사용하기

`push`를 사용하여 배열에 추가할 수 있다.

`push`는 가변 인수를 허용하기 때문에 여러 요소를 동시에 추가할 수 있다.

그러나 `push`에 배열을 추가하면 `배열 안에 배열`이 들어간다.

기존 배열에 배열이 아닌 요소로서 하나씩 추가하고 싶다면 `apply`를 써야한다.


```javascript

var array = ['a', 'b'];
var elements = [0,1,2,3];
array.push.apply(array, elements);
console.info(array); // ['a','b',0,1,2,3]

```
