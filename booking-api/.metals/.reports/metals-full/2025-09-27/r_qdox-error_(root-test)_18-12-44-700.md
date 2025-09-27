error id: jar:file:///C:/Users/Gamer/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/com/google/guava/guava/32.1.3-jre/guava-32.1.3-jre-sources.jar!/com/google/common/collect/RowSortedTable.java
jar:file:///C:/Users/Gamer/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/com/google/guava/guava/32.1.3-jre/guava-32.1.3-jre-sources.jar!/com/google/common/collect/RowSortedTable.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[38,19]

error in qdox parser
file content:
```java
offset: 1252
uri: jar:file:///C:/Users/Gamer/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/com/google/guava/guava/32.1.3-jre/guava-32.1.3-jre-sources.jar!/com/google/common/collect/RowSortedTable.java
text:
```scala
/*
 * Copyright (C) 2010 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Interface that extends {@code Table} and whose rows are sorted.
 *
 * <p>The {@link #rowKeySet} method returns a {@link SortedSet} and the {@link #rowMap} method
 * returns a {@link SortedMap}, instead of the {@link Set} and {@link Map} specified by the {@link
 * Table} interface.
 *
 * @author Warren Dukes
 * @since 8.0
 */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public interface Ro@@wSortedTable<
        R extends @Nullable Object, C extends @Nullable Object, V extends @Nullable Object>
    extends Table<R, C, V> {
  /**
   * {@inheritDoc}
   *
   * <p>This method returns a {@link SortedSet}, instead of the {@code Set} specified in the {@link
   * Table} interface.
   */
  @Override
  SortedSet<R> rowKeySet();

  /**
   * {@inheritDoc}
   *
   * <p>This method returns a {@link SortedMap}, instead of the {@code Map} specified in the {@link
   * Table} interface.
   */
  @Override
  SortedMap<R, Map<C, V>> rowMap();
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

QDox parse error in /com/google/common/collect/RowSortedTable.java