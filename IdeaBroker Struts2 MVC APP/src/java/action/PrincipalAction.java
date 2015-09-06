/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

/**
 *
 * @author hevora
 */
public class PrincipalAction extends BaseAction{
    private int notifications;
    
    @Override
    public String execute() throws Exception {
        setPath("/principal");
        
        setNotifications(10);

        return SUCCESS;
    }
    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }
    
    
}
