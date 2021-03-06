package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.*;

public class Namer {
    private static final String endingOfMaleMiddleName = "ич";
    private static final String endingOfFemaleMiddleName = "на";

    public enum Sex {
        Male,
        Female,
        Other
    }

    static public class LineNamer {
        private String firstName; // Имя
        private String middleName; // Отчество
        private String secondName; // Фамилия

        private Calendar birthday;
        private Sex sex;

        // Example:
        // Варгин Дмитрий Александрович 11.05.2001
        public LineNamer(String line) {
            String[] buffer = line.split(" ");
            this.firstName = buffer[1];
            this.secondName = buffer[0];
            this.middleName = buffer[2];
            this.sex = Namer.getSexByMNEnd(this.middleName);

            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            try {
                Date tmp = df.parse(buffer[3]);
                this.birthday = Calendar.getInstance();
                this.birthday.setTime(tmp);
            } catch (ParseException e) {
                this.birthday = new GregorianCalendar(
                        1900,
                        Calendar.JANUARY,
                        0
                );
            }
        }

        public String getFirstName() {
            return firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public String getSecondName() {
            return secondName;
        }

        public Sex getSex() {
            return sex;
        }

        public Calendar getBirthday() {
            return birthday;
        }

        public long getAge() {
            Calendar now = new GregorianCalendar();
            if (this.birthday != null) {
                return (now.getTime().getTime() -
                        this.birthday.getTime().getTime())
                        / (1000L * 3600 * 24 * 365);
            }
            return 0;
        }

        public String getSecondNameAndInitials() {
            return String.format(
                    "%s %c. %c.",
                    this.secondName,
                    this.firstName.charAt(0),
                    this.middleName.charAt(0)
            );
        }

        public String getRUSex() {
            return switch (this.sex) {
                case Male -> "Муж.";
                case Female -> "Жен.";
                default -> "Трансформер";
            };
        }

        public void printResult() {
            System.out.println("ФИО: " + getSecondNameAndInitials());
            System.out.println("Пол: " + getRUSex());
            System.out.println("Возраст: " + getAge() + "\n");
        }
    }

    static public String getEndingOfMaleMiddleName() {
        return endingOfMaleMiddleName;
    }

    static public String getEndingOfFemaleMiddleName() {
        return endingOfFemaleMiddleName;
    }

    static public Sex getSexByMNEnd(String middleName) {
        if (middleName.endsWith(Namer.endingOfMaleMiddleName)) {
            return Sex.Male;
        }
        if (middleName.endsWith(Namer.endingOfFemaleMiddleName)) {
            return Sex.Female;
        }
        return Sex.Other;
    }

    static public ArrayList<LineNamer> getPeopleFromConsole() {
        ArrayList<LineNamer> result = new ArrayList<LineNamer>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите количество строчек:");
        int num = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите данные\n[Фамилия] [Имя] [Очество] [Дата рождения]");
        for (int i = 0; i < num; i++) {
            result.add(new LineNamer(scanner.nextLine()));
        }
        scanner.close();
        return result;
    }

    static public ArrayList<LineNamer> getPeopleFromFile(String file_name) {
        ArrayList<LineNamer> result = new ArrayList<LineNamer>();
        File file = new File(System.getProperty("user.dir") +
                "/data/" + file_name);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                result.add(new LineNamer(scanner.nextLine()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
