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

As a general best practice, we should externalize configuration values for our applications. Besides overriding these properties for different stages, we can use the same technique when testing our applications to, for example, connect to a mocked external system. By overriding “application-integration test.properties”, we can set up different databases just for tests, separate from the production databases.

e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

C - Run the tests in a simplified and light environment, simulating the behavior of an application server, by using @WebMvcTestmode. 
@MockMvc provides support for Spring MVC testing. It encapsulates all web application beans and makes them available for testing, providing an entry point to server-side testing.
The repository component isn't involve (by mocking the dependencies on the service with @MockBean).

D - Start the full web context with @SpringBootTest (with Web Environment enabled). 
The @SpringBootTest annotation tells Spring Boot to look for a main configuration class (one with @SpringBootApplication, for instance) and use that to start a Spring application context. This is an integration test in which several components participate. However, no API client is involved (we use MockMvc). 

E - Similar to D; however, instead of assessing a convenient servletentry point for tests, uses an API client (so request and response un/marshaling will be involved).
Instead of using MockMvc, we use TestRestTemplate to invoke rest endpoints. It's similar to deployment but in the context of tests.

