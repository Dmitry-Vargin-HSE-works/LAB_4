package com.company;

public class Main {
    public static void main(String[] args) {
        String line = "Варгин Дмитрий Александрович 11.05.2001";
        Namer.LineNamer ln = new Namer.LineNamer(line);
        System.out.println("Простое созадние объекта".toUpperCase() + ":\nДанные:" + line + "\n");
        ln.printResult();

        System.out.println("Ввод через файл из папки data:".toUpperCase());
        for (Namer.LineNamer lineNamer : Namer.getPeopleFromFile("people.txt")) {
            lineNamer.printResult();
        }

        System.out.println("Консольный ввод:".toUpperCase());
        for (Namer.LineNamer lineNamer : Namer.getPeopleFromConsole()) {
            lineNamer.printResult();
        }
    }
}
