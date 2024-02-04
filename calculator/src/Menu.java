import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    public static int mainThing() {
        List<String> list = new ArrayList<>();
        list.add("Выберите действие:");
        list.add("1-Призвети новый расчет");
        list.add("2-Вывести вывести историю");
        list.add("0-выход");
        list.add("Введите цифру нужного пункта - ");
        return printlList(list,0,2);
    }

    private static int printlList(final List<String> list, int start, int end){
        do {
            for (int i = 0; i < list.size()-1; i++) {
                System.out.println(list.get(i));
            }
            System.out.print(list.getLast());
            try {
                return Input.number(start, end);
            } catch (Exception e) {
                System.out.println("Действия с таким номером не существует");
            }
        } while (true);
    }

    public static int sign() {
        List<String> list = new ArrayList<>();
        list.add("Выберите действие:");
        list.add("1 Сложение ");
        list.add("2 Вычетание ");
        list.add("3 Умножение ");
        list.add("4 Деление ");
        list.add("Введите цифру нужного пункта - ");
        return printlList (list,0,4);
    }

    public static int result(final Calculation calculation) {
        List<String> list = new ArrayList<>();
        list.add(calculation.toString());
        list.add("Выберите действие:");
        if (!calculation.is_isDivisionByZero()) list.add("1-Призвести новый расчет c результатом");
        list.add("2-назад");
        list.add("0-выход");
        list.add("Введите цифру нужного пункта - ");
        int number;
        do {
            number = printlList(list,0,2);
            if (calculation.is_isDivisionByZero() && number == 1) {
                System.out.println("Введен не существующий пункт меню");
                continue;
            }
        }while (false);
        return number;
    }

    public static int history(final List<Integer> spisok) {
        List<String> list = new ArrayList<>();
        list.add("Выберите действие:");
        for (Integer integer : spisok)
            list.add("Расчеты № " + integer);
        list.add("-1-назад в меню");
        list.add("0-выход");
        list.add("Введите цифру нужного пункта - ");
        return printlList(list,-1,spisok.size());
    }

    public static int history(final Calculation calculation) {
        List<String> list = new ArrayList<>();
        list.add("Расчет");
        list.add(calculation.toString());
        list.add("Комментарии к расчету");
        list.addAll(calculation.get_listComments());
        list.add("Выберите действие:");
        if (!calculation.is_isDivisionByZero()) list.add("1-Призвести новый расчет c результатом");
        list.add("2-Добавить комментарий");
        list.add("3-назад в историю");
        list.add("0-выход");
        list.add("Введите цифру нужного пункта - ");
        int number;
        do {
            number = printlList(list,0,3);
            if (calculation.is_isDivisionByZero() && number == 1) {
                System.out.println("Введен не существующий пункт меню");
                continue;
            }
        }while (false);
        return number;
    }

    public static String comment() throws IOException {
        System.out.print("Введите комментарий - ");
        return Input.string();
    }

    public static int number(String text) {
        try {
            do {
                System.out.print("Введите " + text + " число - ");
                try {
                    return Input.number();
                } catch (NumberFormatException ignored) {
                    System.out.println("Введено не целое число");
                }
            } while (true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}