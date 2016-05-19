/**
 * @author Artur Vasilov
 */
public class ConstructiveWay {

    public static void main(String[] args) {
        double eps = 0.05;
        double d = eps * eps * Math.log(1 / eps);
        double coef = Math.pow(1 / d, 1.25);

        for (int i = 2; i < 41; i++) {
            double result = coef * Math.pow(1.0 * i, 1.25);
            int qubits = Math.getExponent(result);
            System.out.println(i + " - " + qubits);
        }
    }

}
