package bll.validators;

/**
 * Interfata cu o singura metoda abstracta, validate(), care orimeste ca parametru un obiect de tipul generic T
 * @param <T> tip generic
 */
public interface Validator<T> {
    public void validate(T t);
}
