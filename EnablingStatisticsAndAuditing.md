# Introduction #
Statistics and Auditing requires functionality present in the Spring 2.5 libraries, thus the configuration information detailed below would go into your Spring configuration file.

# Details #

At its core, Inspektr uses Spring's @AspectJ aspects, so in order to enable both statistics and/or audit trail facilities, define the following element in your Spring configuration file (you would need to include the Spring Aop XML schema declaration: http://static.springframework.org/spring/docs/2.5.x/reference/xsd-config.html#xsd-config-body-schemas-aop) :

```
   <aop:aspectj-autoproxy/> 
```


then,

to enable statistics gathering, define the following bean:

```
   <bean id="statisticManagementAspect" class="org.inspektr.statistics.StatisticManagementAspect">
      <constructor-arg index="0">
         <list>
              ... (list of StatisticManagers)
         </list>
      </constructor-arg>
      <constructor-arg index="1" value="APP_ID" />
   </bean>
```

to enable auditing, define the following bean:

```
   <bean id="auditTrailManagementAspect" class="org.inspektr.audit.AuditTrailManagementAspect">
      <constructor-arg index="0" ref="auditablePrincipalResolver" />
      <constructor-arg index="1">
         <list>
         ... (list of AuditableResourceResolvers)
         </list>
      </constructor-arg>
      <constructor-arg index="2">
         <list>
         ... (list of auditTrailManagers)
      </constructor-arg>
      <constructor-arg index="3" value="APP_ID" />
   </bean>
```