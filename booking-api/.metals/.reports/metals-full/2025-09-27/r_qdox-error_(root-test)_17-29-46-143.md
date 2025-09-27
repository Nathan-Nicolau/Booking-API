error id: jar:file:///C:/Users/Gamer/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/postgresql/postgresql/42.7.3/postgresql-42.7.3-sources.jar!/org/postgresql/core/BaseConnection.java
jar:file:///C:/Users/Gamer/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/postgresql/postgresql/42.7.3/postgresql-42.7.3-sources.jar!/org/postgresql/core/BaseConnection.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[89,62]

error in qdox parser
file content:
```java
offset: 3127
uri: jar:file:///C:/Users/Gamer/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/postgresql/postgresql/42.7.3/postgresql-42.7.3-sources.jar!/org/postgresql/core/BaseConnection.java
text:
```scala
/*
 * Copyright (c) 2003, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.postgresql.core;

import org.postgresql.PGConnection;
import org.postgresql.PGProperty;
import org.postgresql.jdbc.FieldMetadata;
import org.postgresql.jdbc.TimestampUtils;
import org.postgresql.util.LruCache;
import org.postgresql.xml.PGXmlFactoryFactory;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.checkerframework.dataflow.qual.Pure;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Driver-internal connection interface. Application code should not use this interface.
 */
public interface BaseConnection extends PGConnection, Connection {
  /**
   * Cancel the current query executing on this connection.
   *
   * @throws SQLException if something goes wrong.
   */
  @Override
  void cancelQuery() throws SQLException;

  /**
   * Execute a SQL query that returns a single resultset. Never causes a new transaction to be
   * started regardless of the autocommit setting.
   *
   * @param s the query to execute
   * @return the (non-null) returned resultset
   * @throws SQLException if something goes wrong.
   */
  ResultSet execSQLQuery(String s) throws SQLException;

  ResultSet execSQLQuery(String s, int resultSetType, int resultSetConcurrency)
      throws SQLException;

  /**
   * Execute a SQL query that does not return results. Never causes a new transaction to be started
   * regardless of the autocommit setting.
   *
   * @param s the query to execute
   * @throws SQLException if something goes wrong.
   */
  void execSQLUpdate(String s) throws SQLException;

  /**
   * Get the QueryExecutor implementation for this connection.
   *
   * @return the (non-null) executor
   */
  QueryExecutor getQueryExecutor();

  /**
   * Internal protocol for work with physical and logical replication. Physical replication available
   * only since PostgreSQL version 9.1. Logical replication available only since PostgreSQL version 9.4.
   *
   * @return not null replication protocol
   */
  ReplicationProtocol getReplicationProtocol();

  /**
   * <p>Construct and return an appropriate object for the given type and value. This only considers
   * the types registered via {@link org.postgresql.PGConnection#addDataType(String, Class)} and
   * {@link org.postgresql.PGConnection#addDataType(String, String)}.</p>
   *
   * <p>If no class is registered as handling the given type, then a generic
   * {@link org.postgresql.util.PGobject} instance is returned.</p>
   *
   * <p>value or byteValue must be non-null</p>
   * @param type the backend typename
   * @param value the type-specific string representation of the value
   * @param byteValue the type-specific binary representation of the value
   * @return an appropriate object; never null.
   * @throws SQLException if something goes wrong
   */
  Object getObject(String type, @Nullable String value, byte @@@Nullable [] byteValue)
      throws SQLException;

  @Pure
  Encoding getEncoding() throws SQLException;

  TypeInfo getTypeInfo();

  /**
   * <p>Check if we have at least a particular server version.</p>
   *
   * <p>The input version is of the form xxyyzz, matching a PostgreSQL version like xx.yy.zz. So 9.0.12
   * is 90012.</p>
   *
   * @param ver the server version to check, of the form xxyyzz eg 90401
   * @return true if the server version is at least "ver".
   */
  boolean haveMinimumServerVersion(int ver);

  /**
   * <p>Check if we have at least a particular server version.</p>
   *
   * <p>The input version is of the form xxyyzz, matching a PostgreSQL version like xx.yy.zz. So 9.0.12
   * is 90012.</p>
   *
   * @param ver the server version to check
   * @return true if the server version is at least "ver".
   */
  boolean haveMinimumServerVersion(Version ver);

