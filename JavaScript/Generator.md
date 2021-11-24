
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
