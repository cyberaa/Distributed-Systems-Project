
package objectos;

import java.util.ArrayList;
import java.util.List;

public class PedidoCompra {
    private List<PropostaCompra> listaPropostasCompras;
    private double valorMelhorOferta;
    private int idCompradorMelhorOferta;
    private PropostaCompra melhorOferta;
    private boolean autorizacaoVenda;

    public PedidoCompra() {
        this.listaPropostasCompras = new ArrayList<>();
    }

    public List<PropostaCompra> getListaPropostasCompras() {
        return listaPropostasCompras;
    }

    public void setListaPropostasCompras(List<PropostaCompra> listaPropostasCompras) {
        this.listaPropostasCompras = listaPropostasCompras;
    }

    public double getValorMelhorOferta() {
        return valorMelhorOferta;
    }

    public void setValorMelhorOferta(double valorMelhorOferta) {
        this.valorMelhorOferta = valorMelhorOferta;
    }

    public boolean isAutorizacaoVenda() {
        return autorizacaoVenda;
    }

    public void setAutorizacaoVenda(boolean autorizacaoVenda) {
        this.autorizacaoVenda = autorizacaoVenda;
    }

    public int getIdCompradorMelhorOferta() {
        return idCompradorMelhorOferta;
    }

    public void setIdCompradorMelhorOferta(int idCompradorMelhorOferta) {
        this.idCompradorMelhorOferta = idCompradorMelhorOferta;
    }

    public PropostaCompra getMelhorOferta() {
        return melhorOferta;
    }

    public void setMelhorOferta(PropostaCompra melhorOferta) {
        this.melhorOferta = melhorOferta;
    }
    
    
}
