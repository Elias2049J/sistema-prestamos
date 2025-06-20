# Sistema de Préstamos Personales

## Descripción
Sistema de gestión de préstamos que permite registrar clientes, crear préstamos y gestionar pagos de cuotas. Desarrollado en **Java** con interfaz gráfica **Swing** y orientado a objetos.

## Características
- Registro de clientes
- Creación de préstamos con cálculo automático de cuotas a pagar
- Generación de cronograma de pagos
- Gestión de pagos
- Historial de pagos realizados por un cliente

## Arquitectura
- **Patrón Repository:** Para persistir datos
- **Implementación-Componente:** Encapsula la lógica de negocio
- **Factory:** Para crear instancias y facilitar la inyección de dependencias

## Estructura del Proyecto
```
src/main/java/org/sistema/
├── entity/         # Clases de dominio
├── repository/     # Almacenamiento de datos en memoria
├── use_case/       # Operaciones de negocio e intenciones del usuario
├── model/          # Implementaciones de los use_case
├── vista/          # Interfaz gráfica con Swing
├── factory/        # Fábrica de casos de uso
├── persistencia/   # Persistencia de préstamos
├── data/           # Contiene historial de pagos, cronograma generado para un préstamo y una lista de préstamos realizados
└── interfaces/     # Interfaces de persistencia y repository
```
## Requisitos
- Java 21
- Maven

## Ejecución
1. Clona el repositorio.
2. Compila el proyecto con Maven:
   ```bash
   mvn clean install
   ```
3. Ejecuta la aplicación:
   ```bash
   mvn exec:java -Dexec.mainClass="org.sistema.Main"
   ```
## Autor
- Henoc Elías Jorge Ugarte
- www.github.com/Elias2049J
- @henoceliasjorgeugarte@gmail.com