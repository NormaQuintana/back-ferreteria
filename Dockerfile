# Usar una imagen base con Maven y OpenJDK 21
FROM maven:3.8.6-openjdk-21

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos del proyecto
COPY . /app

# Ejecutar el comando de construcción
RUN mvn clean package

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar el archivo JAR
CMD ["java", "-jar", "target/mi-backend-0.0.1-SNAPSHOT.jar"]
