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

أوامر التحقق
````md
```sql
SHOW TABLES;
SELECT * FROM USERS;
SELECT * FROM SERVICES;
SELECT * FROM APPOINTMENTS;

 

