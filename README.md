# نظام إدارة المواعيد الذكي
Appointment Smart Scheduling System

## 1. وصف عام عن المشروع
يهدف مشروع نظام إدارة المواعيد الذكي إلى تطوير نظام Backend لإدارة المواعيد بشكل منظم وذكي باستخدام Spring Boot.
يسمح النظام بحجز المواعيد ضمن أوقات عمل محددة، ويمنع التعارضات الزمنية والحجز خارج أوقات الدوام.
يدعم النظام عدة أدوار (ADMIN, STAFF, CUSTOMER) ويطبق آليات أمان باستخدام JWT.
---

## 2. أهداف المشروع
- بناء RESTful APIs باستخدام Spring Boot
- تطبيق منطق أعمال واقعي لإدارة المواعيد
- التعامل الدقيق مع التواريخ والأوقات
- تطبيق مبادئ Clean Code وفصل الطبقات
- تأمين النظام باستخدام Spring Security و JWT
- تصميم قاعدة بيانات مترابطة وقابلة للتوسع

---

## 3. المستخدمون (Roles)
- ADMIN: إدارة المستخدمين، الخدمات، أوقات العمل، والموافقة على المواعيد
- STAFF: الاطلاع على المواعيد المخصصة له
- CUSTOMER: حجز، تعديل، وإلغاء المواعيد

---

## 4. التقنيات المستخدمة
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security (JWT)
- H2 Database
- Maven

---

## 5. هيكلية النظام
يعتمد النظام على بنية الطبقات التالية:
- Controller Layer
- Service Layer
- Repository Layer
- Entity Layer

---

## 6. قاعدة البيانات
الجداول الرئيسية:
- USERS: معلومات المستخدمين والأدوار
- SERVICES: تفاصيل الخدمات
- APPOINTMENTS: بيانات المواعيد وحالاتها

الدخول إلى H2 Console:
https://localhost:8443/h2-console

أوامر التحقق:
```sql
SHOW TABLES;
SELECT * FROM USERS;
SELECT * FROM SERVICES;
SELECT * FROM APPOINTMENTS;

 7. الأمان

المصادقة باستخدام JWT

التفويض بناءً على الدور (Role-Based Authorization)

منع الوصول إلى الـ APIs بدون Token

8. كيفية تشغيل المشروع

تثبيت Java 17 و Maven

فتح المشروع باستخدام IDE

تنفيذ الأوامر التالية:

mvn clean install
mvn spring-boot:run


اختبار الـ APIs باستخدام Postman

9. توثيق الـ APIs
تسجيل مستخدم

POST
https://localhost:8443/api/auth/register

{
  "firstName": "أحمد",
  "lastName": "علي",
  "email": "ahmed.ali@test.com",
  "password": "Ahmed123!",
  "phone": "0912345678",
  "role": "CUSTOMER"
}

تسجيل الدخول

POST
https://localhost:8443/api/auth/login

{
  "email": "ahmed.ali@test.com",
  "password": "Ahmed123!"
}

جلب جميع المستخدمين (ADMIN فقط)

GET
https://localhost:8443/api/users

Authorization: Bearer <ADMIN_TOKEN>

جلب الملف الشخصي

GET
https://localhost:8443/api/users/profile

Authorization: Bearer <CUSTOMER_TOKEN>

حذف مستخدم (ADMIN فقط)

DELETE
https://localhost:8443/api/users/{id}

Authorization: Bearer <ADMIN_TOKEN>

إنشاء خدمة (ADMIN فقط)

POST
https://localhost:8443/api/services

Authorization: Bearer <ADMIN_TOKEN>

{
  "name": "استشارة طبية",
  "description": "استشارة طبية لمدة 30 دقيقة",
  "price": 150.0,
  "duration": 30,
  "staff": {
    "id": 2
  }
}

جلب الخدمات

GET
https://localhost:8443/api/services

Authorization: Bearer <TOKEN>

حذف خدمة (ADMIN فقط)

DELETE
https://localhost:8443/api/services/{id}

Authorization: Bearer <ADMIN_TOKEN>

حجز موعد

POST
https://localhost:8443/api/appointments?customerId=1&staffId=2&serviceId=1

Authorization: Bearer <CUSTOMER_TOKEN>

{
  "appointmentDate": "2024-12-15",
  "startTime": "09:00:00",
  "endTime": "09:30:00",
  "notes": "أول موعد اختبار"
}

جلب مواعيد العميل

GET
https://localhost:8443/api/appointments/customer/1

Authorization: Bearer <CUSTOMER_TOKEN>

جلب مواعيد الموظف

GET
https://localhost:8443/api/appointments/staff/2

Authorization: Bearer <STAFF_TOKEN>

جلب جميع المواعيد (ADMIN فقط)

GET
https://localhost:8443/api/appointments/all

Authorization: Bearer <ADMIN_TOKEN>



