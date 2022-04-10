package pack;

import java.util.Scanner;

import static pack.Currency.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String date = "";
        do {
            System.out.println("Введите дату в формате DD/MM/YYYY: ");
            date = scanner.nextLine();
            if (date.equals("/q")) break;
            RssCourseAnalyzer analyzer = new RssCourseAnalyzer();
            System.out.println(analyzer.courseOnDate(date, new Currency[]{AZN, AUD, USD, EUR, GBP, BYN}));
            System.out.println("Выход из программы - /q");
        } while (true);
    }
}
