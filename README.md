# 個人書籍管理系統

## 一、專案簡介

本專案為一套書籍資料管理系統，針對多平台購書與閱讀情境下，書籍資訊分散、系列追蹤困難與閱讀紀錄管理不便等問題進行整合設計。系統以後端服務為核心，實作完整的資料模型設計與 CRUD 功能，並提供複合查詢與關聯管理能力。

本專案除基本 CRUD 外，亦著重於：

* 複合條件查詢（動態搜尋）
* 實體關聯設計（OneToMany / ManyToMany）
* DTO 與 Entity 分離
* 全域例外處理（Global Exception Handling）
* 單元測試（Service / Repository 層）

---

## 二、問題背景

隨著實體書與電子書平台並行發展，使用者可能在不同平台購買相同書籍，或難以有效追蹤系列出版進度。此外，閱讀心得與個人評價往往分散於不同平台或社群媒體，缺乏結構化管理。

本系統旨在提供：

* 書籍與作者資料集中管理
* 系列出版追蹤
* 結構化閱讀心得保存
* 標籤化分類與多條件查詢
* 追蹤清單建立機制

---

## 三、系統架構

採用典型三層式架構：
```
Controller → Service → Repository → Database
           ↓
          DTO ⇄ Mapper ⇄ Entity
```
### 各層職責：

* **Controller**
  處理 HTTP Request / Response，負責接收前端請求並回傳結果，不包含業務邏輯。

* **Service**
  負責核心業務邏輯處理與資料驗證，並協調 Repository 與 Mapper。

* **Repository**
  負責資料存取，透過 JPA 操作資料庫。

* **DTO（Data Transfer Object）**
  作為對外資料傳輸模型，避免直接暴露 Entity，提升系統安全性與彈性。

* **Mapper**
  負責 Entity 與 DTO 之間的轉換，將資料轉為適合對外輸出的格式，或將請求資料轉為系統內部使用的 Entity。

### 設計說明：

* 將 DTO 與 Entity 明確分離，避免資料結構耦合
* 透過 Mapper 集中管理轉換邏輯，提升程式可讀性與維護性
* Service 層不直接處理資料格式轉換，確保單一職責原則（SRP）
  
---

## 四、核心模組設計

系統主要包含以下核心實體：

* 書籍（Book）
* 作者（Author）
* 系列（Series）
* 標籤（Tag）
* 閱讀心得（Experience）
* 追蹤清單（Tracking List）

### 關聯設計：

* Author ↔ Book：OneToMany
* Series ↔ Book：OneToMany
* Book ↔ Tag：ManyToMany
* Book ↔ Experience：OneToMany

---

## 五、已實作功能

### 1. 書籍管理

* 書籍基本 CRUD
* 複合條件查詢（依作者、標籤、系列等條件篩選）

### 2. 作者管理

* 作者基本 CRUD
* 查詢該作者名下書籍列表

### 3. 標籤管理

* 標籤基本 CRUD
* 查詢具特定標籤之書籍清單

### 4. 閱讀心得管理

* 心得基本 CRUD
* 與書籍關聯儲存

### 5. 追蹤清單功能

* 建立書籍追蹤清單
* 支援後續擴充出版狀態管理

---

## 六、技術實作

* Spring Boot（RESTful API）
* Spring Data JPA（ORM 映射）
* Hibernate（關聯管理）
* MVC 分層架構
* DTO / Entity 分離設計
* Swagger（OpenAPI 文件）

---

## 七、API 文件

啟動專案後可透過 Swagger UI 查看 API：

```
http://localhost:8080/swagger-ui/index.html
```

---

## 八、專案啟動方式

1. Clone 專案
2. 使用 Maven 或 Gradle build
3. 啟動 Spring Boot

預設 Port：

```
http://localhost:8080
```

---

## 九、資料庫設定

* 開發環境：MySQL
* 測試環境：H2

---

## 十、全域例外處理（Global Exception Handling）

本專案透過 `@ControllerAdvice` 實作全域例外處理機制，統一管理系統錯誤回應，避免在 Controller 層重複撰寫 try-catch。

### 處理範圍：

* 資源不存在（NotFoundException → 404）
* 請求格式錯誤（ValidationException → 400）
* 其他未預期錯誤（500）

### 設計重點：

* 統一錯誤回傳格式
* 明確錯誤訊息
* HTTP Status 與語意一致

### 錯誤回應範例：

```json
{
  "errorCode": "NOT_FOUND",
  "message": "查無此作者ID"
}
```

---

## 十一、單元測試（Unit Test）

本專案針對 Service 與 Controller 層撰寫單元測試，以確保系統邏輯與資料存取正確性。

### 使用技術：

* JUnit 5
* Mockito

#本專案使用 JUnit 與 Mockito 進行單元測試，確保核心邏輯正確性。

目前測試重點：

- Controller：驗證 API 行為與回應格式
- Service：驗證業務邏輯、資料處理與例外拋出

Repository（JPA）測試目前尚未完整導入，
主要因資料庫初始化與測試隔離設定仍在調整中，
後續預計使用 @DataJpaTest 補強。

---

## 十二、複合查詢設計（進階）

本系統提供多條件搜尋功能，支援以下條件組合：

* 作者
* 系列
* 標籤

透過動態條件組合方式實作，避免大量 if-else 判斷，提升擴展性與維護性。

---

## 十三、未來可擴充方向

* 使用者系統（登入 / 權限控管）
* 書籍評分與推薦系統
* 系列出版狀態追蹤
* 分頁與排序優化
* 查詢效能優化（避免 N+1 問題）

---

