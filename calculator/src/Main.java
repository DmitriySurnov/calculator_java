import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Calculation _calculation;

    public static void main(String[] args) throws IOException {
        while (true){
            int menuItem = Menu.mainThing();
            if (menuItem == 1) {
                _calculation = new Calculation();
                calculations();
            }
            else if (menuItem == 2) calculationHistory();
            else if (menuItem == 0) break;
        }
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
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        int menuItem = Menu.history(spisok);
        if (menuItem == -1) return;
        if (menuItem == 0) System.exit(0);
        else {
            calculationHistory(menuItem - 1);
            calculationHistory();
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
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        while (true) {
            int menuItem = Menu.history(_calculation);
            switch (menuItem) {
                case 1: {
                    calculations();
                    menuItem = 3;
                    break;
                }
                case 2: {
                    addComment();
                    break;
                }
                case 0:
                    System.exit(0);
            }
            if (menuItem == 3) break;
        }
    }

    private static void addComment() throws IOException {
        _calculation.addListComments(Menu.comment());
        SqLite.WriteDB(_calculation,1);
    }

    private static void calculations() {
        if (_calculation.get_isPrimary()) _calculation.set_First(Menu.number("первое"));
        _calculation.set_Sign(Menu.sign());
        _calculation.set_Second(Menu.number("второе"));
        _calculation.Calc();
        SqLite.WriteDB(_calculation,0);
        int menuItem = Menu.result(_calculation);
        if (menuItem == 0)  System.exit(0);
        if (menuItem == 1) {
            _calculation.Continue();
            calculations();
        }
    }
}
// 137