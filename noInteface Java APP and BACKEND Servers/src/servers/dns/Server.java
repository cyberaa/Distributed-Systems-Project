/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servers.dns;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Iris
 */
public class Server {
    private String type;
    private String ipaddressRMI;
    private String ipaddressTCP;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    public Server(String type, String addressTCP, String addressRMI, ObjectInputStream ois, ObjectOutputStream oos){
        this.type = type;
        this.ipaddressRMI = addressRMI;
        this.ipaddressTCP = addressTCP;
        this.ois = ois;
        this.oos = oos;
    }

    public String getIpaddressRMI() {
        return ipaddressRMI;
    }

    public void setIpaddressRMI(String ipaddressRMI) {
        this.ipaddressRMI = ipaddressRMI;
    }

    public String getIpaddressTCP() {
        return ipaddressTCP;
    }

    public void setIpaddressTCP(String ipaddressTCP) {
        this.ipaddressTCP = ipaddressTCP;
    }

    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }
    
    @Override
    public String toString(){
        return "Tipo: "+type+"\nTCP Address:"+ipaddressTCP+"\nRMI Address:"+ipaddressRMI;
    }

}
