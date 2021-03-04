package com.company;

import com.company.Namer;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {
        String line = "Варгин Дмитрий Александрович 11.05.2001";
        Namer.LineNamer lineNamer = new Namer.LineNamer(line);
        System.out.println(lineNamer.getAge());
        System.out.println(lineNamer.getSecondNameAndInitials());
    }
}