  /**
   * Encode a string using the database's client_encoding (usually UTF8, but can vary on older
   * server versions). This is used when constructing synthetic resultsets (for example, in metadata
   * methods).
   *
   * @param str the string to encode
   * @return an encoded representation of the string
   * @throws SQLException if something goes wrong.
   */
  byte @PolyNull [] encodeString(@PolyNull String str) throws SQLException;

  /**
   * Escapes a string for use as string-literal within an SQL command. The method chooses the
   * applicable escaping rules based on the value of {@link #getStandardConformingStrings()}.
   *
   * @param str a string value
   * @return the escaped representation of the string
   * @throws SQLException if the string contains a {@code \0} character
   */
  String escapeString(String str) throws SQLException;

  /**
   * Returns whether the server treats string-literals according to the SQL standard or if it uses
   * traditional PostgreSQL escaping rules. Versions up to 8.1 always treated backslashes as escape
   * characters in string-literals. Since 8.2, this depends on the value of the
   * {@code standard_conforming_strings} server variable.
   *
   * @return true if the server treats string literals according to the SQL standard
   * @see QueryExecutor#getStandardConformingStrings()
   */
  boolean getStandardConformingStrings();

  // Ew. Quick hack to give access to the connection-specific utils implementation.
  @Deprecated
  TimestampUtils getTimestampUtils();

  // Get the per-connection logger.
  Logger getLogger();

  // Get the bind-string-as-varchar config flag
  boolean getStringVarcharFlag();

  /**
   * Get the current transaction state of this connection.
   *
   * @return current transaction state of this connection
   */
  TransactionState getTransactionState();

  /**
   * Returns true if value for the given oid should be sent using binary transfer. False if value
   * should be sent using text transfer.
   *
   * @param oid The oid to check.
   * @return True for binary transfer, false for text transfer.
   */
  boolean binaryTransferSend(int oid);

  /**
   * Return whether to disable column name sanitation.
   *
   * @return true column sanitizer is disabled
   */
  boolean isColumnSanitiserDisabled();

  /**
   * Schedule a TimerTask for later execution. The task will be scheduled with the shared Timer for
   * this connection.
   *
   * @param timerTask timer task to schedule
   * @param milliSeconds delay in milliseconds
   */
  void addTimerTask(TimerTask timerTask, long milliSeconds);

  /**
   * Invoke purge() on the underlying shared Timer so that internal resources will be released.
   */
  void purgeTimerTasks();

  /**
   * Return metadata cache for given connection.
   *
   * @return metadata cache
   */
  LruCache<FieldMetadata.Key, FieldMetadata> getFieldMetadataCache();

  CachedQuery createQuery(String sql, boolean escapeProcessing, boolean isParameterized,
      String... columnNames)
      throws SQLException;

  /**
   * By default, the connection resets statement cache in case deallocate all/discard all
   * message is observed.
   * This API allows to disable that feature for testing purposes.
   *
   * @param flushCacheOnDeallocate true if statement cache should be reset when "deallocate/discard" message observed
   */
  void setFlushCacheOnDeallocate(boolean flushCacheOnDeallocate);

  /**
   * Indicates if statements to backend should be hinted as read only.
   *
   * @return Indication if hints to backend (such as when transaction begins)
   *         should be read only.
   * @see PGProperty#READ_ONLY_MODE
   */
  boolean hintReadOnly();

  /**
   * Retrieve the factory to instantiate XML processing factories.
   *
   * @return The factory to use to instantiate XML processing factories
   * @throws SQLException if the class cannot be found or instantiated.
   */
  PGXmlFactoryFactory getXmlFactoryFactory() throws SQLException;

  /**
   * Indicates if error details from server used in included in logging and exceptions.
   *
   * @return true if should be included and passed on to other exceptions
   */
  boolean getLogServerErrorDetail();
}

