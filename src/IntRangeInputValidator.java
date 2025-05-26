public class IntRangeInputValidator extends InputValidator {
    int min, max;
    IntRangeInputValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }
    public static boolean validateIntRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
    public boolean validate(int value) {
        lastValid = validateIntRange(value, min, max);
        return validateIntRange(value, min, max);
    }
}
