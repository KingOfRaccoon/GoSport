# GoSport (тестовое задание)

Мобильное приложение под операционную систему Android, разработанное для прохождения тестового задания компании GoSport.
Главной задачей было реализовать сворачивание одного из виджетов в заголовке при скролле.

## Описание проекта

Мультимодульное приложение, которое разработано по стандратам [Google Clean Architecture](https://developer.android.com/topic/architecture).
Приложение содержит в себе 3 модуля:
- data (содержит реализацию репозитория и работу с базой данных и сетью)
- domain (содержит сущности, интерфейс репозитория и use-cases)
- presentation (содержит активити, экраны, все, что относится к UI, UIStates и ViewModels)

## Стек приложения

- [Jetpack Compose](https://developer.android.com/develop/ui/compose)
- [Ktor](https://ktor.io/docs/welcome.html)
- [Coil](https://coil-kt.github.io/coil/compose/)
- [Room](https://developer.android.com/training/data-storage/room)
- [Koin](https://insert-koin.io)
- [Navigatoin Compose](https://developer.android.com/develop/ui/compose/navigation)

## Запуск приложения

IDE: Android Studio/Fleet/IntellijIdea

Запуск: для запуска мобильного приложения в эмуляторе понадобится виртуализация
Для Intel: Установка Intel HAXM
Для AMD: включить в настройках BIOS/UEFI виртуализацию

## Скриншоты
<p>
  <img src="../master/main.png" alt="Главный экран" width="300" />
  <img src="../master/main_scroll.png" alt="Главный экран при скролле" width="300" />
</p>
