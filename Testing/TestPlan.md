# Требования к проекту

### Содержение

1. [Введение](#1) 
2. [Объект тестирования](#2) 
3. [Риски](#3) 
4. [Аспекты тестирования](#4) 
5. [Подходы к тестированию](#5) 
6. [Представление результатов](#6) 
7. [Выводы](#7) 
  



## 1\. Введение 
В данном документе представлен план тестирования приложения "Болтливый крокодил".  Цель тестирования - проверка работоспособности приложения и выявление ошибок, допущенных при реализации данного приложения.


## 2\. Объект тестирования 
Объект тестирования - мобильное приложение "Болтливый крокодил". Данное приложение разработано с целью скрасить досуг пользователей.

Атрибуты качества по ISO 25010:  

1. Функциональная пригодность  
 - Функциональная полнота  
 - Функциональная корректность  
 - Функциональная целесообразность
2. Удобство использования
 - Эстетика пользовательского интерфейса  
 - Работоспособность
 - Защита от ошибок пользователя
 - Доступность


## 3\. Риски
  - Установка новой версии ОС Android;
  - Недостаточно места для хранения данных приложения;


## 4\. Аспекты тестирования 
1. Настройки 
  - Необходимо проверить возможность пользователя выбрать необходимые ему настройки, такие как:
  Установка количества слов для победы
  Установка времени раунда
  Установка настроек по умолчанию.
  
2. Добавление команды
  - Необходимо проверить возможность пользователей добавлять команды.
  - Необходимо проверить возможность пользователей давать название команде.
  - Необходимо исключить возможность одинаковых названий команд.
  - Необходимо проверить поле ввода названия команды, чтобы оно не было пустым.
  - Необходимо проверить возможность пользователей изменить название команды.

3. Удаление команды
  - Необходимо проверить возможность пользователей удалить команду.
 
4. Ход игры 
  - Необходимо проверить возможность пользователей поставить игру на паузу.
 
5. Результаты раунда 
  - Необходимо проверить возможность пользователей изменять результаты раунда, а именно: 
  Изменение статуса соответствующего неотгаданного слова на отгаданного
  Изменение статуса соответсвующего "ошибочно отгаданного" слова на неотгаданного

6. Проверка восстановления игры
 - Необходимо проверить возможность пользователей восстановить игру,

Нефункциональные требования к приложению:
 - Все функциональные элементы пользовательского интерфейса имеют названия, описывающие действие, которое выполняет элемент. Контраст между цветами шрифта, функциональными элементами и фоном должен обеспечивать удобство работы с приложением.
 - Размер шрифта не менее 13 пт , функциональные элементы должны быть равны или больше размеру основного текста на экране

## 5\. Подходы к тестированию 
Наряду с ручным тестированием будет использовано модульное тестирование продукта. 


## 6\. Представление результатов 
Результаты тестирования представлены в документе []().


## 7\. Выводы 
Этот документ позволяет протестировать основные функции приложения. Успешное прохождение всех тестов не гарантирует полной работоспособности, однако позволяет 
утверждать соответствие разработанного приложения предъявляемым к нему требованиям.