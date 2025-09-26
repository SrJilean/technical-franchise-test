# Technical Franchise Test - Backend

Este proyecto es un **servicio backend** desarrollado en **Spring Boot (Java 17)** con conexión a una base de datos *
*MySQL en AWS RDS**, empaquetado en **Docker** y desplegado en **AWS ECS Fargate** usando **Terraform** y **GitHub
Actions**.

---

## 🚀 Requisitos previos

Asegúrate de tener instalado lo siguiente:

- [Java 17](https://adoptium.net/)
- [Maven](https://maven.apache.org/) (o usar el wrapper `./mvnw`)
- [Docker](https://www.docker.com/)
- [Terraform](https://developer.hashicorp.com/terraform/downloads)
- [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html) configurado con credenciales
  válidas

Las credenciales validas están mas abajo.

---

## ⚙️ Configuración local

El instructivo se va a hacer con las instrucciones necesarias para realizar la instalación del proyecto en dispositivos
Windows

1. Clonar el repositorio:

   ```cmd
   git clone https://github.com/<tu-usuario>/technical-franchise-test.git
   cd technical-franchise-test

1.1. Los siguientes comandos se deben realizar en la raiz del proyecto

2. Instalar dependencias

    ```cmd
    ./mvnw clean install

3. Configurar crecendiales de AWS

    ```cmd
   aws configure
   ```

   A continuación se deben asignar las siguientes credenciales a medida que se vayan pidiendo:

    ```cmd
    AWS Access Key ID [None]: AKIAWIELA72XAZHMT7EY
    AWS Secret Access Key [None]: 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
    Default region name [None]: us-east-1
    Default output format [None]: json
    ```

4. Hacer build del docker

    ```cmd
    docker build -t franchises-svc:local .
    ```

5. Correr el contenedor

    ```cmd
   docker run -p 8080:8080 -e SPRING_R2DBC_URL="r2dbc:mysql://technical-franchise-test.ceyqol1qxxxd.us-east-1.rds.amazonaws.com:3306/technical_test" -e SPRING_R2DBC_USERNAME="admin" -e SPRING_R2DBC_PASSWORD="19012030Abc" franchises-svc:local
    ```

6. Probar el servicio

   ```cmd
   Invoke-RestMethod -Uri "http://localhost:8080/api/franchises" -Method GET
   ```

## Peticiones a la nube

Para hacer peticiones a la aplicación alojada en la nube, se pueden hacer directamente al url a continuación:

   ```
   http://44.200.128.110:8080/
   ```

## Documentación de peticiones

El proyecto incluye una interfaz interactiva de **Swagger UI** para explorar y probar los endpoints disponibles.

Una vez que el servicio esté corriendo, puedes acceder agregando "/swagger-ui.html" al final del url donde estés
haciendo las peticiones