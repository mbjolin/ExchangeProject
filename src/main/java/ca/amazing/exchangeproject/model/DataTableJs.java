package ca.amazing.exchangeproject.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataTableJs {

  private List<Map<String, String>> columns = new LinkedList<>();

  private List<?> data = new LinkedList<>();

  private String primaryKey;

  private String table;

  public List<Map<String, String>> getColumns() {
    return columns;
  }

  public void setColumns(List<Map<String, String>> columns) {
    this.columns = columns;
  }

  public List<?> getData() {
    return data;
  }

  public void setData(List<?> data) {
    this.data = data;
  }

  public String getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(String primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

}
