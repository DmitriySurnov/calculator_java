import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Calculation {
    private int _id;
    private int _result;
    private int _first;
    private int _sign;
    private int _second;
    private boolean _isDivisionByZero;
    private final List<String> _listComments;

    public String lastComment() {
        return _listComments.getLast();
    }

    public boolean get_isPrimary() {
        return _isPrimary;
    }

    private boolean _isPrimary;

    public Calculation() {
        _result = 0;
        _first = 0;
        _sign = -1;
        _second = 0;
        _isDivisionByZero = false;
        _listComments = new ArrayList<>();
        _isPrimary = true;
        _id = -1;
    }

    public Calculation(ResultSet resultSet) throws SQLException {
        _listComments = new ArrayList<>();
        _isPrimary = false;
        while (resultSet.next()) {
            _id = resultSet.getInt("ID");
            if (isNumber(resultSet.getString("result"))) {
                _result = resultSet.getInt("result");
                _isDivisionByZero = false;
            } else {
                _isDivisionByZero = true;
                _result = 0;
            }
            _first = resultSet.getInt("first");
            _sign = resultSet.getInt("sign");
            _second = resultSet.getInt("second");
        }
    }

    private static boolean isNumber(String stroke) {
        try {
            Integer.parseInt(stroke);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public void set_listComments(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            _listComments.add(resultSet.getString("comment"));
        }
    }

    public void addListComments(String textComment) {
        _listComments.add(textComment);
    }

    public int get_id() {
        return _id;
    }

    public void set_First(int first) {
        this._first = first;
    }

    public void set_Sign(int sign) {
        this._sign = sign;
    }

    public void set_Second(int second) {
        this._second = second;
    }

    public void Continue() {
        _isPrimary = true;
    }

    public void Calc() {
        switch (_sign) {
            case 1: {
                _result = _first + _second;
                break;
            }
            case 2: {
                _result = _first - _second;
                break;
            }
            case 3: {
                _result = _first * _second;
                break;
            }
            case 4: {
                if (_second == 0) {
                    _isDivisionByZero = true;
                } else {
                    _result = _first / _second;
                }
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append(_first).append(" ");
        switch (_sign) {
            case 1: {
                line.append("+");
                break;
            }
            case 2: {
                line.append("-");
                break;
            }
            case 3: {
                line.append("*");
                break;
            }
            case 4: {
                line.append("\\");
                break;
            }
        }
        line.append(" ").append(_second).append("  = ").append(_isDivisionByZero ? "Деление на ноль" : _result);
        return line.toString();
    }

    public String get_result() {
        return _isDivisionByZero ? "Деление на ноль" : String.valueOf(_result);
    }

    public int get_first() {
        return _first;
    }

    public int get_sign() {
        return _sign;
    }

    public int get_second() {
        return _second;
    }

    public boolean is_isDivisionByZero() {
        return _isDivisionByZero;
    }

    public List<String> get_listComments() {
        return _listComments.stream().toList();
    }
}