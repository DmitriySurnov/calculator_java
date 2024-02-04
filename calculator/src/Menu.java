import java.io.IOException;
import java.util.List;

public class Menu {
    public static int mainThing() {
        do {
            System.out.println("Выберите действие:");
            System.out.println("1-Призвети новый расчет");
            System.out.println("2-Вывести вывести историю");
            System.out.println("0-выход");
            System.out.print("Введите цифру нужного пункта - ");
            try {
                return Input.number(0, 2);
            } catch (Exception e) {
                System.out.println("Действия с таким номером не существует");
            }
        } while (true);
    }

    public static int sign() {
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1 Сложение ");
            System.out.println("2 Вычетание ");
            System.out.println("3 Умножение ");
            System.out.println("4 Деление ");
            System.out.print("Введите цифру нужного пункта - ");
            try {
                return Input.number(1, 4);
            } catch (Exception e) {
                System.out.println("Действия с таким номером не существует");
            }
        }
    }

    public static int result(final Calculation calculation) {
        System.out.println(calculation);
        do {
            int number;
            System.out.println("Выберите действие:");
            if (!calculation.is_isDivisionByZero()) System.out.println("1-Призвести новый расчет c результатом");
            System.out.println("2-назад");
            System.out.println("0-выход");
            System.out.print("Введите цифру нужного пункта - ");
            try {
                number = Input.number(0, 2);
            } catch (Exception e) {
                System.out.println("Введен не существующий пункт меню");
                continue;
            }
            if (calculation.is_isDivisionByZero() && number == 1) {
                System.out.println("Введен не существующий пункт меню");
                continue;
            }
            return number;
        } while (true);
    }

    public static int history(final List<Integer> spisok) {
        while (true) {
            System.out.println("Выберите действие:");
            for (Integer integer : spisok) {
                System.out.println("Расчеты № " + integer);
            }
            System.out.println("-1-назад в меню");
            System.out.println("0-выход");
            System.out.print("Введите цифру нужного пункта - ");
            try {
                return Input.number(-1, spisok.size());
            } catch (Exception e) {
                System.out.println("Действия с таким номером не существует");
            }
        }
    }

    public static int history(final Calculation calculation) {
        while (true) {
            int number;
            System.out.println("Расчет");
            System.out.println(calculation);
            System.out.println("Комментарии к расчету");
            List<String> listComments = calculation.get_listComments();
            if (listComments.isEmpty()) System.out.println("комментарии нету");
            for (String comment : listComments) {
                System.out.println(comment);
            }
            System.out.println("Выберите действие:");
            if (!calculation.is_isDivisionByZero()) System.out.println("1-Призвести новый расчет c результатом");
            System.out.println("2-Добавить комментарий");
            System.out.println("3-назад в историю");
            System.out.println("0-выход");
            System.out.print("Введите цифру нужного пункта - ");
            try {
                number = Input.number(0, 3);
            } catch (Exception e) {
                System.out.println("Введен не существующий пункт меню");
                continue;
            }
            if (calculation.is_isDivisionByZero() && number == 1) {
                System.out.println("Введен не существующий пункт меню");
                continue;
            }
            return number;
        }
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
