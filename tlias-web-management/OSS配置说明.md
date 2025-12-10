# OSS 配置说明

## ⚠️ 安全提示

**不要在 `application.yml` 中直接写入敏感信息（access-key-id 和 access-key-secret）！**

这些信息会被提交到 Git，存在泄露风险。

## 推荐配置方式

### 方式1：使用环境变量（推荐，适用于生产环境）

#### Windows PowerShell
```powershell
$env:OSS_ACCESS_KEY_ID="你的access_key_id"
$env:OSS_ACCESS_KEY_SECRET="你的access_key_secret"
```

#### Windows CMD
```cmd
set OSS_ACCESS_KEY_ID=你的access_key_id
set OSS_ACCESS_KEY_SECRET=你的access_key_secret
```

#### Linux/Mac
```bash
export OSS_ACCESS_KEY_ID=你的access_key_id
export OSS_ACCESS_KEY_SECRET=你的access_key_secret
```

#### 永久设置（Windows）
1. 打开"系统属性" -> "高级" -> "环境变量"
2. 在"用户变量"或"系统变量"中添加：
   - `OSS_ACCESS_KEY_ID` = 你的access_key_id
   - `OSS_ACCESS_KEY_SECRET` = 你的access_key_secret

#### 永久设置（Linux/Mac）
在 `~/.bashrc` 或 `~/.zshrc` 中添加：
```bash
export OSS_ACCESS_KEY_ID=你的access_key_id
export OSS_ACCESS_KEY_SECRET=你的access_key_secret
```
然后执行：`source ~/.bashrc` 或 `source ~/.zshrc`

### 方式2：使用本地配置文件（适用于本地开发）

1. 复制示例文件：
   ```bash
   cp src/main/resources/application-local.yml.example src/main/resources/application-local.yml
   ```

2. 编辑 `application-local.yml`，填入你的实际配置：
   ```yaml
   aliyun:
     oss:
       access-key-id: 你的实际access_key_id
       access-key-secret: 你的实际access_key_secret
   ```

3. 启动应用时激活 local profile：
   ```bash
   java -jar app.jar --spring.profiles.active=local
   ```
   或在 IDE 中设置 VM options：`-Dspring.profiles.active=local`

**注意**：`application-local.yml` 已加入 `.gitignore`，不会被提交到 Git。

## 配置优先级

1. **环境变量**（最高优先级）
2. `application-local.yml`（如果激活了 local profile）
3. `application.yml`（仅包含占位符，不会生效）

## 验证配置

启动应用后，如果配置正确，应用会正常启动。如果配置错误，会看到错误日志提示。

