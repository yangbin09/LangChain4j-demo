# 分支规范

## 分支类型

### 1. 主要分支

| 分支 | 说明 | 保护状态 |
|------|------|----------|
| `master` | 主分支，稳定版本 | ✅ 受保护 |
| `tutorial/*` | 教程章节分支 | ✅ 受保护 |

### 2. 教程分支命名

```
tutorial/YYYY-MM-DD-chapter{N}
```

**示例：**
- `tutorial/2026-05-15-chapter1` - 2026年5月15日第一章
- `tutorial/2026-05-15-chapter2` - 2026年5月15日第二章

## 工作流程

### 开发新章节

1. **从 master 创建新分支**
   ```bash
   git checkout master
   git pull origin master
   git checkout -b tutorial/2026-05-15-chapter3
   ```

2. **开发并提交**
   ```bash
   git add .
   git commit -m "feat: 第三章内容"
   ```

3. **推送到远程**
   ```bash
   git push -u origin tutorial/2026-05-15-chapter3
   ```

4. **设置分支保护**
   - 设置 Require PR before merging
   - 设置 Require 1 approval

5. **创建 Pull Request**
   - 从新分支 PR 到 master
   - 待审批后合并

### 修复问题

1. 从 master 创建修复分支
   ```bash
   git checkout -b fix/description
   ```

2. 修复完成后合并回 master

## 分支保护规则

所有受保护分支必须：

- ✅ 通过 Pull Request 合并
- ✅ 至少 1 人审批
- ✅ 禁止强制推送
- ✅ 禁止删除分支

## 提交信息规范

```
<type>: <description>

[optional body]
```

**Type 类型：**
- `feat`: 新功能
- `fix`: 修复 bug
- `docs`: 文档更新
- `refactor`: 重构
- `test`: 测试相关
- `chore`: 构建/工具相关

**示例：**
```
feat: 添加流式输出支持

- 添加 StreamingChatModel
- 新增 /stream/chat 接口
```
