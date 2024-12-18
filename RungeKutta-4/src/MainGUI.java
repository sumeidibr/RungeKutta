import flanagan.integration.DerivFunction;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {

    public static void main(String[] args) {
        // Iniciar a interface gráfica
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI().createAndShowGUI();
            }
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Equação Diferencial - Runge-Kutta");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2)); // Ajustado para incluir os botões de função

        JLabel x0Label = new JLabel("Valor inicial de x0:");
        JTextField x0Field = new JTextField();
        JLabel y0Label = new JLabel("Valor inicial de y0:");
        JTextField y0Field = new JTextField();
        JLabel xnLabel = new JLabel("Valor final de xn:");
        JTextField xnField = new JTextField();
        JLabel hLabel = new JLabel("Tamanho do passo h:");
        JTextField hField = new JTextField();
        JLabel formulaLabel = new JLabel("Fórmula (em termos de x e y):");
        JTextField formulaField = new JTextField("sin(y)"); // Fórmula padrão

        JButton calculateButton = new JButton("Calcular");

        // Tabela para mostrar os resultados
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("x");
        model.addColumn("y");
        model.addColumn("k1");
        model.addColumn("k2");
        model.addColumn("k3");
        model.addColumn("k4");
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 200));

        // Botões para fórmulas específicas
        JButton eulerButton = new JButton("f(x, y) = e^y");
        JButton lnButton = new JButton("f(x, y) = ln(x + y)");
        JButton sinButton = new JButton("f(x, y) = sin(x) + cos(y)");

        // Botões para as novas funções matemáticas
        JButton arcsinButton = new JButton("f(x, y) = arcsin(x)");
        JButton arctanButton = new JButton("f(x, y) = arctan(x)");
        JButton squareButton = new JButton("f(x, y) = x^2");
        JButton sqrtButton = new JButton("f(x, y) = sqrt(x)");
        JButton secButton = new JButton("f(x, y) = sec(x)");
        JButton cscButton = new JButton("f(x, y) = csc(x)");

        panel.add(x0Label);
        panel.add(x0Field);
        panel.add(y0Label);
        panel.add(y0Field);
        panel.add(xnLabel);
        panel.add(xnField);
        panel.add(hLabel);
        panel.add(hField);
        panel.add(formulaLabel);
        panel.add(formulaField);
        panel.add(calculateButton);
        panel.add(scrollPane);
        panel.add(eulerButton);
        panel.add(lnButton);
        panel.add(sinButton);

        // Adicionando os novos botões à interface
        panel.add(arcsinButton);
        panel.add(arctanButton);
        panel.add(squareButton);
        panel.add(sqrtButton);
        panel.add(secButton);
        panel.add(cscButton);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Ação do botão de calcular
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double x0 = Double.parseDouble(x0Field.getText());
                    double y0 = Double.parseDouble(y0Field.getText());
                    double xn = Double.parseDouble(xnField.getText());
                    double h = Double.parseDouble(hField.getText());
                    String formula = formulaField.getText(); // Fórmula fornecida pelo usuário

                    // Criar a função a partir da fórmula
                    DerivFunction derivFunc = createDerivativeFunction(formula);

                    // Limpar a tabela antes de preencher
                    model.setRowCount(0);

                    // Chamada ao método fourthOrder
                    fourthOrder(derivFunc, x0, y0, xn, h, model);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro: Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro: Fórmula inválida. Verifique a sintaxe.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ações dos botões de fórmulas específicas
        eulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formulaField.setText("exp(y)"); // Inserir f(x, y) = e^y
            }
        });

        lnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formulaField.setText("log(x + y)"); // Inserir f(x, y) = ln(x + y)
            }
        });

        sinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formulaField.setText("sin(x) + cos(y)"); // Inserir f(x, y) = sin(x) + cos(y)
            }
        });

        // Ações para os novos botões de funções matemáticas
        arcsinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formulaField.setText("asin(x)"); // Inserir f(x, y) = arcsin(x)
            }
        });

        arctanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formulaField.setText("atan(x)"); // Inserir f(x, y) = arctan(x)
            }
        });

        squareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formulaField.setText("x^2"); // Inserir f(x, y) = x^2
            }
        });

        sqrtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formulaField.setText("sqrt(x)"); // Inserir f(x, y) = sqrt(x)
            }
        });

        secButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formulaField.setText("1/cos(x)"); // Inserir f(x, y) = sec(x)
            }
        });

        cscButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formulaField.setText("1/sin(x)"); // Inserir f(x, y) = csc(x)
            }
        });
    }

    // Criar a função derivada a partir da fórmula fornecida
    public DerivFunction createDerivativeFunction(String formula) {
        return new DerivFunction() {
            @Override
            public double deriv(double x, double y) {
                // Usar a expressão fornecida pelo usuário para calcular a derivada
                try {
                    Expression expression = new ExpressionBuilder(formula)
                            .variables("x", "y")
                            .build()
                            .setVariable("x", x)
                            .setVariable("y", y);
                    return expression.evaluate();
                } catch (Exception e) {
                    throw new IllegalArgumentException("Erro ao avaliar a fórmula", e);
                }
            }
        };
    }

    // Método fourthOrder atualizado para exibir resultados na tabela
    public static void fourthOrder(DerivFunction g, double x0, double y0, double xn, double h, DefaultTableModel model) {
        // Loop de integração usando o método de Runge-Kutta
        double x = x0;
        double y = y0;
        while (x <= xn) {
            double k1 =  g.deriv(x, y);
            double k2 =  g.deriv(x + (h/2), y + (h/2) * k1);
            double k3 = g.deriv(x + (h/2) * h, y + (h/2) * k2);
            double k4 =  g.deriv(x + h, y + h * k3);

            // Adicionar os resultados à tabela
            model.addRow(new Object[]{String.format("%.2f", x), String.format("%.5f", y),
                    String.format("%.5f", k1), String.format("%.5f", k2), String.format("%.5f", k3), String.format("%.5f", k4)});

            // Atualizar y
            y = y + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
            x = x + h;
        }
    }
}
