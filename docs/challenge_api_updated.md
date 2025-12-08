# 一百件小事挑战API接口文档（更新版）

## 1. 获取任务列表

### 接口说明
获取所有一百件小事任务列表，包括预设任务和用户自定义任务。

### 请求URL
```
GET /api/challenge/tasks
```

### 请求头
```
Authorization: Bearer <token>
Content-Type: application/json
```

### 响应数据格式
```json
{
  "success": true,
  "message": "获取成功",
  "data": [
    {
      "id": 1,
      "taskName": "一起看日出",
      "taskDescription": "在海边或山顶一起观看美丽的日出",
      "taskIndex": 1,
      "category": "preset",
      "iconUrl": null,
      "isActive": true,
      "createdAt": "2023-12-01 10:30:00",
      "updatedAt": "2023-12-01 10:30:00",
      "record": {
        "status": "completed",
        "photoUrl": "http://example.com/photo.jpg",
        "note": "今天的日出特别美",
        "location": "海边沙滩",
        "completedDate": "2023-12-01",
        "completedTime": "06:30",
        "weather": "晴天",
        "feeling": "非常开心，和你一起看日出是难忘的经历",
        "isFavorited": false,
        "completedAt": "2023-12-01 06:35:00",
        "createdAt": "2023-12-01 06:35:00",
        "updatedAt": "2023-12-01 06:35:00"
      }
    }
  ]
}
```

### 字段说明
| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | number | 是 | 任务唯一标识 |
| taskName | string | 是 | 任务名称 |
| taskDescription | string | 否 | 任务描述 |
| taskIndex | number | 否 | 任务序号 |
| category | string | 是 | 任务类别：preset(预设)、custom(自定义) |
| iconUrl | string | 否 | 任务图标URL |
| isActive | boolean | 是 | 是否启用 |
| createdAt | string | 是 | 创建时间，格式：yyyy-MM-dd HH:mm:ss |
| updatedAt | string | 是 | 更新时间，格式：yyyy-MM-dd HH:mm:ss |
| record | object | 否 | 用户对该任务的记录信息 |

#### record字段说明
| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| status | string | 是 | 任务状态：pending(待完成)、completed(已完成) |
| photoUrl | string | 否 | 完成时上传的照片URL |
| note | string | 否 | 完成时的备注说明 |
| location | string | 否 | 完成地点 |
| completedDate | string | 否 | 完成日期，格式：YYYY-MM-DD |
| completedTime | string | 否 | 完成时间，格式：HH:MM |
| weather | string | 否 | 完成时的天气 |
| feeling | string | 否 | 完成时的感受 |
| isFavorited | boolean | 是 | 是否收藏 |
| completedAt | string | 否 | 完成时间，格式：yyyy-MM-dd HH:mm:ss |
| createdAt | string | 是 | 创建时间，格式：yyyy-MM-dd HH:mm:ss |
| updatedAt | string | 是 | 更新时间，格式：yyyy-MM-dd HH:mm:ss |

## 2. 获取用户进度

### 接口说明
获取用户的挑战进度信息。

### 请求URL
```
GET /api/challenge/progress
```

### 请求头
```
Authorization: Bearer <token>
Content-Type: application/json
```

### 响应数据格式
```json
{
  "success": true,
  "message": "获取成功",
  "data": {
    "totalTasks": 100,
    "completedCount": 25,
    "favoritedCount": 10,
    "completionRate": 25.0,
    "lastActiveAt": "2023-12-01 10:30:00"
  }
}
```

### 字段说明
| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| totalTasks | number | 是 | 总任务数 |
| completedCount | number | 是 | 已完成任务数 |
| favoritedCount | number | 是 | 已收藏任务数 |
| completionRate | number | 是 | 完成率(%) |
| lastActiveAt | string | 否 | 最后活动时间，格式：yyyy-MM-dd HH:mm:ss |

## 3. 标记任务完成/取消完成（核心接口）

### 接口说明
标记任务为完成状态或取消完成状态，并可附带详细记录信息。

### 请求URL
```
POST /api/challenge/complete
```

### 请求头
```
Authorization: Bearer <token>
Content-Type: application/json
```

### 请求参数
```json
{
  "taskId": 1,
  "completed": true,
  "photoUrl": "http://example.com/photo.jpg",
  "note": "今天的日出特别美",
  "location": "海边沙滩",
  "completedDate": "2023-12-01",
  "completedTime": "06:30",
  "weather": "晴天",
  "feeling": "非常开心，和你一起看日出是难忘的经历"
}
```

