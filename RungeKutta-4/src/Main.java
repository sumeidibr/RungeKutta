import flanagan.integration.RungeKutta;
import flanagan.integration.DerivFunction;

public class Main {
    public static void main(String[] args) {
        // Função derivativa para dy/dx = y + ln(y * x)
        DerivFunction derivFunc = new DerivFunction() {
            @Override
            public double deriv(double x, double y) {
                return y + Math.log(y * x);  // A equação diferencial y' = y + ln(y * x)
            }
        };

        // Condições iniciais
        double x0 = 1.0;  // Valor inicial de x (x0 = 1)
        double y0 = 0.85831;  // Valor inicial de y (y(1) = 0.85831)
        double xn = 1.4;  // Valor final de x
        double h = 0.1;   // Tamanho do passo

        // Chamada do método fourthOrder para resolver a equação diferencial
        fourthOrder(derivFunc, x0, y0, xn, h);
    }

    // Método fourthOrder que usa o Runge-Kutta para resolver a equação diferencial
    public static void fourthOrder(DerivFunction g, double x0, double y0, double xn, double h) {
        RungeKutta rk = new RungeKutta();
        rk.setInitialValueOfX(x0);   // Configura o valor inicial de x
        rk.setFinalValueOfX(xn);     // Configura o valor final de x
        rk.setInitialValueOfY(y0);   // Configura o valor inicial de y
        rk.setStepSize(h);           // Configura o tamanho do passo

        // Loop de integração usando o método de Runge-Kutta
        double x = x0;
        double y = y0;
        while (x <= xn) {
            // Cálculo dos coeficientes k1, k2, k3, k4
            double k1 = h * g.deriv(x, y);
            double k2 = h * g.deriv(x + 0.5 * h, y + 0.5 * k1);
            double k3 = h * g.deriv(x + 0.5 * h, y + 0.5 * k2);
            double k4 = h * g.deriv(x + h, y + k3);

            // Imprimir os valores de k1, k2, k3, k4
            System.out.printf("x = %.2f, y = %.5f, k1 = %.5f, k2 = %.5f, k3 = %.5f, k4 = %.5f\n", x, y, k1, k2, k3, k4);

            // Atualização de y usando a fórmula do Runge-Kutta de quarta ordem
            y = y + (k1 + 2*k2 + 2*k3 + k4) / 6;
            x = x + h;
        }
    }
}