```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:49)
	scala.meta.internal.mtags.MtagsIndexer.index(MtagsIndexer.scala:21)
	scala.meta.internal.mtags.MtagsIndexer.index$(MtagsIndexer.scala:20)
	scala.meta.internal.mtags.JavaMtags.index(JavaMtags.scala:39)
	scala.meta.internal.mtags.Mtags.index(Mtags.scala:101)
	scala.meta.internal.mtags.Mtags.allSymbols(Mtags.scala:21)
	scala.meta.internal.mtags.SymbolIndexBucket.allSymbols(SymbolIndexBucket.scala:289)
	scala.meta.internal.mtags.SymbolIndexBucket.addMtagsSourceFile(SymbolIndexBucket.scala:300)
	scala.meta.internal.mtags.SymbolIndexBucket.$anonfun$query0$1(SymbolIndexBucket.scala:216)
	scala.meta.internal.mtags.SymbolIndexBucket.$anonfun$query0$1$adapted(SymbolIndexBucket.scala:216)
	scala.collection.immutable.Set$Set1.foreach(Set.scala:177)
	scala.meta.internal.mtags.SymbolIndexBucket.query0(SymbolIndexBucket.scala:216)
	scala.meta.internal.mtags.SymbolIndexBucket.query(SymbolIndexBucket.scala:189)
	scala.meta.internal.mtags.OnDemandSymbolIndex.$anonfun$findSymbolDefinition$1(OnDemandSymbolIndex.scala:142)
	scala.collection.immutable.List.flatMap(List.scala:294)
	scala.meta.internal.mtags.OnDemandSymbolIndex.findSymbolDefinition(OnDemandSymbolIndex.scala:142)
	scala.meta.internal.mtags.OnDemandSymbolIndex.definition(OnDemandSymbolIndex.scala:48)
	scala.meta.internal.metals.Docstrings.indexSymbol(Docstrings.scala:138)
	scala.meta.internal.metals.Docstrings.documentation(Docstrings.scala:49)
	scala.meta.internal.metals.MetalsSymbolSearch.documentation(MetalsSymbolSearch.scala:51)
	scala.meta.internal.pc.MetalsGlobal.symbolDocumentation(MetalsGlobal.scala:303)
	scala.meta.internal.pc.CompletionItemResolver.handleSymbol(CompletionItemResolver.scala:35)
	scala.meta.internal.pc.CompletionItemResolver.resolve(CompletionItemResolver.scala:28)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$completionItemResolve$1(ScalaPresentationCompiler.scala:426)
	scala.meta.internal.pc.CompilerAccess.withSharedCompiler(CompilerAccess.scala:148)
	scala.meta.internal.pc.ScalaPresentationCompiler.completionItemResolve(ScalaPresentationCompiler.scala:427)
	scala.meta.internal.metals.Compilers.$anonfun$completionItemResolve$2(Compilers.scala:331)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.metals.Compilers.$anonfun$completionItemResolve$1(Compilers.scala:330)
	scala.Option.flatMap(Option.scala:283)
	scala.meta.internal.metals.Compilers.completionItemResolve(Compilers.scala:329)
	scala.meta.internal.metals.MetalsLspService.$anonfun$completionItemResolve$1(MetalsLspService.scala:1158)
	scala.meta.internal.metals.CancelTokens$.future(CancelTokens.scala:38)
	scala.meta.internal.metals.MetalsLspService.completionItemResolve(MetalsLspService.scala:1156)
	scala.meta.internal.metals.WorkspaceLspService.$anonfun$completionItemResolve$1(WorkspaceLspService.scala:593)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.metals.WorkspaceLspService.completionItemResolve(WorkspaceLspService.scala:593)
	scala.meta.metals.lsp.DelegatingScalaService.completionItemResolve(DelegatingScalaService.scala:134)
	jdk.internal.reflect.GeneratedMethodAccessor13.invoke(Unknown Source)
	java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	java.base/java.lang.reflect.Method.invoke(Method.java:568)
	org.eclipse.lsp4j.jsonrpc.services.GenericEndpoint.lambda$recursiveFindRpcMethods$0(GenericEndpoint.java:65)
	org.eclipse.lsp4j.jsonrpc.services.GenericEndpoint.request(GenericEndpoint.java:128)
	org.eclipse.lsp4j.jsonrpc.RemoteEndpoint.handleRequest(RemoteEndpoint.java:271)
	org.eclipse.lsp4j.jsonrpc.RemoteEndpoint.consume(RemoteEndpoint.java:201)
	org.eclipse.lsp4j.jsonrpc.json.StreamMessageProducer.handleMessage(StreamMessageProducer.java:185)
	org.eclipse.lsp4j.jsonrpc.json.StreamMessageProducer.listen(StreamMessageProducer.java:97)
	org.eclipse.lsp4j.jsonrpc.json.ConcurrentMessageProcessor.run(ConcurrentMessageProcessor.java:114)
	java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:539)
	java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	java.base/java.lang.Thread.run(Thread.java:833)
```
#### Short summary: 

QDox parse error in /org/postgresql/core/BaseConnection.java