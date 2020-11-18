package cn.zhh.sso;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class User {

    public Map<String, String> userMap = new HashMap<>();

    public User() {
        this.userMap.put("haohong", "123456");
        this.userMap.put("hxy", "123456");
    }
}
