# otus_java_2019_12
#HomeWork 21
#Sergey Akimov



Сохранение 1000 пользователей
- без кэш = 987 мс
- c кэш = 977 мс

Чтение 1000 пользователей и суммарный возраст = 509 490
- без кэш = ~870, при этом - чтений из кэша, все из БД
- c кэш = ~320 - при этом ~800 чтений из кэша, остальные 200 их БД
--- последний факт говорит о том, что кэш сбрасывается при недостатке памяти