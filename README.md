# Purpose of this Repo
* Mainly to self-study the features of Spring ecosystem
* Reinforcing the very fundamental DI and IoC concept to 
* Exploring Aspect Oriented Programming concept to 
* Applying it in the real life project.

# What will be done?
* Deep dive learning into Spring Framework 
* Applying and understanding Design Patterns such as Strategy or Template/Callback pattern
* While exploring a new concept such as Dynamic Proxy, learning test will be created to familiarise the workflow and syntax

# Outcome
1. Eventually, this learning project will be developed into Spring MVC web application
2. Solidifying the knowledge of Spring Framework and OOP


# Spring annotation and XML Configuration Summary

```@Configuration = ApplicationContext.xml
*when DI is done via @Configuration <context:annotation-config /> is not needed anymore. It’s for standard annotations
@ContextConfiguration(classes=*Context.class) = @ContextConfiguration (locations”classpath:/*”)
@ImportResource(“/applicationContext.xml”) = @Configuration + applicationContext.xml
@ImportResource = importing XML DI ApplicationContext.xml
@Import = importing DI Configuration Class

@Bean = <Bean  />
@Resource = @Autowired, @Resource = by name, @Autowired = by type
<tx: annotation-driven /> is for @Transactional or other transaction
To replace <tx:annotation-driven /> we need the below 4
  1.	org.springframework.transaction.interceptor.TransactionInterceptor
  2.	org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor
  3.	org.springframework.transaction.annotation.AnnotationTransactionAttributeSource
  4.	org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator
it might be better to keep <tx:..> with @ImportResource(“/applicationContext.xml”)
but, it can be replaced with @EnableTransactionManagement

@Autowired – Bean Scanning and autowiring
  1.	Apply to setters or field variable
  2.	Then, checks the setter’s input parameter
  3.	“Scan” for the same type in the bean configuration
  4.	If 2 or more of the same type exists, it looks at the bean method name
  5.	if not found the exact match, it will throw an error
when referring to the bean defined in XML, use @Autowired instead of calling @Bean method

@Component & @ComponentScan – Automatic Bean Registration & Automatic Bean Scanner
  1. Declare package/s to be scanned in @Configuration class
    1.1	@ComponentScan(basePackages="com.jun.spring_practice.user.dao")
  2. Apply @Component on a class which will become a bean
  3. Bean Auto Registration is not automatically supported, hence @ComponentScan is needed. Because component scanning is an expensive operation, only specified packages will be scanned

@Components
Public class UserDaoJDBC implements UserDao

*meta annotation is an annotation that is granted to the definition of annotation
To create a meta annotation, to grant a common trait between multiple annotations use @component: Public @interface SnsConnector

@Repository = @Component as a meta annotation + something (for classes like DAO)
@Service = @Component + something (for classes like services)
@Profile 
  1. Gets ignored whether it’s imported using 
  2. @Import or stated in @ContextConfiguration IF not in @ActiveProfiles
@ActiveProfiles : registering profiles during execution, working as some sort of filter

Using nested static member class, it’s easier to see the configuration of AppContext class

@PropertySource : external sources like database properties can be saved in a separate text .properties file. Then passed to “Environment” Type env object which can be injected using @Autowired
@Value: used to get a value through ${placeholder}. This can be used in xml like <property name=”driverClass” value=${db.driverClass} />
This method requires PropertySourcesPlaceholderConfigurer

@Enable* = @Import(value=something.class) + public @interface annotationName
```
