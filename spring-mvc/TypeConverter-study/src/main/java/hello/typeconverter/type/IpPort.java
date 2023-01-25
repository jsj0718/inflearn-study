package hello.typeconverter.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// 127.0.0.1:8080

@Getter
@ToString
@EqualsAndHashCode // 참조값이 달라도 필드(ip, port)가 동일하면 같다고 판단 
public class IpPort {

    private String ip;
    private int port;

    public IpPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
}
