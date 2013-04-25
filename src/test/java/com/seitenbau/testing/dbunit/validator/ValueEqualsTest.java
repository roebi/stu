package com.seitenbau.testing.dbunit.validator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ValueEqualsTest
{

  /**
   * Test the equals method.
   * 
   * @throws Exception
   */
  @Test
  public void testEquals() throws Exception
  {
    Object expectedValueToEqual = new Object();
    ValueEquals sut = new ValueEquals("MyReplaceId", expectedValueToEqual);
    assertEquals( sut.compareDataSetElementTo(expectedValueToEqual) , 0 );;
  }
  
  @Test(expected=AssertionError.class)
  public void testNotEquals() throws Exception
  {
    Object expectedValueToEqual = new Object();
    ValueEquals sut = new ValueEquals("MyReplaceId", expectedValueToEqual);
    sut.compareDataSetElementTo(new Object());
  }

}
