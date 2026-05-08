# Workflow Git — Practica Final

**Administrador del repositorio:** Hugo  
**Ramas del equipo:** `Hugo` · `Marcos` · `Daniel`  
**Rama principal:** `main`

---

## Regla fundamental

Nadie hace cambios directamente en `main`.  
Todo cambio pasa por la rama propia → Pull Request → revisión de Hugo → merge a `main`.

---

## 1. Configuración inicial (solo una vez)

Clona el repositorio y sitúate en tu rama:

```bash
git clone <url-del-repositorio>
cd Practica-Final
git checkout TuNombre        # Hugo / Marcos / Daniel
```

---

## 2. Flujo de trabajo diario

### Paso 1 — Actualizar tu rama con lo último de main

Antes de ponerte a trabajar, trae los cambios que otros hayan mergeado a `main`:

```bash
git checkout TuNombre
git merge main
```

### Paso 2 — Trabajar y guardar cambios

Edita código en IntelliJ normalmente. Cuando termines una parte:

```bash
git add .
git commit -m "Descripción breve de lo que hiciste"
git push origin TuNombre
```

Ejemplos de mensajes de commit:
- `"Implementar MatrizHabitacion"`
- `"Añadir movimiento jugador dentro de habitacion"`
- `"Corregir bug en BFS celdas alcanzables"`

### Paso 3 — Pedir que tus cambios entren en main (Pull Request)

Cuando tu funcionalidad esté terminada y probada:

1. Ve a GitHub → tu repositorio
2. Haz clic en **"Compare & pull request"** (aparece automáticamente al hacer push)
3. Selecciona: `base: main` ← `compare: TuNombre`
4. Escribe un título y descripción de lo que añades
5. Haz clic en **"Create pull request"**
6. **Espera a que Hugo lo revise y apruebe**

### Paso 4 — Hugo revisa y aprueba (solo Hugo)

Hugo recibe el Pull Request y:

1. Revisa el código en GitHub
2. Si está correcto → **"Merge pull request"** → cambios entran en `main`
3. Si hay problemas → añade comentarios en el PR → el autor corrige y hace push a su rama → el PR se actualiza automáticamente

---

## 3. Resumen visual

```
Marcos/Daniel editan código
        │
        ▼
   git commit + push → rama propia (Marcos / Daniel)
        │
        ▼
   Pull Request en GitHub (rama → main)
        │
        ▼
   Hugo revisa el código
        │
   ┌────┴────┐
   │         │
  OK       Cambios
   │         │
   ▼         ▼
 Merge     Autor corrige
 a main    y hace push
```

---

## 4. Comandos de referencia rápida

| Acción | Comando |
|--------|---------|
| Ver en qué rama estás | `git branch` |
| Cambiar de rama | `git checkout TuNombre` |
| Actualizar rama con main | `git merge main` |
| Ver estado de cambios | `git status` |
| Guardar cambios | `git add . && git commit -m "mensaje"` |
| Subir cambios | `git push origin TuNombre` |
| Ver historial | `git log --oneline` |

---

## 5. Normas del equipo

- **Nunca** hagas `git push origin main` directamente
- **Siempre** actualiza tu rama con `git merge main` antes de empezar a trabajar
- Un commit = una cosa concreta (no mezcles features distintas en un commit)
- Si tienes un conflicto que no sabes resolver → avisa a Hugo antes de forzar nada
