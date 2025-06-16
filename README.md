[![GitHub CI with Maven](https://github.com/TeaCupMe/HoIU3/actions/workflows/maven.yml/badge.svg)](https://github.com/TeaCupMe/HoIU3/actions/workflows/maven.yml)
![Java CI with Maven for feature/api-v2](https://github.com/TeaCupMe/HoIU3/actions/workflows/maven.yml/badge.svg?branch=feature%2Fapi-v2)
> [!CAUTION]
> Игра, представленная в этом репозитории, всё ещё
> находится в разработке. Подробнее ознакомится с
> уже реализованным функционалом и планами можно в секции [планы](#Планы) 
> 

# Heroes of IU3 - Герои ИУ3
Одной из задач в рамках лабораторных работ по курсу "Технологий программирования" кафедры ИУ3 МГТУ им. Н. Э. Баумана было разработать простую консольную игры - клон известной **Heroes of Might and Magic 3**.

Игру назвали **HOIU3**, в качестве дополнительного функционала была добавлена возможность играть с другими игроками по сети - через сервер-брокер сессий.

## Игровой процесс
Игра представляет собой пошаговую стратегию, где у каждого игрока есть один или несколько героев, ведущих свою армию. Герои могут перемещаться по карте, подбирать сокровища, сражаться с врагами(монстрами) и другими игроками.

Бой проходит в автоматическом режиме - выигрывает тот, у кого больше суммарная сила юнитов в армии.

## Об авторе
Игра сделана студентом второго курса кафедры ИУ3 МГТУ им. Н. Э. Баумана, Гиленко А.М., ИУ3-42Б. По всем вопросам - aleksey@gilenko.net

## Планы
- [ ] Интеграция с веб-сервером 
  - [X] Получение сессии
  - [ ] Отправка сессии
- [ ] Игровой процесс
  - [X] Управление курсором для получения информации о карте
  - [ ] Движение героя
    - [X] Базовое пошаговое движение
    - [ ] Движение с помощью курсора
      - [ ] Построение пути с минимальной затратой выносливости ([feature/path-finder](https://github.com/TeaCupMe/HoIU3/tree/feature/path-finder))
  - [ ] Завершение хода :bangbang:
  - [ ] Битвы
    - [ ] Битвы с монстрами
    - [ ] Битвы с другими игроками
  - [ ] Подбор сокровищ
  - [ ] Взаимодействие с крепостью
    - [ ] Покупка сооружений
    - [ ] Покупка юнитов
    - [ ] Покупка героев
- [X] UI/UX
  - [X] Splash screen (рисунок невероятной [AnKam](https://github.com/KamyshA11))
  - [X] Окно с игрой
  - [X] Вывод заполнителя поля при первом запуске
  - [X] Мигающий курсор
- [ ] Misc
  - [X] Интеграция логирования

## Зависимости
Некоторые maven-пакеты, используемые в этом проекте были написаны
автором либо специально для этого проекта, либо для предыдущих проектов.
- `space.crtech.utils:logger` - [ссылка](https://github.com/TeaCupMe/space.crtech.utils.logger/packages/2542970)
  - `space.crtech.utils:formatter` - [ссылка](https://github.com/TeaCupMe/space.crtech.utils.formatter/packages/2543003)
> [!NOTE]
> Так как Maven не может установить пакет с Github Packages без аутентификации,
> проще всего скачать файлы `.jar` по ссылкам и вручную добавить их к проекту:
> ```xml
> <dependency>
>   <groupId>space.crtech.utils</groupId>
>   <artifactId>logger</artifactId>
>   <version>1.0</version>
>   <scope>system</scope> 
>   <systemPath>/путь/к/файлу/logger-1.0.jar</systemPath>
> </dependency>
>
> <dependency>
>   <groupId>space.crtech.utils</groupId>
>   <artifactId>formatter</artifactId>
>   <version>1.0</version>
>   <scope>system</scope> 
>   <systemPath>/путь/к/файлу/formatter-1.0.jar</systemPath>
> </dependency>
> ```
> Шаблон этих блоков ` <dependency>` уже написан в `pom.xml`
