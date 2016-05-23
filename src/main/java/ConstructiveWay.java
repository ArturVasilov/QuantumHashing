/**
 * @author Artur Vasilov
 */
public class ConstructiveWay {

    public static void main(String[] args) {
        double eps = 0.05;

        for (int i = 2; i < 41; i++) {
            int pow = (int) Math.pow(2, i + 1);
            double l = Math.log(pow) * 2 / (eps * eps);
            int result = (int) Math.round(Math.log(l) / Math.log(2));
            int qubits = result + 1;
            System.out.println(i + " - " + qubits);
        }
    }

}
