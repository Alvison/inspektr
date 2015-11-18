# Introduction #
Methods in your application can be marked as Auditable, by using the Auditable annotation, detailed below.


# Details #
The Auditable annotation is what let's the Inspektr library know to audit a particular method.

The @Auditable annotation has the following properties:

  * applicationCode - allows you to override the application code specified at the application level (useful for auditing subsystems)
  * action - a text description of the action
  * successSuffix - value to append to the end of the action  for a successful invocation.
  * failureSuffix- value to append to the end of the action  for a failed invocation.
  * actionResolverClass - the class name of the ActionResolver that can translate the invocation into the appropriate action.
  * resourceResolverClass - the class name of the ResourceResolver that can translate the invocation into the appropriate action.

A simple Auditable method could look like this
```
public final class MyAuditableClass {

   @Auditable(action="getting result", resourceResolverClass=org.inspektr.example.ResourceResolver.class)
   public int getResult(final Object object) {
      return 0;
   }
}
```