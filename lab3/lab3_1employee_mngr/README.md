# 3.1:

a) 
assertThat(fromDb).isNotNull();
        
assertThat(fromDb.getEmail()).isEqualTo(emp.getEmail());

assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());

assertThat(found).extracting(Employee::getName).containsOnly("bob");

assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");

b) Na classe B_EmployeeService_UnitTest, onde é testado o serviço.  

`@Mock( lenient = true) private EmployeeRepository employeeRepository;`

c) @Mock é uma anotação que faz parte do Mockito, por isso a aplicação SpringBoot não tem conhecimento da sua
existência. Já a anotação @MockBean é parte do SpringBoot, e por isso a aplicação conhece e o têm com um bean em seu applicationContext.

d) Seu papel é fazer o "set up" do que é necessário para que a base de dados real seja usada. Vai ser usado nas classes D e E, onde são feitos testes de integração.

`@TestPropertySource( locations = "application-integrationtest.properties")`

e) O teste C foca em testar o comportamento apenas dos controllers, e por isso não envolve as camadas de serviço nem de repositório, além de não envolver a base de dados. Para isso é feito um mock do serviço a partir do MockBean. No D, é feito um teste de integração, no qual todas as camadas são envolvidas e contexto da aplicação é todo carregado (via a anotação @SpringBootTest). Porém, o cliente http é um mock. Finalmente, no E, é feito também um teste de integração, só que dessa vez a usar de um cliente http real.