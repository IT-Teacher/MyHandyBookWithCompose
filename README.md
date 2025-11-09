# HamdyBook API Documentation

HamdyBook is a platform for accessing a collection of books, including their details, categories, and user interactions. This API provides endpoints to retrieve book information, user authentication, comments, and search functionality. The Figma design for the project can be found [here](https://www.figma.com/design/pXbmoTXbjWN9QxIQiL3jww/Untitled?node-id=0-1&p=f&t=1pdUFiG7ZL4QUBVd-0).

## Base URL
```
http://handybook.uz
```

## Endpoints

### 1. Get All Books
- **Endpoint**: `/book-api`
- **Method**: GET
- **Description**: Retrieves a list of all books available on the platform.
- **Response**:
  ```json
  [
      {
          "id": 1,
          "name": "Alkimyogar",
          "type_id": 1,
          "file": "http://handybook.uz/frontend/web/file/701697625957.pdf",
          "audio": "http://handybook.uz/frontend/web/file/701697625957.mp3",
          "year": "2016",
          "author": "Paulo Koelo",
          "status": 0,
          "reyting": 3,
          "description": "Insonning Yerdagi umri mobaynida amalga oshiradigan ishlari nimalardan iborat? ...",
          "image": "http://handybook.uz/frontend/web/img/701697625957.png",
          "lang": "uzbek",
          "count_page": 67,
          "publisher": "www.ziyouz.com"
      },
      ...
  ]
  ```

### 2. Get Single Book
- **Endpoint**: `/book-api/view?id={id}`
- **Method**: GET
- **Description**: Retrieves details of a specific book by its ID.
- **Parameters**:
  - `id` (required): The ID of the book.
- **Response**:
  ```json
  {
      "id": 1,
      "name": "Alkimyogar",
      "type_id": 1,
      "file": "http://handybook.uz/frontend/web/file/701697625957.pdf",
      "year": "2016",
      "author": "Paulo Koelo",
      "status": 0,
      "reyting": 3,
      "description": "Insonning Yerdagi umri mobaynida amalga oshiradigan ishlari nimalardan iborat? ...",
      "image": "http://handybook.uz/frontend/web/img/701697625957.png",
      "lang": "uzbek",
      "count_page": 67,
      "publisher": "www.ziyouz.com"
  }
  ```

### 3. User Login
- **Endpoint**: `/book-api/login`
- **Method**: POST
- **Description**: Authenticates a user and returns an access token.
- **Request Body**:
  ```json
  {
      "username": "admin",
      "password": "45761888"
  }
  ```
- **Response**:
  ```json
  {
      "id": 1,
      "user name": "admin",
      "full name": "Aliyev Ali",
      "access_token": "67x7Drh27WpfsZ6v22iQWDMYBVRzPCzidd24bAsDM8MKzM_nJGAu0c4XwLLRH1In"
  }
  ```

### 4. User Registration
- **Endpoint**: `/book-api/register`
- **Method**: POST
- **Description**: Registers a new user and returns an access token.
- **Request Body**:
  ```json
  {
      "username": "admin",
      "fullname": "Xosilov Qodir",
      "email": "ali@gmail.com",
      "password": "45761888"
  }
  ```
- **Response**:
  ```json
  {
      "id": 1,
      "user name": "admin",
      "full name": "Aliyev Ali",
      "access_token": "67x7Drh27WpfsZ6v22iQWDMYBVRzPCzidd24bAsDM8MKzM_nJGAu0c4XwLLRH1In"
  }
  ```

### 5. Get Main Book
- **Endpoint**: `/book-api/main-book`
- **Method**: GET
- **Description**: Retrieves the featured or main book.
- **Response**:
  ```json
  {
      "id": 4,
      "name": "Yulduzli tunlar",
      "type_id": 1,
      "file": "http://handybook.uz/frontend/web/file/941697704667.pdf",
      "year": "2016",
      "author": "Pirimqul Qodirov",
      "status": 1,
      "reyting": 3,
      "description": "„Yulduzli tunlar“ — oʻzbek yozuvchisi Pirimqul Qodirov qalamiga mansub tarixiy roman. ...",
      "image": "http://handybook.uz/frontend/web/img/941697704667.jpg",
      "lang": "uzbek",
      "count_page": 280,
      "publisher": "SHARQ NASHRIYOT"
  }
  ```

### 6. Get All Categories
- **Endpoint**: `/book-api/all-category`
- **Method**: GET
- **Description**: Retrieves a list of all book categories.
- **Response**:
  ```json
  [
      {
          "type_name": "Badiiy adabiyot"
      },
      ...
  ]
  ```

### 7. Filter Books by Category
- **Endpoint**: `/book-api/category?name={category_name}`
- **Method**: GET
- **Description**: Retrieves a list of books filtered by category name.
- **Parameters**:
  - `name` (required): The name of the category (e.g., "Badiiy adabiyot").
- **Response**:
  ```json
  [
      {
          "id": 5,
          "name": "Jinoyat va jazo",
          "type_id": 1,
          "file": "http://handybook.uz/frontend/web/file/101697709667.pdf",
          "year": "0000",
          "author": "Fyodor Mihaylovich Dostoyevsk",
          "status": 0,
          "reyting": 3,
          "description": "Jinoyat va jazo gʻoyasi Dostoevskiyda koʻp yillar davomida pishib yetilgan ...",
          "image": "http://handybook.uz/frontend/web/img/101697709667.jpg",
          "lang": "uzbek",
          "count_page": 480,
          "publisher": "www.ziyouz.com"
      },
      ...
  ]
  ```

### 8. Get Comments for a Book
- **Endpoint**: `/book-api/comment?id={book_id}`
- **Method**: GET
- **Description**: Retrieves all comments for a specific book.
- **Parameters**:
  - `id` (required): The ID of the book.
- **Response**:
  ```json
  [
      {
          "username": "admin",
          "text": "O'qish uchun tavsiya qilaman."
      },
      ...
  ]
  ```

### 9. Post a Comment
- **Endpoint**: `/comment-api/create`
- **Method**: POST
- **Description**: Allows a user to post a comment and rating for a book.
- **Request Body**:
  ```json
  {
      "book_id": 3,
      "user_id": 1,
      "text": "OK",
      "reyting": 2
  }
  ```
- **Response**:
  ```json
  {
      "book_id": "3",
      "user_id": "1",
      "text": "ok",
      "reyting": "2",
      "id": 5
  }
  ```

### 10. Search Books by Name
- **Endpoint**: `/book-api/search-name?name={search_term}`
- **Method**: GET
- **Description**: Searches for books by their name.
- **Parameters**:
  - `name` (required): The search term for the book name (e.g., "yulduz").
- **Response**:
  ```json
  [
      {
          "id": 4,
          "name": "Yulduzli tunlar",
          "type_id": 1,
          "file": "http://handybook.uz/frontend/web/file/941697704667.pdf",
          "year": "2016",
          "author": "Pirimqul Qodirov",
          "status": 1,
          "reyting": 3,
          "description": "„Yulduzli tunlar“ — oʻzbek yozuvchisi Pirimqul Qodirov qalamiga mansub tarixiy roman. ...",
          "image": "http://handybook.uz/frontend/web/img/941697704667.jpg",
          "lang": "uzbek",
          "count_page": 280,
          "publisher": "SHARQ NASHRIYOT"
      },
      ...
  ]
  ```

## Notes
- All responses are in JSON format.
- Authentication is required for posting comments and accessing certain endpoints (e.g., `/comment-api/create`).
- Use the `access_token` received from the login or registration endpoints for authenticated requests.
- The API supports books in multiple languages, primarily Uzbek, as indicated by the `lang` field.
- The Figma design provides a visual representation of the platform's interface.