### 参数说明
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| taskId | number | 是 | 任务ID |
| completed | boolean | 是 | 是否完成 |
| photoUrl | string | 否 | 完成时上传的照片URL |
| note | string | 否 | 完成时的备注说明 |
| location | string | 否 | 完成地点 |
| completedDate | string | 否 | 完成日期，格式 YYYY-MM-DD |
| completedTime | string | 否 | 完成时间，格式 HH:MM |
| weather | string | 否 | 完成时的天气 |
| feeling | string | 否 | 完成时的感受 |

### 响应数据格式
```json
{
  "success": true,
  "message": "标记完成成功",
  "data": {
    "taskId": 1,
    "status": "completed",
    "photoUrl": "http://example.com/photo.jpg",
    "note": "今天的日出特别美",
    "location": "海边沙滩",
    "completedDate": "2023-12-01",
    "completedTime": "06:30",
    "weather": "晴天",
    "feeling": "非常开心，和你一起看日出是难忘的经历",
    "completedAt": "2023-12-01 06:35:00"
  }
}
```

### 响应字段说明
| 字段名 | 类型 | 说明 |
|--------|------|------|
| taskId | number | 关联的任务ID |
| status | string | 任务状态：pending, completed |
| photoUrl | string | 完成时上传的照片URL |
| note | string | 完成时的备注说明 |
| location | string | 完成地点 |
| completedDate | string | 完成日期 |
| completedTime | string | 完成时间 |
| weather | string | 完成时的天气 |
| feeling | string | 完成时的感受 |
| completedAt | string | 完成时间 |

### 错误响应示例
```json
{
  "success": false,
  "message": "参数验证失败",
  "code": 400
}
```

### 注意事项
1. 当completed为false时，表示取消任务完成状态，此时其他字段可不传或传null
2. 所有时间字段请使用标准格式
3. photoUrl应为完整的URL地址
4. 该接口支持幂等性，重复调用相同参数不会产生副作用

## 4. 收藏/取消收藏任务

### 接口说明
收藏或取消收藏指定任务。

### 请求URL
```
POST /api/challenge/favorite
```

### 请求头
```
Authorization: Bearer <token>
Content-Type: application/json
```

### 请求参数
```json
{
  "taskId": 1,
  "favorited": true
}
```

### 参数说明
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| taskId | number | 是 | 任务ID |
| favorited | boolean | 是 | 是否收藏 |

### 响应数据格式
```json
{
  "success": true,
  "message": "操作成功",
  "data": {
    "taskId": 1,
    "favorited": true
  }
}
```

## 5. 添加自定义任务

### 接口说明
添加用户自定义的任务。

### 请求URL
```
POST /api/challenge/task/add
```

### 请求头
```
Authorization: Bearer <token>
Content-Type: application/json
```

### 请求参数
```json
{
  "taskName": "一起去野餐",
  "taskDescription": "在郊外享受大自然的美好"
}
```

### 参数说明
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| taskName | string | 是 | 任务名称，最长50个字符 |
| taskDescription | string | 否 | 任务描述，最长200个字符 |

### 响应数据格式
```json
{
  "success": true,
  "message": "添加成功",
  "data": {
    "id": 101,
    "taskName": "一起去野餐",
    "category": "custom",
    "status": "pending"
  }
}
```

## 6. 删除自定义任务

### 接口说明
删除用户自定义的任务。

### 请求URL
```
POST /api/challenge/task/delete
```

### 请求头
```
Authorization: Bearer <token>
Content-Type: application/json
```

### 请求参数
```json
{
  "taskId": 101
}
```

### 参数说明
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| taskId | number | 是 | 任务ID |

### 响应数据格式
```json
{
  "success": true,
  "message": "删除成功"
}
```

## 7. 上传照片

### 接口说明
上传完成任务时的照片。

### 请求URL
```
POST /api/challenge/upload
```

### 请求头
```
Authorization: Bearer <token>
Content-Type: multipart/form-data
```

### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| file | file | 是 | 要上传的照片文件 |

### 响应数据格式
```json
{
  "success": true,
  "message": "照片上传成功",
  "data": {
    "photoUrl": "http://example.com/uploads/challenge/photo_20231201_063000.jpg"
  }
}
```