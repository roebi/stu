package com.seitenbau.testing.dbunit.demomodel.dsl;

import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.demomodel.STUDataSet;
import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowBuilder_Persons;

import groovy.lang.Closure;

/**
 * Represents a STU Data Set Scope. Parses the DSL and builds
 * the datasets
 */
public class STUDSL 
{

  private final STUDataSet dataset;

  /**
   * The Jobs table
   */
  final JobsTable jobsTable;
  
  /**
   * The Teams table
   */
  final TeamsTable teamsTable;
  
  /**
   * The Persons table
   */
  final PersonsTable personsTable;
  
  public STUDSL() {
    this(new STUDataSet() 
    {
      @Override
      public void initDataSet() { }
    });
  }
  
  public STUDSL(STUDataSet dataset)
  {
    this.dataset = dataset;
    jobsTable = new JobsTable(this, dataset.table_Jobs);
    teamsTable = new TeamsTable(this, dataset.table_Teams);
    personsTable = new PersonsTable(this, dataset.table_Persons);
  }

  /**
   * Parses and integrates the tables to the data set
   * Supported tables:
   * <ul>
   *   <li>jobsTable</li>
   *   <li>teamsTable</li>
   *   <li>personsTable</li>
   * </ul>
   *  
   * @param table Table data
   */
  public void tables(Closure<?> table)
  {
    setReferencesContext();
    table.setDelegate(this);
    table.setResolveStrategy(Closure.DELEGATE_FIRST);
    table.call();
    unsetReferencesContext();
  }
  
  /**
   * Parses the defined relations and integrates them into the data set
   * @param relations The relations data
   */
  public void relations(Closure<?> relations)
  {
    setReferencesContext();
    relations.call();
    unsetReferencesContext();
  }
  
  private void setReferencesContext()
  {
    JobsRef.setThreadLocalScope(this);
    TeamsRef.setThreadLocalScope(this);
    PersonsRef.setThreadLocalScope(this);
  }
  
  private void unsetReferencesContext()
  {
    JobsRef.unsetThreadLocalScope();
    TeamsRef.unsetThreadLocalScope();
    PersonsRef.unsetThreadLocalScope();
  }
 
  
  /**
   * Replaces all occurrences of a Jobs reference with the represented value
   * @param reference The reference, which can be resolved now
   * @param row The row containing the represented values 
   */
  void replaceJobsRefWithId(JobsRef reference, RowBuilder_Jobs row)
  {
    // Ref: Persons on JobId
    for (RowBuilder_Persons builder : personsTable.getAllRowBuilders()) {
      if (builder.getJobIdRaw() == reference)
      {
        builder.setJobId(row.getId());
      }
    }
    
  }
  
  /**
   * Replaces all occurrences of a Teams reference with the represented value
   * @param reference The reference, which can be resolved now
   * @param row The row containing the represented values 
   */
  void replaceTeamsRefWithId(TeamsRef reference, RowBuilder_Teams row)
  {
    // Ref: Persons on TeamId
    for (RowBuilder_Persons builder : personsTable.getAllRowBuilders()) {
      if (builder.getTeamIdRaw() == reference)
      {
        builder.setTeamId(row.getId());
      }
    }
    
  }
  
  /**
   * Replaces all occurrences of a Persons reference with the represented value
   * @param reference The reference, which can be resolved now
   * @param row The row containing the represented values 
   */
  void replacePersonsRefWithId(PersonsRef reference, RowBuilder_Persons row)
  {
  }


  /**
   * Allows Jobs table data access using references
   * @param ref The reference which represents tha data
   * @return The accessor object 
   */
  public RowBuilder_Jobs ref(JobsRef ref)
  {
    return ref.getBuilder(this);
  }

  /**
   * Allows Teams table data access using references
   * @param ref The reference which represents tha data
   * @return The accessor object 
   */
  public RowBuilder_Teams ref(TeamsRef ref)
  {
    return ref.getBuilder(this);
  }

  /**
   * Allows Persons table data access using references
   * @param ref The reference which represents tha data
   * @return The accessor object 
   */
  public RowBuilder_Persons ref(PersonsRef ref)
  {
    return ref.getBuilder(this);
  }
  
  /**
   * Creates a DbUnit dataset
   * @return The Dbunit dataset
   */
  public IDataSet createDataSet() 
  {
    return dataset.createDBUnitDataSet();
  }
}