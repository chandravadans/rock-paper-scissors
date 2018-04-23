# Rock, Paper, Scissors
##### An implementation by Chandravadan

## Prerequisites
* Java 8 installation, javac available on the command line with the ```$JAVA_HOME``` environment variable available and
set to the root of the installation (preferable)

* Internet connectivity

## Running the game

### On Linux based systems
On a linux system, the ```run.sh``` script can be used directly. This does 2 things:
1. Cleans from previous runs.
2. Downloads the necessary dependencies, compiles and runs the program.

Alternatively, you can also perform the steps individually by using the Maven wrapper commands,
as described in the next section.

### On Windows systems
On Windows systems, the Maven wrapper can be used to run the code. Following are the steps

````
> mvnw clean
> mvnw compile
> mvnw exec:java
````

## Configuration options
The code can be configured to be run not only with the traditional elements - rock, paper, scissors but also with
any arbitrary number of elements (rock, paper, scissors, lizard, spock in the popular TV Show Big Bang Theory). There are
3 files in ```src/main/resources``` that contain the configuration parameters for the game:

* ```game.properties``` : This has 3 lines - the elements to be used for the game and the locations of the rules and players files
* ```rules-classic.json``` : This is the location of the file where the rules are defined
* ```players.json```: This is the location of the file where the players are defined

The existing files model the classic rock, paper, scissors game - 2 players, one human and the other machine and 
three elements - rock, paper and scissors. These can be modified in any way you want, rules for Sheldon's version are defined
as an example in ```rules-sheldon.json```

Also, as mentioned in the ```players.json``` file, you can also configure players to be human or machines. To be fair, machines make random
moves.

## Libraries used
Following libraries have been used in the project:
* ```Gson```    : To parse the json config files
* ```logback``` : To provide logging capabilities
* ```junit5```  : For unit tests
