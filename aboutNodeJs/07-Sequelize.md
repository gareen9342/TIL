# Sequelize

Node에서 데이터베이스를 ORM형식으로 쉽게 다룰 수 있으려면 Sequelize란 ORM이 유명하고 이걸 이용하면 좋다.    
시퀄라이즈는 기본적으로 커넥션객체를 재사용한다. 알아서 커넥션을 잘 잡아준다.
클로즈시에는 sequelize.close()를 이용한다    
**(비동기, 프로미스 함수를 리턴한다.)**

시퀄라이즈를 배우기 쉽게 해주려고

[GitHub - papb/sequelize-sscce: Base repository for creating and sharing Sequelize SSCCEs](https://github.com/papb/sequelize-sscce)

이런 깃헙 레파지토리를 제공한다. 공부할거면 이걸 참고하기

모든 메소드는 비동기적이고 프로미스를 리턴함.    
try catch finally 를 이용하면 좋다

## 용어

### Model

하나의 테이블을 의미 field만 정의되어 있으면 getter, setter로 접근이 가능함.

예제는 아래와 같다.

```jsx
instance.field
// is the same as
instance.get('field')
// is the same as
instance.getDataValue('field')
```

Model에 사용할 수 있는 method들은 하단 링크

[Sequelize](https://sequelize.org/master/class/lib/model.js~Model.html#instance-constructor-constructor)

#### 모델 정의하기

sequelize.define을 이용한다.

```jsx
const { Sequelize, DataTypes } = require('sequelize');
const sequelize = new Sequelize('sqlite::memory:');

const User = sequelize.define('User', {
  // Model attributes are defined here
  firstName: {
    type: DataTypes.STRING,
    allowNull: false
  },
  lastName: {
    type: DataTypes.STRING
    // allowNull defaults to true
  }
}, {
  // Other model options go here
});

// `sequelize.define` also returns the model
console.log(User === sequelize.models.User); // true
```

데이터베이스에 정의된 것과 코드로 정의한 데이터가 다를 수도 있다

그럴 경우엔 sync 라는 것을 이용한다.

```jsx
User.sync() : 없는 경우 테이블 만들고 이미 존재할 경우 아무것도 하지 않음
User.sync({ force: true }) : 테이블을 만들고 이미 존재하는 경우 테이블 드롭
User.sync({ alter: true }) : 
```

## Queries

### INSERT

```jsx
// Create a new user
const jane = await User.create({ firstName: "Jane", lastName: "Doe" });
console.log("Jane's auto-generated ID:", jane.id);
```

### SELECT

간단한 예시

```jsx
// Find all users
const users = await User.findAll();
console.log(users.every(user => user instanceof User)); // true
console.log("All users:", JSON.stringify(users, null, 2));
```

특정한 속성을 가지고 올떄는 attributes 옵션을 사용할 수 있다.

```jsx
Model.findAll({
  attributes: ['foo', 'bar']
});

//================================
Model.findAll({
  attributes: ['foo', ['bar', 'baz'], 'qux']
		// SELECT foo, bar AS baz, qux FROM ...
});

```

sequelize.fn으로 aggregation을 할 수 있다.

```jsx
Model.findAll({
  attributes: [
    'foo',
    [sequelize.fn('COUNT', sequelize.col('hats')), 'n_hats'],
//SELECT foo, COUNT(hats) AS n_hats, bar FROM ...
    'bar'
  ]
});
```

### sequelize fn이란...

[Sequelize](https://sequelize.org/master/class/lib/sequelize.js~Sequelize.html#static-method-fn)

database function 표현하는 오브젝트를 만들어 준다.

컬럼을 표현하고 싶을 떄는 sequelize.col을 사용하면 된다.

→ 파라미터들

`fn(fn: string, args: any): fn`

### 조건문 Operator

특정한 조건을 줘야 할 때 사용할 수 있는 Operator들

```jsx
const { Op } = require("sequelize");
Post.findAll({
  where: {
    [Op.and]: [{ a: 5 }, { b: 6 }],            // (a = 5) AND (b = 6)
    [Op.or]: [{ a: 5 }, { b: 6 }],             // (a = 5) OR (b = 6)
    someAttribute: {
      // Basics
      [Op.eq]: 3,                              // = 3
      [Op.ne]: 20,                             // != 20
      [Op.is]: null,                           // IS NULL
      [Op.not]: true,                          // IS NOT TRUE
      [Op.or]: [5, 6],                         // (someAttribute = 5) OR (someAttribute = 6)

      // Using dialect specific column identifiers (PG in the following example):
      [Op.col]: 'user.organization_id',        // = "user"."organization_id"

      // Number comparisons
      [Op.gt]: 6,                              // > 6
      [Op.gte]: 6,                             // >= 6
      [Op.lt]: 10,                             // < 10
      [Op.lte]: 10,                            // <= 10
      [Op.between]: [6, 10],                   // BETWEEN 6 AND 10
      [Op.notBetween]: [11, 15],               // NOT BETWEEN 11 AND 15

      // Other operators

      [Op.all]: sequelize.literal('SELECT 1'), // > ALL (SELECT 1)

      [Op.in]: [1, 2],                         // IN [1, 2]
      [Op.notIn]: [1, 2],                      // NOT IN [1, 2]

      [Op.like]: '%hat',                       // LIKE '%hat'
      [Op.notLike]: '%hat',                    // NOT LIKE '%hat'
      [Op.startsWith]: 'hat',                  // LIKE 'hat%'
      [Op.endsWith]: 'hat',                    // LIKE '%hat'
      [Op.substring]: 'hat',                   // LIKE '%hat%'
      [Op.iLike]: '%hat',                      // ILIKE '%hat' (case insensitive) (PG only)
      [Op.notILike]: '%hat',                   // NOT ILIKE '%hat'  (PG only)
      [Op.regexp]: '^[h|a|t]',                 // REGEXP/~ '^[h|a|t]' (MySQL/PG only)
      [Op.notRegexp]: '^[h|a|t]',              // NOT REGEXP/!~ '^[h|a|t]' (MySQL/PG only)
      [Op.iRegexp]: '^[h|a|t]',                // ~* '^[h|a|t]' (PG only)
      [Op.notIRegexp]: '^[h|a|t]',             // !~* '^[h|a|t]' (PG only)

      [Op.any]: [2, 3],                        // ANY ARRAY[2, 3]::INTEGER (PG only)

      // In Postgres, Op.like/Op.iLike/Op.notLike can be combined to Op.any:
      [Op.like]: { [Op.any]: ['cat', 'hat'] }  // LIKE ANY ARRAY['cat', 'hat']

      // There are more postgres-only range operators, see below
    }
  }
});
```

IN 연산자를 사용할 때 array 안에다가 여러 조건을 쓰게 되면 IN으로 사용할 수 있다.

## Raw Queries

[Sequelize](https://sequelize.org/master/manual/raw-queries.html)

```jsx
const [results, metadata] = await sequelize.query("UPDATE users SET y = 42 WHERE x = 12");
// Results will be an empty array and metadata will contain the number of affected rows.
```

metadata 에 직접 접근할 필요가 없을 경우 아래와 같은 형태로

```jsx
const { QueryTypes } = require('sequelize');
const users = await sequelize.query("SELECT * FROM `users`", { type: QueryTypes.SELECT });
// We didn't need to destructure the result here - the results were returned directly
```

## Sub Queries

[Sequelize](https://sequelize.org/master/manual/sub-queries.html)

sub query와 같은 경우는 findAll의 attributes 에 가지고 올 쿼리문을 sequelize.literal로 넣어주므로서 할 수 있다.

```javascript
Post.findAll({
    attributes: {
        include: [
            [
                // Note the wrapping parentheses in the call below!
                sequelize.literal(`(
                    SELECT COUNT(*)
                    FROM reactions AS reaction
                    WHERE
                        reaction.postId = post.id
                        AND
                        reaction.type = "Laugh"
                )`),
                'laughReactionsCount'
            ]
        ]
    }
});

```

- IFNULL 바꿔보기

  IFNULL(columnA, "") → 아래와 같은 예시로 사용할 수 있다.
``javascript
table1.findAll({
      attributes: [[sequelize.fn('IFNULL', sequelize.col('A.price'), sequelize.col('B.price')]]
    });
```