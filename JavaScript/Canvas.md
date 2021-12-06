# Canvas 

## HTML < canvas > 엘리먼트 사용법

`<canvas>` 요소는 `width`와 `height` 속성 두가지만 있다.

width와  height 속성을 지정하지 않으면 캔버스는 넓이300, 높이150을 갖는다.

`canvas`는 대체적으로 id속성으로 구분하는 것이 좋다.

------

### 대체 콘텐츠

`canvas`는 `video`, `audio`, `picture`처럼 `img`와는 달리 캔버스를 지원하지 않는 오래된 브라우저들을 위한 대체 컨텐츠를 정의하기 쉽다.

대체 컨텐츠를 제공하는것은 `canvas`태그 안에 대체 컨텐츠를 삽입하면 된다.

`canvas`태그를 지원하지 않는 브라우저는 컨테이너를 무시하고 컨테이너 내부의 대체 컨텐츠를 렌더링한다.

대체 컨텐츠를 제공하는 방식때문에 `canvas` 요소는 `닫기 (</canvas>)`가 필수이다.

------

### 렌더링 컨텍스트

캔버스는 처음에 비어있으며 `렌더링 컨텍스트`에 접근하여 그리도록 할 필요가 있다.

`<canvas>`요소는 `getContext()` 메소드를 이용해 렌더링 컨텍스트와 그리기 함수들을 사용할 수 있다.

`getContext()` 메서드는 렌더링 컨텍스트 타입을 지정하는 하나의 파라미터를 가진다.

` JavaScript DOC` 에서 다루는 튜토리얼에서는 `CanvasRenderingContext2D` == `2D`를 다룬다.

### 예시

```html
<canvas id='tutorial'></canvas>
```

```javascript
var canvas = document.getElementById('tutorial');
var ctx = canvas.getContext('2d');
```


## 기본 예제 네모 그리기

```javascript
var canvas = document.getElementById('tutorial');
var ctx = canvas.getContext('2d');

ctx.fillStyle = "rgb(200,0,0)"; // fillStyle 색상 지정 rgb(red,green,blue)
ctx.fillRect(10,10,50,50);      // fillRect(x좌표,y좌표,넓이width,높이height)
```

------

## 캔버스를 이용한 도형 그리기

캔버스 위에 물체를 그릴 때에는 `path`를 사용하는것이 필수적이다.

`canvas`는 오직 `하나의 원시적인 도형`만을 제공한다. 바로 `직사각형`이다.

다른 모든 도형들은 무조건 하나 혹은 하나 이상의 `path`와 여러 점으로 이어진 선으로 만들어진다.

------

## 직사각형을 그리는 함수

`fillRect(x, y, width, height)` 색칠된 직사각형을 그린다.

`strokeRect(x, y, width, height)` 직사각형 윤곽선을 그린다.

`clearRect(x, y, width, height)` 특정 부분을 지우는 직사각형이며, 이 부분은 완전히 투명해진다.

------

## 경로 그리기 `PATH`

경로를 이용하여 도형을 만들 때에는 몇가지 추가적인 단계를 거쳐야 한다.

1. 경로를 생성한다.
2. 그리기 명령어를 사용하여 경로상에 그린다.
3. 경로가 한번 만들어졋다면 경로를 렌더링 하기위해 윤곽선을 그리거나 도형 내부를 채울 수 있다.

## 다음은 위 단계들을 실행하기 위해 사용되는 함수이다.

`beginPath()` 새로운 경로를 만든다.

`Path 메소드` 물체를 구성할 때 필요한 여러 경로를 설정하는데 사용하는 함수.

`closePath()` 현재 하위 경로의 시작 부분과 연결된 직선을 추가한다.

`stroke()` 윤곽선을 이용하여 도형을 그린다.

`fill()` 경로의 내부를 채워서 내부가 채워진 도형을 그린다.

--------

## 경로를 만들기 위한 첫 단계는

`beginPath()` 메소드를 사용하는것이다.

## 두번째 단계는

실제로 경로가 그려지는 위치를 설정하는 메소드를 호출하는 것이다.

#### 세번째 단계는

선택사항으로 `closePath()` 메소드를 호출하는 것이다.

이 메소드는 `현재 점 위치`와 `시작 점 위치`를 `직선으로 이어서` 도형을 닫는다.

이미 도형이 닫혔거나 한 점만 존재한다면 이 메소드는 아무것도 하지 않는다.

```
fill() 메소드 호출시 열린 도형은 자동으로 닫히게 되므로 closePath()를 호출하지 않아도 된다.

이것은 stroke() 메소드에는 적용되지 않는다.
```

------

## 삼각형 그리기

예시
```html
<canvas id='canvas'></canvas>
```

```javascript
const canvas = document.getElementById('canvas');
const ctx = canvas.getContext('2d');
ctx.beginPath();        // 새로 경로 그린다.
ctx.moveTo(75,50);      // 75, 50으로 이동
ctx.lineTo(100,75);     // 100, 75로 라인그린다.
ctx.lineTo(100,25);     // 100, 25로 라인그린다.
ctx.fill();             // 도형 채운다.
```

## 펜 이동하기

`moveTo(x, y)` 펜을 x와 y로 지정된 좌표로 옮긴다.

```javascript
const canvas = document.getElementById('canvas');
const ctx = canvas.getContext('2d');

ctx.beginPath();
ctx.arc(75, 75, 50, 0, Math.PI * 2, true); // Outer circle

ctx.moveTo(110, 75);
ctx.arc(75, 75, 35, 0, Math.PI, false);  // Mouth (clockwise)

ctx.moveTo(65, 65);
ctx.arc(60, 65, 5, 0, Math.PI * 2, true);  // Left eye

ctx.moveTo(95, 65);
ctx.arc(90, 65, 5, 0, Math.PI * 2, true);  // Right eye

ctx.stroke();
```

## 선 그리기

직선을 그리기 위해서는 lineTo() 메소드를 사용한다.

`lineTo(x,y)` 현재의 드로잉 위치에서 x,y로 지정된 위치까지 선을 그린다.

`moveTo(x,y)` 메소드를 통해 시작점을 변경할 수 있다.


#### 예시

```javascript
const canvas = document.getElementById('canvas');
const ctx = canvas.getContext('2d');

ctx.beginPath(); // 시작
ctx.moveTo(25,25); //25,25로 이동
ctx.lineTo(105,25); // 25,25 -> 105,25까지 직선
ctx.lineTo(25,105); // 105,25 -> 25,105까지 직선
ctx.fill(); // 채우기
ctx.closePath(); // 시작지점과 끝지점 선으로 그려서 연결 // 근데 fill로 채워서 티안날듯
ctx.stroke(); // 그리기
```
