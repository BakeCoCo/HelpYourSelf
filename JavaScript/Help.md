# 배열

```
array 입력
unshift() 맨앞에 입력
push()
```

```
array 제거
shift() 맨앞에 제거
pop() 
```

# Class 사용법

```
var [변수명] = class [className] [extends] {
  // class body
};
```
## OR

```
class [클래스명] {
  constructor([입력값,입력값]){
  // 변수 초기화
    this.입력값 = 입력값;
  }
  
  [함수] {
  // 함수 body
  }
}

```

```
class Person{
  constructor(name, age){
    this.name = name;
    this.age = age;
  }
  
  good() {
    return 'My Name is '+name+' and '+age+' years old' ;
  }
};
```

```
var people = new Person('Lim Ha Jun','31');

people.good();

```




