package cn.zhh.sso;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Result {

    public Object data;
    public String message;
    public int code;
    public boolean success;
}
