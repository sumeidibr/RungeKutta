import flanagan.integration.DerivFunction;

public class Main {
    public static void main(String[] args) {
        // Exemplo de função derivativa dada pelo usuário: dy/dx = sin(x)
        DerivFunction derivFunc = new DerivFunction() {
            @Override
            public double deriv(double x, double y) {
                // Retorno da derivada (função da equação diferencial)
                return Math.sin(y);
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
        // Definir a string que representa a equação
        String function = "f(x, y) = sin(x)";  // Aqui a função da derivada é sin(x)

        // Imprimir os valores introduzidos pelo usuário antes do loop
        System.out.println("Valores introduzidos pelo usuário:");
        System.out.printf("x0 = %.2f, y0 = %.5f, xn = %.2f, h = %.2f\n", x0, y0, xn, h);

        // Fórmulas dinâmicas dos coeficientes k1, k2, k3, k4
        System.out.println("Fórmulas dinâmicas para os coeficientes k1, k2, k3, k4:");

        // Imprimir as fórmulas uma vez antes do loop
        System.out.printf("x = %.2f, y = %.5f\n", x0, y0);
        System.out.println("k1 = h * f(x, y) = " + h + " * f(" + x0 + ", " + y0 + ") ");
        System.out.println("k2 = h * f(x + h/2, y + k1/2) = " + h + " * f(" + (x0 + 0.5 * h) + ", " + (y0 + 0.5 * h) + ")");
        System.out.println("k3 = h * f(x + h/2, y + k2/2) = " + h + " * f(" + (x0 + 0.5 * h) + ", " + (y0 + 0.5 * h) + ")");
        System.out.println("k4 = h * f(x + h, y + k3) = " + h + " * f(" + (x0 + h) + ", " + (y0 + h) + ")");
        System.out.println("---------------------------------------");

        // Loop de integração usando o método de Runge-Kutta
        double x = x0;
        double y = y0;
        while (x <= xn) {
            // Cálculo dos coeficientes k1, k2, k3, k4 usando a função dada
            double k1 = h * g.deriv(x, y);
            double k2 = h * g.deriv(x + 0.5 * h, y + 0.5 * k1);
            double k3 = h * g.deriv(x + 0.5 * h, y + 0.5 * k2);
            double k4 = h * g.deriv(x + h, y + k3);

            // Imprimir os valores de k1, k2, k3, k4 para cada passo
            System.out.printf("x = %.2f, y = %.5f, k1 = %.5f, k2 = %.5f, k3 = %.5f, k4 = %.5f\n", x, y, k1, k2, k3, k4);

            // Atualização de y usando a fórmula do Runge-Kutta de quarta ordem
            y = y + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
            x = x + h;
        }
    }
}
