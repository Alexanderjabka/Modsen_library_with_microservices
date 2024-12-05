Чтобы запустить программу в терминале вызвать docker-compose up --build После запуска программы в postman используя коллекцию приложенную к приложению (файл Requests for modsen_library) протестировать его приложение разворачивается на localhost:8080 поставить basic auth username/password -> admin/admin

При добавлении новой книги , она сразу попадает в библиотеку в раздел доступные, после взятия книги она попадает в раздел недоступные и ей устанавливаются: дата, когда книгу взяли , дата, когда книгу нужно вернуть (+7 дней с момента взятия)

валидация данных: id (long) bookId (long) isbn (String,length 10-13 characters,not null) title (String,length max 255 characters,not null) genre (String,length max 100 characters,not null) description (String,max 500 characters,not null) author (String,max 100 characters,not null) BookRequest (json, { "isbn": "test__isbn", // 10-13 char "title": "test title", // max 255 char "genre": "test genre", //max 100 char "description": "test description", //max 500 char "author": "test author name" //max 100 char } )

Эндпоинты :

Получение списка всех книг GET /books Возвращает список всех книг.

Получение информации о книге по ID GET /books/{id} Параметр: id (Long): ID книги. Возвращает информацию о книге.

Получение информации о книге по ISBN GET /books/isbn/{isbn} Параметр: isbn (String): ISBN книги. Возвращает информацию о книге.

Добавление новой книги POST /books Тело запроса: BookRequest: объект с информацией о книге. Возвращает информацию о добавленной книге.

Обновление книги по ID PUT /books/{id} Параметры: id (Long): ID книги. Тело запроса: BookRequest: объект с обновленной информацией о книге. Возвращает сообщение об успешном обновлении.

Удаление книги по ID DELETE /books/delete/{id} Параметры: id (Long): ID книги. Возвращает сообщение об успешном удалении.

Получение списка доступных книг GET /library/available Возвращает список всех книг, которые доступны для заимствования. 

Получение списка недоступных книг GET /library/unavailable Возвращает список всех книг, которые в данный момент заимствованы.

Взять книгу из библиотеки (заимствование) POST /library/borrow/{bookId} Параметры: bookId (Long): ID книги. Возвращает сообщение об успешном заимствовании книги.

Вернуть книгу в библиотеку POST /library/return/{bookId} Параметры: bookId (Long): ID книги. Возвращает сообщение об успешном возврате книги.