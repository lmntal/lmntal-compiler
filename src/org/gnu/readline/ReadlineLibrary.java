/**************************************************************************
/* ReadlineLibrary.java -- A typesafe enum class
/*
/* Java Wrapper Copyright (c) 1998-2001 by Bernhard Bablok (mail@bablokb.de)
/*
/* This program is free software; you can redistribute it and/or modify
/* it under the terms of the GNU Library General Public License as published
/* by  the Free Software Foundation; either version 2 of the License or
/* (at your option) any later version.
/*
/* This program is distributed in the hope that it will be useful, but
/* WITHOUT ANY WARRANTY; without even the implied warranty of
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/* GNU Library General Public License for more details.
/*
/* You should have received a copy of the GNU Library General Public License
/* along with this program; see the file COPYING.LIB.  If not, write to
/* the Free Software Foundation Inc., 59 Temple Place - Suite 330,
/* Boston, MA  02111-1307 USA
/**************************************************************************/

package org.gnu.readline;

/**
 This class implements a typesafe enumeration of the backing libraries.

 @version $Revision: 1.1 $
 @author  $Author: LMNtal $
*/

public final class ReadlineLibrary {
  
  /**
     Constant for fallback, pure Java implementation.
  */

  public static final ReadlineLibrary PureJava = 
    new ReadlineLibrary("PureJava");

  /**
     Constant for GNU-Readline implementation.
  */

  public static final ReadlineLibrary GnuReadline = 
    new ReadlineLibrary("JavaReadline");

  /**
     Constant for Editline implementation.
  */

  public static final ReadlineLibrary Editline = 
    new ReadlineLibrary("JavaEditline");

  /**
     Constant for Getline implementation.
  */

  public static final ReadlineLibrary Getline = 
    new ReadlineLibrary("JavaGetline");

  /**
     The name of the backing native library.
  */

  private String iName;

  /**
     Constructor. The constructor is private, so only the predefined
     constants are available.
  */

  private ReadlineLibrary(String name) {
    iName = name;
  }

  /**
     Query name of backing library.

     @return Name of backing library, or "PureJava", in case fallback
     implementation is used.
  */

  public String getName() {
    return iName;
  }

  /**
     Return ReadlineLibrary-object with given name.
     
     @return one of the predefined constants
  */

  public static ReadlineLibrary byName(String name) {
    if (name.equals("GnuReadline"))
      return GnuReadline;
    else if (name.equals("Editline"))
      return Editline;
    else if (name.equals("Getline"))
      return Getline;
    else if (name.equals("PureJava"))
      return PureJava;
    return null;
  }
}

