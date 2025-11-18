# 心形墙（爱心照片墙）接口文档

## 基础信息
- **Base URL**: `https://{backend-domain}/api`  
- **认证方式**: 所有接口（除导出公开分享模式外）需在 Header 携带：`Authorization: Bearer {token}`  
- **统一响应格式**:

```json
{
  "success": true,
  "message": "操作成功",
  "data": {...}   // 或数组 / null
}
```

## 一、项目管理（心形墙项目）

### 1. 创建项目
- **接口**: `POST /heart-wall/projects`
- **请求体（JSON）**:

```json
{
  "projectName": "我们的回忆",     // 必填，项目名称
  "description": "记录我们的美好时光", // 可选，项目描述
  "isPublic": false,              // 是否公开（可选，默认 false）
  "maxPhotos": 40                 // 最大照片数（可选，可设上限）
}
```

- **响应 data 示例**:

```json
{
  "projectId": 1,
  "projectName": "我们的回忆",
  "description": "记录我们的美好时光",
  "isPublic": false,
  "maxPhotos": 40,
  "photoCount": 0,
  "createdAt": "2025-11-01T10:00:00Z",
  "updatedAt": "2025-11-01T10:00:00Z"
}
```

### 2. 获取项目列表
- **接口**: `GET /heart-wall/projects`
- **Query 参数**: 无
- **响应 data**:

```json
[
  {
    "projectId": 1,
    "projectName": "我们的回忆",
    "description": "记录我们的美好时光",
    "isPublic": false,
    "maxPhotos": 40,
    "photoCount": 12,
    "createdAt": "2025-11-01T10:00:00Z",
    "updatedAt": "2025-11-05T09:30:00Z"
  }
]
```

### 3. 获取项目详情
- **接口**: `GET /heart-wall/projects/{projectId}`
- **路径参数**:
  - `projectId`：项目 ID
- **响应 data**：项目基本信息 + 照片列表

```json
{
  "project": {
    "projectId": 1,
    "projectName": "我们的回忆",
    "description": "记录我们的美好时光",
    "isPublic": false,
    "maxPhotos": 40,
    "photoCount": 12,
    "createdAt": "2025-11-01T10:00:00Z",
    "updatedAt": "2025-11-05T09:30:00Z"
  },
  "photos": [
    {
      "photoId": 101,
      "projectId": 1,
      "photoUrl": "https://example.com/photo.jpg",
      "thumbnailUrl": "https://example.com/thumb.jpg",
      "positionIndex": 1,
      "caption": "这是我们第一次约会",
      "takenDate": "2025-11-01",
      "uploadedAt": "2025-11-01T11:00:00Z"
    }
  ]
}
```

### 4. 更新项目
- **接口**: `PUT /heart-wall/projects/{projectId}`
- **路径参数**:
  - `projectId`：项目 ID
- **请求体（JSON，部分字段可选）**:

```json
{
  "projectName": "我们的甜蜜回忆", // 可选
  "description": "记录我们的甜蜜时光", // 可选
  "isPublic": true,                 // 可选
  "maxPhotos": 60                   // 可选
}
```

- **响应 data**：更新后的项目信息

### 5. 删除项目
- **接口**: `DELETE /heart-wall/projects/{projectId}`
- **路径参数**:
  - `projectId`：项目 ID
- **响应 data**：`{ "deleted": true }`

## 二、照片管理

### 6. 上传照片（方式一：前端已有图片 URL）
- **接口**: `POST /heart-wall/photos`
- **请求体（JSON）**:

```json
{
  "projectId": 1,                                  // 必填
  "photoUrl": "https://example.com/photo.jpg",     // 必填，原图 URL
  "thumbnailUrl": "https://example.com/thumb.jpg", // 可选，缩略图 URL
  "positionIndex": 1,                              // 必填，位置索引（由前端或后端约定）
  "caption": "这是我们第一次约会",                    // 可选，照片说明
  "takenDate": "2025-11-01"                        // 可选，拍摄日期 YYYY-MM-DD
}
```

- **响应 data 示例**:

```json
{
  "photoId": 101,
  "projectId": 1,
  "photoUrl": "https://example.com/photo.jpg",
  "thumbnailUrl": "https://example.com/thumb.jpg",
  "positionIndex": 1,
  "caption": "这是我们第一次约会",
  "takenDate": "2025-11-01",
  "uploadedAt": "2025-11-01T11:00:00Z"
}
```

### 7. 上传照片（方式二：直接上传文件）
- **接口**: `POST /heart-wall/photos/upload`
- **Header**: `Content-Type: multipart/form-data`
- **FormData**:

| 字段名        | 类型   | 必填 | 说明                |
| ------------- | ------ | ---- | ------------------- |
| `file`        | file   | 是   | 照片文件            |
| `projectId`   | number | 是   | 项目 ID             |
| `positionIndex` | number | 是 | 位置索引（1-40）    |
| `caption`     | string | 否   | 照片说明            |
| `takenDate`   | string | 否   | 拍摄日期 YYYY-MM-DD |

- **响应 data**：同方式一

### 8. 删除照片
- **接口**: `DELETE /heart-wall/photos/{photoId}`
- **路径参数**:
  - `photoId`：照片 ID
- **响应 data**：`{ "deleted": true }`

## 数据库表结构

### at_heart_wall_projects 表
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 项目ID |
| user_id | bigint | 创建用户ID |
| project_name | varchar(200) | 项目名称 |
| description | text | 项目描述 |
| photo_count | int | 已上传照片数量 |
| max_photos | int | 最大照片数量 |
| cover_photo_url | varchar(500) | 封面照片URL |
| is_public | tinyint(1) | 是否公开 |
| created_at | timestamp | 创建时间 |
| updated_at | timestamp | 更新时间 |

### at_heart_wall_photos 表
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 照片ID |
| project_id | bigint | 项目ID |
| user_id | bigint | 上传用户ID |
| photo_url | varchar(500) | 照片URL |
| thumbnail_url | varchar(500) | 缩略图URL |
| position_index | int | 照片位置索引（1-40） |
| caption | text | 照片说明 |
| taken_date | date | 拍摄日期 |
| uploaded_at | timestamp | 上传时间 |
| updated_at | timestamp | 更新时间 |