# CI2693-Lab3-Arbitrage

Sandibel Soares 17-10614

Para la solución de este laboratorio se usó una modificación del algoritmo de Bellman-Ford de 
forma que el cálculo de los costos de los caminos se hiciera con multiplicaciones y  estos 
costos fueran negativos. De este modo, si el algoritmo reporta un ciclo de costo negativo, 
en el contexto de nuestro problema, significa que se consiguió un ciclo de cambios de monedas 
en donde se va incrementado la ganancia obtenida usando arbitraje.
