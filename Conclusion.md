Параметры
    количество итераций  - 100
    количество элементов массива - 5 000 000
    максимальная память 512 ГБ
    максимальное время на stop the world - 10 msec

//-Xms512m -Xmx512m
// -Xlog:gc=debug:file=C:\Users\Serg\tmp\gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
// -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=C:\Users\Serg\tmp\dump
// -XX:+???  -XX:MaxGCPauseMillis=10


Метод сборки мусора - UseParallelGC
    time:63
    Number of Young GC = 206
    Duration of Young GC = 8935
    Number of Old GC = 91
    Duration of Old GC = 16780
Total duration of GC = 25715
Итого на сборку мусора = 25 секунд


Метод сборки мусора - UseSerialGC
    time:50
    Number of Young GC = 95
    Duration of Young GC = 7173
    Number of Old GC = 19
    Duration of Old GC = 2958
Total duration of GC = 10131
Итого на сборку мусора 10 секунд


По результатам оценки лучше показал себя метод Serial - меньше время выполнения программы, меньше время на сборку мусора