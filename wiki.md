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
| id | pokemon全国图鉴id | Int |
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
