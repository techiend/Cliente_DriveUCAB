**Consola de servico para Módulos de Ubii Pagos C.A.**

*En el siguiente git se encuentra todo el proyecto relacionado a la Consola de control de los módulos de Ubii Pagos*

---

## ¿Cómo empezar?

El proceso de desarrollo comienza clonando el branch "release/desarrollo". Con dicho branch en forma local, comienza todo el proceso de **DESARROLLO** del servicio.

---

## Proceso correcto para realizar un **MERGE**

### Local -> Desarrollo ###
1. Lo correcto es realizar pruebas de forma local y una vez aprobadas dichas pruebas realizar un **Commit** a "*release/desarrollo*".
2. Una vez realizado el **Commit**, se debe subir el *objeto.jar* al servidor en cuestión. (Desarrollo)

### Desarrollo -> Calidad ###
3. Al realizar las pruebas correspondientes en dicho servidor, se procede a realizar un **Commit** a "*bugfix/calidad*".
4. Una vez realizado el **Commit**, se debe subir el *objeto.jar* al servidor en cuestión. (Calidad)
5. Al realizar las pruebas correspondientes en dicho servidor, en caso de error se debe hacer **Rollback** y realizar los ajustes pertinentes desde el paso **1.**, en caso de no haber ningún error, se procede a realizar un **Commit** a "*release/calidad*".

### Calidad -> Producción ###
6. Al aprobar las pruebas en "*release/calidad*" se debe subir el *objeto.jar* al servidor en cuestión. (Producción)
7. Se deben realizar nuevamente las pruebas en el servidor y una vez aprobadas dichas pruebas, realizar un **Commit** a "*master*", en caso de error se debe hacer **Rollback** y realizar los ajustes pertinentes desde el paso **1.**,


---

**©** *Contenido **NETAMENTE PRIVADO** prohibida su extracción de la empresa o publicación.*