# 모든 조합을 만드는 식


모든 조합을 한 번씩 만들 수 있는 조합을 만들 때에는 비트연산을 활용하면 좋다.   
이진수 치환시 특정 수의 n번째 인덱스가 무슨 수인지 알 수 있는 식을 이용하여 해결할 수 있다.    
-->>>>> ( n & (1 << idx) )
```javascript
    const array = ['A', 'B', 'C', 'D'];
    const sets = 1 << array.length; // sets === 16 // array 의 길이만큼 이진수 자리를 세팅하고 검사한다고 생각
    for (let i = 1; i < sets; i++) {
        let temp = '';
        for (let j = 0; j < array.length; j++) {
            if (i & (1 << j)) { // i의 j번째 인덱스가 0이 아닐 때!
                temp += array[array.length - 1 - j];
            }
        }
		console.log(i, i.toString(2), temp);
    }
```


콘솔에 출력해본 결과물.
![bit](../images/bit.png)   
   
처음에는 이해하기 어려웠었지만 이해하고 나니 비트 연산을 이렇게 활용할 수 있구나~ 
깨달아서 좋았다 😄