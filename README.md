# AI画像解析ログ収集アプリケーション

- このアプリケーションは、指定された画像ファイルパスをもとにAI解析APIを呼び出し、その結果をDBに保存するWebアプリケーションです。
- ※APIの実装は存在しないため、JSONレスポンスを想定したモックを用意しています。

---

## ✅ 前提条件

以下の環境が必要となります。

- Java 17 以上
- Gradle
- Git

---

## 🚀 セットアップ手順

### 1. リポジトリのクローン

```bash
git clone https://github.com/comacmeemee/example-imagepath-analysys.git
```

### 2. プロジェクトのビルド

```bash
./gradlew build
```

### 3. アプリケーションの動作確認

```bash
./gradlew bootRun
```

### 4. 動作確認

ブラウザで以下にアクセスしてください。
画面が正常に表示されれば成功です。

```bash
http://localhost:8080/analysis
```

## 🧪 アプリケーションの利用について

### 画像パスの送信と結果確認

API（Mock-up）からの応答結果に応じて、フォーム下にメッセージが表示されます。

### 保存されたデータの確認

サーバ起動時にメモリ上にH2データベースを構築し、終了時に破棄されます。
H2データベースに保存した内容は、ブラウザ上で H2Console から閲覧できます。

```bash
http://localhost:8080/h2-console
```

#### 接続情報
- JDBC URL: jdbc:h2:mem:testdb
- ユーザー名: sample
- パスワード:（空欄）