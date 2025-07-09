
# Sistema de Fidelidad Gamificada

Programa de gestión de fidelización para tiendas de conveniencia con acumulación de puntos, niveles y bonificaciones.

## Diseño del Sistema

- **Arquitectura**: 3-capas (Modelo-Repositorio-Servicio)
- **Patrones**: Inyección de Dependencias, Singleton implícito
- **Flujo de compra**:
  1. Validar cliente
  2. Calcular puntos base (monto/100)
  3. Aplicar multiplicador de nivel
  4. Verificar bonus de 3 compras/día
  5. Actualizar estado del cliente
  6. Registrar compra

## Diagrama UML


```mermaid
classDiagram
    direction TB
    
    Cliente "1" --> "*" Compra : realiza
    Cliente --> Nivel : tiene
    
    class Cliente {
        -String id
        -String nombre
        -String correo
        -int puntos
        -Nivel nivel
        -int comprasHoy
        -LocalDate ultimaCompraFecha
        +getId() String
        +getNombre() String
        +getCorreo() String
        +getPuntos() int
        +getNivel() Nivel
        +setPuntos(int puntos) void
        +setNivel(Nivel nivel) void
    }
    
    class Compra {
        -String idCompra
        -String idCliente
        -double monto
        -LocalDate fecha
        +getIdCompra() String
        +getIdCliente() String
        +getMonto() double
        +getFecha() LocalDate
    }
    
    class Nivel {
        <<enumeration>>
        BRONCE(1.0)
        PLATA(1.2)
        ORO(1.5)
        PLATINO(2.0)
        -double multiplicador
        +getMultiplicador() double
        +determinarNivel(int puntos)$ Nivel
    }
```


## Instrucciones de Uso

### Compilación
```bash
mvn clean install
```

### Ejecución
```bash
mvn exec:java -Dexec.mainClass="com.fidelidad.MainApp"
```

### Pruebas Unitarias
```bash
mvn test
```

### Generación de Reporte de Cobertura
```bash
mvn jacoco:report
```
El reporte se generará en: `target/site/jacoco/index.html`

## Ejemplo de Salida de Tests
```
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
```

## Cobertura de Código

**Herramienta**: JaCoCo  
**Tipo de cobertura**: 
- Líneas de código ejecutadas
- Ramas condicionales
- Complejidad ciclomática

**Razón de elección**:
- Integración nativa con Maven
- Genera reportes HTML interactivos
- Métricas estándar en el ecosistema Java
- Fácil configuración mediante pom.xml

## Notas Técnicas

### Metodología TDD
Se aplicó Desarrollo Guiado por Pruebas en componentes críticos:
- Cálculo de puntos
- Determinación de niveles
- Validación de correos electrónicos

### Validaciones
- Reglas de negocio implementadas en la capa de servicio
- Validación de formatos (email, montos positivos, fechas válidas)

### Extensibilidad
- Diseñado para fácil migración a base de datos
- Patrón Repository permite cambiar implementación de persistencia
- Jerarquía de niveles implementada como enum extensible

### Consideraciones
- Persistencia actual en memoria (no persistente entre ejecuciones)
- El bonus por compras consecutivas aplica solo para compras el mismo día
- Los niveles se recalculan automáticamente tras cada compra
```
