  # ShopSecurity

## 項目簡介

以前後端分離方式開發圖書電子商場的後端程式，後端程式分為 分類別模組、圖書模組、評論模組、使用者模組。
資料存取使用 MyBatis框架，介面採用 RESTful 風格設計，安全性原則採用 Spirng Security 來實作，並利用 JWT 實現 Token 驗證。
用 @RestControllerAdvice 定義全局的異常處理。

### 使用技術和版本

- **Spring Boot**: 2.6.4
- **Spring Security**: 安全框架
- **MySQL**: 8.3.0
- **MyBatis框架**: 數據庫訪問
- **pagehelper**: MyBatis分頁插件
- **JWT(JSON Web Token)**: 實作token驗證
- **devtools**: 熱部署
- **Lombok**: 簡化程式碼
- **Maven**: 項目構建和依賴管理

## 項目結構

```plaintext
├── src/main/java
│   └── com/louis/shopsecurity
│       ├── cofig
│       │   ├── handler
│       │   │   ├── MyAuthenticationFailureHandler.java
│       │   │   └── MyAuthenticationSuccessHandler.java
│       │   └── WebSecurityConfig.java
│       ├── controller
│       │   ├── result
│       │   │   ├── BaseResult.java
│       │   │   ├── DataResult.java
│       │   │   └── PaginationResult.java
│       │   ├── BookController.java
│       │   ├── CategoryController.java
│       │   ├── ResourceController.java
│       │   └── UserController.java
│       ├── exception
│       │   └── GlobalExceptionHandler.java
│       ├── jwt
│       │   ├── dto
│       │   │   └── PayloadDto.java
│       │   ├── filter
│       │   │   └── JwtAuthenticationFilter.java
│       │   └── util
│       │       └── JwtUtil.java
│       ├── persistence
│       │   ├── entity
│       │   │   ├── Book.java
│       │   │   ├── BookDetail.java
│       │   │   ├── Category.java
│       │   │   ├── Comment.java
│       │   │   └── User.java
│       │   └── mapper
│       │       ├── BookMapper.java
│       │       ├── CategoryMapper.java
│       │       ├── CommentMapper.java
│       │       └── UserMapper.java
│       ├── service
│       │   ├── BookService.java
│       │   ├── CategoryService.java
│       │   └── UserService.java
│       └── ShopSecurityApplication.java
├── src/main/resources
│   └── application.properties
├── src/test/java
│   └── com/louis/shopsecurity
│       └── ShopSecurityApplicationTests.java
├── .gitignore
├── pom.xml
└── README.md
