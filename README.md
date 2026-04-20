<div align="center">

# Отчет

</div>

<div align="center">

## Практическая работа №10

</div>

<div align="center">

## Использование аппаратных возможностей устройства. Разрешения, уведомления, вибрация, камера

</div>

**Выполнил:**  
Покидов Матвей Юрьевич

**Курс:** 2

**Группа:** ИНС-б-о-24-1

**Направление:** 09.03.02

**Профиль:** Информационные системы и технологии

---

### Цель работы
Изучить использование аппаратных возможностей устройства в Android: систему разрешений (Permissions), создание и отправку уведомлений (Notifications), работу с вибрацией (Vibration) и доступ к камере (Camera) для предварительного просмотра и съёмки.

### Задания для самостоятельного выполнения

**Вариант 10 (Помощник преподавателя):**  
Создать приложение «Помощник преподавателя» с тремя функциями:
- **Отметить посещаемость** — с выводом Toast-сообщения и вибрацией
- **Напомнить о следующей паре** — с отправкой push-уведомления за 10 минут до пары
- **Сфотографировать доску** — с запросом разрешения на камеру и отображением предварительного просмотра

---

### Ход работы

В результате выполнения самостоятельного задания было создано Android-приложение «Помощник преподавателя», которое помогает преподавателю в организации учебного процесса: отмечать посещаемость, получать напоминания о парах и фотографировать доску с помощью камеры устройства.

#### 1. Создание интерфейса пользователя

Главный экран приложения содержит три кнопки для выполнения основных функций и список сегодняшних пар с указанием времени.

<div align="center">

![Главный экран приложения](https://github.com/mamontoff2018-lgtm/PR-10/blob/master/Рисунок1.png)

**Рисунок 1** — Главный экран приложения «Помощник преподавателя»

</div>

#### 2. Реализация функции «Отметить посещаемость»

<div align="center">

![Отметка посещаемости](https://github.com/mamontoff2018-lgtm/PR-10/blob/master/Рисунок2.png)

**Рисунок 2** — Результат отметки посещаемости (Toast и вибрация)

</div>

#### 3. Реализация функции «Напомнить о следующей паре»

При нажатии на кнопку создаётся и отправляется push-уведомление с напоминанием о предстоящей паре.

<div align="center">

![Напоминание о паре](https://github.com/mamontoff2018-lgtm/PR-10/blob/master/Рисунок3.png)

**Рисунок 3** — Push-уведомление с напоминанием о следующей паре

</div>

#### 4. Реализация функции «Сфотографировать доску»

При нажатии на кнопку сначала запрашивается разрешение на использование камеры (опасное разрешение). После получения разрешения открывается экран с предварительным просмотром камеры.

<div align="center">

![Предварительный просмотр камеры](https://github.com/mamontoff2018-lgtm/PR-10/blob/master/Рисунок4.png)

**Рисунок 4** — Экран предварительного просмотра камеры

</div>

**Экран предварительного просмотра камеры:**
- Содержит `SurfaceView` для отображения изображения с камеры
- Кнопка «Закрыть камеру» для возврата на главный экран
- Использует `Camera` или `CameraX` для доступа к камере устройства

#### 5. Добавление разрешений в AndroidManifest.xml

Для работы приложения необходимо добавить следующие разрешения:

```xml
<!-- Разрешение на вибрацию -->
<uses-permission android:name="android.permission.VIBRATE" />

<!-- Разрешение на камеру -->
<uses-permission android:name="android.permission.CAMERA" />

<!-- Для отправки уведомлений (не требуется отдельного разрешения) -->
<!-- Для камеры также нужно указать feature -->
<uses-feature android:name="android.hardware.camera" android:required="true" />
```

---


### Вывод

В ходе выполнения практической работы №10 (самостоятельное задание, вариант 3):

1. **Изучена система разрешений Android:**

2. **Реализована работа с уведомлениями:**

3. **Реализована работа с вибрацией:**
 
4. **Реализована работа с камерой:**
  
5. **Создано практическое приложение-помощник преподавателя:**


---

### Ответы на контрольные вопросы

**1. Какие типы разрешений существуют в Android? В чём их отличие?**

- **Нормальные разрешения (Normal)** — не представляют угрозы для приватности, предоставляются автоматически при установке (например, INTERNET, ACCESS_NETWORK_STATE)
- **Опасные разрешения (Dangerous)** — дают доступ к конфиденциальным данным, требуют явного запроса у пользователя во время выполнения (например, CAMERA, READ_CONTACTS, ACCESS_FINE_LOCATION)

**2. Как запросить опасное разрешение во время выполнения приложения?**

```kotlin
if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
    != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(this, 
        arrayOf(Manifest.permission.CAMERA), 
        REQUEST_CODE)
} else {
    // Разрешение уже есть
}
```

**3. Какие методы необходимо переопределить для обработки результата запроса разрешения?**

Необходимо переопределить метод `onRequestPermissionsResult()`:
```kotlin
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    if (requestCode == REQUEST_CODE) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Разрешение получено
        } else {
            // Разрешение отклонено
        }
    }
}
```

**4. Как создать канал уведомлений для Android 8.0 и выше?**

```kotlin
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    val channel = NotificationChannel(
        CHANNEL_ID,
        "Название канала",
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = "Описание канала"
    }
    val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.createNotificationChannel(channel)
}
```

**5. Как отправить уведомление пользователю?**

```kotlin
val notification = NotificationCompat.Builder(this, CHANNEL_ID)
    .setSmallIcon(android.R.drawable.ic_dialog_info)
    .setContentTitle("Заголовок")
    .setContentText("Текст уведомления")
    .setPriority(NotificationCompat.PRIORITY_HIGH)
    .setAutoCancel(true)
    .build()

val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
manager.notify(NOTIFICATION_ID, notification)
```

**6. Как реализовать вибрацию устройства?**

```kotlin
val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
} else {
    vibrator.vibrate(500)
}
```

**7. Какие разрешения необходимы для работы с камерой?**

- `android.permission.CAMERA` — опасное разрешение, требуется запрос во время выполнения
- В `AndroidManifest.xml` также нужно указать:
```xml
<uses-feature android:name="android.hardware.camera" android:required="true" />
```

**8. Как отобразить предварительный просмотр с камеры?**

Для отображения предварительного просмотра необходимо:
1. Добавить `SurfaceView` в разметку
2. Получить доступ к камере через `Camera` или `CameraX`
3. Установить SurfaceHolder.Callback для обработки событий поверхности
4. В методе `surfaceCreated()` открыть камеру и начать предварительный просмотр
