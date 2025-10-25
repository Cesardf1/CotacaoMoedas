import View.CotacaoView;

public class Main {
    public static void main(String[] args) {
        try {
            CotacaoView view = new CotacaoView();
            view.iniciar();
        } catch (Exception e) {
            System.err.println("Erro ao iniciar a aplicação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}