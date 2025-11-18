# 一百件事接口文档

## 基础信息
- **Base URL**: `https://{backend-domain}/api`
- **认证方式**: 所有接口需在请求头携带 `Authorization: Bearer {token}`（JWT）
- **通用响应格式**: `{ success: boolean, message: string, data: any }`（列表接口可将数据直接放在 `data` 字段内）

## 接口列表

### 1. 获取任务列表
- **接口地址**: `GET /api/challenge/tasks`
- **请求参数**: 无
- **响应数据**:
  - `tasks[]`
    - `id`、`taskName`
    - `taskDescription`
    - `taskIndex`（预设任务序号，自定义为 `null`）
    - `category`：`preset` / `custom`
    - `iconUrl`
    - `status`：`pending` / `completed`
    - `photoUrl`、`note`、`isFavorited`、`completedAt`
    - `userRecord`（可选，若用户有完成记录则展开）
- **规则**:
  - 返回全部预设任务 + 当前用户自定义任务
  - 预设按 `taskIndex` 升序，自定义按创建时间倒序
  - `status` 需融合用户记录（默认 `pending`）

### 2. 获取用户进度
- **接口地址**: `GET /api/challenge/progress`
- **响应数据**:
  - `totalTasks`
  - `completedCount`
  - `favoritedCount`
  - `completionRate`（百分比，小数一位）
  - `lastActiveAt`
- **逻辑**: 统计预设+自定义任务数量，汇总用户完成与收藏情况

### 3. 添加自定义任务
- **接口地址**: `POST /api/challenge/task/add`
- **请求体**:
  - `taskName`（必填，≤50 字）
  - `taskDescription`（可选，≤200 字）
- **响应数据**:
  - 新任务详情（`id`、`taskName`、`category:'custom'`、`status:'pending'`…）
- **校验**:
  - 已绑定情侣关系
  - 名称长度、描述长度
  - 默认 `taskIndex:null`、`isFavorited:false`

### 4. 删除自定义任务
- **接口地址**: `POST /api/challenge/task/delete`
- **请求体**:
  - `taskId`（必填）
- **响应**: `{ success:true, message:"删除成功" }`
- **逻辑**:
  - 仅允许删除自己创建的自定义任务
  - 预设任务禁止删除
  - 建议软删除（`isActive:false`）或直接物理删除

### 5. 标记任务完成/取消
- **接口地址**: `POST /api/challenge/complete`
- **请求体**:
  - `taskId`（必填）
  - `completed`（bool，必填）
  - `photoUrl`（可选，仅完成时）
  - `note`（可选 ≤500 字，仅完成时）
- **响应数据**:
  - `taskId`
  - `status`：`completed` / `pending`
  - `photoUrl`、`note`
  - `completedAt`
- **规则**:
  - `completed:true` 时创建/更新记录并写入时间
  - `completed:false` 时清空记录（照片、备注、时间）

### 6. 上传完成照片
- **接口地址**: `POST /api/challenge/upload`
- **Headers**: `Content-Type: multipart/form-data`
- **FormData**:
  - `file`（jpg/jpeg/png，≤10MB）
- **响应数据**:
  - `photoUrl`
- **建议**:
  - 上传后返回可访问的 URL
  - 控制格式与大小，失败返回 400/500

### 7. 收藏 / 取消收藏任务
- **接口地址**: `POST /api/challenge/favorite`
- **请求体**:
  - `taskId`（必填）
  - `favorited`（bool，必填）
- **响应数据**:
  - `taskId`
  - `isFavorited`
- **逻辑**:
  - 收藏即写/更新记录，取消则删除记录
  - 任务存在性校验

## 统一业务约束

- **情侣绑定**: 除获取任务列表/进度外，其余接口建议校验绑定关系。
- **任务归属**: 自定义任务需校验 `createdBy`；预设任务禁止删除。
- **并发约束**: 同一任务/用户对 `complete`、`favorite` 接口需做幂等设计（以 `taskId+userId` 唯一约束）。
- **错误码**: 参数错误 400、未授权 401、无权限 403、资源不存在 404、服务器异常 500。
- **时间格式**: 统一 ISO 8601（UTC 或带时区）。