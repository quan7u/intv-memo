> 很晚才认识到自己很菜，算法也是需要背的



## 动态规划

### 背包问题

> **“选一次，从后遍；可重复，从前扫”**

- 01 背包 ➜ 倒序容量（避免重复使用）

```java
// 给定容量为 target，每个物品只能选一次
for (int i = 0; i < n; i++) { // 遍历每个物品
    for (int j = target; j >= weight[i]; j--) { // 倒序，防止重复使用
        dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
    }
}
```

- 完全背包 ➜ 正序容量（允许多次使用）

```java
// 每个物品可以选无限次
for (int j = 0; j <= target; j++) { // 遍历容量
    for (int i = 0; i < n; i++) { // 每种物品都尝试
        if (j >= weight[i]) {
            dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
        }
    }
}
```

