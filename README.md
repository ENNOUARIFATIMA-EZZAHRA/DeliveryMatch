# 🚚 DeliveryMatch

تطبيق ويب يربط بين السائقين والمرسلين في إطار مفهوم النقل التعاوني، بهدف تحسين استغلال المسافات وتخفيض تكاليف النقل.

## ✨ الميزات الرئيسية

### 👤 المستخدمون
- **السائقون**: نشر إعلانات الرحلات وإدارة الطلبات
- **المرسلون**: البحث عن رحلات وإرسال طلبات النقل
- **المدراء**: إدارة المنصة والإحصائيات

### 🔧 التقنيات المستخدمة

#### Backend
- **Spring Boot 3** - إطار العمل الرئيسي
- **Spring Security** - الأمان والمصادقة
- **Spring Data JPA** - التفاعل مع قاعدة البيانات
- **PostgreSQL** - قاعدة البيانات
- **JWT** - المصادقة
- **Docker** - الحاويات

#### Frontend
- **Angular 16** - إطار العمل
- **TailwindCSS** - التصميم
- **Chart.js** - الرسوم البيانية
- **TypeScript** - لغة البرمجة

## 🚀 التثبيت والتشغيل

### المتطلبات
- Docker & Docker Compose
- Node.js 18+ (للتطوير)
- Java 17+ (للتطوير)

### التشغيل السريع مع Docker

```bash
# استنساخ المشروع
git clone https://github.com/your-username/DeliveryMatch.git
cd DeliveryMatch

# تشغيل جميع الخدمات
docker-compose up -d

# الوصول للتطبيق
# Frontend: http://localhost
# Backend API: http://localhost:8080
# Database: localhost:5432
```

### التطوير المحلي

#### Backend
```bash
cd backend
./mvnw spring-boot:run
```

#### Frontend
```bash
cd front
npm install
npm start
```

## 📁 هيكل المشروع

```
DeliveryMatch/
├── backend/                 # Spring Boot Backend
│   ├── src/main/java/
│   │   ├── controller/      # Controllers
│   │   ├── model/          # Entities
│   │   ├── repository/     # Data Access
│   │   ├── service/        # Business Logic
│   │   └── security/       # Security Config
│   ├── Dockerfile
│   └── pom.xml
├── front/                   # Angular Frontend
│   ├── src/app/
│   │   ├── pages/          # Components
│   │   ├── service/        # Services
│   │   └── shared/         # Shared Components
│   ├── Dockerfile
│   └── package.json
├── docker-compose.yml
└── README.md
```

## 🔐 الأمان

- **JWT Authentication** - مصادقة آمنة
- **Role-based Access Control** - التحكم في الصلاحيات
- **Input Validation** - التحقق من المدخلات
- **CORS Configuration** - إعدادات الأمان
- **SQL Injection Protection** - حماية من حقن SQL

## 🧪 الاختبارات

### Backend Tests
```bash
cd backend
./mvnw test
```

### Frontend Tests
```bash
cd front
npm test
```

## 📊 الإحصائيات والمراقبة

- **Health Checks** - فحص صحة الخدمات
- **Logging** - تسجيل الأحداث
- **Error Handling** - معالجة الأخطاء
- **Performance Monitoring** - مراقبة الأداء

## 🚀 النشر

### Production Deployment
```bash
# بناء وتشغيل في الإنتاج
docker-compose -f docker-compose.prod.yml up -d
```

### Environment Variables
```bash
# Backend
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/deliverymatch
JWT_SECRET=your-secret-key
SPRING_PROFILES_ACTIVE=production

# Frontend
API_URL=http://localhost:8080/api
```

## 🔧 التحسينات المضافة

### ✅ تم إنجازه
- [x] معالجة الأخطاء الشاملة (Backend & Frontend)
- [x] اختبارات JUnit للـ Backend
- [x] اختبارات Angular للـ Frontend
- [x] Docker configuration محسن
- [x] Loading states
- [x] صفحة Profile
- [x] معالجة الأخطاء في Services
- [x] تحسينات الأداء

### 🚧 قيد التطوير
- [ ] Chart.js للإحصائيات
- [ ] نظام التقييمات
- [ ] التنبيهات في الوقت الفعلي
- [ ] البحث المتقدم
- [ ] تطبيق الهاتف المحمول

## 🤝 المساهمة

1. Fork المشروع
2. إنشاء branch جديد (`git checkout -b feature/AmazingFeature`)
3. Commit التغييرات (`git commit -m 'Add some AmazingFeature'`)
4. Push إلى Branch (`git push origin feature/AmazingFeature`)
5. فتح Pull Request

## 📝 الترخيص

هذا المشروع مرخص تحت رخصة MIT - انظر ملف [LICENSE](LICENSE) للتفاصيل.

## 📞 الدعم

- 📧 البريد الإلكتروني: support@deliverymatch.com
- 🐛 الإبلاغ عن الأخطاء: [Issues](https://github.com/your-username/DeliveryMatch/issues)
- 📖 التوثيق: [Wiki](https://github.com/your-username/DeliveryMatch/wiki)

---

**Made with ❤️ for better transportation** 