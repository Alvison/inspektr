# Introduction #

Both the auditing and the statistics features can be used independently.  However, regardless of which one you use, there is some common configuration between the two.


# Configuring the Validation Bean Post Processor #
Inspektr utilizes annotations to handle its Inversion of Control dependency validation (i.e. whether you supplied the correct dependencies).  These annotations are checked using a BeanPostProcessor configured in your application's Spring configuration file.  You can add an entry like this:

```
<bean id="validationAnnotationBeanPostProcessor" class="org.inspektr.common.ioc.validation.ValidationAnnotationBeanPostProcessor" />
```

# Configuring the ClientInfo Filter #
Both the auditing and the statistics component require specific information about the client (their IP Address, the IPAddress of the server they are using, etc.).  This information is obtained using a filter and ThreadLocal combination.  Therefore, the following needs to be placed in the web.xml:

Where you've defined the filters:

```
<filter>
   <filter-name>Inspektr ClientInfo Filter</filter-name>
   <filter-class>org.inspektr.common.web.ClientInfoThreadLocalFilter</filter-class>
</filter>
```

You then need to map the filter to the paths you wish to audit/log statistics for:

```
<filter-mapping>
  <filter-name>Inspektr ClientInfo Filter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```