# AQA_Diplom
## Для запуска проекта потребуется следующее предустановленное ПО:
* IntelliJ IDEA (разработка велась в версии 2022.2.1)
* Docker Desktop для запуска контейнера с СУБД
## Запуск проекта осуществляется следующими действиями:
1. Запуск программы Docker Desktop
1. Открытие проекта в IntelliJ IDEA
1. Запуск СУБД через терминал IDEA командой ```docker-compose up -d```
1. Запуск приложения aqa-shop.jar следующими командами:
    1. ```cd artifacts``` - для перехода в папку с aqa-shop.jar
    1. ```java -jar aqa-shop.jar -d``` - сам запуск
1. Запуск автотестов:
    * ```./gradlew clean test allureServe``` - с автоматическим запуском отчета Allure после прохождения всех тестов
    * ```./gradlew clean test allureReport``` - с формированием отчета Allure после прохождения всех тестов без его автоматического открытия (сам отчет будет находится по пути в папке проекта build/reports/allure-report/allureReport/index.html)
  
