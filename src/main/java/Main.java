
public class Main {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Frame frame = new MainFrame(new Grafico(),800,620);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Graficacion Universo -j3d");
    }
 
}

