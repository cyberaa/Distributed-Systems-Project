/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import objectos.Favorito;
import objectos.Ideia;

/**
 *
 * @author Iris
 */
public interface InterfaceFavoritos {
    public boolean inserirFavorito(Favorito favorito);
    public boolean apagarUmFavoritoDeUmUtilizador(int idIdeia,int idUtilizador);
    public boolean apagarOsFavoritoDeUmUtilizador(int idUtilizador);
    public List<Ideia> getTodosIdeiasDosFavoritosDeUmUtilizador(int idUtilizador);
    
}
