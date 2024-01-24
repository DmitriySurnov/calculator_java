import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Calculation _calculation;

    public static void main(String[] args) throws IOException {
        int number = -1;
        do {
            System.out.println("Выберите действие:");
            System.out.println("1-Призвети новый расчет");
            System.out.println("2-Вывести вывести историю");
            System.out.println("0-выход");
            System.out.print("Введите цифру нужного пункта - ");
            try {
                number = enteringNumbers(0, 2);
            } catch (Exception e) {
                System.out.println("Действия с таким номером не существует");
                continue;
            }
            if (number == 1) {
                _calculation = new Calculation();
                calculations();
            }
            if (number == 2) calculationHistory();

        } while (number != 0);
    }

    private static void calculationHistory() throws IOException {
        List<Integer> spisok = new ArrayList<>();
        try (SqLite db = new SqLite()) {
            try {
                ResultSet resultSet = db.ReadDB(-1, 2);
                while (resultSet.next()) {
                    spisok.add(resultSet.getInt("ID"));
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        while (true) {
            System.out.println("Выберите действие:");
            for (Integer integer : spisok) {
                System.out.println("Расчеты № " + integer);
            }
            System.out.println("-1-назад в меню");
            System.out.println("0-выход");
            System.out.print("Введите цифру нужного пункта - ");
            int number;
            try {
                number = enteringNumbers(-1, spisok.size());
            } catch (Exception e) {
                System.out.println("Действия с таким номером не существует");
                continue;
            }
            if (number == -1) break;
            if (number == 0) System.exit(0);
            else {
                calculationHistory(number - 1);
                calculationHistory();
                break;
            }
        }
    }

    private static void calculationHistory(int index) throws IOException {
        try (SqLite db = new SqLite()) {
            try {
                _calculation = new Calculation(db.ReadDB(index, 0));
                _calculation.set_listComments(db.ReadDB(index, 1));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        while (true) {
            int number;
            System.out.println("Расчет");
            System.out.println(_calculation);
            System.out.println("Комментарии к расчету");
            List<String> listComments = _calculation.get_listComments();
            if (listComments.isEmpty()) System.out.println("комментарии нету");
            for (String comment : listComments) {
                System.out.println(comment);
            }
            System.out.println("Выберите действие:");
            if (!_calculation.is_isDivisionByZero()) System.out.println("1-Призвести новый расчет c результатом");
            System.out.println("2-Добавить комментарий");
            System.out.println("3-назад в историю");
            System.out.println("0-выход");
            System.out.print("Введите цифру нужного пункта - ");
            try {
                number = enteringNumbers();
            } catch (NumberFormatException ignored) {
                continue;
            }
            if (number >= 0 && number <= 3) {
                if (_calculation.is_isDivisionByZero() && number == 1) {
                    System.out.println("Введен не существующий пункт меню");
                    continue;
                }
            } else {
                System.out.println("Введен не существующий пункт меню");
            }
            switch (number) {
                case 1: {
                    calculations();
                    number = 3;
                    break;
                }
                case 2: {
                    addComment(index);
                    break;
                }
                case 0:
                    System.exit(0);
            }
            if (number == 3) break;
        }
    }

    private static void addComment(int index) throws IOException {
        System.out.print("Введите комментарий - ");
        String textComment = enteringString();
        try (SqLite db = new SqLite()) {
            try {
                db.WriteDBComments(index, textComment);
                _calculation.set_listComments(textComment);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private static int enteringNumbers(String text) throws IOException {
        do {
            System.out.print("Введите " + text + " число - ");
            try {
                return enteringNumbers();
            } catch (NumberFormatException ignored) {
                System.out.println("Введено не целое число");
            }
        } while (true);
    }

    private static int enteringNumbers(int min, int max) throws Exception {
        int number = enteringNumbers();
        if (!(number >= min && number <= max)) throw new Exception("");
        return number;
    }

    private static void calculations() throws IOException {
        if (_calculation.get_isPrimary()) _calculation.set_first(enteringNumbers("первое"));

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1 Сложение ");
            System.out.println("2 Вычетание ");
            System.out.println("3 Умножение ");
            System.out.println("4 Деление ");
            System.out.print("Введите цифру нужного пункта - ");
            try {
                _calculation.set_sign(enteringNumbers(1, 4));
                break;
            } catch (Exception e) {
                System.out.println("Действия с таким номером не существует");
            }
        }

        _calculation.set_second(enteringNumbers("второе"));

        _calculation.Calc();
        System.out.println(_calculation);

        try (SqLite db = new SqLite()) {
            try {
                db.WriteDBCalculations(_calculation.get_first(), _calculation.get_sign(),
                        _calculation.get_second(), _calculation.get_result());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        int number;
        do {
            System.out.println("Выберите действие:");
            if (!_calculation.is_isDivisionByZero()) System.out.println("1-Призвести новый расчет c результатом");
            System.out.println("2-назад");
            System.out.println("0-выход");
            System.out.print("Введите цифру нужного пункта - ");
            try {
                number = enteringNumbers();
            } catch (NumberFormatException ignored) {
                continue;
            }
            if (number >= 0 && number <= 2) {
                if (_calculation.is_isDivisionByZero() && number == 1) {
                    System.out.println("Введен не существующий пункт меню");
                    continue;
                }
                break;
            } else {
                System.out.println("Введен не существующий пункт меню");
            }
        } while (true);
        if (number == 0) {
            System.exit(0);
        }
        if (number == 1) {
            _calculation.Continue();
            calculations();
        }
    }

    private static String enteringString() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    private static int enteringNumbers() throws IOException {
        return Integer.parseInt(enteringString());
    }
}
// 242