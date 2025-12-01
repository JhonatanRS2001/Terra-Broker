# Mini Broker - Proyecto Java Spring Boot - Jhonatan Romero

> Os presento Terra-Broker, una aplicación web tipo broker financiero, desarrollada con Java, Spring Boot y Thymeleaf.  
> Permite a los usuarios simular la compra y venta de activos financieros, gestionar su cartera y ver gráficos de precios en tiempo real.

---

## Tecnologías utilizadas

- **Backend:** Java 17, Spring Boot, Spring Data JPA, Hibernate  
- **Base de datos:** MySQL / PostgreSQL  
- **Frontend:** Thymeleaf, HTML, CSS, Bootstrap  
- **Gráficos y visualización:** Chart.js (para representar precios de activos y evolución de la cartera)  
- **Control de versiones:** Git & GitHub  
- **Simulación de mercado:** Servicio interno que actualiza automáticamente los precios de los activos habiendo PUMPS y CRASH aleatorios en los activos  

---

## Funcionalidades principales

1 **Gestión de usuarios**
   - Registro e inicio de sesión
   - Cifrado de contraseñas con PasswordEncoder
   - Perfil con saldo disponible y cartera
   - Modificacion de cuenta

2 **Gestión de cartera**
   - Visualización de las posiciones actuales
   - Valor de cada activo y ganancia/pérdida
   - Gráfico con evolución del saldo de la cartera

3 **Compra y venta de activos**
   - Registro de cada operación como una transacción
   - Actualización automática de las posiciones

4 **Historial de transacciones**
   - Ver todas las compras, ventas e ingresos realizadas
   - Visualizacion de beneficios/perdidas cada transacción

5 **Simulación de precios en tiempo real**
   - Precios de activos cambian automáticamente
   - Posiciones se recalculan automáticamente según el precio del mercado

6 **Dashboard**
   - Valor total de la cartera
   - Distribución de activos
   - Gráficos de evolución de la cartera y precios

## ⚙️ Instalación y ejecución

1 **Clonar el repositorio**

git clone https://github.com/JhonatanRS2001/Terra-Broker

2 **Configurar la base de datos (application.properties)**

- spring.datasource.url=jdbc:mysql://localhost:3306/mini_broker
- spring.datasource.username=usuario
- spring.datasource.password=12345678
- spring.jpa.hibernate.ddl-auto=update

3 **Ejecutar la aplicación**
mvn spring-boot:run

4 **Abrir en el navegador**
http://localhost:8080
