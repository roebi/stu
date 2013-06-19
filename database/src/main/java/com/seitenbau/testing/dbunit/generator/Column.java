package com.seitenbau.testing.dbunit.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.seitenbau.testing.util.CamelCase;

public class Column
{

  private static final String ID_SUFFIX = "_id";

  private final Table _table;

  private final DataType _dataType;

  private final String _name;

  private final String _javaName;

  private final String _groovyName;

  private final String _description;

  private final Relation _relation;

  private final ColumnMetaData _metaData;

  private final List<Column> _referencedBy;

  Column(Table table, String name, String javaName, String groovyName, String description, DataType dataType,
      Relation relation, Set<String> flags)
  {
    _table = table;
    _name = name;
    _javaName = javaName;
    _groovyName = groovyName;
    _description = description;
    _dataType = dataType;
    _relation = relation;

    _metaData = new ColumnMetaData(flags);

    _referencedBy = new ArrayList<Column>();
    if (relation != null)
    {
      _relation.getColumn()._referencedBy.add(this);
    }
  }

  public Table getTable()
  {
    return _table;
  }

  public String getJavaType()
  {
    return _dataType.getJavaType();
  }

  public String getType()
  {
    return _dataType.getDataType();
  }

  DataType getDataType()
  {
    return _dataType;
  }

  public String getName()
  {
    return _name;
  }

  public String getJavaName()
  {
    return _javaName;
  }

  public String getJavaNameFirstLower()
  {
    return CamelCase.makeFirstLowerCase(getJavaName());
  }

  public String getGroovyName()
  {
    return _groovyName;
  }

  public String getDescription()
  {
    return _description;
  }

  public Relation getRelation()
  {
    return _relation;
  }

  public ColumnMetaData getMetaData()
  {
    return _metaData;
  }

  public List<Column> getReferencedByList()
  {
    return Collections.unmodifiableList(_referencedBy);
  }

  public String getTruncatedReferenceName()
  {
    if (_relation == null || !_groovyName.endsWith(ID_SUFFIX))
    {
      return null;
    }

    final String result = _groovyName.substring(0, _groovyName.length() - ID_SUFFIX.length());
    for (Column column : _table.getColumns())
    {
      if (result.equals(column.getName()))
      {
        // column cannot be truncated
        return null;
      }
    }

    return result;
  }

  public boolean isIdentifier()
  {
    return _metaData.hasFlag(ColumnMetaData.IDENTIFIER);
  }

  public boolean isUnique()
  {
    return _metaData.hasFlag(ColumnMetaData.UNIQUE);
  }

  public boolean isImmutable()
  {
    return _metaData.hasFlag(ColumnMetaData.IMMUTABLE);
  }

  public boolean isNextValueMethodGenerated()
  {
    return _metaData.hasFlag(ColumnMetaData.ADD_NEXT_METHOD);
  }

  public boolean isAutoInvokeValueGeneration()
  {
    return _metaData.hasFlag(ColumnMetaData.AUTO_INVOKE_NEXT);
  }

  public boolean isAutoIncrement()
  {
    return _metaData.hasFlag(ColumnMetaData.AUTO_INCREMENT);
  }

  public boolean isFutureValueSupported()
  {
    return !isUnique() && _relation == null;
  }

}
