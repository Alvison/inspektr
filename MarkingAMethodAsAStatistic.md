# Introduction #

# Details #
The Statistic annotation has the following properties:
  * applicationCode (optional) - application code to denote this specific subsection of the system.
  * requiredPrecision (optional -defaults to HOUR) - an array of required levels of precision for this statistic.
  * name - the name of the statistic we are gathering.

# Example #
```
public final class MyStatisticClass {

   @Statistic(name="count method")
   public void count() {
     return;
   }
}
```