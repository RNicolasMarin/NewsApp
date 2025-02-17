*NewsApp*

Esta es una aplicacion de Noticias hecha con Kotlin y Jetpack Compose para Android consultando la api de https://www.jsonplaceholder.org/.

Este proyecto esta estructurado por funcionalidades y dentro de cada una esta dividido en data, domain y presentation. Al mismo nivel hay otro package core que internamente esta dividido de la misma forma.

**Funcionalidades:**

- core //codigo general
- maps //pantalla con el mapa
- posts //Pantalla con lista y detalle de los posts
- users //pantalla de usuarios

Capas dentro de cada funcionalidad:
- posts
- - data //contiene el acceso a datos, en este caso el servicio retrofit relacionado a posts y los dtos, mappers, implementacion del repositorio y como instanciarlo con dependency injection 
- - domain //contiene los modelos, la interface del repositorio y casos de uso de usarlos.
- - presentation //contiene lo relacionado a cada pantalla: Una clase Screen en compose que representa una pantalla, su ViewModel, State y una sealed interface de Actions que ul usuario puede hacer en esas pantalla.

El proyecto empieza desde MainActivity con el Composable *NewsApp* que se encarga de la navegacion definiendo las 4 rutas/pantallas posibles usando un NavHost:
- Home: Lista de Posts,
- Detail: Detalles del Post
- Users: Lista de Usuarios
- Maps: Mapa con la ubicacion del usuario

Tambien incluye NavigationBar para moverme entre las pantallas de Home y Users.

Como ejemplo de como funcionan estas funcionalidades y como estan estructuradas esta la funcionalidad de Posts. Esta app esta construida usando MVVM/MVI:

Por parte de presentation tenemos estas clases
- HomeScreen //Tiene como variable al ViewModel y le envia Actions con lo que debe hacer
- - HomeViewModel //Tiene un observable con un State y la informacion que tiene esa pantalla
   
Los Composables Screen tienen un metodo que recibe/instancia su viewModel y llama a otro metodo que recibe el State del viewmodel y una funcion para llamar a los Actions. Esto es para simplificar el usos de previews usando el Composable sin el viewmodel y poder manejar el State pasado. 

HomeViewModel recibe un repository ya que la capa depresentation puede acceder a la capa de domain.
La capa de data usa su module de DI para inyectar una implementacion del repository (ya que tambien puede acceder a la capa de domain) que usa el service de retrofit para las consultas y usa mappers para convertir la respuesta de formato dto a una clase del domain.

- PostRepositoryImpl
- - PostService

**Construido con:**
- Jetpack Compose: para la UI
- MVVM/MVI: Patron de arquitectura
- Hilt: para inyeccion de dependencias
- Retrofit: para consultas https
- Coil: para cargar imagenes
- Google Map Api: para mostrar la ubicacion de los usuarios en un mapa al seleccionar un usuario.
- Junit, Truth y Mock: para Unit test y mocking respuestas.

**Correr App:**
Clonar la app, pararse en master, reemplazar en el archivo manifest el valor "YOUR_API_KEY_HERE" por tu Api Key de Google Map y correr la app.




