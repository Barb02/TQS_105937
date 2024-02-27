# NMec: 105937
# Bárbara Nóbrega Galiza

# 1.2

##Correr testes um a um:

https://www.baeldung.com/maven-run-single-test

# 2.2 e 2.3 (Geocoding)

Inicialmente:

* Sut (Subject of Test): AdressResolver, método findAddressForLocation()
* Mock: interface httpClient

Motivos: Latência e custos de usar logo a api externa pros testes

Depois: teste de integração, usando a api externa de fato. 

`mvn install failsafe:integration-test` para fazer o teste de integração, falha se n tiver net.
