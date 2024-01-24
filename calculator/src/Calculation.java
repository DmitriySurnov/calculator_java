import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Calculation {
    private int _result;
    private int _first;
    private int _sign;
    private int _second;
    private boolean _isDivisionByZero;
    private List<String> _listComments;

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
    }

    public Calculation(ResultSet resultSet) throws SQLException {
        _listComments = new ArrayList<>();
        _isPrimary = false;
        while (resultSet.next()) {
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

    public void set_listComments(ResultSet resultSet)throws SQLException{
        while (resultSet.next()) {
            _listComments.add(resultSet.getString("comment"));
        }
    }
    public void set_listComments(String textComment){
            _listComments.add(textComment);
    }

    public void set_first(int _first) {
        this._first = _first;
    }

    public void set_sign(int _sign) {
        this._sign = _sign;
    }

    public void set_second(int _second) {
        this._second = _second;
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
        List<String> list = _listComments.stream().toList();
        return list;
    }
}