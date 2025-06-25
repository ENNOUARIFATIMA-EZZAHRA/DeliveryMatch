# استخدام Node.js 18 مع Alpine Linux
FROM node:18-alpine AS builder

# تعيين مجلد العمل
WORKDIR /app

# نسخ ملفات التبعيات
COPY package*.json ./

# تثبيت التبعيات
RUN npm ci --only=production

# نسخ الكود المصدري
COPY . .

# بناء التطبيق
RUN npm run build

# استخدام Nginx للتشغيل
FROM nginx:alpine

# نسخ الملفات المبنية
COPY --from=builder /app/dist/* /usr/share/nginx/html/

# نسخ إعدادات Nginx المخصصة
COPY nginx.conf /etc/nginx/nginx.conf

# فتح المنفذ
EXPOSE 80

# أمر التشغيل
CMD ["nginx", "-g", "daemon off;"] 