package CrFormatter;

public class FormatterOptions {
    String format = "";
    String textColor = "";
    String backgroundColor = "";

    public FormatterOptions(String _format, String _textColor, String _backgroundColor) {
        format = _format;
        textColor = _textColor;
        backgroundColor = _backgroundColor;
    }
    public FormatterOptions(String _format, String _textColor) {
        format = _format;
        textColor = _textColor;
        backgroundColor = "_backgroundColor";
    }
    public FormatterOptions(String _format) {
        format = _format;
        textColor = "";
        backgroundColor = "_backgroundColor";
    }
}
