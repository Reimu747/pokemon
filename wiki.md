# 接口文档
### Url的根路径为 http://domain/pokemon

## 1、获取全部pokemon列表接口
### 请求地址: http://domain/pokemon/get/all GET

### 请求参数

### 响应示例  
```
{
    "msg": "success",
    "data": [
        ......
        {
            "id": 6,
            "name": "喷火龙"
        },
        {
            "id": 7,
            "name": "杰尼龟"
        },
        {
            "id": 8,
            "name": "卡咪龟"
        },
        {
            "id": 9,
            "name": "水箭龟"
        },
        {
            "id": 10,
            "name": "绿毛虫"
        },
        ......
    ],
    "timestamp": 1547505815274,
    "result_code": 200
}
```

### 响应参数
| 参数名称 | 参数说明 | 参数类型 |
|:------:|:-------:|:------:|
| msg | 响应信息 | String |
| data | 响应数据 | JsonArray |
| id | pokemon全国图鉴id | Int |
| name | pokemon名称 | String |
| timestamp | 响应时间戳 | Long |
| result_code | 响应码 | Int |

## 2、按pokemon名称查询接口
### 请求地址: http://domain/pokemon/get/pokemon GET

### 请求参数
| 参数名称 | 参数说明 | 参数类型 | 是否必填 |
|:-------:|:------:|:-------:|:------:|
| name | pokemon名称 | String | 是 |

### 响应示例  
```
{
    "msg": "success",
    "data": {
        "name": "皮卡丘",
        "national_pokedex_id": 25,
        "type_one": "电",
        "type_two": null,
        "ability_one": "静电",
        "ability_two": "静电",
        "ability_invisible": "避雷针",
        "hp_ss": 35,
        "attack_ss": 55,
        "defense_ss": 40,
        "special_attack_ss": 50,
        "special_defense_ss": 50,
        "speed_ss": 90,
        "catch_rate": 255
    },
    "timestamp": 1547507604153,
    "result_code": 200
}
```

### 响应参数
| 参数名称 | 参数说明 | 参数类型 |
|:------:|:-------:|:------:|
| msg | 响应信息 | String |
| data | 响应数据 | JsonArray |
| name | pokemon名称 | String |
| national_pokedex_id | pokemon全国图鉴id | Int |
| type_one | 第一属性 | String |
| type_two | 第二属性 | String |
| ability_one | 第一特性 | String |
| ability_two | 第二特性 | String |
| ability_invisible | 隐藏特性 | String |
| hp_ss | hp种族值 | Int
| attack_ss | 物攻种族值 | Int
| defense_ss | 物防种族值 | Int
| special_attack_ss | 特攻种族值 | Int
| special_defense_ss | 特防种族值 | Int
| speed_ss | 速度种族值 | Int
| catch_rate | 捕获度 | Int
| timestamp | 响应时间戳 | Long |
| result_code | 响应码 | Int |

## 3、计算pokemon能力值
### 请求地址: http://domain/pokemon/get/stats POST

### 请求示例    
```
{
    "name": "皮卡丘",
    "hp_ivs": 0,
    "attack_ivs": 0,
    "defense_ivs": 0,
    "special_attack_ivs": 0,
    "special_defense_ivs": 0,
    "speed_ivs": 0,
    "hp_bs": 0,
    "attack_bs": 0,
    "defense_bs": 0,
    "special_attack_bs": 0,
    "special_defense_bs": 0,
    "speed_bs": 0,
    "level": 100,
    "increase_ability_index": 0,
    "decrease_ability_index": 2
}
```

### 请求参数
| 参数名称 | 参数说明 | 参数类型 | 是否必填 |
|:-------:|:------:|:-------:|:------:|
| name | pokemon名称 | String | 是 |
| hp_ivs | hp个体值 | Int | 是 |
| attack_ivs | 物攻个体值 | Int | 是 |
| defense_ivs | 物防个体值 | Int | 是 |
| special_attack_ivs | 特攻个体值 | Int | 是 |
| special_defense_ivs | 特防个体值 | Int | 是 |
| speed_ivs | 速度个体值 | Int | 是 |
| hp_bs | hp努力值 | Int | 是 |
| attack_bs | 物攻努力值 | Int | 是 |
| defense_bs | 物防努力值 | Int | 是 |
| special_attack_bs | 特攻努力值 | Int | 是 |
| special_defense_bs | 特防努力值 | Int | 是 |
| speed_bs | 速度努力值 | Int | 是 |
| level | 等级 | Int | 是 |
| increase_ability_index | 增强的能力的序号 | Int | 是 |
| decrease_ability_index | 削弱的能力的序号 | Int | 是 |
> increase_ability_index 和 decrease_ability_index 范围为0 - 4,分别代表物攻，物防，特攻，特防，速度。  
> 例如increase_ability_index 为0，decrease_ability_index为2，就代表+物攻-特攻的固执性格。

### 响应示例  
```
{
    "msg": "success",
    "data": {
        "hp_stats": 180,
        "attack_stats": 126,
        "defense_stats": 85,
        "special_attack_stats": 94,
        "special_defense_stats": 105,
        "speed_stats": 185
    },
    "timestamp": 1547514992875,
    "result_code": 200
}
```

### 响应参数
| 参数名称 | 参数说明 | 参数类型 |
|:------:|:-------:|:------:|
| msg | 响应信息 | String |
| data | 响应数据 | JsonArray |
| hp_stats | hp能力值 | Int |
| attack_stats | 物攻能力值 | Int |
| defense_stats | 物防能力值 | Int | 
| special_attack_stats | 特攻能力值 | Int |
| special_defense_stats | 特防能力值 | Int |
| speed_stats | 速度能力值 | Int |
| timestamp | 响应时间戳 | Long |
| result_code | 响应码 | Int |
