# Alpaca Emblem

Alpaca Emblem es la implementacion de un juego de estrategia por turnos, donde participan dos jugadores en este
se encuentran unidades e items que pueden equipar estos. El objetivo principal de este videojuego es derrotar a la unidad
enemiga denominada Heroe.

## Interfaces

_Explicación del sistema de implementación de las interfaces._

### Creacion de interfaces y clases abstractas en los items

Una de las principales ideas de esta tarea fue la implementación de diversas interfaces, para cada una de las entidades que 
participan en el codigo. Los item los podemos ver en tres tipos: armas fisicas, armas magicas y armas de curación, por ahora
poseen atributos muy parecidos, pero no actuan de la misma manera, a su vez estas interfaces son implementadas por cada clase 
abstracta(fisica, magica y de curación). Por otro lado las unidades implementan la misma interface y clase abstracta Unit, pues 
todas actuan de la misma manera.

| Interfaces    | Tipos de Armas |
| ------------- | -------------  |
| IAttackItem   | Fisicas        |
| IMagic        | Magicas        |
| IHeal         | Curación       |


_A su vez estas interfaces extienden a la interface que representa a todos los items, la cual se denomina IEquipableItem_




## Implementación de las Armas magicas

La implementación de los libros magicos (light, dark y anima) se basaron en las clases LightBook, DarkBook y AnimaBook, estas
son hijas de la clase abstracta AbstractBook, la cual a su vez implementa la interfaz IMagic, la idea principal de esta,
es que contengan todos los parametros necesarios que deba poseer un tipo de libro magico (daño, counters, etc). Las armas se dividieron en 3 paquetes contenidos en items, cada paquete contiene un tipo de item (magico, fisico y de curación).

A continuacion se presenta el UML de las armas magicas:

<img src="https://i.ibb.co/y0SMHxW/Package-magic.png" />

## Implementación del Sorcerer

La implementación del Sorcerer es muy parecida a las demas unidades, pues poseen parametros similares a las demas unidades, una
de las unicas diferencias que presenta son el tipo de armas que puede equipar, estas son las Armas Magicas(libros), esta unidad recibe los parametros de hitPoints, movement, location, maxItems, List items y a su vez implementa la interfaz de IUnit.

El constructor del sorcerer recibe los hitPoints, el movimiento, la locacion inicial y la lista de items, este realiza un super hacia la clase abstracta de Abstract Unit, el constructor se ve de la siguiente manera:

```
    public Sorcerer(final int hitPoints, final int movement, final Location location,
                    IEquipableItem... items){
        super(hitPoints, movement, location,3, items);
    }
```


## Implementación del sistema de combate

El sistema de combate se basa principalmente en un ataque y contrataque, una unidad con un arma equipada (dependiendo de la clase), podra atacar a una unidad enemiga siempre y cuando esta a la distancia correspondiente al arma, este ataque dependera 
del arma que posea el enemigo, pues el daño se calcula con el daño del arma equipada considerando si esta es fuerte o debil al arma del enemigo.


### Diseño

El diseño se basa principalmente en la metodologias del Doble Dispatch y overriding por parte de cada una de las clases correspondiente a las unidades. En la clase abstracta de unidad se implemento un metodo denominado attackEnemy que recibe como 
parametro, otra unidad a la cual se atacara, esta se encarga de mandar un mensaje al arma equipada del atacante sobre el metodo 
attack, este metodo se encuentra en los items capaces de atacar. Cada arma posee un metodo propio de attack por medio de Overriding, esta funcion attack cumple la tarea de realizar el Doble Dispatch, pues hace que el arma del enemigo mande un mensaje con el metodo counter (existen distintos counter en cada clase de las armas de modo que se pueda reconocer facilmente cual arma es debil contra el objeto y viceversa) y de esta forma calcular cuanto daño corresponde hacer en el ataque. Una vez calculado el daño se llama al metodo Damage, el cual se encarga de ver que sucedera una vez realizado el ataque. takeDamage es un metodo de la clase Abstract Unit, el cual se encarga de quitarle la vida a una unidad (en este caso al que recibio el golpe). Luego se verificara que la unidad este viva o no y si no lo es, se procede a un contrataque, calculando el daño y quitandoselo a la unidad correspondiente. La funcion check se encarga de verificar que el daño sea positivo, de lo contrario el trunca en 0.

La funcion counter realizada el Doble Dispatch en el sentido que, cuando una unidad realiza un ataque, no sabe cual es el arma del enemigo, es por esto que el metodo se encarga de que el arma del enemigo reciba el mensaje sobre el metodo counter, metodo que se encuentra en su respectiva clase, y de esta manera calcular el daño por medio de un Overriding.

Veamos un ejemplo de como se calcularia el daño cuando un libro de Anima golpea a un libro de Light, veamos que para calcular el daño se ejecutaria anima.attack(light):

```
    @Override
    public double attack(IEquipableItem item){

        return item.counterAnima(this);

    }
```
Aqui item seria Light, es decir retornaria el metodo counterAnima donde el metodo lookup empezaria en la clase del libro Light:


