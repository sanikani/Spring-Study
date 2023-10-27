package hello.typeconverter.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class IpPort {
    private String id;
    private int port;

    public IpPort(String id, int port) {
        this.id = id;
        this.port = port;
    }
}
