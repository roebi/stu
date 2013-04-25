package com.seitenbau.testing.asserts.fest;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.datatype.XMLGregorianCalendar;

import com.seitenbau.testing.asserts.fest.impl.AtomicIntegerAssert;
import com.seitenbau.testing.asserts.fest.impl.DateAssert;
import com.seitenbau.testing.asserts.fest.impl.FileAssertSB;
import com.seitenbau.testing.asserts.fest.impl.UrlAssert;
import com.seitenbau.testing.asserts.fest.impl.FileAssertSB.ProvidesFile;
import com.seitenbau.testing.asserts.fest.impl.SbStringAssert;
import com.seitenbau.testing.asserts.fest.impl.TicketAssert;
import com.seitenbau.testing.asserts.fest.impl.UriAssert;
import com.seitenbau.testing.util.date.Datum;

/**
 * Internal factory used by {@link Assertions} to create the actual assertion object.
 * This allows you to replace the created assertions objects with your own, but you're still
 * limited to the original api :(. 
 */
public class AssertionsFactory
{
  static AssertionsFactory _factory = new AssertionsFactory();

  public static void registerFactory(AssertionsFactory factory) {
    _factory=factory;
  }

  public static AssertionsFactory get()
  {
    return _factory;
  }

  public FileAssertSB create(ProvidesFile file)
  {
    return new FileAssertSB(file);
  }

  public FileAssertSB create(File file)
  {
    return new FileAssertSB(file);
  }

  public DateAssert create(Calendar datum)
  {
    return new DateAssert(datum);
  }

  public DateAssert create(Datum datum)
  {
    return new DateAssert(datum.asCalendar());
  }

  public DateAssert create(Date datum)
  {
    return new DateAssert(datum);
  }

  public SbStringAssert create(String text)
  {
    return new SbStringAssert(text);
  }

  public UriAssert create(URI uri)
  {
    return new UriAssert(uri);
  }
  
  public UrlAssert create(URL url)
  {
    return new UrlAssert(url);
  }


  public AtomicIntegerAssert create(AtomicInteger number)
  {
    return new AtomicIntegerAssert(number);
  }

  public TicketAssert createTicket(String... tickets)
  {
    return new TicketAssert(tickets);
  }

  public DateAssert create(XMLGregorianCalendar xmlDate)
  {
    if(xmlDate != null)
    {
      return create(xmlDate.toGregorianCalendar());
    }
    else 
    {
      return create((Date) null);
    }
  }

}