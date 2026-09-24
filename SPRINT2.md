# TecCerca — Sprint 2

## Recorrido del cliente

Login → Home → búsqueda y categorías → perfil del técnico → formulario → confirmación.

- Home con nombre del usuario autenticado, perfil, catálogo real y técnicos cercanos.
- Búsqueda por nombre, descripción y especialidad; ignora mayúsculas y tildes.
- Categorías obtenidas de GET /api/especialidades. La búsqueda de técnicos consulta cada especialidad y combina los resultados sin duplicados.
- Zona inicial: Lima centro, radio de 5 km. La opción «Usar mi ubicación» solicita permiso y actualiza la búsqueda; denegarlo no bloquea la app.
- Perfil con avatar de iniciales, puntuación, distancia, experiencia y descripción.
- Formulario con descripción obligatoria, progreso, error, protección contra doble envío y confirmación tras respuesta exitosa.
- Atrás desde la confirmación vuelve al Home. Búsqueda y categoría se conservan al visitar perfiles.

## Configuración de demostración

El login actual devuelve idUsuario, nombre, correo e idRol; no devuelve idCliente. Ambos IDs pertenecen a tablas distintas.

ConfiguracionDemo.kt contiene dos relaciones verificadas por consulta de solo lectura a la BD local:

| Usuario | idUsuario | idCliente |
| --- | --- | --- |
| Juan Pérez | 1 | 1 |
| Luis Torres | 5 | 2 |

Esta relación es temporal y solo corresponde a esa base de datos de demostración. Otras cuentas muestran que necesitan vincularse y no pueden enviar a nombre de otro cliente. Para soportar cuentas nuevas, se necesitará obtener idCliente mediante el contrato de sesión o un endpoint de consulta; no se modificó el backend en este sprint.

Las notificaciones muestran un aviso de disponibilidad futura; no se simulan alertas ni respuestas del técnico. El perfil muestra los datos reales del login y permite cerrar sesión. No se almacena la contraseña. El estado de navegación se conserva en la restauración de la actividad, sin implementar autenticación persistente.

## Verificación

Pruebas locales: `gradlew.bat :app:testDebugUnitTest`

Pruebas de interfaz con un emulador conectado: `gradlew.bat :app:connectedDebugAndroidTest`

APK de demostración: `gradlew.bat :app:assembleDebug`

Las pruebas de interfaz inyectan respuestas controladas: no crean solicitudes reales ni cambian la BD. Cubren filtrado/vacío, agrupación de especialidades, perfil, validación, envío único, errores, confirmación y transporte Parcelable. Guardan capturas de las pantallas en el directorio externo sprint2-qa de la aplicación en el emulador.

## Prueba manual con el backend

1. Iniciar el backend existente y mantener accesible la URL de ConfiguracionApi.kt.
2. Iniciar sesión con una cuenta de demostración vinculada.
3. Buscar un nombre o servicio; probar categorías, búsqueda sin coincidencias y limpiar filtros.
4. Abrir un perfil, volver y verificar que se conservan los filtros.
5. Solicitar servicio, describir el problema y enviar una sola vez.
6. Verificar la confirmación y volver al Home.
7. El backend rechaza solicitudes activas duplicadas; el repositorio devuelve null para errores HTTP y la UI muestra un aviso general, no un éxito.

SolicitudRepositorio y el backend se mantuvieron sin cambios durante esta ampliación.

## Adaptación visual a las referencias de Figma

- Identidad teal, fondo gris muy claro, paneles blancos, bordes finos y encabezados centrados.
- Login con logo y panel de acceso; registro accesible aunque no se hayan completado las credenciales.
- Categorías en cuadrícula de dos columnas, con conteos calculados a partir de los técnicos recibidos.
- Tarjetas compactas con iniciales, especialidad, puntuación y distancia.
- Navegación inferior: Buscar, Cercanos y Perfil. El perfil del cliente ahora es una pantalla propia con cierre de sesión.
- Perfil técnico con información profesional por filas y acción «Solicitar servicio».
- Lista ordenable por distancia o mejor puntuación.

La referencia de mapa se adaptó a una vista de cercanía y selección de zona: el endpoint actual no entrega coordenadas individuales para dibujar marcadores reales. Las fotos se representan con iniciales. No se inventan apellidos, edad, verificaciones, antigüedad, cantidad de opiniones o reparaciones; tampoco se agregaron acciones de edición/recuperación sin una operación conectada.

## Verificación final de esta adaptación

- APK generado con BUILD SUCCESSFUL.
- 3 pruebas unitarias aprobadas.
- 10 pruebas instrumentadas aprobadas en el emulador Android 17, mediante ejecución directa de AndroidJUnitRunner.
- Capturas del emulador recuperadas y revisadas visualmente en app/build/outputs/figma-qa/sprint2-qa.
- Las capturas de pruebas utilizan datos controlados; el APK utiliza el login y las consultas reales del proyecto.
- No se crearon solicitudes reales durante las pruebas y no se modificó el backend.

Registro de la ejecución: app/build/figma-instrumentacion-final.log.
APK: app/build/outputs/apk/debug/app-debug.apk.
