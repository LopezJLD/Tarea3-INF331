
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

## Instrucciones
1. **Compilar**:
```bash
mvn clean install
```

2. **Ejecutar**:
```bash
mvn exec:java -Dexec.mainClass="com.fidelidad.MainApp"
```

3. **Pruebas**:
```bash
mvn test
```

4. **Cobertura** (JaCoCo):
```bash
mvn jacoco:report
```
Reporte en: `target/site/jacoco/index.html`

## Ejemplo de Salida de Tests
```
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
```

## Cobertura
**Herramienta**: JaCoCo  
**Tipo**: Cobertura de líneas y ramas  
**Razón**:  
- Proporciona métricas claras de código ejercitado  
- Integración sencilla con Maven  
- Genera reportes HTML detallados  
- Estándar en ecosistema Java  
```

---



### Notas Adicionales
**TDD**: Los tests se desarrollaron primero en casos críticos (cálculo de puntos, niveles)
**Cobertura**: JaCoCo mide:
   - Líneas de código ejecutadas
   - Decisiones lógicas cubiertas
   - Complejidad ciclomática
**Extensibilidad**: Diseñado para fácil migración a base de datos
**Validaciones**: Se incluyen en servicio, no en modelos


## Diagrama UML
```mermaid
classDiagram
    Cliente "1" -- "*" Compra
    Cliente --> Nivel
    class Cliente{
        -String id
        -String nombre
        -String correo
        -int puntos
        -Nivel nivel
        -int comprasHoy
        -LocalDate ultimaCompraFecha
    }
    class Compra{
        -String idCompra
        -String idCliente
        -double monto
        -LocalDate fecha
    }
    class Nivel{
        <<enum>>
        BRONCE
        PLATA
        ORO
        PLATINO
        +double multiplicador
    }

