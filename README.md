# Taller 5

Introducción a AWS

## Empezando

Para ver los programas en la web solo es necesario abrir heroku, si se desea ver el codigo fuente o correr el servidor desde local sera necesario tener maven, git y heroku en su computador.

### Prerrequisitos

#### Java
 Java es necesario para correr el programa, para comprobar si esta instalado desde la linea de comandos:

```
>java -version

java version "1.8.0_181"
Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)
```

#### Maven
El programa corre con maven, para comprobar si esta instalado desde la linea de comandos:

```
>mvn -v

Apache Maven 3.6.1 (d66c9c0b3152b2e69ee9bac180bb8fcc8e6af555; 2019-04-04T14:00:29-05:00)
Maven home: C:\Program Files\apache-maven-3.6.1\bin\..
Java version: 1.8.0_181, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk1.8.0_181\jre
Default locale: es_CO, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

#### Git
Para descargar el programa se necesita git, para comprobar si esta instalado desde la linea de comandos:

```
> git --version

git version 2.21.0.windows.1
```

#### Heroku
La pagina web esta alojada en heroku, para comprobar si esta instalado desde la linea de comandos:

```
> heroku -v

heroku/7.37.0 win32-x64 node-v12.13.0

```

### Instalación

Para instalar el repositorio con los calentamientos se instala en la ruta deseada desde git de esta forma

```
> git clone https://github.com/svega99/IntroduccionAWS.git

```

Para compilar desde maven:

```
> mvn package
```
 
## Ejecutando el programa

### Heroku 
[![Heroku](https://camo.githubusercontent.com/be46aee4f8d55e322c3e7db60ea23a4deb5427c9/68747470733a2f2f6865726f6b752d62616467652e6865726f6b756170702e636f6d2f3f6170703d6865726f6b752d6261646765)](https://shrouded-mesa-15887.herokuapp.com/index.html)

### Cliente
Si quiere ejecutar cualquier el cliente desde local debe poner en la linea de comandos desde la ruta del proyecto (como ejemplo se ponen a correr 10 hilos):

```
>  mvn  exec:java -D "edu.escuelaing.arep.awsclient.URLReader" -Dexec.args="https://shrouded-mesa-15887.herokuapp.com/index.html 10"

```


## Diseño

[DISEÑO.pdf](DISEÑO.pdf)

## Authors

* **Santiago Vega Romero**  - [svega99](https://github.com/svega99)

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details
