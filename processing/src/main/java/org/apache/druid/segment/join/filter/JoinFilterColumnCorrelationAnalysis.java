/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.druid.segment.join.filter;

import org.apache.druid.math.expr.Expr;

import java.util.List;

/**
 * Represents an analysis of what base table columns, if any, can be correlated with a column that will
 * be filtered on.
 * <p>
 * For example, if we're joining on a base table via the equiconditions (id = j.id AND f(id2) = j.id2),
 * then we can correlate j.id with id (base table column) and j.id2 with f(id2) (a base table expression).
 */
public class JoinFilterColumnCorrelationAnalysis
{
  private final String joinColumn;
  private final List<String> baseColumns;
  private final List<Expr> baseExpressions;

  public JoinFilterColumnCorrelationAnalysis(
      String joinColumn,
      List<String> baseColumns,
      List<Expr> baseExpressions
  )
  {
    this.joinColumn = joinColumn;
    this.baseColumns = baseColumns;
    this.baseExpressions = baseExpressions;
  }

  public String getJoinColumn()
  {
    return joinColumn;
  }

  public List<String> getBaseColumns()
  {
    return baseColumns;
  }

  public List<Expr> getBaseExpressions()
  {
    return baseExpressions;
  }

  public boolean supportsPushDown()
  {
    return !baseColumns.isEmpty() && baseExpressions.isEmpty();
  }
}
