# globalThis 와 this

`globalThis` 속성은 `this`값을 가진 전역 객체를 반환한다.


```javascript
function global(){
  return typeof globalThis.XMLHttpRequest === 'function';
}

console.log(global()); // true

console.log(globalThis.XMLHttpRequest);
console.log(this.XMLHttpRequest);
console.log(XMLHttpRequest);
// Object { UNSENT: 0, OPENED: 1, HEADERS_RECEIVED: 2, LOADING: 3, DONE: 4 }

```


|globalThis Property|||
|-|-|-|
|Writable|Enumerable|Configurable|
|yes|no|yes|

## 설명

웹에서는 `window`, `self`, `frames` 를 사용할 수 있지만. 

`Web Workers` 에서는 `self`만 동작합니다.

`Node.js`에서는 아무것도 쓸수 없고 `global`을 사용해야 한다.

`globalThis` 속성은 환경에 무관하게 전역 `this`값, 즉 `전역 객체` 에 접근하는 표준 방법을 제공한다.

`window`,`self`등 비슷한 속성과는 다르게 `브라우저/비브라우저` 맥락 모두에서 동작을 보장한다.

따라서 코드를 구동하는 환경을 모르더라도 전역 객체에 일관적으로 접근할 수 있다.


`globalThis`없이 현재 환경의 `전역 객체`를 가져오는 방법중 유일한 방법은

`Function('return this')()` 인데 일부 환경에서는 `CSP(en-US)`위반에 걸리는 코드이므로

`es6-shim`은 다음 검사를 수행합니다.

```javascript
var getGlobal = function () {
  if (typeof self !== 'undefined') { return self; }
  if (typeof window !== 'undefined') { return window; }
  if (typeof global !== 'undefined') { return global; }
  throw new Error('unable to locate global object');
};

var globals = getGlobal();

if (typeof globals.setTimeout !== 'function') {
  // no setTimeout in this environment!
}
```
`globalThis` 를 사용할 수 있으면 환경별 전역 객체 검사는 필요하지 않다.
```javascript
if (typeof globalThis.setTimeout !== 'function') {
  // no setTimeout in this environment!
}
```

# this

JavaScript 함수의 `this`키워드는 다른 언어와 조금 다르게 동작하며 엄격,비엄격 모드에서도 차이가 있다.

대부분의 `this`는 값은 `함수를 호출한 방법`에 의해 결정된다.

실행중에는 할당으로 설정할 수 없고 함수를 호출할 때 마다 다를 수 있다.

ES5는 함수를 어떻게 호출햇는지 상관하지 않고 this값을 설정할 수 있는 `bind` 메서드를 도입했으며

ES2015는 스스로의 `this` 바인딩을 제공하지 않는 `화살표 함수`를 추가했다.

```javascript
const test = {
  prop : 42,
  func : function() {
    return this.prop;
  }
};

console.log(test.func()); // 42
```

## 단순 호출

(use strict)
`엄격모드`가 아닌 `this`의 값이 호출에 의해 설정되지 않읍므로, 기본값으로 브라우저에서는 window인 전역 객체를 참조한다.

```javascript
function f1(){
  return this;
}
// 브라우저
console.log(f1() === window); // true
// Node.js
console.log(f1() === global); // true
```


반면에 엄격모드 에서는 `this`값은 실행 문맥에 진입하며 설정되는 값을 유지하므로

다음 예시에서 보여지는 것 처럼 `this`는 `undefined`로 남아있습니다.

```javascript
function f2(){
  "use strict"; // 엄격 모드
  return this;
}

console.log(f2() === undefined); // true
console.log(this.f2() === undefined); // false
console.log(globalThis.f2() === undefined); // false

```

`this`값을 한 문맥에서 다른 문맥으로 넘기려면 다음 예시와 같이 `call()`이나 `apply()`를 사용해야된다.

### 예시1

```javascript
// call 또는 apply의 첫 번째 인자로 객체가 전달될 수 있으며 this가 그 객체에 묶임
var obj = {a : 'Custom'};

// 변수를 선언하고 변수에 프로퍼티로 전역 window를 할당
var a = 'Global';

function whatsThis(){
  return this.a; // 함수 호출 방식에 따라 값이 달라짐
}

console.log(whatsThis());           // this는 'Global'. 함수 내에서 설정되지 않았으므로 global/window 객체로 초기값을 설정.
console.log(whatsThis.call(obj));   // this는 'Custom'. 함수 내에서 obj로 설정한다.
console.log(whatsThis.apply(obj));  // this는 'Custom'. 함수 내에서 obj로 설정한다.

```
