# ===== 1-bosqich: build =====
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Avval faqat bog'liqliklar ro'yxati. pom.xml o'zgarmasa, Docker
# keyingi RUN natijasini cache'dan oladi va kutubxonalarni qayta yuklamaydi.
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline

# Kod keyin ko'chiriladi: kod o'zgarsa faqat shu yerdan qayta boshlanadi.
COPY src src
RUN ./mvnw -B package -DskipTests

# ===== 2-bosqich: ishga tushirish =====
# JDK, Maven va manba kod bu image'ga o'tmaydi — faqat jar.
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
