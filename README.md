 #Вытесняющий кэш.
 Проект состит из двух модулей:
 1) Модуль вытесняющего кэша.
 2) Веб-модуль, использовался для нагрузочного тестирования.
 
 Код покрыт Unit-тестами. Нагрузочное тестирование проводилось с использованием программы JMeter.
 
 Нагрузочное тестирование в себя включало:
 1) Отправка HTTP-Request Get-запросов, для получения всех записей кэша.
 2) Отправка HTTP-Request Post-запросов, для записи данных в кэш и возвращения данных записи клиенту.
 3) Отправка HTTP-Request Delete-запросов, для удаления записи из кэша и возвращения данных удаленной записи клиенту.
 4) Общее количество имитируемых пользователей 450, разбитые на 3 группы.
 5) Время "пробуждения(подъёма)" пользователей составляет от 50 до 70 секунд. 
 6) Разрыв между запусками групп оставляет 100 секунд.
 7) Время работы каждой группы составляет 350 секунд.
 8) Общее время теста составляет 550 секунд.
 
 Результаты тестирования в виде графиков и csv файлов приложены к прoекту ([в веб-модуле](https://github.com/Aljasones/Preemptive_Cache/tree/master/web-module/jmeter-results)).