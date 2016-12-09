package com.river.image;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Administrator on 2016/9/9.
 */
public class Test {
    public  static void main(String[] args){
      Runtime runtime = Runtime.getRuntime();

      Set<String> str = new HashSet<>();
      str.add("a");
      str.add("b");
      str.add("a");
      Vector<String> all=new Vector<>();
      all.add("a");
      all.add("b");
      all.add("a");
      Enumeration<String> enu = all.elements();
      while (enu.hasMoreElements())
      {
        System.out.print(enu.nextElement());
      }
      System.out.print("位移运算符："+(4>>2));
  }
}
