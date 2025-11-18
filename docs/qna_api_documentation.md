# 甜蜜问答接口文档

## 基础信息
- **Base URL**: `https://{backend-domain}/api`
- **认证方式**: 所有接口需在请求头携带 `Authorization: Bearer {token}`（JWT）
- **通用响应格式**: `{ success: boolean, message: string, data: any }`

## 接口列表

### 1. 获取问题列表
- **接口地址**: `GET /api/qna/questions`
- **请求参数**: 无
- **响应数据**:
  - `defaultQuestions[]`: 预设问题列表（`id`, `text`, `isDefault=true`）
  - `customQuestions[]`: 当前用户自定义问题列表（`id`, `text`, `isDefault=false`, `userId`, `createdAt`）
- **要点**:
  - 预设问题按 `orderIndex` 升序排列
  - 自定义问题按创建时间倒序排列
  - 仅返回 `isActive=true` 的问题

### 2. 提交答案
- **接口地址**: `POST /api/qna/answer/submit`
- **请求体**:
  - `questionId` (number, 必填)
  - `answer` (string, 必填，建议≤1000字符)
  - `questionText` (string, 可选冗余字段)
- **响应数据**:
  - `answerId`: 答案ID
  - `partnerAnswer`: 对方答案（若对方已回答）
  - `hasPartnerAnswered`: 对方是否已回答（bool）
  - `bindTime/createdAt`: 回答时间（ISO 时间）
- **校验规则**:
  - 用户必须已绑定情侣关系
  - 问题必须存在且用户未重复回答
  - 答案内容不能为空

### 3. 获取历史回答
- **接口地址**: `GET /api/qna/history`
- **查询参数**:
  - `page` (default 1)
  - `pageSize` (default 20, 建议 ≤100)
- **响应数据**:
  - `total`: 总记录数
  - `page`: 当前页码
  - `pageSize`: 每页大小
  - `list[]`: 回答列表
    - `id`: 答案ID
    - `questionId`: 问题ID
    - `question`: 问题内容
    - `myAnswer`: 我的回答
    - `partnerAnswer`: 对方回答
    - `createdAt`: 创建时间（ISO）
    - `updatedAt`: 更新时间（ISO）
- **要点**:
  - 仅返回当前用户已回答的记录
  - 按 `createdAt` 倒序排列

### 4. 获取对方答案
- **接口地址**: `GET /api/qna/partner`
- **查询参数**:
  - `questionId` (number, 必填)
- **响应数据**:
  - `hasAnswered` (bool): 对方是否已回答
  - `answer` (string): 对方答案（可为空）
  - `answeredAt` (ISO): 回答时间（可为空）
- **要点**:
  - 仅绑定情侣可访问
  - 若对方未回答，返回 `hasAnswered:false`

### 5. 添加自定义问题
- **接口地址**: `POST /api/qna/question/add`
- **请求体**:
  - `text` (string, 必填，建议 ≤100 字符)
- **响应数据**:
  - `id`: 问题ID
  - `text`: 问题内容
  - `isDefault`: 是否为预设问题（false）
  - `userId`: 创建用户ID
  - `createdAt`: 创建时间
- **要点**:
  - 必须已绑定情侣
  - 后端需做长度与敏感词校验
  - 默认 `orderIndex=999`, `isActive=true`

### 6. 删除自定义问题
- **接口地址**: `POST /api/qna/question/delete`
- **请求体**:
  - `questionId` (number, 必填)
- **响应数据**: `{ success:true, message:"删除成功" }`
- **要点**:
  - 仅允许删除自己创建的自定义问题
  - 预设问题不可删除
  - 若问题已有回答，转为软删除（`isActive:false`）

## 业务与错误约束

### 关系校验
- 除获取问题列表外，其余接口均需验证情侣绑定状态
- 未绑定返回 `400` + `message:"未绑定情侣关系"`

### 数据完整性
- 单用户同一问题只能有一条回答
- 重复提交应返回 `400` 错误
- 删除问题前需校验问题归属与回答情况

### 分页
- 若请求未带 `page/pageSize`，默认 `1/20`
- 最大 `pageSize` 推荐 `100`

### 响应码
- 成功: `200`
- 参数错误: `400`
- 未授权: `401`
- 无权限: `403`
- 不存在: `404`
- 服务器异常: `500`

## 数据库表结构

### at_questions 表
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 问题ID |
| question_text | varchar(500) | 问题内容 |
| category | enum('preset','custom') | 问题类型 |
| created_by | bigint | 创建者用户ID |
| order_index | int | 排序序号 |
| is_active | tinyint(1) | 是否启用 |
| created_at | timestamp | 创建时间 |
| updated_at | timestamp | 更新时间 |

### at_answers 表
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 答案ID |
| question_id | bigint | 问题ID |
| user_id | bigint | 回答用户ID |
| answer_text | text | 答案内容 |
| answered_at | timestamp | 回答时间 |
| updated_at | timestamp | 更新时间 |
