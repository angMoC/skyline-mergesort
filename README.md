# Skyline con MergeSort
Este proyecto resuelve el problema del **Skyline** utilizando el algoritmo de **MergeSort**. 

## Contexto
Este proyecto es de carácter académico y fue desarrollado como parte del currículo de aprendizaje de 
algoritmos y estructuras de datos. El objetivo es implementar y analizar el algoritmo **MergeSort** aplicado al 
problema del **Skyline**, probando así las habilidades en la resolución de problemas complejos mediante técnicas de **divide y vencerás**.

## Uso

```bash
java Skyline [-t] [-h] [fichero_entrada] [fichero_salida]
```

-t: Habilita trazas de las llamadas recursivas.  
-h: Muestra la ayuda.  
fichero_entrada: Archivo con los datos de los edificios.  
fichero_salida: Archivo de salida con el skyline generado.  

## Ejemplo de uso
```
java Skyline -t datosEdificios.txt resultadoSkyline.txt
```
### Entrada
Cada línea del archivo de entrada contiene la información de un edificio en el formato:
```
x_izquierda, x_derecha, altura
```
En el ejemplo:
```
2,9,10
3,6,15
5,12,12
13,16,10
13,16,10
15,17,5
```
### Salida

El archivo de salida contiene las coordenadas del skyline en formato (x, altura):
```
(2,10)(3,15)(6,12)(12,0)(13,10)(16,5)(17,0)
```
+ (2, 10): El skyline comienza con el primer edificio a altura 10.
+ (3, 15): El segundo edificio es más alto y cubre parte del primero.
+ (6, 12): El tercer edificio reemplaza al segundo, pero es más bajo.
+ (12, 0): Todos los edificios terminan en x = 12, por lo que la altura vuelve a 0.
+ (13, 10): Los edificios 4 y 5 comienzan, elevando nuevamente el skyline a 10.
+ (16, 5): El último edificio tiene una altura menor de 5 y empieza en x = 16.
+ (17, 0): El último edificio termina en x = 17, y el skyline vuelve a 0.
