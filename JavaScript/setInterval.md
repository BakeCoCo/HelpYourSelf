## setInterval 사용법

### html
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Practice001</title>
    <script src="javascript/practice001.js"></script>
</head>
<body>
<a href="#" id="lotto" style="font-size: 30px;"></a>
<br>
<div id="setLotto"></div>
</body>
</html>
```

### js
```javascript
document.addEventListener("DOMContentLoaded", function(){

    // lotto 라는 id에 click이벤트로 setNum을 해준다.
    let lottoClick = document.getElementById('lotto');
    lottoClick.addEventListener('click',setNum);
    
    // setInterval= ranNum 함수를 60ms 마다 실행.
    setInterval(ranNum,60);
}

// ranNum 함수
function ranNum(){
    // lotto의 textContent를 1~45까지 랜덤숫자 세팅
    document.getElementById('lotto').textContent = String(Math.floor(Math.random()*45 +1));
}

// setNum 함수
function setNum(){
    // setLotto id를 세팅
    let kk = document.getElementById('setLotto');
    // lotNum 이라는 element 생성
    let setLotto = document.createElement('lotNum');
    // lotNum element select All
    let countNum = document.querySelectorAll('lotNum');
    
    // 숫자 중복 체크하려고
    let lottoChk = true;
    
    // 뽑은 숫자가 7개 안넘을때까지
    if(countNum.length<7){
        // 
        setLotto.textContent = document.getElementById('lotto').textContent+' ';
        for(let i = 0; i<countNum.length; i++ ){
            if(countNum[i].textContent.trim() == setLotto.textContent.trim()){
                alert('같은숫자 ㄴㄴ해 : '+setLotto.textContent);
                lottoChk = false;
            }
        }
        if(lottoChk){
            kk.appendChild(setLotto);
        }
    }else{
        if(confirm('로또 번호 다시 뽑을거?')){
            kk.innerHTML='';
        }
    }
}
```