```
 @Override
    public double counterAnima(IEquipableItem item){

        return item.getPower()*1.5;
    }
```
Podemos ver que retornaria el poder del item, en este caso Anima multiplicado por 1.5 pues es fuerte contra Light. Es importante señalar que los metodos correspondientes para todos los items se encuentran en la clase Abstracta, luego se hace un overriding en cada clase para ir viendo caso a caso.

Es importante ver que existe un metodo llamado check, que se encarga de verificar que si una unidad golpea y su daño es disminuido, no inflija daño negativo, esto provacaria errores de curación. Por otro lado para evitar que un clerico el sanar reciba un contraataque, existe un verificador, tal que el daño inflinjido sea negativo (en ese caso no recibira un contraataque).

## Implementación del sistema de Intercambio

El sistema de intercambios se dividio en tres casos, de manera de darle mas flexividad al metodo de trade, uno es un intermcambio donde las dos unidades intercambian 1 item, otro donde una unidad regala un item y otro donde una unidad recibe un item. Se decidio este diseño para hacer màs comoda las dos situaciones y no estar trabajando con items nulos (en el caso de los dos ultimos).

El metodo trade recibe a la unidad con la que se realizara el intercambio, y los dos items que se intercambiaran. Primero verifica que todas las condiciones se cumplan, es decir, que esten a distancia 1 y que las unidades contengan los items que desean intercambiar, sigue quitar los elementos de la lista de ambos personajes y agregar los cambios a esta, luego se procede a nombrar como dueños de los items a quien corresponda.

Los pasos del programa se verian en el siguiente esquema:

<img src="http://img.fenixzone.net/i/62CbnyV.png" />

La parte importante en el codigo se muesta en el siguiente codigo, donde delivered es el item que se entregara y received el que se recibira, this y unit son las unidades participantes, el metodo unEquipAItem desequipara el item en caso de tenerlo equipado (para evitar cualquier error), el metodo add y remove item se encargan de eliminar o agregar el item correspondiente y finalmente se configura el owner de manera que se intercambien.
```
        this.unEquipAItem(delivered);
        unit.unEquipAItem(received);
        this.removeItem(delivered);
        unit.removeItem(received);
        this.addItem(received);
        unit.addItem(delivered);
        delivered.setOwner(unit);
        received.setOwner(this);
 ```


## Cambios al programa inicial

Uno de los cambios principales al programa inicial es el sistema de equipar un arma una cierta unidad, al comienzo este se realizaba con un if y un instanceof que verificaba que el arma correspondiera a la unidad. El cambio se basa en Doble Dispatch, en donde el arma recibe el mensaje de equipar el arma, luego la unidad recibira el mensaje para equipar cierta arma, de esta forma cada clase de unidades tendra un overriding de el metodo Equipar cierta arma y asi el equipar no producira errores y sera mucho más ampliada.

Se corrigieron algunos errores de tipeo, como por ejemplo equiparse a una unidad, o overridings donde no debian ir, etc. 


### Supuestos realizados

Los supuestos son los siguiente:

- No pueden haber dos unidades en la misma posición, es decir, las unidades pueden estar a 1 de distancia como minimo.
- No tener arma equipada significa que el parametro equippedItem es null.
- El mapa esta bien programado (no fue corregido nada en el codigo).



### Como ejecutarlo

Para ejecutar el programa existen una serie de test que ponen a prueba casos bordes y que el programa funcione correctamente en los distintos metodos, para el combate se verifica que ambas unidades golpen, que cambien de estado a muerto si esto ocurre, etc. Por otro lado tambien existen test que verifican los daños que producen las armas dependiendo a quien se ataque. Tambien se testean que las armas se equipen a las unidades correspondientes y no ocurran equivocaciones.

### Explicación de los test

#### Test de intercambio

En estos test principalmente se toman dos unidades (que por lo general era una getUnitTest() y una alpaca de prueba. Luego se agregaban distintos items a las unidades y se llamaba el metodo de trade, al finalizar se hacen test para probar que los items estuvieran en los inventarios correctos, que los owners fueran intercambiados. Por otro lado tambien se testearon intercambios con inventario lleno (esto para el caso de regalar o recibir un item, pues en el intercambio no es necesario).

#### Test de Combate

Estos test se centran principalmente en medir el daño que debe inflingir cada unidad hacia cada tipo de arma (se toman todos los casos para evitar errores), de esta manera se verifica que los golpes contra fuertes y debiles se produscan normalmente, tambien se testea que no se pueda golpear con daño negativo(solo el clerigo puede hacer eso pero es más una curación). Por otro lado se ponen casos bordes como pegar a unidades sin armas o pegar sin una arma (tambien que la alpaca intente golpear).

#### Test de equipación

Estos se centran en intentear equipar armas que las unidades no pueden equiparse (como que el clerigo se equipe un Hacha por ejemplo) o tambien que la llama se intente equipar un item. Por otro lado se crearon test que verifican que para tener equipada un arma, esta debe estar primero en el inventario.


