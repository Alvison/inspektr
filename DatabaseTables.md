# Introduction #
While Inspektr provides simple example managers that will either write to the standard output or a log file, any serious enterprise logging and statistics gathering will need to be stored in a database.

# Details #

Inspektr currently requires two tables, one for auditing and one for statistics.  Below, you'll find the basic CREATE table statements:

```
CREATE TABLE COM_STATISTICS
 (
  STAT_SERVER_IP VARCHAR2(15)  NOT NULL,
  STAT_DATE      DATE          NOT NULL,
  APPLIC_CD      VARCHAR2(5)   NOT NULL,
  STAT_PRECISION VARCHAR2(6)   NOT NULL,
  STAT_COUNT     NUMBER        NOT NULL,
  STAT_NAME      VARCHAR2(100) NOT NULL
 )
```
(Composite Key: STAT\_SERVER\_IP, STAT\_DATE, APPLIC\_CD, STAT\_PRECISION, STAT\_NAME)

```
CREATE TABLE COM_AUDIT_TRAIL
 (
  AUD_USER      VARCHAR2(100)  NOT NULL,
  AUD_CLIENT_IP VARCHAR(15)    NOT NULL,
  AUD_SERVER_IP	VARCHAR(15)    NOT NULL,
  AUD_RESOURCE  VARCHAR2(100)  NOT NULL,
  AUD_ACTION    VARCHAR2(100)  NOT NULL,
  APPLIC_CD     VARCHAR2(5)    NOT NULL,
  AUD_DATE      TIMESTAMP      NOT NULL
 )
```