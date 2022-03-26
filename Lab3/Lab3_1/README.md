3.1 Employee manager example

a) Identify a couple of examples on the use of AssertJ expressive methods chaining.

(A_EmployeeRepositoryTest)

assertThat( found ).isEqualTo(alex);
assertThat(fromDb).isNull()
assertThat(fromDb.getEmail()).isEqualTo( emp.getEmail());

(B_EmployeeService_UnitTest)

assertThat(found.getName()).isEqualTo(name);
assertThat(doesEmployeeExist).isEqualTo(true);

(D_EmployeeRestControllerIT)
assertThat(found).extracting(Employee::getName).containsOnly("bob");

b) Identify an example in which you mock the behavior of the repository (and avoid involving a database). 

We can find an example in which we mock the behavior of the repository with the test B_EmployeeService_UnitTest. Since the EmployeeService depends on the repository and, yet, we only want to run unit tests on this service, we mock the responses of the repository.

(Function setUp())
Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);

c) What is the difference between standard @Mock and @MockBean?

This annotation is a shorthand for the Mockito.mock(), a method that allows us to create a mock object of a class or an interface. We can then use the mock to stub return values for its methods and verify if they were called.

It's important to note that we should only use it in a test class. Unlike the mock() method, we need to enable Mockito annotations to use this annotation.

On the other hand, we can use the @MockBean to add mock objects to the Spring application context. The mock will replace any existing bean of the same type in the application context.

If no bean of the same type is defined, a new one will be added. This annotation is useful in integration tests where a particular bean, like an external service, needs to be mocked.

d) What is the role of the file “application-integration test.properties”? In which conditions will it be used?

As a general best practice, we should externalize configuration values for our applications. Besides overriding these properties for different stages, we can use the same technique when testing our applications to, for example, connect to a mocked external system.

It acts like the regular application.properties file, but instead of being used by the production version of the application it will be used by the test classes. This allow us to change the database system that will be used in tests and create a database only for tests, for example.

e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

C - O ambiente carregado é mais reduzido

D - Carregar um ambiente de teste MVC (MockMvc), permite interagir diretamente com o ???
Deployment para um ambiente especial de testes

E - Deployment no contexto de testes
Ativar o servidor
TestRestTemplate, não acede ao mvc
Invoca endpoints rest

