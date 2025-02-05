Prueba Tecnica Android Developer
******************************

Tecnologias usadas:

Lenguaje: Kotlin
Jetpack Compose
Retrofit
Dagger Hilt
Room

Patron de Diseño
MVVM


La aplicacion se encarga de verficar en la BD local si existe algun registro de Videojuegos, si no existe, realiza un request hacia la API : https://www.freetogame.com/api/games
En donde se obtiene un json con el catalogo de videojuegos, el cual una vez obtenido se registraran los datos en la tabla videogames de la BD local.

Una vez obtenido los datos, se presentaran en panrtalla la lista de videojuegos en un grid (LazyVerticalGrid), en donde se podra buscar por titulo del juego , o en su defecto seleccionar
la categoria en el Spinner de la parte superior para filtar la lista.

Si se desea borrar algun videojuego , basta dejar presionado alguna imagen de la portada de la lista de videojuegos, y se mostrara un AlertDialog, con un mensaje de confirmacion para eliminar el juego seleccionado.
Si la respuesta es Aceptar , se eliminara el registro de la BD local mediando un Delete y se actualizara el listado de videojugos.

Al seleccionar algun videojuego, se mostrara el detalle de los datos mas relevantes, como Su portada, desarrollador, fecha de lanzamiento , descripcion y se podra ir a la Web del Videojuego o al perfil de videojuego
en la pagina FreeToGame.




