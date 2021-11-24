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
  // 함수형태로 초기화 하면 immutable 속성 가짐
    this._입력값 = () => 입력값;
  }
  
  [함수] {
  // 함수 body this.입력값 으로 사용
  }
}

```

```
class Person{
  constructor(name, age,comment){
    this.name = name;
    this.age = age;
    this._comment = () => comment;
  }
  
  good() {
    return 'My Name is '+this.name+' and '+this.age+' years old and '+this._comment() ;
  }
};
```

```
var people = new Person('Lim Ha Jun','31','javascript class function');

people.good();

```




