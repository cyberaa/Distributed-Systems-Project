/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package action;

/**
 *
 * @author Iris
 */
public class PedidosComprasAction extends BaseAction{
    @Override
    public String execute() throws Exception {
        setPath("/pedidosCompras");
        return SUCCESS;

    }
}
